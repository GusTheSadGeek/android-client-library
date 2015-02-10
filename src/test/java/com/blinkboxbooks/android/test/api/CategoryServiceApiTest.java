/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.test.api;

import android.app.Activity;
import android.content.Context;

import com.blinkbox.books.test.MyShadowSystemClock;
import com.blinkboxbooks.android.api.BBBApiConstants;
import com.blinkboxbooks.android.api.model.BBBCategory;
import com.blinkboxbooks.android.api.model.BBBCategoryList;
import com.blinkboxbooks.android.api.net.BBBRequest;
import com.blinkboxbooks.android.api.net.BBBRequestFactory;
import com.blinkboxbooks.android.api.net.BBBRequestManager;
import com.blinkboxbooks.android.api.net.BBBResponse;
import com.blinkboxbooks.android.api.net.responsehandler.BBBBasicResponseHandler;
import com.blinkboxbooks.android.test.AccountHelper;
import com.blinkboxbooks.android.test.TestConstants;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.net.HttpURLConnection;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE, shadows = { MyShadowSystemClock.class } )
public class CategoryServiceApiTest extends TestCase implements TestConstants, BBBApiConstants {

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
	 * tests getting a list of all categories
	 */
	@Test 
	public void testGetAllCategories() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetAllCategoriesRequest(null);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostCatalogue(), PATH_CATALOGUE_CATEGORIES);
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		categoryListHandler.receivedResponse(response);	
	}
	
	/*
	 * tests getting a category by its name
	 */
	@Test 
	public void testGetCategoriesByName() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetCategoryRequest(CATEGORY_NAME);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostCatalogue(), PATH_CATALOGUE_CATEGORIES, PARAM_SLUG, CATEGORY_NAME);
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		categoryListHandler.receivedResponse(response);	
	}
	
	/*
	 * test getting a category by it id
	 */
	@Test 
	public void testGetCategory() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetCategoryWithIdRequest(CATEGORY_ID);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostCatalogue(), PATH_CATALOGUE_CATEGORY);
		expectedUrl = String.format(expectedUrl, CATEGORY_ID);
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		categoryHandler.receivedResponse(response);	
	}
	
	private final BBBBasicResponseHandler<BBBCategory> categoryHandler = new BBBBasicResponseHandler<BBBCategory>() {

		public void receivedData(BBBResponse response, BBBCategory category) {
			assertNotNull(category); //response should not be null if parsing was successful
			
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);
		}

		public void receivedError(BBBResponse response) {
			fail("Error: "+response.toString());
		}
	};
	
	private final BBBBasicResponseHandler<BBBCategoryList> categoryListHandler = new BBBBasicResponseHandler<BBBCategoryList>() {

		public void receivedData(BBBResponse response, BBBCategoryList category) {
			assertNotNull(category); //response should not be null if parsing was successful
			
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);
		}

		public void receivedError(BBBResponse response) {
			fail("Error: "+response.toString());
		}
	};
}