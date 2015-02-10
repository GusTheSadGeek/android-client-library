/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.test.api;

import android.app.Activity;
import android.content.Context;

import com.blinkbox.books.test.MyShadowSystemClock;
import com.blinkboxbooks.android.api.BBBApiConstants;
import com.blinkboxbooks.android.api.model.BBBEmpty;
import com.blinkboxbooks.android.api.model.BBBLibraryChanges;
import com.blinkboxbooks.android.api.model.BBBLibraryItem;
import com.blinkboxbooks.android.api.model.BBBLibraryItemList;
import com.blinkboxbooks.android.api.model.BBBReadingStatus;
import com.blinkboxbooks.android.api.net.BBBRequest;
import com.blinkboxbooks.android.api.net.BBBRequestFactory;
import com.blinkboxbooks.android.api.net.BBBRequestManager;
import com.blinkboxbooks.android.api.net.BBBResponse;
import com.blinkboxbooks.android.api.net.responsehandler.BBBBasicResponseHandler;
import com.blinkboxbooks.android.test.AccountHelper;
import com.blinkboxbooks.android.test.TestConstants;
import com.blinkboxbooks.android.util.BBBCalendarUtil;

import junit.framework.TestCase;

import java.net.HttpURLConnection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE, shadows = { MyShadowSystemClock.class } )
public class LibraryServiceApiTest extends TestCase implements TestConstants, BBBApiConstants {
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		if(TestConstants.VOLLEY_ENABLED) {
			Context context = new Activity();
			BBBRequestManager.getInstance().initVolleyRequestQueue(context.getApplicationContext());		
		}
		
		BBBRequestFactory.getInstance().setHostDefault(HOST);
		BBBRequestFactory.getInstance().setHostLibrary(HOST_LIBRARY);
		BBBRequestManager.getInstance().setInterface(AccountHelper.getInstance());
		
		if(AccountHelper.getInstance().getAccessToken() == null) {
			AuthenticationApiTest test = new AuthenticationApiTest();
			test.setUp();
			test.testAuthentication();
		}
	}
	
	/*
	 * tests getting a users library
	 */
	@Test 
	public void testGetAllLibraryItems() throws Exception {		
		String lastSyncTime = null;
		BBBRequest request = BBBRequestFactory.getInstance().createGetAllLibraryItemsRequest(lastSyncTime);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostLibrary(), PATH_MY_LIBRARY);
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		libraryChanges.receivedResponse(response);
	}
	
	/*
	 * tests getting changes to a users library since the last sync time
	 */
	@Test 
	public void testGetAllLibraryItemsSinceSyncTime() throws Exception {		
		BBBRequest request = BBBRequestFactory.getInstance().createGetAllLibraryItemsRequest(TestConstants.LAST_SYNC_TIME);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostLibrary(), PATH_MY_LIBRARY, PARAM_LAST_SYNC_DATE_TIME,
				BBBCalendarUtil.formatTime(TestConstants.LAST_SYNC_TIME, BBBCalendarUtil.FORMAT_TIME_STAMP));
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		libraryChanges.receivedResponse(response);
	}
	
	/*
	 * tests getting an individual library item
	 */
	@Test 
	public void testGetIndividualLibraryItem() throws Exception {		
		BBBRequest request = BBBRequestFactory.getInstance().createGetLibraryItemRequest(LIBRARY_ITEM_ID);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostLibrary(), PATH_MY_LIBRARY_ITEM);
		expectedUrl = String.format(expectedUrl, LIBRARY_ITEM_ID);
		
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		libraryItemHandler.receivedResponse(response);
	}
	
	/*
	 * tests updating the status of an individual library item
	 */
	@Test 
	public void testUpdateIndividualLibraryItem() throws Exception {	
		BBBRequest request = BBBRequestFactory.getInstance().createUpdateLibraryItemRequest(LIBRARY_ITEM_ID, BBBReadingStatus.READING);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostLibrary(), PATH_MY_LIBRARY_ITEM);
		expectedUrl = String.format(expectedUrl, LIBRARY_ITEM_ID);
		
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		emptyObjectHandler.receivedResponse(response);
	}
	
	/*
	 * tests getting the users current library  
	 */
	@Test 
	public void testGetCurrentLibrary() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetCurrentLibraryRequest(null);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostLibrary(), PATH_MY_LIBRARY_CURRENT);		
		
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		libraryItemListHandler.receivedResponse(response);
	}

	/*
	 * tests getting the users purchased items
	 */
	@Test 
	public void testGetPurchasedItems() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetCurrentLibraryPurchasedRequest();
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostLibrary(), PATH_MY_LIBRARY_CURRENT_PURCHASED);				
		
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		libraryItemListHandler.receivedResponse(response);
	}
	
	/*
	 * tests getting the available samples
	 */
	@Test 
	public void testGetSamples() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetCurrentLibrarySampledRequest();
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostLibrary(), PATH_MY_LIBRARY_CURRENT_SAMPLED);				
		
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		libraryItemListHandler.receivedResponse(response);
	}
			
	/*
	 * tests getting the users on device library
	 */
	@Test 
	public void testGetDeviceLibrary() throws Exception {	
		BBBRequest request = BBBRequestFactory.getInstance().createGetLibraryOnDeviceRequest();
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostLibrary(), PATH_MY_LIBRARY_ONDEVICE);				
		
		assertEquals(expectedUrl, request.getUrl());
		
