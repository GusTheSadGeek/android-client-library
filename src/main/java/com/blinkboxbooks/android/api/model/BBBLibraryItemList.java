/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** Represents a list of {@link BBBLibraryItem} objects. */
public class BBBLibraryItemList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The data type of this object */
	public String type;
	
	/** The list of {@link BBBLibraryItem} objects. */
	public BBBLibraryItem[] items;
}
