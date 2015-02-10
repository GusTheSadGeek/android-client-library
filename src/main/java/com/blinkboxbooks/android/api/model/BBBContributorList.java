/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** A list of {@link BBBContributor} objects */
public class BBBContributorList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The array of {@link BBBContributor} objects */
	public BBBContributor[] items;
}
