/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** A users credit card */
public class BBBPostCreditCard implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** name on card */
	public String name;
	
	/** credit card number */
	public String number;
	
	/** the cvv */
	public String cvv;
	
	/** the expiry month */
	public String expiry_month;

	/** the expiry year */
	public String expiry_year;
	
	/** the address against which the card is registered */
	public BBBAddress address;
}
