/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** An address */
public class BBBAddress implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Address line 1*/
	public String street_1;
	
	/** Address line 2 */
	public String street_2;
	
	/** town */
	public String town;
	
	/** postcode */
	public String postcode;
	
	/** country code */
	public String country;
}
