/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/** A users credit card */
public class BBBCreditCard implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The type of this object */
	public String type;
	
	/** The guid of this object */
	public String guid;
	
	/** id/token for the card */
	public String id;
		
	/** path to resource */
	public String href;
	
	/** credit card number masked */
	public String maskedNumber;
	
	/** image for the credit card */
	public String imageUrl;
		
	/** has the card expired */
	public boolean expired; 
	
	@SerializedName("default")
	public boolean isDefault;
	
	/** expiry month */
	public int expirationMonth;
	
	/** expiry year */
	public int expirationYear;
	
	/** the card holders name */
	public String cardholderName;
	
	/** the card type */
	public String cardType;
	
	/** The billing address */
	public BBBBillingAddress billingAddress;
}
