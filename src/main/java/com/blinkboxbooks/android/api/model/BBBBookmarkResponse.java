/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** Represents a bookmark response */
public class BBBBookmarkResponse extends BBBObject implements Serializable {

	private static final long serialVersionUID = 1L;
		
	/** The server timestamp for when the bookmark was created */
	public String createdDate;
	
	/** The server timestamp for this update */
	public String updatedTimeDate;
	
	/** The number of fields which were modified */
	public int numOfUpdatedFields;
}
