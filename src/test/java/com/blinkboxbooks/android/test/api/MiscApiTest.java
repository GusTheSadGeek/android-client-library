package com.blinkboxbooks.android.test.api;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.app.Activity;
import android.content.Context;

import com.blinkbox.books.test.MyShadowSystemClock;
import com.blinkboxbooks.android.api.BBBApiConstants;
import com.blinkboxbooks.android.api.net.BBBRequest;
import com.blinkboxbooks.android.api.net.BBBRequestFactory;
import com.blinkboxbooks.android.api.net.BBBRequestManager;
import com.blinkboxbooks.android.api.net.BBBResponse;
import com.blinkboxbooks.android.test.AccountHelper;
import com.blinkboxbooks.android.test.TestConstants;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE, shadows = { MyShadowSystemClock.class } )
public class MiscApiTest extends TestCase implements TestConstants, BBBApiConstants {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		if(TestConstants.VOLLEY_ENABLED) {
			Context context = new Activity();
			BBBRequestManager.getInstance().initVolleyRequestQueue(context.getApplicationContext());		
		}
		
		BBBRequestFactory.getInstance().setHostDefault(HOST);
		BBBRequestFactory.getInstance().setHostLibrary(HOST_LIBRARY);
		BBBRequestManager.getInstance().setInterface(AccountHelper.getInstance());
	}
	
	@Test 
	public void testCheckFileSize() throws Exception {		
		BBBRequest request = BBBRequestFactory.getInstance().createCheckFileSizeHeadRequest(DOWNLOAD_EPUB_FILE);
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		
        List<String> contentLength = response.getHeaders().get("Content-Length");

        if(contentLength != null && contentLength.size() > 0) {
            String length = contentLength.get(0);

            try {
                long l = Long.parseLong(length);
                
                if(l <= 0) {
                	fail("file size was zero");
                }
                
            } catch(ArrayIndexOutOfBoundsException e) {}
        }
	}
}
