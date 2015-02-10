/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** An item in the users basket */
public class BBBBasketItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The type of this object */
	public String type;
	
	/** The id of this basket item */
	public String id;
	
	/** The isbn of the basket item */
	public String isbn;
	
	/** The price of the basket item */
	public BBBPrice price;
}