/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** Base object with some common data attributes */
public abstract class BBBObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The data type of this object */
	public String type;
	
	/** The guid of this object */
	public String guid;
	
	/** The id of this object */
	public String id;
}
