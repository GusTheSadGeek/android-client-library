/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** User information response */
public class BBBUserInformationResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** the user id */
	public String user_id;
	
	/** user uri */
	public String user_uri;
	
	/** username */
	public String user_username;
	
	/** users first name */
	public String user_first_name;
	
	/** users last name */
	public String user_last_name;
		
	/** has the user allowed marketing communications */
	public boolean user_allow_marketing_communications;
}
