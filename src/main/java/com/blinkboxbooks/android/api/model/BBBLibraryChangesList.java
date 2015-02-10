/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** A list of changes to a users library */
public class BBBLibraryChangesList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The data type of this object */
	public String type;
	
	/** List of BBBBookInfo objects that have changed */
	public BBBLibraryItem[] items;
}
