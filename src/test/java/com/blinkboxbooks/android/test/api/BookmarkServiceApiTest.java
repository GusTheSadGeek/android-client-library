/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.test.api;

import android.app.Activity;
import android.content.Context;

import com.blinkbox.books.test.MyShadowSystemClock;
import com.blinkboxbooks.android.api.BBBApiConstants;
import com.blinkboxbooks.android.api.model.BBBBookmark;
import com.blinkboxbooks.android.api.model.BBBBookmarkList;
import com.blinkboxbooks.android.api.model.BBBBusinessErrorsList;
import com.blinkboxbooks.android.api.net.BBBRequest;
import com.blinkboxbooks.android.api.net.BBBRequestFactory;
import com.blinkboxbooks.android.api.net.BBBRequestManager;
import com.blinkboxbooks.android.api.net.BBBResponse;
import com.blinkboxbooks.android.api.net.responsehandler.BBBBasicResponseHandler;
import com.blinkboxbooks.android.test.AccountHelper;
import com.blinkboxbooks.android.test.TestConstants;
import com.blinkboxbooks.android.util.BBBTextUtils;
import com.google.gson.Gson;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.net.HttpURLConnection;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE, shadows = { MyShadowSystemClock.class } )
public class BookmarkServiceApiTest extends TestCase implements TestConstants, BBBApiConstants {

	private static BBBBookmark bookmark;
	
	private static String lastSyncDateTime;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		if(TestConstants.VOLLEY_ENABLED) {
			Context context = new Activity();
			BBBRequestManager.getInstance().initVolleyRequestQueue(context.getApplicationContext());		
		}
				
		BBBRequestFactory.getInstance().setHostDefault(HOST);
		BBBRequestFactory.getInstance().setHostBookmark(HOST_BOOKMARK);
		BBBRequestManager.getInstance().setInterface(AccountHelper.getInstance());
		
