/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** list of a users clubcards */
public class BBBClubcardList extends BBBList implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** array of returned clubcards */
	public BBBClubcard[] items;
}
