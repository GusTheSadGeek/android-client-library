/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** Represents an item on a payment receipt */
public class BBBPaymentReceiptItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** the type of this object */
	public String type;
	
	/** the name of this payment item */
	public String name;
	
	/** the amount this paid via this method */
	public BBBPrice money;
	
	/** the receipt id */
	public String receipt;
}
