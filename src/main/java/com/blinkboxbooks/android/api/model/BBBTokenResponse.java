/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** Token response */
public class BBBTokenResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The OAuth2 access token */
	public String access_token;
	
	/** The type of token e.g. bearer */
	public String token_type;
	
	/** The time until this token will expire */
	public int expires_in;
	
	/** The refresh token. Use to get a new access token */
	public String refresh_token;
	
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
	
	/** client id */
	public String client_id;
	
	/** client uri */
	public String client_uri;
	
	/** client name */
	public String client_name;

	/** client brand */
	public String client_brand;
	
	/** client model */
	public String client_model;
	
	/** client operating system */
	public String client_os;
	
	/** date this device was last used */
	public String last_used_date;
	
	/** client secret */
	public String client_secret;
}