/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.net;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.blinkboxbooks.android.api.BBBApiConstants;
import com.blinkboxbooks.android.api.model.BBBAuthenticationError;
import com.blinkboxbooks.android.api.model.BBBTokenResponse;
import com.blinkboxbooks.android.api.net.responsehandler.BBBResponseHandler;
import com.blinkboxbooks.android.util.BBBTextUtils;
import com.blinkboxbooks.android.util.IOUtils;
import com.blinkboxbooks.android.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.ConcurrentModificationException;

/**
 * Class responsible for executing requests and making sure the response gets routed to the correct handler
 */
public class BBBRequestManager implements BBBApiConstants {

	private static final String TAG = BBBRequestManager.class.getName();

	private static BBBRequestManager sInstance;

	/**
	 * Singleton getter
	 *
	 * @return the BBBRequestManager singleton object
	 */
	public static BBBRequestManager getInstance() {

		if (sInstance == null) {
			sInstance = new BBBRequestManager();
		}

		return sInstance;
	}

	private static final int CONNECTION_TIMEOUT = 60 * 1000;

	private static final int RETRY_DELAY = 1000;

	private static final int MAX_RETRIES = 2;

	private final HashMap<String, BBBResponseHandler> mResponseHandlers;

	private BBBRequestManagerInterface mInterface;

	private RequestQueue mQueue;

	private Long mArtificialLatency;

	private String mUserAgent;
	
	private BBBRequestManager() {
		mResponseHandlers = new HashMap<String, BBBResponseHandler>();
	}
	
	public void setUserAgent(String userAgent) {
		 mUserAgent = userAgent;
	}

	/**
	 * Creates a Volley request queue. This will cause all Requests to be executed via the Volley library.
	 *
	 * @param context
	 */
	public void initVolleyRequestQueue(Context context) {
		mQueue = Volley.newRequestQueue(context);
	}

	/**
	 * Sets the callback object to receive notifications from BBBRequestManager
	 *
	 * @param theInterface the object you want to receive callbacks
	 */
	public void setInterface(BBBRequestManagerInterface theInterface) {
		this.mInterface = theInterface;
	}

	/**
	 * Set this to add an artificial latency to every request. Useful for testing progress dialogs on Wifi
	 *
	 * @param artificialLatency the latency in milliseconds
	 */
	public void setArtificialLatency(Long artificialLatency) {
		mArtificialLatency = artificialLatency;
	}

	/**
	 * Adds a BBBResponseHandler to a map of BBBResponseHandler objects.
	 *
	 * @param responseHandlerId the identifier for this listener. A matching id must be used in addRequest() if you wish this listener to handle the response for that request
	 * @param listener          the listener to be set for this responseHandlerId
	 */
	synchronized public void addResponseHandler(String responseHandlerId, BBBResponseHandler listener) {
		mResponseHandlers.put(responseHandlerId, listener);
	}

	/**
	 * Removes a BBBResponseHandler from the list. If your BBBResponseHandler is an Activity, you must always call this method in the Activities onDestroy() method to ensure it is
	 * properly garbage collected.
	 *
	 * @param responseHandlerId the id of the listener to be removed
	 */
	synchronized public void removeResponseHandler(String responseHandlerId) {
		mResponseHandlers.remove(responseHandlerId);
	}

	/**
	 * Executes a {@link BBBRequest} and forwards the response to the correct BBBResponseHandler
	 *
	 * @param responseHandlerId the id of the request to be executed. Must match an id to a previously added response handler.
	 * @param request           the {@link BBBRequest} we want to execute
	 */
	public void executeRequest(String responseHandlerId, BBBRequest request) {
		ExecuteRequestTask task = new ExecuteRequestTask(request, responseHandlerId);
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
	}

	/**
	 * Executes a {@link BBBRequest} and uses the given BBBResponseHandler to deal with it. This method is preferable when you know there is no chance of your listener being
	 * destroyed as you don't have to add t
	 *
	 * @param request the {@link BBBRequest} we want to execute
	 * @param handler the handler we want to use to handle the response
	 */
	public void executeRequest(BBBRequest request, BBBResponseHandler handler) {
		ExecuteRequestTask task = new ExecuteRequestTask(request, handler);
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
	}

	/*
	 * Executes a request via volley if enabled else falls back to HttpUrlConnection
	 */
	private BBBResponse executeRequest(BBBRequest request) {

		if (mQueue == null) {
			return executeHttpURLConnectionRequest(request);
		} else {
			return executeVolleyRequest(request);
		}
	}

