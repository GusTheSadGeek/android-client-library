/*******************************************************************************
  * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.test.api;

import android.app.Activity;
import android.content.Context;

import com.blinkbox.books.test.MyShadowSystemClock;
import com.blinkboxbooks.android.api.BBBApiConstants;
import com.blinkboxbooks.android.api.model.BBBAuthenticationError;
import com.blinkboxbooks.android.api.model.BBBClientInformation;
import com.blinkboxbooks.android.api.model.BBBClientInformationList;
import com.blinkboxbooks.android.api.model.BBBTokenResponse;
import com.blinkboxbooks.android.api.net.BBBRequest;
import com.blinkboxbooks.android.api.net.BBBRequestFactory;
import com.blinkboxbooks.android.api.net.BBBRequestManager;
import com.blinkboxbooks.android.api.net.BBBResponse;
import com.blinkboxbooks.android.api.net.responsehandler.BBBBasicResponseHandler;
import com.blinkboxbooks.android.test.AccountHelper;
import com.blinkboxbooks.android.test.TestConstants;
import com.blinkboxbooks.android.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import junit.framework.TestCase;

import java.net.HttpURLConnection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE, shadows = { MyShadowSystemClock.class } )
public class AuthenticationApiTest extends TestCase implements TestConstants, BBBApiConstants {

	private static final String TAG = AuthenticationApiTest.class.getSimpleName();

	public static long SLEEP_TIME = 5 * 1000;

	@Before
	public void setUp() throws Exception {
		super.setUp();
			
		if(TestConstants.VOLLEY_ENABLED) {
			Context context = new Activity();
			BBBRequestManager.getInstance().initVolleyRequestQueue(context.getApplicationContext());		
		}
		
		BBBRequestFactory.getInstance().setHostAuthentication(AUTHENTICATE_HOST);
		BBBRequestManager.getInstance().setInterface(AccountHelper.getInstance());
	}

	/*
	 * tests authentication. we should get back an object which contains the refresh token and access token
	 */
	@Test 
	public void testAuthentication() throws Exception {
		LogUtils.i(TAG, "testAuthentication");

		BBBRequest request = BBBRequestFactory.getInstance().createAuthenticateRequest(USERNAME, PASSWORD, CLIENT_ID, CLIENT_SECRET);

		String expectedUrl = AccountHelper.constructUrl(AUTHENTICATE_HOST, PATH_OAUTH2_TOKEN);

		assertEquals(expectedUrl, request.getUrl());

		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);

		authenticationHandler.receivedResponse(response);
	}

	@Test 
	public void testRefreshToken() throws Exception {
		LogUtils.i(TAG, "testRefreshToken");

		BBBRequest request = BBBRequestFactory.getInstance().createGetRefreshAuthTokenRequest(AccountHelper.getInstance().getRefreshToken(), CLIENT_ID, CLIENT_SECRET);

		String expectedUrl = AccountHelper.constructUrl(AUTHENTICATE_HOST, PATH_OAUTH2_TOKEN);

		assertEquals(expectedUrl, request.getUrl());

		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);

		authenticationHandler.receivedResponse(response);
	}

	@Test 
	public void testRegistration() throws Exception {
		LogUtils.i(TAG, "testRegistration");

		BBBRequest request = BBBRequestFactory.getInstance().createRegisterAccountRequest(USERNAME, "Android", "User", PASSWORD, true, "1", "2", "3", "5");

        String expectedUrl = AccountHelper.constructUrl(AUTHENTICATE_HOST, PATH_OAUTH2_TOKEN);

        assertEquals(expectedUrl, request.getUrl());

		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);

		registrationHandler.receivedResponse(response);
	}

	@Test 
	public void testGetRegisteredDevices() throws Exception {
		LogUtils.i(TAG, "testGetRegisteredDevices");

		if(AccountHelper.getInstance().getAccessToken() == null) {
			testAuthentication();
		}

		BBBRequest request = BBBRequestFactory.getInstance().createGetClientsRequest();

		String expectedUrl = AccountHelper.constructUrl(AUTHENTICATE_HOST, PATH_CLIENTS);
		assertEquals(expectedUrl, request.getUrl());

		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		clientListHandler.receivedResponse(response);
	}

	/*
	 * register a client. we should get back an object which contains the client id and client secret
	 */
	public void testRegisterClient() throws Exception {
		LogUtils.i(TAG, "testRegisterClient");

		BBBRequest request = BBBRequestFactory.getInstance().createRegisterClientRequest(CLIENT_NAME2, CLIENT_MODEL, CLIENT_BRAND, CLIENT_OS);

        String expectedUrl = AccountHelper.constructUrl(AUTHENTICATE_HOST, PATH_CLIENTS);

        assertEquals(expectedUrl, request.getUrl());

		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);

		addClientHandler.receivedResponse(response);
	}

    public void testUpdateRegisteredClient() throws Exception {
        LogUtils.i(TAG, "testUpdateRegisterClient");

        BBBRequest request = BBBRequestFactory.getInstance().createUpdateRegisteredClientRequest(CLIENT_NAME2, CLIENT_MODEL, CLIENT_BRAND, CLIENT_OS);

        String expectUrl = AccountHelper.constructUrl(AUTHENTICATE_HOST, PATH_CLIENTS);

        assertEquals(expectUrl, request.getUrl());

        BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);

        addClientHandler.receivedResponse(response);
    }

	private final BBBBasicResponseHandler<BBBClientInformationList> clientListHandler = new BBBBasicResponseHandler<BBBClientInformationList>() {

		public void receivedData(BBBResponse response, BBBClientInformationList data) {
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);

			assertNotNull(data);
			assertNotNull(data.clients);
			
			// Unregister all the devices, if there are any
//			for (BBBClientInformation client : data.clients) {
//				String clientId = BBBTextUtils.getIdFromGuid(client.client_id);
//				BBBRequest request = BBBRequestFactory.getInstance().createUnregisterClientRequest(clientId);
//				BBBRequestManager.getInstance().executeRequestSynchronously(request).getResponseData();
//			}
		}

		public void receivedError(BBBResponse response) {
			fail("Error: "+response.toString());
		}
	};

	private final BBBBasicResponseHandler<BBBTokenResponse> authenticationHandler = new BBBBasicResponseHandler<BBBTokenResponse>() {

		public void receivedData(BBBResponse response, BBBTokenResponse data) {
			assertNotNull(response); //response should not be null if parsing was successful
			
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);
			assertNotNull(data.access_token);
			assertTrue(data.expires_in >= 0);
			assertEquals("bearer", data.token_type);

			AccountHelper.getInstance().setRefreshToken(data.refresh_token);
			AccountHelper.getInstance().setAccessToken(data.access_token);
		}

		public void receivedError(BBBResponse response) {
			fail("Error: " + response.toString());
		}
	};

	private final BBBBasicResponseHandler<BBBTokenResponse> registrationHandler = new BBBBasicResponseHandler<BBBTokenResponse>() {

		@Override
		public void receivedData(BBBResponse response, BBBTokenResponse data) {
			fail("Successfully registered - the account " + USERNAME + " should already be registered");
		}

		@Override
		public void receivedError(BBBResponse response) {
			assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.getResponseCode());
			BBBAuthenticationError error = null;
			try {
				error = new Gson().fromJson(response.getResponseData(), BBBAuthenticationError.class);
			} catch (JsonSyntaxException e) {
			}
			assertNotNull(error);
			assertEquals(ERROR_INVALID_REQUEST, error.error);
			assertEquals(ERROR_USERNAME_ALREADY_TAKEN, error.error_reason);
		}
	};

	private final BBBBasicResponseHandler<BBBClientInformation> addClientHandler = new BBBBasicResponseHandler<BBBClientInformation>() {

        public void receivedData(BBBResponse response, BBBClientInformation data) {
            assertNotNull(response); //response should not be null if parsing was successful

            assertEquals(HttpURLConnection.HTTP_OK, response.getResponseCode());
            assertEquals(CLIENT_NAME, data.client_name);
        }

        public void receivedError(BBBResponse response) {
            fail("Error code: " + response.getResponseCode() + " " + response.getResponseData()); //we should not get to here if the test completes as expected
        }
    };
}
