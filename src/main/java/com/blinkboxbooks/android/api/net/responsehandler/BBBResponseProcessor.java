/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.net.responsehandler;

import java.net.HttpURLConnection;

import com.blinkboxbooks.android.api.net.BBBResponse;
import com.blinkboxbooks.android.util.LogUtils;
import com.blinkboxbooks.android.util.ReflectionUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Helper class for quickly extracting the JSON data from a BBBResponse object and turning it into the data type you expect
 * 
 * @param <T>
 */
public class BBBResponseProcessor<T> {

	private static final String TAG = BBBResponseProcessor.class.getSimpleName();
	
	/**
	 * Extracts the JSON from a BBBResponse and returns the data type as specified in the generic type T
	 * 
	 * @param response the BBBResponse object we want to transform
	 * @return the parsed data type or null if there was an error
	 */
	public T processResponse(BBBResponse response) {
	
		if(response != null && response.getResponseCode() < HttpURLConnection.HTTP_MULT_CHOICE) {
			String json = response.getResponseData();
			
			try {
				T data = new Gson().fromJson(json, returnedClass());
				return data;
			} catch(JsonSyntaxException e) {
				LogUtils.d(TAG, e.getMessage(), e);
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> returnedClass() {
		return (Class<T>)ReflectionUtil.getTypeArguments(BBBResponseProcessor.class, getClass()).get(0);
	}
}
