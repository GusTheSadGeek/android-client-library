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
import com.blinkboxbooks.android.api.model.BBBBasket;
import com.blinkboxbooks.android.api.model.BBBBasketItem;
import com.blinkboxbooks.android.api.net.BBBRequest;
import com.blinkboxbooks.android.api.net.BBBRequestFactory;
import com.blinkboxbooks.android.api.net.BBBRequestManager;
import com.blinkboxbooks.android.api.net.BBBResponse;
import com.blinkboxbooks.android.api.net.responsehandler.BBBBasicResponseHandler;
import com.blinkboxbooks.android.test.AccountHelper;
import com.blinkboxbooks.android.test.TestConstants;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE, shadows = { MyShadowSystemClock.class } )
public class BasketServiceApiTest extends TestCase implements TestConstants, BBBApiConstants {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		if(TestConstants.VOLLEY_ENABLED) {
			Context context = new Activity();
			BBBRequestManager.getInstance().initVolleyRequestQueue(context.getApplicationContext());		
		}
		
		BBBRequestFactory.getInstance().setHostDefault(HOST);
		BBBRequestFactory.getInstance().setHostBasket(HOST_BASKET);
		BBBRequestManager.getInstance().setInterface(AccountHelper.getInstance());
		
		if(AccountHelper.getInstance().getAccessToken() == null) {
			AuthenticationApiTest test = new AuthenticationApiTest();
			test.setUp();
			test.testAuthentication();
		}
	}

	/* Test adding item to basket */
	@Test 
	public void testAddItemToBasket() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createAddBasketItemRequest(BOOK_BASKET_ITEM_ISBN);
		
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostBasket(), PATH_MY_BASKETS_ITEMS);
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		
		if(response.getResponseCode() != HttpURLConnection.HTTP_OK) {
			fail("Error: "+response.toString());
		}
	}
	
	/* Test getting the users basket */
	@Test 
	public void testGetBasket() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetBasketRequest();
		
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostBasket(), PATH_MY_BASKETS);
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		basketHandler.receivedResponse(response);
	}
	
	/* Test getting a single basket item */
	@Test 
	public void testGetBasketItem() throws Exception {
		BBBBasket basket = AccountHelper.getInstance().getBasket();
		
		if(basket == null || basket.items == null || basket.items.length < 1) {
			return;
		}
		
		String id = basket.items[0].id;
		
		BBBRequest request = BBBRequestFactory.getInstance().createGetBasketItemRequest(id);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostBasket(), String.format(PATH_MY_BASKETS_ITEMS_, id) );
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		basketItemHandler.receivedResponse(response);
	}
	
	/* Test deleting a basket item */
	@Test 
	public void testDeleteBasketItem() throws Exception {
		BBBBasket basket = AccountHelper.getInstance().getBasket();
		
		if(basket == null || basket.items == null || basket.items.length < 1) {
			return;
		}
		
		String id = basket.items[0].id;
		
		BBBRequest request = BBBRequestFactory.getInstance().createDeleteBasketItemRequest(id);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostBasket(), String.format(PATH_MY_BASKETS_ITEMS_, id) );
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		
		if(response.getResponseCode() != HttpURLConnection.HTTP_OK) {
			fail("Error: "+response.toString());
		}
	}
	
	/* Test clearing the whole basket */
	@Test 
	public void testClearBasket() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createClearBasketRequest();
		
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostBasket(), PATH_MY_BASKETS);
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		
		if(response.getResponseCode() != HttpURLConnection.HTTP_OK) {
			fail("Error: "+response.toString());
		}
	}
	
	private final BBBBasicResponseHandler<BBBBasketItem> basketItemHandler = new BBBBasicResponseHandler<BBBBasketItem>() {

		public void receivedData(BBBResponse response, BBBBasketItem item) {
			
			if(item == null) {
				fail("Basket item was null: "+response.toString());
			} 
		}

		public void receivedError(BBBResponse response) {
			fail("Error: "+response.toString());
		}
	};
	
	private final BBBBasicResponseHandler<BBBBasket> basketHandler = new BBBBasicResponseHandler<BBBBasket>() {

		public void receivedData(BBBResponse response, BBBBasket basket) {
			
			if(basket == null) { 
				fail("Basket was null: "+response.toString());
			} else {
				AccountHelper.getInstance().setBasket(basket);
			}
		}

		@Override
		public void receivedError(BBBResponse response) {
			fail("Error: "+response.toString());
		}
	};
}