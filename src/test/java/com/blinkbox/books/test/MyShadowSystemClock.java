package com.blinkbox.books.test;

import org.robolectric.annotation.Implements;

import android.os.SystemClock;

@Implements(value = SystemClock.class, callThroughByDefault = true)
public class MyShadowSystemClock {
	
    public static long elapsedRealtime() {
        return 0;
    }
}