/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** Holds information about errors from the auth server */
public class BBBAuthenticationError implements Serializable {

	private static final long serialVersionUID = 1L;

	/** the error */
	public String error;
	
	/** machine readable error string*/
	public String error_reason;
	
	/** human readable error string*/
	public String error_description;
}
