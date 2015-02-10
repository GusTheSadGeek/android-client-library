/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** Information about a contributor including their name to display, the number of books they have */
public class BBBContributor extends BBBObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The display name of this contributor */
	public String displayName;
	
	/** The name to use for sorting */
	public String sortName;
		
	/** The number of books this contributor has */
	public int booksCount;
	
	/** The Biography of the contributor */
	public String biography;
	
	/** Links associated with this contributor */
	public BBBLink[] links;
	
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
