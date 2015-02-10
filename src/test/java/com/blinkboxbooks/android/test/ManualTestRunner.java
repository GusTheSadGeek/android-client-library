/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.test;

import junit.framework.TestSuite;
import junit.textui.TestRunner;

import com.android.volley.VolleyLog;
import com.blinkboxbooks.android.test.api.*;
import com.blinkboxbooks.android.util.LogUtils;

public class ManualTestRunner {
	
	public static void main(String[] args) {	
		TestSuite suite = new TestSuite();
		
		LogUtils.setLoggingEnabled(true);
		
		VolleyLog.DEBUG = true;
		
		suite.addTestSuite(AuthenticationApiTest.class);
		suite.addTestSuite(BookmarkServiceApiTest.class);
		suite.addTestSuite(BookPriceServiceApiTest.class);
		suite.addTestSuite(BookServiceApiTest.class);
		suite.addTestSuite(CategoryServiceApiTest.class);
		suite.addTestSuite(ContributorServiceApiTest.class);
		suite.addTestSuite(PromotionServiceApiTest.class);
		suite.addTestSuite(PublisherServiceApiTest.class);
		suite.addTestSuite(SearchServiceApiTest.class);
		suite.addTestSuite(LibraryServiceApiTest.class);
		suite.addTestSuite(ClubcardServiceApiTest.class);
		suite.addTestSuite(BasketServiceApiTest.class);
		suite.addTestSuite(MiscApiTest.class);
		
		TestRunner.run(suite);
	}
}