	/**
	 * Executes a BBBRequest on the same thread as the caller
	 *
	 * @param request the BBBRequest object
	 * @return the BBBBResponse object
	 */
	public BBBResponse executeRequestSynchronously(BBBRequest request) {

		if (mArtificialLatency != null && mArtificialLatency > 0) {

			try {
				Thread.sleep(mArtificialLatency);
			} catch (InterruptedException e) {
			}
		}

		if (request.requiresAuthentication()) {
			String accessToken = mInterface.getAccessToken();

			if (!BBBTextUtils.isEmpty(accessToken)) {
				request.setHeader(BBBApiConstants.HEADER_AUTHORIZATION, PARAM_BEARER_ + new String(accessToken));
			} else {
				return new BBBResponse(request, "Could not get auth token in BBBRequestManager", HttpURLConnection.HTTP_UNAUTHORIZED, null);
			}
		}

		BBBResponse response = executeRequest(request);

		boolean retryRequest = false;

		// if this happened the access token has become invalid, probably. need to get a new one with the refresh token
		if (response.getResponseCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
			LogUtils.e(TAG, "401 error received.");

			try {
				String fieldValue = response.getAuthenticateHeaderField(BBBApiConstants.PARAM_ERROR_REASON);

				if (BBBApiConstants.ERROR_UNVERIFIED_IDENTITY.equals(fieldValue)) {
					LogUtils.e(TAG, "unverified_identity error received. must login again.");
					mInterface.elevationRequired();
					return response;
				}

			} catch (Exception e) {
				LogUtils.e(TAG, e.getMessage(), e);
			}

			retryRequest = true;
		} else if (response.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {

			try {
				BBBAuthenticationError error = new Gson().fromJson(response.getResponseData(), BBBAuthenticationError.class);

				if (BBBApiConstants.ERROR_INVALID_CLIENT.equals(error.error)) {
					LogUtils.e(TAG, "invalid_client error received.");
					mInterface.clientInvalidated();

					return response;
				}

			} catch (Exception e) {
				LogUtils.e(TAG, e.getMessage(), e);
			}
		}

		int retries = 0;

		while (retryRequest) {
			retries++;

			if (retries > MAX_RETRIES) {
				continue;
			}

			try {
				Thread.sleep(RETRY_DELAY);
			} catch (Exception e) {
			}

			String refreshToken = mInterface.getRefreshToken();

			if (BBBTextUtils.isEmpty(refreshToken)) {
				LogUtils.e(TAG, "Could not re-authenticate. Did not have refresh token.");
				return response;
			} else {
				LogUtils.e(TAG, "Attimpting to re-authenticate with refresh token");
			}

			String clientId = mInterface.getClientId();
			String clientSecret = mInterface.getClientSecret();

			BBBRequest req = BBBRequestFactory.getInstance().createGetRefreshAuthTokenRequest(mInterface.getRefreshToken(), clientId, clientSecret);
			BBBResponse res = executeRequest(req);

			if (res.getResponseCode() == HttpURLConnection.HTTP_OK) {
				String json = res.getResponseData();

				if (json != null) {

					try {
						BBBTokenResponse authenticationResponse = new Gson().fromJson(json, BBBTokenResponse.class);
						String accessToken = authenticationResponse.access_token;

						if (!BBBTextUtils.isEmpty(accessToken)) {
							LogUtils.e(TAG, "Got new auth token. Re-trying request.");

							mInterface.setAccessToken(accessToken);

							retryRequest = false;

							request.setHeader(BBBApiConstants.HEADER_AUTHORIZATION, PARAM_BEARER_ + new String(accessToken));

							response = executeRequest(request);
						} else {
							LogUtils.e(TAG, "Failed to get new auth token");
						}
					} catch (Exception e) {
						LogUtils.d(TAG, e.getMessage(), e);
					}
				}
			} else if (res.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
				LogUtils.e(TAG, "400 bad request received.");

				try {
					BBBAuthenticationError error = new Gson().fromJson(res.getResponseData(), BBBAuthenticationError.class);

					if (error != null && BBBApiConstants.ERROR_INVALID_GRANT.equals(error.error) || BBBApiConstants.ERROR_INVALID_CLIENT.equals(error.error)) {
						LogUtils.e(TAG, "invalid_grant|invalid_client error received.");
						mInterface.clientInvalidated();
					}
				} catch (JsonParseException e) {
					LogUtils.e(TAG, e.getMessage(), e);
				}
			}
		}

		return response;
	}

	/**
	 * Executes a request using the Volley framework
	 *
	 * @param request the BBBRequest object
	 * @return the BBBResponse object
	 */
	private BBBResponse executeVolleyRequest(BBBRequest request) {
		int method = Request.Method.GET;

		switch (request.getRequestMethod()) {
			case DELETE:
				method = Request.Method.DELETE;
				break;
			case GET:
				method = Request.Method.GET;
				break;
			case POST:
				method = Request.Method.POST;
				break;
			case PUT:
				method = Request.Method.PUT;
				break;
			default:
				method = Request.Method.GET;
				break;
		}

		RequestFuture<BBBResponse> future = RequestFuture.newFuture();

		LogUtils.i(TAG, request.toString());
		LogUtils.curlLog(request, null);

		Map<String, String> headers = request.getHeaders();

		if (headers == null) {
			headers = new HashMap<String, String>();
		}

		headers.put("Connection", "Keep-Alive");

		BBBVolleyRequest req = new BBBVolleyRequest(request, method, request.getUrl(), headers, request.getPostData(), future, future);
		future.setRequest(req);

		mQueue.add(req);

		try {
			BBBResponse response = future.get(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS); // this will block
			LogUtils.d(TAG, response.toString());
			return response;
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), e);
		}

