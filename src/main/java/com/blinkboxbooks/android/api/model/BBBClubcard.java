/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** Response object for clubcard calls POST and GET */
public class BBBClubcard extends BBBObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String rel;
	
	public String href;
	
	/** The clubcard number */
	public String number;
}
