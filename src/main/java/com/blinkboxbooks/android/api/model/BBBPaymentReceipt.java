/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** Represents an item on a payment receipt */
public class BBBPaymentReceipt implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The type of this object */
	public String type;
	
	/** The id of the receipt */
	public String id;
	
	/** ISBN of book that was purchased */
	public String isbn;
	
	/** clubcard points awarded for the transaction */
	public int clubcardPointsAward;
	
	/** date of purchase */
	public String date;
	
	/** price paid for the book */
	public BBBPrice price;
	
	/** The array of payment methods used to complete the purchase */
	public BBBPaymentReceiptItem[] payments;
}
