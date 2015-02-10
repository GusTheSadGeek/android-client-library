/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** A list of business errors */
public class BBBBusinessErrorsList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The business errors */
	public BBBBusinessError[] businessErrors;
	
	public boolean containsError(String errorName) {
		
		if(businessErrors == null || errorName == null) {
			return false;
		}
		
		for(int i=0; i<businessErrors.length; i++) {
			
			if(errorName.equals(businessErrors[i].code)) {
				return true;
			}
		}
		
		return false;
	}
}
