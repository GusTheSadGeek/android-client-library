/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** The users basket */
public class BBBBasket implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The type of this object */
	public String type;
	
	/** The total value of all the items in the basket */
	public BBBPrice total;
	
	/** The number of basket items */
	public int count;
	
	/** The basket items */
	public BBBBasketItem[] items;
}
