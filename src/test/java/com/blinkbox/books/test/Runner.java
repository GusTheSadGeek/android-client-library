package com.blinkbox.books.test;

import org.junit.runners.model.InitializationError;

import org.robolectric.RobolectricTestRunner;

/**
 * Use this runner instead of RobolectricTestRunner with @RunWith annotation.
 */
public class Runner extends RobolectricTestRunner {

    public Runner(final Class<?> testClass) throws InitializationError {
    	super(testClass);
    }
}
