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
import com.blinkboxbooks.android.api.model.BBBBookPriceList;
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
public class BookPriceServiceApiTest extends TestCase implements TestConstants, BBBApiConstants {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		if(TestConstants.VOLLEY_ENABLED) {
			Context context = new Activity();
			BBBRequestManager.getInstance().initVolleyRequestQueue(context.getApplicationContext());		
		}
				
		BBBRequestFactory.getInstance().setHostDefault(HOST);
		BBBRequestFactory.getInstance().setHostCatalogue(HOST_CATALOGUE);
		BBBRequestManager.getInstance().setInterface(AccountHelper.getInstance());
	}
	
	/*
	 * tests getting a list of prices for a book in a country
	 */
	@Test 
	public void testGetPrices() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetPricesRequest(ISBN);
	
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostCatalogue(), PATH_CATALOGUE_PRICES, PARAM_BOOK, ISBN);
		
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		
		bookPriceListHandler.receivedResponse(response);		
	}
	
	private final BBBBasicResponseHandler<BBBBookPriceList> bookPriceListHandler = new BBBBasicResponseHandler<BBBBookPriceList>() {

		public void receivedData(BBBResponse response, BBBBookPriceList bookPriceList) {
			assertNotNull(bookPriceList); //response should not be null if parsing was successful
			
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);
		}

		public void receivedError(BBBResponse response) {
			fail("Error: "+response.toString());
		}
	};
}
