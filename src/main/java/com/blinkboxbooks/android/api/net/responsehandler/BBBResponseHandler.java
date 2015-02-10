/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.net.responsehandler;

import com.blinkboxbooks.android.api.net.BBBRequest;
import com.blinkboxbooks.android.api.net.BBBResponse;

/**
 * Implement this to create a response handler which can deal with data returned by an API call
 */
public interface BBBResponseHandler {

	/**
	 * Callback triggered when a request completes.
	 *
	 * @param response the Response
	 */
	public void receivedResponse(BBBResponse response);

	/**
	 * Called when the request completely fails due to a connection error. In this instance there is no response data to handle so we simply notify the handler that the request failed.
	 */

	/*
	 * TODO: We need to think about this. There is no way that we can resolve this connection Error
	 * back to the BBBRequest that has failed. This method should really take a single BBBRequest
	 * argument to facilitate this. However making this change will require a chunk of work, and I
	 * wanted to discuss with Chirag before making such a major change.
	 *
	 * MIA 20/01/2014
	 */
	public void connectionError(BBBRequest request);
}
