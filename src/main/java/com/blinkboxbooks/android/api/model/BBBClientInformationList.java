/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** An array of client information objects */
public class BBBClientInformationList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public BBBClientInformation[] clients;
}