		if(AccountHelper.getInstance().getAccessToken() == null) {
			AuthenticationApiTest test = new AuthenticationApiTest();
			test.setUp();
			test.testAuthentication();
		}
	}
	
	/*
	 * Tests getting all the bookmarks for the user 
	 */
	@Test 
	public void testGetBookmarks() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetBookmarksRequest(null, null, null);
		
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostBookmark(), PATH_READING_MY_BOOKMARKS);
		
		assertEquals(expectedUrl, request.getUrl());
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		
		bookmarkListHandler.receivedResponse(response);		
	}
	
	/*
	 * Tests adding a bookmark
	 */
	@Test 
	public void testAddBookmark() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createAddBookmarkRequest(ISBN_2, BOOKMARK_TYPE_LAST_READ_POSITION, USERNAME, "cfi_position"+Math.random(), "test bookmark"+Math.random(), null, null, null, 45, "preview");
		
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostBookmark(), PATH_READING_MY_BOOKMARKS);
		
		assertEquals(expectedUrl, request.getUrl());
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		
		if(response.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			
			if(response.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST && !BBBTextUtils.isEmpty(response.getResponseData())) {
				BBBBusinessErrorsList businessErrorsList = new Gson().fromJson(response.getResponseData(), BBBBusinessErrorsList.class);
				
				if(!businessErrorsList.containsError(ERROR_BOOKMARK_ALREAD_EXISTS)) {
					fail("Error: "+response.toString());
				}
			} else {
				fail("Error: "+response.toString());
			}
		}
	}

	/*
	 * Tests getting all the bookmarks for the user
	 */
	@Test
	public void testGetBookmarksWithSyncDate() throws Exception {
		sleep(2000);

		testGetBookmarks(); // this will set the lastSyncDateTime

		sleep(2000);

		BBBRequest request = BBBRequestFactory.getInstance().createGetBookmarksRequest(null, null, lastSyncDateTime);

		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostBookmark(), PATH_READING_MY_BOOKMARKS, PARAM_LAST_SYNC_DATE_TIME, lastSyncDateTime);

		assertEquals(expectedUrl, request.getUrl());
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);

		BBBBookmarkList bookmarkList = new Gson().fromJson(response.getResponseData(), BBBBookmarkList.class);

		if(bookmarkList.bookmarks != null && bookmarkList.bookmarks.length > 0) {
			bookmark = bookmarkList.bookmarks[0];
		}
	}


	/*
	 * Tests getting all the bookmarks of a particular type given an ISBN
	 */
	@Test
	public void testGetBookmarksWithSyncDateAndISBN() throws Exception {
		sleep(2000);

		testAddBookmark();

		sleep(2000);

		BBBRequest request = BBBRequestFactory.getInstance().createGetBookmarksRequest(ISBN_2, null, lastSyncDateTime, BOOKMARK_TYPE_LAST_READ_POSITION);

		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostBookmark(), PATH_READING_MY_BOOKMARKS, PARAM_BOOK, ISBN_2, PARAM_LAST_SYNC_DATE_TIME, lastSyncDateTime, PARAM_BOOKMARK_TYPE, BOOKMARK_TYPE_LAST_READ_POSITION);

		assertEquals(expectedUrl, request.getUrl());
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);

		BBBBookmarkList bookmarkList = new Gson().fromJson(response.getResponseData(), BBBBookmarkList.class);

		assertNotNull(bookmarkList.bookmarks);
		assertEquals(1, bookmarkList.bookmarks.length);
		assertEquals(ISBN_2, bookmarkList.bookmarks[0].book);
	}


	private void sleep(long time) {
		
		try {
			Thread.sleep(time);
		} catch(InterruptedException e) {}
	}
	
	/*
	 * Tests getting a single bookmark
	 */
	@Test 
	public void testGetBookmark() throws Exception {
		
		if(bookmark == null) {
			return;
		}
		
		BBBRequest request = BBBRequestFactory.getInstance().createGetBookmarkRequest(bookmark.id);
		
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostBookmark(), String.format(PATH_READING_MY_BOOKMARKS_ITEM, bookmark.id));
		
		assertEquals(expectedUrl, request.getUrl());
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		
		bookmarkHandler.receivedResponse(response);		
	}
	
	/*
	 * Tests updating a bookmark
	 */
	@Test 
	public void testUpdateBookmark() throws Exception {
		
		if(bookmark == null) {
			return;
		}
		
		BBBRequest request = BBBRequestFactory.getInstance().createUpdateBookmarkRequest(bookmark.id, "HIGHLIGHT", USERNAME, "updated_CFI", "updated Name", null, null, null, 65, "updated preview");
		
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostBookmark(), String.format(PATH_READING_MY_BOOKMARKS_ITEM, bookmark.id));
		
		assertEquals(expectedUrl, request.getUrl());
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		
		if(response.getResponseCode() != HttpURLConnection.HTTP_OK) {
			fail("Error: "+response.toString());
		}
	}
	
	/*
	 * Tests deleting a bookmark
	 */
	@Test 
	public void testDeleteBookmark() throws Exception {
		
		if(bookmark == null) {
			return;
		}
				
		BBBRequest request = BBBRequestFactory.getInstance().createDeleteBookmarkRequest(bookmark.id, USERNAME);
		
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostBookmark(), String.format(PATH_READING_MY_BOOKMARKS_ITEM, bookmark.id), PARAM_DELETED_BY, USERNAME);
				
		assertEquals(expectedUrl, request.getUrl());
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		
		if(response.getResponseCode() >= HttpURLConnection.HTTP_MULT_CHOICE) {
			fail("Error: "+response.toString());
		}
	}
		
	/*
	 * Response handler for receiving a single bookmark
	 */
	private final BBBBasicResponseHandler<BBBBookmark> bookmarkHandler = new BBBBasicResponseHandler<BBBBookmark>() {

		public void receivedData(BBBResponse response, BBBBookmark bookmarkList) {
			assertNotNull(bookmarkList); //response should not be null if parsing was successful
			
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);
		}

		public void receivedError(BBBResponse response) {
			fail("Error: "+response.toString());
		}
	};
	
	/*
	 * Response handler for receiving a list of books
	 */
	private final BBBBasicResponseHandler<BBBBookmarkList> bookmarkListHandler = new BBBBasicResponseHandler<BBBBookmarkList>() {

		public void receivedData(BBBResponse response, BBBBookmarkList bookmarkList) {
			assertNotNull(bookmarkList); //response should not be null if parsing was successful
			
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);
			
			lastSyncDateTime = bookmarkList.lastSyncDateTime;
			
			if(bookmarkList.bookmarks != null && bookmarkList.bookmarks.length > 0) {				
				bookmark = bookmarkList.bookmarks[0];
			}
		}

		public void receivedError(BBBResponse response) {
			fail("Error: "+response.toString());
		}
	};
}
