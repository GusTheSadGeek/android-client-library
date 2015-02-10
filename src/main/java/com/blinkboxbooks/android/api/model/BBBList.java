/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** Basic list information including total results, current offset and the current number of results */
public abstract class BBBList implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** The data type of the list */
	public String type;
	
	/** The total number of results that can be obtained for this list */
	public int numberOfResults;
	
	/** The offset into the entire list of items that this particular list starts from */
	public String offset;
	
	/** The number of elements in this list */
	public String count;
}
