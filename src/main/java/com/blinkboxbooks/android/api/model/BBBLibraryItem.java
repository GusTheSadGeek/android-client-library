/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** Represents a book in the users library */
public class BBBLibraryItem extends BBBObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** reading status unread */
	public static String READING_STATUS_UNREAD = "UNREAD";
	
	/** reading status finished */
	public static String READING_STATUS_FINISHED = "FINISHED";
	
	/** reading status reading */
	public static String READING_STATUS_READING = "READING";
	
	/** purchase status sampled */
	public static String PURCHASE_STATUS_SAMPLED = "SAMPLED";
	
	/** purchase status purchased */
	public static String PURCHASE_STATUS_PURCHASED = "PURCHASED";

	/** visibility status archived */
	public static String STATUS_CURRENT = "CURRENT";
	
	/** visibility status archived */
	public static String STATUS_ARCHIVED = "ARCHIVED";
	
	/** visibility status deleted */
	public static String STATUS_DELETED = "DELETED";
	
	/** The ISBN for the book the library item refers to */
	public String isbn;
	
	/** The purchase status of the book. e.g. SAMPLED, PURCHASED */
	public String purchaseStatus;
	
	/** The visibility status of the book. e.g. CURRENT, ARCHIVED, DELETED */
	public String visibilityStatus;
	
	/** The reading status of the book. e.g. UNREAD, FINISHED, READING */
	public String readingStatus;
	
	/** The date this book was sampled */
	public String sampledDate;
	
	/** The date this book was purchased */
	public String purchasedDate;
	
	/** The date this book was archived */
	public String archivedDate;
	
	/** The date this book was deleted */
	public String deletedData;
		
	/** the maximum number of devices this book can be downloaded to */
	public int maxNumberOfAuthorisedDevices;
	
	/** the number of devices this book has been downloaded to */
	public int numberOfAuthorisedDevices;
		
	/** Links associated with this library item. should find media and decrypt key paths here */
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
