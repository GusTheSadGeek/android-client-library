/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** Represents a Promotion */
public class BBBPromotion extends BBBObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** the name of this promotion */
	public String name;
	
	/** the title of this promotion */
	public String title;
	
	/** the sub title of this promotion */
	public String subtitle;
	
	/** The display name of this promotion */
	public String displayName;
	
	/** The date from which this promotion is active */
	public String activated;
	
	/** The date from which this promotion ceases to be active */
	public String deactivated;
	
	/** The sequence of this promotion. Used for ordering */
	public int sequence;
	
	/** The location of this promotion */
	public int location;
	
	/** The image url for this promotion */
	public String image;
	
	/** Links associated with this promotion */
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
