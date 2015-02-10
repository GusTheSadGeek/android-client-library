/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** Price of a book including a possible discount price */
public class BBBBookPrice extends BBBObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The currency of the price */
	public String currency;
	
	/** The price of the book */
	public double price;
	
	/** Optional discount price */
	public double discountPrice = -1;
	
	/** The number of clubcard points the user would receive for this purchase */
	public int clubcardPointsAward;
	
	/** Link associated with this price */
	public BBBLink[] links;
	
	/**
	 * returns the price of the book which is the discount price if it exists or the original price
	 */
	public double getPriceToPay() {
		return discountPrice != -1 ? discountPrice : price;
	}
	
	/**
	 * Returns the first link represented by the URN. 
	 * 
	 * @param urn the URN for the data type we are interested in
	 * @return {@link BBBLink} object.
	 */	
	public BBBLink getLinkData(String urn) {
		
		if(urn == null || links == null) {
			return null;
		}
				
		for(int i=0; i<links.length; i++) {
			
			if(urn.equals(links[i].rel)) {
				return links[i];
			}
		}
		
		return null;
	}
}
