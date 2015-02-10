/*******************************************************************************
 * Copyright (c) 2014 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** Represents the response we get when we request a users credit */
public class BBBCreditResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The type of this object */
	public String type;
	
	/** The credit object */
	public BBBCredit credit;
}