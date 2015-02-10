/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** List of prices for a book. */
public class BBBBookPriceList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The data type of this object */
	public String type;
		
	/** The array of {@link BBBBookPrice} objects */
	public BBBBookPrice[] items;
}
