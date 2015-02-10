/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.blinkboxbooks.android.api.BBBApiConstants;

import java.util.Map;

/**
 * An extension to the base Volley Request that can return a BBBResponse object
 */
public class BBBVolleyRequest extends Request<BBBResponse> {

	private final BBBRequest mRequest;

	private final Listener<BBBResponse> mListener;

	private final byte[] mRequestBody;

	private final Map<String, String> mHeaders;

	private String mContentType;

	public BBBVolleyRequest(BBBRequest request, int method, String url, Map<String, String> headers, byte[] requestBody, Listener<BBBResponse> responseListener, ErrorListener errorListener) {
		super(method, url, errorListener);

		mRequest = request;
		mListener = responseListener;
		mRequestBody = requestBody;
		mHeaders = headers;
	}

	/**
	 * Sets the content type for the request body
	 *
	 * @param contentType the Content Type
	 */
	public void setContentType(String contentType) {
		this.mContentType = contentType;
	}

	@Override
	protected void deliverResponse(BBBResponse response) {
		mListener.onResponse(response);
	}

	@Override
	protected Response<BBBResponse> parseNetworkResponse(NetworkResponse networkResponse) {
		BBBResponse res = new BBBResponse(mRequest, networkResponse.statusCode);
		res.setHeaders(networkResponse.headers);
		res.setResponseData(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));

		Response<BBBResponse> response = Response.success(res, HttpHeaderParser.parseCacheHeaders(networkResponse));

		deliverResponse(res);

		return response;
	}

	@Override
	protected VolleyError parseNetworkError(VolleyError volleyError) {
		BBBResponse res;

		if (volleyError == null || volleyError.networkResponse == null) {
			res = new BBBResponse(mRequest, BBBApiConstants.ERROR_CONNECTION_FAILED);
		} else {
			res = new BBBResponse(mRequest, volleyError.networkResponse.statusCode);
			res.setHeaders(volleyError.networkResponse.headers);
			res.setResponseData(volleyError.networkResponse.data, HttpHeaderParser.parseCharset(volleyError.networkResponse.headers));
		}

		deliverResponse(res);

		return super.parseNetworkError(volleyError);
	}

	@Override
	public byte[] getBody() {

		if (mRequestBody != null) {
			return mRequestBody;
		}

		try {
			return super.getBody();
		} catch (AuthFailureError e) {
		}

		return null;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {

		if (mHeaders != null) {
			return mHeaders;
		} else {
			return super.getHeaders();
		}
	}

	@Override
	public String getBodyContentType() {

		if (mContentType != null) {
			return mContentType;
		} else {
			return super.getBodyContentType();
		}
	}
}