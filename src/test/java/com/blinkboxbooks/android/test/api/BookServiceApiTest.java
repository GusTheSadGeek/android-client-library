/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.test.api;

import android.app.Activity;
import android.content.Context;

import com.blinkbox.books.test.MyShadowSystemClock;
import com.blinkboxbooks.android.api.BBBApiConstants;
import com.blinkboxbooks.android.api.model.BBBBookInfo;
import com.blinkboxbooks.android.api.model.BBBBookInfoList;
import com.blinkboxbooks.android.api.model.BBBSynopsis;
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
public class BookServiceApiTest extends TestCase implements TestConstants, BBBApiConstants {
	
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
	 * tests getting a book based on its id
	 */
	@Test 
	public void testGetBookWithId() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetBookRequest(BOOK_ID);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostCatalogue(), PATH_CATALOGUE_BOOK);
		expectedUrl = String.format(expectedUrl, BOOK_ID);
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		bookInfoHandler.receivedResponse(response);
	}
	
	/*
	 * tests getting more than one book at a time
	 */
	@Test 
	public void testGetMultipleBooks() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetBooksRequest(null, null, ISBN, ISBN_2);
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		bookInfoListHandler.receivedResponse(response);
	}
	
	/*
	 * tests getting a list of books by query with different offset and count parameters
	 */
	@Test 
	public void testGetBooksWithQuery() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createSearchBooksRequest(QUERY, OFFSET, COUNT);		
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostCatalogue(), PATH_CATALOGUE_BOOKS, PARAM_QUERY, QUERY, PARAM_OFFSET, String.valueOf(OFFSET), 
				PARAM_COUNT, String.valueOf(COUNT));
		assertEquals(expectedUrl, request.getUrl());
	
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		bookInfoListHandler.receivedResponse(response);
				
		request = BBBRequestFactory.getInstance().createSearchBooksRequest(QUERY, null, null);			
		expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostCatalogue(), PATH_CATALOGUE_BOOKS, PARAM_QUERY, QUERY);
		assertEquals(expectedUrl, request.getUrl());
		
		response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		bookInfoListHandler.receivedResponse(response);
				
		request = BBBRequestFactory.getInstance().createSearchBooksRequest(null, null, null);	
		expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostCatalogue(), PATH_CATALOGUE_BOOKS);		
		assertEquals(expectedUrl, request.getUrl());
		
		response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		bookInfoListHandler.receivedResponse(response);
	}

	/*
	 * tests getting a list of books for a category location
	 */
	@Test 
	public void testGetBooksForCategoryLocation() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetBooksForCategoryLocationRequest(CATEGORY_LOCATION, OFFSET, COUNT, null, null, null, null);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostCatalogue(), PATH_CATALOGUE_BOOKS, PARAM_CATEGORY_LOCATION, String.valueOf(CATEGORY_LOCATION),PARAM_OFFSET, String.valueOf(OFFSET), 
				PARAM_COUNT, String.valueOf(COUNT));
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		bookInfoListHandler.receivedResponse(response);
		
		request = BBBRequestFactory.getInstance().createGetBooksForCategoryLocationRequest(CATEGORY_LOCATION, null, null, null, null, null, null);
		expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostCatalogue(), PATH_CATALOGUE_BOOKS, PARAM_CATEGORY_LOCATION, String.valueOf(CATEGORY_LOCATION));
		assertEquals(expectedUrl, request.getUrl());
		
		response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		bookInfoListHandler.receivedResponse(response);
	}
	
	/*
	 * tests getting a list of books for a contributor
	 */
	@Test 
	public void testGetBooksForContributor() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetBooksForContributorIdRequest(CONTRIBUTOR_ID, OFFSET, COUNT, null, null, null, null);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostCatalogue(), PATH_CATALOGUE_BOOKS, PARAM_CONTRIBUTOR, String.valueOf(CONTRIBUTOR_ID),PARAM_OFFSET, String.valueOf(OFFSET), 
				PARAM_COUNT, String.valueOf(COUNT));
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		bookInfoListHandler.receivedResponse(response);

		request = BBBRequestFactory.getInstance().createGetBooksForContributorIdRequest(CONTRIBUTOR_ID, null, null, null, null, null, null);
		expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostCatalogue(), PATH_CATALOGUE_BOOKS, PARAM_CONTRIBUTOR, String.valueOf(CONTRIBUTOR_ID));
		assertEquals(expectedUrl, request.getUrl());
		
		response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		bookInfoListHandler.receivedResponse(response);
	}
	
	/*
	 * tests getting a list of books for a publisher
	 */
	@Test 
	public void testGetBooksForPublisher() throws Exception {		
		BBBRequest request = BBBRequestFactory.getInstance().createGetBooksForPublisherIdRequest(PUBLISHER_ID, OFFSET, COUNT);	
		
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostCatalogue(), PATH_CATALOGUE_BOOKS, PARAM_PUBLISHER, String.valueOf(PUBLISHER_ID),PARAM_OFFSET, String.valueOf(OFFSET), 
				PARAM_COUNT, String.valueOf(COUNT));
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		bookInfoListHandler.receivedResponse(response);
		
		request = BBBRequestFactory.getInstance().createGetBooksForPublisherIdRequest(PUBLISHER_ID, null, null);	
		expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostCatalogue(), PATH_CATALOGUE_BOOKS, PARAM_PUBLISHER, String.valueOf(PUBLISHER_ID));
		assertEquals(expectedUrl, request.getUrl());
		
		response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		bookInfoListHandler.receivedResponse(response);
	}
	
	/*
	 * tests getting a list of books for a category
	 */
	@Test 
	public void testGetBooksForCategory() throws Exception {		
		BBBRequest request = BBBRequestFactory.getInstance().createGetBooksForCategoryIdRequest(CATEGORY_ID, OFFSET, COUNT, null, null, null, null);
		
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostCatalogue(), PATH_CATALOGUE_BOOKS, PARAM_CATEGORY, String.valueOf(CATEGORY_ID),PARAM_OFFSET, String.valueOf(OFFSET), 
				PARAM_COUNT, String.valueOf(COUNT));
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		bookInfoListHandler.receivedResponse(response);
		
		request = BBBRequestFactory.getInstance().createGetBooksForCategoryIdRequest(CATEGORY_ID, null, null, null, null, null, null);
		expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostCatalogue(), PATH_CATALOGUE_BOOKS, PARAM_CATEGORY, String.valueOf(CATEGORY_ID));
		assertEquals(expectedUrl, request.getUrl());
		
		response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		bookInfoListHandler.receivedResponse(response);
	}
	
	/*
	 * tests getting a synopsis for a book
	 */
	@Test 
	public void testGetSynopsis() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetSynopsisRequest(BOOK_ID);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostCatalogue(), PATH_CATALOGUE_BOOK_SYNOPSIS);
		expectedUrl = String.format(expectedUrl, BOOK_ID);
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		synopsisHandler.receivedResponse(response);
	}
	
	/*
	 * tests getting a list of related books for a book
	 */
	@Test 
	public void testGetRelatedBooks() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetRelatedBooksRequest(BOOK_ID, OFFSET, COUNT);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostCatalogue(), PATH_CATALOGUE_BOOK_RELATED);
		expectedUrl = String.format(expectedUrl, BOOK_ID);
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		bookInfoListHandler.receivedResponse(response);
	}
	
	private final BBBBasicResponseHandler<BBBBookInfo> bookInfoHandler = new BBBBasicResponseHandler<BBBBookInfo>() {

		public void receivedData(BBBResponse response, BBBBookInfo bookInfo) {
			assertNotNull(bookInfo); //response should not be null if parsing was successful
			
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);
		}

		public void receivedError(BBBResponse response) {
			fail("Error: "+response.toString());
		}
	};
		
	private final BBBBasicResponseHandler<BBBBookInfoList> bookInfoListHandler = new BBBBasicResponseHandler<BBBBookInfoList>() {

		public void receivedData(BBBResponse response, BBBBookInfoList bookInfoList) {
			assertNotNull(bookInfoList); //response should not be null if parsing was successful
			
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);
		}

		public void receivedError(BBBResponse response) {
			fail("Error: "+response.toString());
		}
	};
		
	private final BBBBasicResponseHandler<BBBSynopsis> synopsisHandler = new BBBBasicResponseHandler<BBBSynopsis>() {

		public void receivedData(BBBResponse response, BBBSynopsis synopsis) {
			assertNotNull(synopsis); //response should not be null if parsing was successful
			
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);
		}

		public void receivedError(BBBResponse response) {
			fail("Error: "+response.toString());
		}
	};
}