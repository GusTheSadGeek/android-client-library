/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** Represents a bookmark */
public class BBBBookmark extends BBBObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The type of Bookmark e.g. HIGHLIGHT */
	public String bookmarkType;
	
	/** The isbn of the book this bookmark belongs to */
	public String book;
	
	/** The CFI position */
	public String position;
	
	/** name of the bookmark */
	public String name;
	
	/** some annotation */
	public String annotation;

	/** The style of the bookmark */
	public String style;
	
	/** The colour of the bookmark */
	public String colour;
	
	/** The client id that created the bookmark */
	public String createdByClient;
	
	/** The date this bookmark was created */
	public String createdDate;
	
	/** The last client id to update this bookmark */
	public String updatedByClient;
	
	/** The date this bookmark was last updated */
	public String updatedDate;
	
	/** A preview of the content this bookmark points to */
	public String preview;
	
	/** The reading percentage */
	public int readingPercentage;
	
	/** has this bookmark been deleted */
	public boolean deleted;
	
	/** Links associated with this bookmark */
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
