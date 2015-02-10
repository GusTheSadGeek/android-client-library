/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** Represents a list of bookmarks */
public class BBBBookmarkList extends BBBList implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** the server time of the sync */
	public String lastSyncDateTime;

	/** The array of {@link BBBBookmark} objects */
	public BBBBookmark[] bookmarks;
}
