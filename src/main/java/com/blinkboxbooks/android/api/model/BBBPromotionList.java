/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** A list of {@link BBBPromotion} objects */
public class BBBPromotionList extends BBBList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The array of {@link BBBPromotion} objects */
	public BBBPromotion[] items;
}
