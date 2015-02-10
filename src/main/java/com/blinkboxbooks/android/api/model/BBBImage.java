/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** A path to an image. */
public class BBBImage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** the type of this image. e.g. urn:blinkboxbooks:image:cover */
	public String rel;
	
	/** the full path to this image */
	public String src;
}
