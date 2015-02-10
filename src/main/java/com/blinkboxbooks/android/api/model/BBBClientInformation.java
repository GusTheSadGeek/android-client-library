/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** Represents a client registered to a users account */
public class BBBClientInformation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** A globally unique identifier for the client. This string MUST be treated as opaque by clients. */
	public String client_id;
	
	/** The URL from which information about the client can be retrieved */
	public String client_uri;
	
	/** The friendly name for a client. */
	public String client_name;
	
	/** The device model of the client */
	public String client_model;
	
	/** A secret value associated with the client, used with client_id to authenticate the client. */
	public String client_secret;
	
	/** The date when this client was last used */
	public String last_used_date;
}
