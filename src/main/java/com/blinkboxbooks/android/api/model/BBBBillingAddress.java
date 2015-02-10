/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import java.io.Serializable;

/** billing address of credit card */
public class BBBBillingAddress implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** address line 1 */
	public String line1;
	
	/** address line 2 */
	public String line2;
	
	/** address town */
	public String locality;
	
	/** address post code */
	public String postcode;
}
