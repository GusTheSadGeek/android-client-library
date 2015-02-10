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
import com.blinkboxbooks.android.api.model.BBBPublisher;
import com.blinkboxbooks.android.api.model.BBBPublisherList;
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
public class PublisherServiceApiTest extends TestCase implements TestConstants, BBBApiConstants {
	
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
	 * tests getting a list of all publishers
	 */
	@Test 
	public void testGetAllPublishers() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetPublishersRequest(null, null, null, null, (Integer[])null);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostCatalogue(), PATH_CATALOGUE_PUBLISHERS);
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		publisherListResponseHandler.receivedResponse(response);
	}
	
	/*
	 * tests getting a list of specific publishers
	 */
	@Test 
	public void testGetSpecifiedPublishers() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetPublishersRequest(null, null, null, null, PUBLISHER_IDS);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostCatalogue(), PATH_CATALOGUE_PUBLISHERS);
		expectedUrl = AccountHelper.appendParameters(expectedUrl, PARAM_ID, (Object[])PUBLISHER_IDS);
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		publisherListResponseHandler.receivedResponse(response);
	}
	
	/*
	 * tests getting a specific publisher
	 */
	@Test 
	public void testGetPublisherWithId() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetPublisherRequest(PUBLISHER_ID);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostCatalogue(), PATH_CATALOGUE_PUBLISHER);
		expectedUrl = String.format(expectedUrl, PUBLISHER_ID);
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		publisherResponseHandler.receivedResponse(response);
	}
	
	private final BBBBasicResponseHandler<BBBPublisherList> publisherListResponseHandler = new BBBBasicResponseHandler<BBBPublisherList>() {

		public void receivedData(BBBResponse response, BBBPublisherList publisherList) {
			assertNotNull(publisherList); //response should not be null if parsing was successful
			
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);
		}

		public void receivedError(BBBResponse response) {
			fail("Error: "+response.toString());
		}
	};
	
	private final BBBBasicResponseHandler<BBBPublisher> publisherResponseHandler = new BBBBasicResponseHandler<BBBPublisher>() {

		public void receivedData(BBBResponse response, BBBPublisher publisher) {
			assertNotNull(publisher); //response should not be null if parsing was successful
			
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);
		}

		public void receivedError(BBBResponse response) {
			fail("Error: "+response.toString());
		}
	};
}