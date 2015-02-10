/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** Response received when performing a book search */
public class BBBBookSearchResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The type of this object */
	public String type;
	
	/** The id */
	public String id;
	
	/** The total number of search results */
	public int numberOfResults;

	/**
	 * A list of suggestions for alternative search criteria
	 */
	public String[] suggestions;
	
	/** list of api links. contains the url used to execute the current search and the url which can be used for the next search */
	public BBBLink[] links;
	
	/** The list of search results */
	public BBBBookSearchItem[] books;
	
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
