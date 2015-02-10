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
import com.blinkboxbooks.android.api.model.BBBBookSearchResponse;
import com.blinkboxbooks.android.api.model.BBBSuggestionList;
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
public class SearchServiceApiTest extends TestCase implements TestConstants, BBBApiConstants {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		if(TestConstants.VOLLEY_ENABLED) {
			Context context = new Activity();
			BBBRequestManager.getInstance().initVolleyRequestQueue(context.getApplicationContext());		
		}
		
		BBBRequestFactory.getInstance().setHostDefault(HOST);
		BBBRequestFactory.getInstance().setHostSearch(HOST_SEARCH);
	}
	
	/*
	 * tests performing a search
	 */
	@Test 
	public void testSearch() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createSearchRequest(QUERY, null, null, null, null);
		
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostSearch(), PATH_SEARCH_BOOKS, PARAM_QUERY, QUERY);
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		searchResponseHandler.receivedResponse(response);
	}
	
	/*
	 * tests getting a list of search suggestions
	 */
	@Test 
	public void testSearchSuggestions() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetSuggestionsRequest(QUERY, LIMIT);
		
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostSearch(), PATH_SEARCH_SUGGESTIONS, PARAM_QUERY, QUERY,
				PARAM_LIMIT, String.valueOf(LIMIT));
		assertEquals(expectedUrl, request.getUrl());
				
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		suggestionsResponseHandler.receivedResponse(response);		
	}
	
	private BBBBasicResponseHandler<BBBBookSearchResponse> searchResponseHandler = new BBBBasicResponseHandler<BBBBookSearchResponse>() {

		public void receivedData(BBBResponse response, BBBBookSearchResponse data) {
			assertNotNull(data); //response should not be null if parsing was successful
			
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);
		}

		public void receivedError(BBBResponse response) {
			fail("Error: "+response.toString());
		}
	};
	
	private BBBBasicResponseHandler<BBBSuggestionList> suggestionsResponseHandler = new BBBBasicResponseHandler<BBBSuggestionList>() {

		public void receivedData(BBBResponse response, BBBSuggestionList data) {
			assertNotNull(data); //response should not be null if parsing was successful
			
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);
		}

		public void receivedError(BBBResponse response) {
			fail("Error: "+response.toString());
		}
	};
}