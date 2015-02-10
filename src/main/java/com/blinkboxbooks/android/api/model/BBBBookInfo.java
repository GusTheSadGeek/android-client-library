/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** Basic book meta data */
public class BBBBookInfo extends BBBObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** Title of the book */
	public String title;
	
	/** Date when the book was published */
	public String publicationDate;
		
	/** has sample */
	public boolean sampleEligible;
		
	/** Links associated with this book */
	public BBBLink[] links;
	
	/** Images associated with this book */
	public BBBImage[] images;
	
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
	
	/**
	 * Returns the first image represented by the URN. 
	 * 
	 * @param urn the URN for the data type we are interested in
	 * @return {@link BBBImage} object.
	 */	
	public BBBImage getImageData(String urn) {
		
		if(urn == null || images == null) {
			return  null;
		}
		
		for(int i=0; i<images.length; i++) {
			
			if(urn.equals(images[i].rel)) {
				return images[i];
			}
		}
		
		return null;
	}
}