//		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
//		libraryItemListHandler.receivedResponse(response);
	}
	
	/*
	 * tests getting the items the user has archived
	 */
	@Test 
	public void testGetArchivedItems() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetArchivedItemsRequest();
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostLibrary(), PATH_MY_LIBRARY_ARCHIVED);
		
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		libraryItemListHandler.receivedResponse(response);
	}
	
	/*
	 * tests getting the items the user has deleted
	 */
	@Test 
	public void testGetDeletedItems() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetDeletedItemsRequest();
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostLibrary(), PATH_MY_LIBRARY_DELETED);
		
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		libraryItemListHandler.receivedResponse(response);
	}
	
	/*
	 * tests adding an item to the archive
	 */
	@Test 
	public void testAddtoArchive() throws Exception {		
		BBBRequest request = BBBRequestFactory.getInstance().createArchiveItemRequest(LIBRARY_ITEM_ID_RESTORE_DELETE);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostLibrary(), PATH_MY_LIBRARY_ARCHIVED);
		
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		emptyObjectHandler.receivedResponse(response);
	}
	
	/*
	 * tests restoring an item from the archive
	 */
	@Test 
	public void testRestoreFromArchive() throws Exception {		
		BBBRequest request = BBBRequestFactory.getInstance().createRestoreFromArchiveRequest(LIBRARY_ITEM_ID_RESTORE_DELETE);
		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostLibrary(), PATH_MY_LIBRARY_CURRENT);
		
		assertEquals(expectedUrl, request.getUrl());
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		emptyObjectHandler.receivedResponse(response);
	}
	
	/*
	 * tests deleting a book. will only work on samples
	 */
	@Test 
	public void testDeleteBook() throws Exception {
//		BBBRequest request = BBBRequestFactory.getInstance().createDeleteLibraryItemRequest(LIBRARY_ITEM_ID_RESTORE_DELETE);
//		String expectedUrl = AccountHelper.constructUrl(BBBRequestFactory.getInstance().getHostLibrary(), PATH_MY_LIBRARY_ITEM);	
//		expectedUrl = String.format(expectedUrl, LIBRARY_ITEM_ID_RESTORE_DELETE);
//		
//		assertEquals(expectedUrl, request.getUrl());
//		
//		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
//		emptyObjectHandler.receivedResponse(response);
	}
	
	private final BBBBasicResponseHandler<BBBLibraryChanges> libraryChanges = new BBBBasicResponseHandler<BBBLibraryChanges>() {

		public void receivedData(BBBResponse response, BBBLibraryChanges data) {
			assertNotNull(data); //response should not be null if parsing was successful
			
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);
		}
		
		public void receivedError(BBBResponse response) {
			fail("Error: "+response.toString());
		}
	};
	
	private final BBBBasicResponseHandler<BBBEmpty> emptyObjectHandler = new BBBBasicResponseHandler<BBBEmpty>() {

		public void receivedData(BBBResponse response, BBBEmpty data) {		
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);
		}

		public void receivedError(BBBResponse response) {
			fail("Error: "+response.toString());
		}
	};
	
	private final BBBBasicResponseHandler<BBBLibraryItemList> libraryItemListHandler = new BBBBasicResponseHandler<BBBLibraryItemList>() {

		public void receivedData(BBBResponse response, BBBLibraryItemList data) {
			assertNotNull(data); //response should not be null if parsing was successful
			
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);
		}

		public void receivedError(BBBResponse response) {
			fail("Error: "+response.toString());
		}
	};
	
	private final BBBBasicResponseHandler<BBBLibraryItem> libraryItemHandler = new BBBBasicResponseHandler<BBBLibraryItem>() {

		public void receivedData(BBBResponse response, BBBLibraryItem data) {
			assertNotNull(data); //response should not be null if parsing was successful
			
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);
		}

		public void receivedError(BBBResponse response) {
			fail("Error: "+response.toString());
		}
	};
}
