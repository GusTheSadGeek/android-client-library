/*******************************************************************************
 * Copyright (c) 2014 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** A list of credit card objects */
public class BBBCreditCardList extends BBBList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public BBBCreditCard[] items;
}
