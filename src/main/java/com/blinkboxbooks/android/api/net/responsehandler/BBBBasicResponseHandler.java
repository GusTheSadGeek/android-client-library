/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.net.responsehandler;

import com.blinkboxbooks.android.api.BBBApiConstants;
import com.blinkboxbooks.android.api.net.BBBRequest;
import com.blinkboxbooks.android.api.net.BBBResponse;
import com.blinkboxbooks.android.util.LogUtils;
import com.blinkboxbooks.android.util.ReflectionUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.net.HttpURLConnection;

public abstract class BBBBasicResponseHandler<T> implements BBBResponseHandler {

	private static final String TAG = BBBBasicResponseHandler.class.getName();

	/**
	 * {inheritDoc}
	 */
	public void receivedResponse(BBBResponse response) {

		if (response == null) {
			receivedError(new BBBResponse(null, BBBApiConstants.ERROR_UNKNOWN));
		} else if (response.getResponseCode() < HttpURLConnection.HTTP_MULT_CHOICE) {
			String json = response.getResponseData();

			try {
				T data = new Gson().fromJson(json, returnedClass());
				receivedData(response, data);
			} catch (JsonSyntaxException e) {
				LogUtils.d(TAG, e.getMessage(), e);
				receivedError(new BBBResponse(response.getRequest(), BBBApiConstants.ERROR_PARSER));
			}
		} else {
			receivedError(response);
		}
	}

	/**
	 * {inheritDoc}
	 */
	public void connectionError(BBBRequest request) {
		receivedError(new BBBResponse(request, BBBApiConstants.ERROR_CONNECTION_FAILED));
	}

	public abstract void receivedData(BBBResponse response, T data);

	public abstract void receivedError(BBBResponse response);

	@SuppressWarnings("unchecked")
	private Class<T> returnedClass() {
		return (Class<T>) ReflectionUtil.getTypeArguments(BBBBasicResponseHandler.class, getClass()).get(0);
	}
}