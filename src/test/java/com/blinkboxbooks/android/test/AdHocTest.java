/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.test;

import java.net.HttpURLConnection;

import com.blinkboxbooks.android.api.BBBApiConstants;
import com.blinkboxbooks.android.api.model.BBBBookmark;
import com.blinkboxbooks.android.api.model.BBBBookmarkList;
import com.blinkboxbooks.android.api.model.BBBClientInformationList;
import com.blinkboxbooks.android.api.net.BBBRequest;
import com.blinkboxbooks.android.api.net.BBBRequestFactory;
import com.blinkboxbooks.android.api.net.BBBRequestManager;
import com.blinkboxbooks.android.api.net.BBBResponse;
import com.blinkboxbooks.android.test.api.AuthenticationApiTest;
import com.blinkboxbooks.android.util.BBBTextUtils;
import com.blinkboxbooks.android.util.LogUtils;
import com.google.gson.Gson;

/** Helper class for performing manual tests of different API calls */
public class AdHocTest implements BBBApiConstants, TestConstants {

	private static BBBBookmark[] list;
	
	private static int readingPercentage = 45;
	
	public static void main(String[] args) {
		LogUtils.setLoggingEnabled(true);
//		getLibrary();
//		getMultipleBooks();
//		getCategories();
//		addBookmark();
//		deleteBookmarks();
//		getBookmarks();
//		registerDevice();
//		deleteAllClients();
	}
	
	private static void setup() {
		BBBRequestFactory.getInstance().setHostDefault(HOST);
		BBBRequestManager.getInstance().setInterface(AccountHelper.getInstance());
		
		if(AccountHelper.getInstance().getAccessToken() == null) {
			AuthenticationApiTest test = new AuthenticationApiTest();
			try {
				test.setUp();
				test.testAuthentication();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void getMultipleBooks() {
		BBBRequest request = BBBRequestFactory.getInstance().createGetBooksRequest(null, null, ISBN, ISBN_2);
		BBBRequestManager.getInstance().executeRequestSynchronously(request);
	}
	
	private static void getLibrary() {
		BBBRequest request = BBBRequestFactory.getInstance().createGetAllLibraryItemsRequest(1000l);
		BBBRequestManager.getInstance().executeRequestSynchronously(request);
	}
	
	private static void getCategories() {
		BBBRequest request = BBBRequestFactory.getInstance().createGetAllCategoriesRequest(null);
		
		BBBRequestManager.getInstance().executeRequestSynchronously(request);
	}
	
	private static void deleteAllClients() {
		BBBClientInformationList list = getClients();
		
		for(int i=0; i<list.clients.length; i++) {
			deleteClient(BBBTextUtils.getIdFromGuid(list.clients[i].client_id));
		}
	}
	
	private static void deleteClient(String clientId) {
		BBBRequest request = BBBRequestFactory.getInstance().createUnregisterClientRequest(clientId);
		
		BBBRequestManager.getInstance().executeRequestSynchronously(request);
	}
	
	private static BBBClientInformationList getClients() {
		BBBRequest request = BBBRequestFactory.getInstance().createGetClientsRequest();
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		
		BBBClientInformationList list = new Gson().fromJson(response.getResponseData(), BBBClientInformationList.class);
		
		return list;
	}
	
	private static void addBookmark() {
		readingPercentage++;
		
		BBBRequest request = BBBRequestFactory.getInstance().createAddBookmarkRequest("3124567896542", BOOKMARK_TYPE_LAST_READ_POSITION, USERNAME, "cfi_position", "test bookmark", null, null, null, readingPercentage, "preview");

		BBBRequestManager.getInstance().executeRequestSynchronously(request);
	}
	
	private static void registerDevice() {
		BBBRequest request = BBBRequestFactory.getInstance().createRegisterClientRequest( String.valueOf(Math.random()), String.valueOf(Math.random()),
				String.valueOf(Math.random()), String.valueOf(Math.random()));
		
		BBBRequestManager.getInstance().executeRequestSynchronously(request);
	}
	
	private static void getBookmarks()  {
		BBBRequest request = BBBRequestFactory.getInstance().createGetBookmarksRequest(null, null, null);
				
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);	
		
		if(response.getResponseCode() == HttpURLConnection.HTTP_OK) {
			String json = response.getResponseData();
			BBBBookmarkList bookmarkList = new Gson().fromJson(json, BBBBookmarkList.class);
			list = bookmarkList.bookmarks;
		}
	}
	
	private static void deleteBookmarks() {
		
		if(list == null) {
			return;
		}
		
		for(int i=0; i<list.length; i++) {
			deleteBookmark(list[i].id);
		}
	}
	
	private static void deleteBookmark(String id) {
		BBBRequest request = BBBRequestFactory.getInstance().createDeleteBookmarkRequest(id, USERNAME);
		
		BBBRequestManager.getInstance().executeRequestSynchronously(request);
	}
}