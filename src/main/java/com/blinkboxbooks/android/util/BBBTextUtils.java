/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.util;

/**
 * Helper class for operations on text
 */
public class BBBTextUtils {
	/**
	 * Checks if a given String contains any charchters other than spaces
	 *
	 * @param text the text you want to check
	 * @return false if the String is null or does not contain any non-blank charachters else true
	 */
	public static boolean isEmpty(String text) {

		if (text == null || text.trim().length() == 0) {
			return true;
		}

		return false;
	}

	/**
	 * Strip the guid prefix and return the id
	 *
	 * @param guid
	 * @return id without prefix
	 */
	public static String getIdFromGuid(String guid) {

		if (guid == null) {
			return null;
		}

		int lastColon = guid.lastIndexOf(':');

		if (lastColon != -1) {
			return guid.substring(lastColon + 1);
		}

		return guid;
	}
}
