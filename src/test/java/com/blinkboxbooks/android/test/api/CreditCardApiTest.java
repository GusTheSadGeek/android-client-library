package com.blinkboxbooks.android.test.api;

import java.net.HttpURLConnection;

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
import com.blinkboxbooks.android.api.model.BBBCreditCardList;
import com.blinkboxbooks.android.api.net.BBBRequest;
import com.blinkboxbooks.android.api.net.BBBRequestFactory;
import com.blinkboxbooks.android.api.net.BBBRequestManager;
import com.blinkboxbooks.android.api.net.BBBResponse;
import com.blinkboxbooks.android.api.net.responsehandler.BBBBasicResponseHandler;
import com.blinkboxbooks.android.test.AccountHelper;
import com.blinkboxbooks.android.test.TestConstants;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE, shadows = { MyShadowSystemClock.class } )
public class CreditCardApiTest extends TestCase implements TestConstants, BBBApiConstants {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		if(TestConstants.VOLLEY_ENABLED) {
			Context context = new Activity();
			BBBRequestManager.getInstance().initVolleyRequestQueue(context.getApplicationContext());		
		}
		
		BBBRequestFactory.getInstance().setHostDefault(HOST);
		BBBRequestFactory.getInstance().setHostCreditcards(HOST_CREDIT_CARDS);
		BBBRequestManager.getInstance().setInterface(AccountHelper.getInstance());
		
		if(AccountHelper.getInstance().getAccessToken() == null) {
			AuthenticationApiTest test = new AuthenticationApiTest();
			test.setUp();
			test.testAuthentication();
		}
	}

	/*
	 * test adding a card
	 */
	@Test
	public void testAddCreditCard() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createAddCreditCardRequest(BRAIN_TREE_ENCRYPTION_KEY, false, CREDIT_CARD_NUMBER, CREDIT_CARD_CVV2, CREDIT_CARD_EXPIRATION_MONTH,
				CREDIT_CARD_EXPIRATION_YEAR, CREDIT_CARD_HOLDER_NAME, ADDRESS_LINE_1, ADDRESS_LINE_2, LOCALITY, POSTCODE);
		
		BBBRequestManager.getInstance().executeRequestSynchronously(request);
	}

	/*
	 * test getting a list of the users card
	 */
	@Test
	public void testGetCreditCards() throws Exception {
		BBBRequest request = BBBRequestFactory.getInstance().createGetCreditCardsRequest();
		
		BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
		
		creditCardListHandler.receivedResponse(response);
	}
	
	/*
	 * tests updating an existing card
	 */
	@Test
	public void testUpdateCreditCard() throws Exception {
		
		if(AccountHelper.getInstance().creditCardList != null && AccountHelper.getInstance().creditCardList.items.length > 0) {
			BBBRequest request = BBBRequestFactory.getInstance().createUpdateCreditCardRequest(BRAIN_TREE_ENCRYPTION_KEY, AccountHelper.getInstance().creditCardList.items[0].id,
					false, CREDIT_CARD_NUMBER, CREDIT_CARD_CVV2, CREDIT_CARD_EXPIRATION_MONTH, CREDIT_CARD_EXPIRATION_YEAR, CREDIT_CARD_HOLDER_NAME, ADDRESS_LINE_1, ADDRESS_LINE_2,
					LOCALITY, POSTCODE_UPDATED);
			
			BBBRequestManager.getInstance().executeRequestSynchronously(request);
		}
	}
	
	/*
	 * test getting a single card
	 */
	@Test
	public void testGetCreditCard() throws Exception {
		
		if(AccountHelper.getInstance().creditCardList != null && AccountHelper.getInstance().creditCardList.items.length > 0) {
			BBBRequest request = BBBRequestFactory.getInstance().createGetCreditCardRequest(AccountHelper.getInstance().creditCardList.items[0].id);
			
			BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
			
			if(response.getResponseCode() != HttpURLConnection.HTTP_OK) {
				fail("Error: "+response.toString());
			}
		}
	}
	
	/*
	 * test deleting a card
	 */
	@Test
	public void testDeleteCreditCard() throws Exception {
	
		if(AccountHelper.getInstance().creditCardList != null && AccountHelper.getInstance().creditCardList.items.length > 0) {
			BBBRequest request = BBBRequestFactory.getInstance().createDeleteCreditCardRequest(AccountHelper.getInstance().creditCardList.items[0].id);
			
			BBBResponse response = BBBRequestManager.getInstance().executeRequestSynchronously(request);
			
			if(response.getResponseCode() != HttpURLConnection.HTTP_OK) {
				fail("Error: "+response.toString());
			}
		}
	}
	
	private BBBBasicResponseHandler<BBBCreditCardList> creditCardListHandler = new BBBBasicResponseHandler<BBBCreditCardList>() {

		public void receivedData(BBBResponse response, BBBCreditCardList data) {
			assertNotNull(data);
			
			AccountHelper.getInstance().creditCardList = data;
			
			assertTrue(response.getResponseCode() == HttpURLConnection.HTTP_OK);
		}
		
		public void receivedError(BBBResponse response) {
			fail("Error: "+response.toString());
		}
	};
}
