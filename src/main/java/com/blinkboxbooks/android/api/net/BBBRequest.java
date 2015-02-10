/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.net;

import java.util.HashMap;
import java.util.Iterator;

import com.blinkboxbooks.android.util.IOUtils;

/**
 * Represents a HTTP request with data including the url, request method, post data and header information
 */
public class BBBRequest {

	// the HTTP request method. i.e GET, POST, PUT, DELETE
	private final BBBHttpRequestMethod mRequestMethod;

	// the url of the request
	private String mUrl;

	// headers for this request
	private HashMap<String, String> mHeaders;
	
	// data to be posted. use with POST request method
	private byte[] mPostData;
	
	//this request requires the correct authentication header to be set
	private final boolean mRequiresAuthentication;
	
	/**
	 * Creates a BBBRequest object with the given host and path. Sets the request method to GET
	 * 
	 * @param host the host to set
	 * @param path the path to set
	 */
	public BBBRequest(String host, String path, boolean requiresAuthentication) {		
		this(host, path, BBBHttpRequestMethod.GET, requiresAuthentication);
	}
	
	/**
	 * Creates a BBBRequest object with the given host, path, and request method
	 * 
	 * @param host The host to set
	 * @param path The path to set
	 * @param requestMethod The request method to set
	 */
	public BBBRequest(String host, String path, BBBHttpRequestMethod requestMethod, boolean requiresAuthentication) {
		mUrl = host;
		
		if(path != null) {
			mUrl += path;
		}
		
		mRequestMethod = requestMethod;
		mRequiresAuthentication = requiresAuthentication;
	}

	/**
	 * returns the request method for this request. Default is GET
	 * 
	 * @return the request method
	 */
	public BBBHttpRequestMethod getRequestMethod() {
		return mRequestMethod;
	}
	
	/**
	 * returns the full URL for this request
	 * 
	 * @return the URL
	 */
	public String getUrl() {
		return mUrl;
	}
	
	/**
	 * Sets a request header with the given values
	 * 
	 * @param name The name of the header field
	 * @param value The value of the header field
	 * 
	 * @return this object
	 */
	public BBBRequest setHeader(String name, String value) {
		
		if(mHeaders == null) {
			mHeaders = new HashMap<String, String>();
		}
		
		mHeaders.put(name, value);
		
		return this;
	}
	
	/**
	 * Returns the headers for this request
	 * 
	 * @return A HashMap containing the headers or null if there are no headers for this request
	 */
	public HashMap<String, String> getHeaders() {
		return mHeaders;
	}
	
	/**
	 * returns the post data
	 * 	
	 * @return the post data
	 */
	public byte[] getPostData() {
		return mPostData;
	}

	/**
	 * set the post data
	 * 
	 * @param postData the data to post
	 */
	public void setPostData(byte[] postData) {
		mPostData = postData;
	}
	
	/**
	 * set the post data
	 * 
	 * @param postData the data to post
	 */
	public void setPostData(String postData) {
		mPostData = postData.getBytes();
	}
		
	/**
	 * Indicates whether this request requires authentication
	 * 
	 * @return true if request requires authentication header to be set else false
	 */
	public boolean requiresAuthentication() {
		return mRequiresAuthentication;
	}

	/**
	 * Formats the URL with the values in the supplied array.
	 * 
	 * @param args teh values to insert into the string
	 */
	public void formatUrl(Object... args) {
		mUrl = String.format(mUrl, args);
	}
	
	/**
	 * Appends multiple parameters of the same name to the URL e.g. host/path?parameter=value1&amp;parameter=value2&amp;parameter=value3
	 * @param name the name of the parameters to add
	 * @param values the array of parameters
	 */
	public void appendParameterArray(String name, Object... values) {
		
		if(values == null) {
			return;
		}
		
		for(int i=0; i<values.length; i++) {
			appendParameter(name, values[i].toString());
		}
	}
	
	/**
	 * Appends a parameter to the URL.
	 * 
	 * @param name the name of the parameter
	 * @param value the value of the parameter
	 */
	public void appendParameter(String name, String value) {
		StringBuilder builder = new StringBuilder();
		builder.append(mUrl);	
		
		if(mUrl.indexOf('?') == -1) { // if this character doesn't exist in the URL this must be the first parameter to be added so we add the '?' char first
			builder.append('?');
		} else {
			builder.append('&');
		}
		
		name = IOUtils.urlEncode(name);
		builder.append(name);
		
		builder.append('=');
		
		value = IOUtils.urlEncode(value);
		builder.append(value);
		
		mUrl = builder.toString();
	}
	
	/**
	 * returns a String encapsulating all the data in this request
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(mRequestMethod);
		builder.append(' ');
		builder.append(mUrl);
		
		if(mHeaders != null && mHeaders.size() > 0) {
			Iterator<String> keys = mHeaders.keySet().iterator();
			String key, value;
			
			builder.append("\nRequest headers: {");
			
			while(keys.hasNext()) {
				key = keys.next();
				value = mHeaders.get(key);
				
				builder.append(key);
				builder.append(':');
				builder.append(value);
				
				if(keys.hasNext()) {
					builder.append(',');
				}
			}
			
			builder.append("}");
		} else {
			builder.append("\nNo request headers");
		}
		
		if(mPostData != null) {
			builder.append('\n');
			builder.append("Request body: '");
			builder.append(new String(mPostData));
			builder.append('\'');
		}
				
		return builder.toString();
	}
}
