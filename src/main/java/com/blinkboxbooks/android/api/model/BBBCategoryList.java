/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** A list of {@link BBBCategory} objects */
public class BBBCategoryList extends BBBList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The array of {@link BBBCategory} objects */
	public BBBCategory[] items;
}
