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

import android.app.Activity;
import android.content.Context;

import com.blinkbox.books.test.MyShadowSystemClock;
import com.blinkboxbooks.android.api.BBBApiConstants;
import com.blinkboxbooks.android.api.model.BBBContributor;
import com.blinkboxbooks.android.api.model.BBBContributorList;
import com.blinkboxbooks.android.api.net.BBBRequest;
import com.blinkboxbooks.android.api.net.BBBRequestFactory;
import com.blinkboxbooks.android.api.net.BBBRequestManager;
import com.blinkboxbooks.android.api.net.BBBResponse;
import com.blinkboxbooks.android.api.net.responsehandler.BBBBasicResponseHandler;
import com.blinkboxbooks.android.test.AccountHelper;
import com.blinkboxbooks.android.test.TestConstants;

import junit.framework.TestCase;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE, shadows = { MyShadowSystemClock.class } )
public class ContributorServiceApiTest extends TestCase implements TestConstants, BBBApiConstants {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		if(TestConstants.VOLLEY_ENABLED) {
			Context context = new Activity();
			BBBRequestManager.getInstance().initVolleyRequestQueue(context.getApplicationContext());		
		}
		
		BBBRequestFactory.getInstance().setHostDefault(HOST);
		BBBRequestFactory.getInstance().setHostCatalogue(HOST_CATALOGUE);
	}
	
	/*
	 * tests getting a list of specific contributors
	 */
	@Test 
	public void testGetSpecifiedContributors() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetContributorsRequest(CONTRIBUTOR_IDS);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostCatalogue(), PATH_CATALOGUE_CONTRIBUTORS);
		expectedUrl = AccountHelper.appendParameters(expectedUrl, PARAM_ID, (Object[])CONTRIBUTOR_IDS);
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		contributorListResponseHandler.receivedResponse(response);		
	}
	
	/*
	 * tests getting a contributor
	 */
	@Test 
	public void testGetContributor() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetContributorRequest(CONTRIBUTOR_ID);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostCatalogue(), PATH_CATALOGUE_CONTRIBUTOR);
		expectedUrl = String.format(expectedUrl, CONTRIBUTOR_ID);
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		contributorResponseHandler.receivedResponse(response);		
	}
	
	private final BBBBasicResponseHandler<BBBContributor> contributorResponseHandler = new BBBBasicResponseHandler<BBBContributor>() {

		public void receivedData(BBBResponse response, BBBContributor contributor) {
			assertNotNull(contributor); //response should not be null if parsing was successful
			
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);
		}

		public void receivedError(BBBResponse response) {
			fail("Error: "+response.toString());
		}
	};
	
	private final BBBBasicResponseHandler<BBBContributorList> contributorListResponseHandler = new BBBBasicResponseHandler<BBBContributorList>() {

		public void receivedData(BBBResponse response, BBBContributorList publisherList) {
			assertNotNull(publisherList); //response should not be null if parsing was successful
			
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);
		}

		public void receivedError(BBBResponse response) {
			fail("Error: "+response.toString());
		}
	};
}