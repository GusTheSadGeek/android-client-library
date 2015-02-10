/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** Holds a list of {@link BBBBookInfo} objects */
public class BBBBookInfoList extends BBBList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The array of {@link BBBBookInfo} objects */
	public BBBBookInfo[] items;
}
