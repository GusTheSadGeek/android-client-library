/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** A book search item */
public class BBBBookSearchItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The isbn of the book */
	public String id;
	
	/** The title of the book */
	public String title;
	
	/** The array of authors of this book */
	public String[] authors;
}
