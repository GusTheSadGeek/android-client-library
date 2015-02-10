/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** A link object which can point to a variety of different resources */
public class BBBLink implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** The type of this link. e.g urn:blinkboxbooks:schema:synopsis */
	public String rel;
	
	/** the path to the resource */
	public String href;
	
	/** the guid of the object this link points to */
	public String targetGuid;
	
	/** the title of this link */
	public String title;
}
