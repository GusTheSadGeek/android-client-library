/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** A list of {@link BBBSuggestion} objects */
public class BBBSuggestionList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** the data type of this object */
	public String type;
	
	/** the array of {@link BBBSuggestion} objects */
	public BBBSuggestion[] items;
}
