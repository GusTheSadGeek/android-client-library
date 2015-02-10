/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** A synopsis for a book */
public class BBBSynopsis extends BBBObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The synopsis text */
	public String text;
}
