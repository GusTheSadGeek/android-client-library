/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.net;

import com.blinkboxbooks.android.api.BBBApiConstants;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Represents a HTTP response including the status code and response body
 */
public class BBBResponse {

	//the request which this is response is for
	private final BBBRequest mRequest;

	//the response data returned from the server
	private String mResponseData;

	//the HTTP status code
	private int mResponseCode;

	//HTTP response headers
	private Map<String, List<String>> mHeaders;

	/**
	 * Creates a response object with the given responseData and statusCode
	 *
	 * @param request      the request which this is response is for
	 * @param responseData the response data
	 * @param responseCode the status code
	 * @param headers      the response headers
	 */
	public BBBResponse(BBBRequest request, String responseData, int responseCode, Map<String, List<String>> headers) {
		mRequest = request;
		mResponseData = responseData;
		mResponseCode = responseCode;
		mHeaders = headers;
	}

	/**
	 * Creates a response object with the given responseData and statusCode
	 *
	 * @param request      the request which this is response is for
	 * @param responseData the response data
	 * @param responseCode the status code
	 */
	public BBBResponse(BBBRequest request, String responseData, int responseCode) {
		mRequest = request;
		mResponseData = responseData;
		mResponseCode = responseCode;
	}

	/**
	 * Creates a response object with the given statusCode
	 *
	 * @param request      the request which this is response is for
	 * @param responseCode the status code
	 */
	public BBBResponse(BBBRequest request, int responseCode) {
		mRequest = request;
		mResponseCode = responseCode;
		mResponseData = null;
	}

	/**
	 * Copies data from the given response object into this one
	 *
	 * @param response
	 */
	public void copy(BBBResponse response) {
		mResponseCode = response.mResponseCode;
		mResponseData = response.mResponseData;
		mHeaders = response.mHeaders;
	}

	/**
	 * Sets the response data
	 *
	 * @param responseData
	 */
	public void setResponseData(String responseData) {
		mResponseData = responseData;
	}

	public void setResponseData(byte[] data, String encoding) {

		try {
			mResponseData = new String(data, encoding);
		} catch (UnsupportedEncodingException e) {
			mResponseData = new String(data);
		}
	}

	/**
	 * Sets the response code
	 *
	 * @param responseCode
	 */
	public void setResponseCode(int responseCode) {
		mResponseCode = responseCode;
	}

	/**
	 * Sets the response headers
	 *
	 * @param headers
	 */
	public void setHeadersList(Map<String, List<String>> headers) {
		mHeaders = headers;
	}

	/**
	 * Getter for the request
	 *
	 * @return the request
	 */
	public BBBRequest getRequest() {
		return mRequest;
	}

	/**
	 * Getter for response code
	 *
	 * @return the response code
	 */
	public int getResponseCode() {
		return mResponseCode;
	}

	/**
	 * Getter for response data
	 *
	 * @return the response data
	 */
	public String getResponseData() {
		return mResponseData;
	}

	/**
	 * Getter for headers
	 *
	 * @return a Map of header information
	 */
	public Map<String, List<String>> getHeaders() {
		return mHeaders;
	}

	public void setHeaders(Map<String, String> headers) {

		if (headers == null) {
			return;
		}

		int size = headers.size();

		if (mHeaders == null) {
			mHeaders = new HashMap<String, List<String>>(size);
		}

		List<String> list;
		String key;

		Iterator<String> itr = headers.keySet().iterator();

		while (itr.hasNext()) {
			key = itr.next();
			list = mHeaders.get(key);

			if (list == null) {
				list = new ArrayList<String>();
				mHeaders.put(key, list);
			}

			list.add(headers.get(key));
		}
	}

	/**
	 * Looks through the headers and pulls out a field belonging to the WWW-Authenticate header
	 *
	 * @param fieldName the field name for the value you want to retrieve
	 * @return the value belonging to the specified field or null if one cannot be found
	 */
	public String getAuthenticateHeaderField(String fieldName) {

		if (mHeaders == null || fieldName == null) {
			return null;
		}

		List<String> authenticationError = mHeaders.get(BBBApiConstants.HEADER_WWW_AUTHENTICATE);

		if (authenticationError == null) {
			return null;
		}

		String[] parameters = null;

		if (authenticationError.size() == 1) {
			parameters = authenticationError.get(0).split(",");
		} else {
			return null;
		}

		String field;
		String[] nameValue;
		String value;

		for (int i = 0; i < parameters.length; i++) {
			field = parameters[i];

			if (field == null) {
				continue;
			}

			field = field.trim();

			nameValue = field.split("=");

			if (nameValue.length == 2) {

				if (nameValue[0].equals(fieldName)) {
					value = nameValue[1];

					if (value == null || value.length() < 2) {
						return value;
					}

					if (value.charAt(0) == '"') {
						value = value.substring(1);
					}

					if (value.charAt(value.length() - 1) == '"') {
						value = value.substring(0, value.length() - 1);
					}

					return value;
				}
			}
		}

		return null;
	}

	/**
	 * returns a String encapsulating all the data in this response
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();

		if (mHeaders != null) {
			Iterator<String> keys = mHeaders.keySet().iterator();

			String key;
			List<String> values;

			int size;

			builder.append("headers: {");

			while (keys.hasNext()) {
				key = keys.next();
				values = mHeaders.get(key);

				builder.append(key);
				builder.append(':');

				size = values.size();

				for (int i = 0; i < size; i++) {
					builder.append(values.get(i));

					if (i < size - 1) {
						builder.append('|');
					}
				}

				if (keys.hasNext()) {
					builder.append(',');
					builder.append(' ');
				}
			}
		}

		builder.append('\n');

		if (mResponseData == null) {
			builder.append("No response data");
		} else {
			builder.append("Response data: " + mResponseData);
		}

		builder.append('\n');

		return builder.toString();
	}
}
