/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** Represents a Category */
public class BBBCategory extends BBBObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The name of the category */
	public String slug;
	
	/** The descriptive name of this category */
	public String displayName;

	/** The sequence of this category. used for sorting */
	public int sequence;

	/** The recommended sequence of this category. used for sorting */
	public int recommendedSequence;
	
	/** The image for this category. */
	public String categoryImage;

	/** Links associated with this category */
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
