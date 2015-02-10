/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** A price object including amount and currency */
public class BBBPrice implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The total amount */
	public double amount;
	
	/** The currency of the amount value */
	public String currency;
}
