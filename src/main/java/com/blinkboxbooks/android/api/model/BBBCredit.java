/*******************************************************************************
 * Copyright (c) 2014 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** Represents the amount of credit on a users account */
public class BBBCredit implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The currency of the amount */
	public String currency;
	
	/** */
	public double amount;
}
