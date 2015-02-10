/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.model;

import android.text.TextUtils;

import java.io.Serializable;

/** Represents a search term suggestion */
public class BBBSuggestion implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The type of this suggestion. e.g. AUTHOR or BOOK */
	public String type;

	/** the suggestion text */
	public String title;

	/** the isbn of the book or the contributor id */
	public String id;

	/** list of authors */
	public String[] authors;

	public String toString() {
		StringBuilder builder = new StringBuilder();

		if(!TextUtils.isEmpty(title)) {
			builder.append(title);
		}

		if(authors != null) {

			if (builder.length() > 0) {
				builder.append(" - ");
			}

			for(int i=0; i<authors.length; i++) {
				builder.append(authors[i]);

				if(i < authors.length - 1) {
					builder.append(", ");
				}
			}
		}

		return builder.toString();
	}
}
