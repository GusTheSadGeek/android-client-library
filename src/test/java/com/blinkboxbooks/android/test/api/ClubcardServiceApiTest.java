/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.test.api;

import java.net.HttpURLConnection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import junit.framework.TestCase;

import android.app.Activity;
import android.content.Context;

import com.blinkbox.books.test.MyShadowSystemClock;
import com.blinkboxbooks.android.api.BBBApiConstants;
import com.blinkboxbooks.android.api.model.BBBClubcard;
import com.blinkboxbooks.android.api.model.BBBClubcardList;
import com.blinkboxbooks.android.api.net.BBBRequest;
import com.blinkboxbooks.android.api.net.BBBRequestFactory;
import com.blinkboxbooks.android.api.net.BBBRequestManager;
import com.blinkboxbooks.android.api.net.BBBResponse;
import com.blinkboxbooks.android.api.net.responsehandler.BBBBasicResponseHandler;
import com.blinkboxbooks.android.test.AccountHelper;
import com.blinkboxbooks.android.test.TestConstants;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE, shadows = { MyShadowSystemClock.class } )
public class ClubcardServiceApiTest extends TestCase implements TestConstants, BBBApiConstants {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		if(TestConstants.VOLLEY_ENABLED) {
			Context context = new Activity();
			BBBRequestManager.getInstance().initVolleyRequestQueue(context.getApplicationContext());		
		}
		
		BBBRequestFactory.getInstance().setHostDefault(HOST);
		BBBRequestFactory.getInstance().setHostClubcard(HOST_CLUBCARDS);
		BBBRequestManager.getInstance().setInterface(AccountHelper.getInstance());
		
		if(AccountHelper.getInstance().getAccessToken() == null) {
			AuthenticationApiTest test = new AuthenticationApiTest();
			test.setUp();
			test.testAuthentication();
		}
	}

	/*
	 * tests associating a clubcard with a user
	 */
	@Test 
	public void testAddClubcard() {
		BBBRequest request = BBBRequestFactory.getInstance().createAddClubcardRequest(TEST_CLUBCARD);
		
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostClubcards(), PATH_MY_CLUBCARDS);
		
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		
		clubcardHandler.receivedResponse(response);
	}
	
	/*
	 * tests getting a users clubcards
	 */
	@Test 
	public void testGetClubcards() {
		BBBRequest request = BBBRequestFactory.getInstance().createGetClubcardsRequest();
		
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostClubcards(), PATH_MY_CLUBCARDS);
		
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		
		clubcardListHandler.receivedResponse(response);
	}
	
	/*
	 * tests dissaccociating a clubcard from a user
	 */
	@Test 
	public void testDeleteClubcard() {
		BBBClubcard clubcard = AccountHelper.getInstance().getClubcard();
		
		if(clubcard == null || clubcard.id == null) {
			return;
		}
		
		BBBRequest request = BBBRequestFactory.getInstance().createDeleteClubcardRequest(clubcard.id);
		
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostClubcards(), PATH_MY_CLUBCARDS+'/'+clubcard.id);
		
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		
		if(response.getResponseCode() != HttpURLConnection.HTTP_OK) {
			fail("Error: "+response.toString());
		}
	}	
	
	private final BBBBasicResponseHandler<BBBClubcardList> clubcardListHandler = new BBBBasicResponseHandler<BBBClubcardList>() {

		public void receivedData(BBBResponse response, BBBClubcardList data) {
			assertNotNull(data); //response should not be null if parsing was successful
			
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);
			
			if(data.items.length == 0) {
				fail("should have got at least one clubcard");
			} else {
				AccountHelper.getInstance().setClubcard(data.items[0]);
			}
		}
		
		public void receivedError(BBBResponse response) {
			fail("Error: "+response.toString());
		}
	};
	
	private final BBBBasicResponseHandler<BBBClubcard> clubcardHandler = new BBBBasicResponseHandler<BBBClubcard>() {

		public void receivedData(BBBResponse response, BBBClubcard data) {
			assertNotNull(data); //response should not be null if parsing was successful
			
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_CREATED);
			
			AccountHelper.getInstance().setClubcard(data);
		}
		
		public void receivedError(BBBResponse response) {
			
			if(response.getResponseCode() != HttpURLConnection.HTTP_BAD_REQUEST && response.getResponseCode() != HttpURLConnection.HTTP_CONFLICT) {
				fail("Error: "+response.toString());
			}
		}
	};
}
