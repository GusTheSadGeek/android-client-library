/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** Holds information about errors */
public class BBBBusinessError implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** the type of this object */
	public String type;
	
	/** the guid */
	public String guid;
	
	/** machine readable error string*/
	public String code;
	
	/** human readable error string*/
	public String message;
}