		return new BBBResponse(request, BBBApiConstants.ERROR_CONNECTION_FAILED);
	}

	/**
	 * Synchronously executes a {@link BBBRequest}. Use if you want to handle your own threading.
	 *
	 * @param request the {@link BBBRequest} we want to execute
	 * @return the The {@link BBBResponse} object object
	 */
	public BBBResponse executeHttpURLConnectionRequest(BBBRequest request) {
		HttpURLConnection urlConnection = null;

		int responseCode = BBBApiConstants.ERROR_CONNECTION_FAILED;
		Map<String, List<String>> responseHeaders = null;
		String responseData = null;
		InputStream in = null;

		try {
			URL url = new URL(request.getUrl());

			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod(request.getRequestMethod().toString());
			urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
			urlConnection.setRequestProperty("Connection", "close");
			
			if(!TextUtils.isEmpty(mUserAgent)) {
				urlConnection.setRequestProperty("User-Agent", mUserAgent);
			}
						
			HashMap<String, String> headers = request.getHeaders();

			// add headers if set
			if (headers != null) {

				Iterator<String> keys = headers.keySet().iterator();
				String key, value;

				while (keys.hasNext()) {
					key = keys.next();
					value = headers.get(key);

					urlConnection.addRequestProperty(key, value);
				}
			}

			BBBHttpRequestMethod httpRequestMethod = request.getRequestMethod();

			byte[] postData = request.getPostData();

			// add post data if set
			if ((httpRequestMethod == BBBHttpRequestMethod.POST || httpRequestMethod == BBBHttpRequestMethod.PUT) && postData != null) {
				urlConnection.setDoOutput(true);
				urlConnection.setFixedLengthStreamingMode(postData.length);

				OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
				out.write(postData);
				out.flush();
				out.close();
			}

			LogUtils.i(TAG, request.toString());
			LogUtils.curlLog(request, null);

			// execute the request. Handle IOException here so we can continue to try and extract response data if exception is thrown
			try {
				responseCode = urlConnection.getResponseCode();
			} catch (IOException e) {
				LogUtils.d(TAG, e.getMessage(), e);

				//this shouldn't throw an exception the second time it is called. 
				responseCode = urlConnection.getResponseCode();
			}

			responseHeaders = urlConnection.getHeaderFields();

			if (responseCode < HttpURLConnection.HTTP_MULT_CHOICE) {
				in = urlConnection.getInputStream();
			} else {
				in = urlConnection.getErrorStream();
			}

			responseData = IOUtils.toString(in);

		} catch (IOException e) {
			LogUtils.d(TAG, e.getMessage(), e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
            try {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            } catch (ConcurrentModificationException e) {
                // There is a very rare crash that comes out of the Splunk Mint code as a result of the disconnect call above. This
                // exception handler just swallows the crash as everything should continue to function.
            }
		}

		BBBResponse response = new BBBResponse(request, responseData, responseCode, responseHeaders);
		LogUtils.d(TAG, response.toString());

		return response;
	}

	private class ExecuteRequestTask extends AsyncTask<BBBRequest, Void, BBBResponse> {

		private final String responseHandlerId;
		private BBBResponseHandler responseHandler;

		// Use this when you know which handler to use for when the request completes
		public ExecuteRequestTask(BBBRequest request, BBBResponseHandler handler) {
			this.responseHandler = handler;
			responseHandlerId = null;
		}

		// Use this to allow the response handler to be swapped out while the request is executing
		public ExecuteRequestTask(BBBRequest request, String responseHandlerId) {
			this.responseHandlerId = responseHandlerId;
		}

		protected BBBResponse doInBackground(BBBRequest... requests) {
			BBBRequest request = requests[0];

			BBBResponse response = executeRequestSynchronously(request);

			return response;
		}

		protected void onPostExecute(BBBResponse response) {
			super.onPostExecute(response);

			if (responseHandler == null) {
				responseHandler = mResponseHandlers.get(responseHandlerId);
			}

			if (responseHandler == null) {
				return;
			}

			if (response.getResponseCode() != BBBApiConstants.ERROR_CONNECTION_FAILED) {
				responseHandler.receivedResponse(response);
			} else {
				responseHandler.connectionError(response.getRequest());
			}
		}
	}
}