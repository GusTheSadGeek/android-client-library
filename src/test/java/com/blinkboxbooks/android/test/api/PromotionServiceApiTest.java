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
import com.blinkboxbooks.android.api.model.BBBPromotion;
import com.blinkboxbooks.android.api.model.BBBPromotionList;
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
public class PromotionServiceApiTest extends TestCase implements TestConstants, BBBApiConstants {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		if(TestConstants.VOLLEY_ENABLED) {
			Context context = new Activity();
			BBBRequestManager.getInstance().initVolleyRequestQueue(context.getApplicationContext());		
		}
		
		BBBRequestFactory.getInstance().setHostDefault(HOST);
		BBBRequestFactory.getInstance().setHostMarketing(HOST_PROMOTION);
	}
	
	/*
	 * tests getting a specific promotion
	 */
	@Test 
	public void testGetSpecifiedPromotion() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetPromotionByIdRequest(PROMOTION_ID, null, null);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostMarketing(), PATH_MARKETING_PROMOTION);
		expectedUrl = String.format(expectedUrl, PROMOTION_ID);
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		promotionResponseHandler.receivedResponse(response);		
	}
	
	/*
	 * tests getting promotions for a specific country
	 */
	@Test 
	public void testGetPromotionsForLocation() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetPromotionsByLocationRequest(LOCATION_ID, null, null);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostMarketing(), PATH_MARKETING_PROMOTIONS, PARAM_LOCATION, String.valueOf(LOCATION_ID));
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		promotionListResponseHandler.receivedResponse(response);		
	}
	
	/*
	 * tests getting a promotion by its name
	 */
	@Test 
	public void testGetPromotionByName() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetPromotionByNameRequest(PROMOTION_NAME, null, null);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostMarketing(), PATH_MARKETING_PROMOTIONS, PARAM_NAME, PROMOTION_NAME);
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		promotionResponseHandler.receivedResponse(response);		
	}
	
	private final BBBBasicResponseHandler<BBBPromotion> promotionResponseHandler = new BBBBasicResponseHandler<BBBPromotion>() {

		public void receivedData(BBBResponse response, BBBPromotion promotion) {
			assertNotNull(promotion); //response should not be null if parsing was successful
			
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);
		}

		public void receivedError(BBBResponse response) {
			
			if(response.getResponseCode() != HttpURLConnection.HTTP_NOT_FOUND) {
				fail("Error: "+response.toString());
			}
		}
	};
	
	private final BBBBasicResponseHandler<BBBPromotionList> promotionListResponseHandler = new BBBBasicResponseHandler<BBBPromotionList>() {

		public void receivedData(BBBResponse response, BBBPromotionList promotionList) {
			assertNotNull(promotionList); //response should not be null if parsing was successful
			
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);
		}

		public void receivedError(BBBResponse response) {
			
			if(response.getResponseCode() != HttpURLConnection.HTTP_NOT_FOUND) {
				fail("Error: "+response.toString());
			}
		}
	};
}
