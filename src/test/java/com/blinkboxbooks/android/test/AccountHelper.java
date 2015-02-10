/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.test;

import com.blinkboxbooks.android.api.model.BBBBasket;
import com.blinkboxbooks.android.api.model.BBBClubcard;
import com.blinkboxbooks.android.api.model.BBBCreditCardList;
import com.blinkboxbooks.android.api.net.BBBRequestManagerInterface;
import com.blinkboxbooks.android.util.IOUtils;

public class AccountHelper implements BBBRequestManagerInterface {
	
	private static AccountHelper instance = new AccountHelper();
	
	public static AccountHelper getInstance() {
		return instance;
	}
	
	public String accessToken;
	
	public String refreshToken;
	
	public BBBClubcard clubcard;
	
	public BBBBasket basket;
	
	public BBBCreditCardList creditCardList;
		
	public BBBBasket getBasket() {
		return basket;
	}

	public void setBasket(BBBBasket basket) {
		this.basket = basket;
	}

	@Override
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	@Override
	public String getAccessToken() {
		return accessToken;
	}

	@Override
	public String getRefreshToken() {
		return refreshToken;
	}

	@Override
	public String getClientId() {
		return TestConstants.CLIENT_ID;
	}

	@Override
	public String getClientSecret() {
		return TestConstants.CLIENT_SECRET;
	}
	
	public void clientInvalidated() {}

	public void setClubcard(BBBClubcard clubcard) {
		this.clubcard = clubcard;
	}

	public BBBClubcard getClubcard() {
		return clubcard;
	}
	
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	/**
	 * Constructs a URL with the given parameters
	 * 
	 * @param host the host name
	 * @param path the path
	 * @param params array of key value pairs. length of array must be even.
	 * @return
	 */
	public static String constructUrl(String host, String path, String... params) {
		StringBuilder builder = new StringBuilder();
		builder.append(host);
		builder.append(path);
		
		if(params != null && params.length > 0) {
						
			for(int i=0; i<params.length; i=i+2)  {
				builder.append(i == 0 ? '?' : '&');				
				builder.append(params[i]);
				builder.append('=');
				builder.append(IOUtils.urlEncode(params[i+1]));					
			}
		}
			
		return builder.toString();
	}
	
	/**
	 * Appends a list of parameters to a url all with the same name. e.g ?id=12&id=13&id=14
	 * 
	 * @param url the url to append to
	 * @param name the name for the parameters you want to append
	 * @param values the parameter values
	 * 
	 * @return the modified url
	 */
	public static String appendParameters(String url, String name, Object... values) {
		StringBuilder builder = new StringBuilder();
		builder.append(url);
		
		if(values != null && values.length > 0) {
						
			for(int i=0; i<values.length; i++)  {
				builder.append(i == 0 ? '?' : '&');				
				builder.append(name);
				builder.append('=');
				builder.append(IOUtils.urlEncode(String.valueOf(values[i])));					
			}
		}
			
		return builder.toString();		
	}

	@Override
	public void elevationRequired() {}
}
