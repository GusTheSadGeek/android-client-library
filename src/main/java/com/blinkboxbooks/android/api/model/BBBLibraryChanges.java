/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** A list of changes to a users library including the sync timestamp from the server */
public class BBBLibraryChanges implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The data type of this object */
	public String type;
	
	/** the server time of the sync */
	public String lastSyncDateTime;
	
	/** the list of library changes */
	public BBBLibraryChangesList libraryChangesList;
}
