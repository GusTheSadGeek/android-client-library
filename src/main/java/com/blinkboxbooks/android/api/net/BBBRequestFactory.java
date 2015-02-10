/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api.net;

import com.blinkboxbooks.android.api.BBBApiConstants;
import com.blinkboxbooks.android.api.model.BBBReadingStatus;
import com.blinkboxbooks.android.util.BBBCalendarUtil;
import com.blinkboxbooks.android.util.BBBTextUtils;
import com.blinkboxbooks.android.util.LogUtils;
import com.braintreegateway.encryption.Braintree;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Factory for creating BBBRequest objects for communication with REST API. Before requests are created, the base host should be set via setDefaultHost().
 * If required, alternate hosts can be set for individual services
 */
public class BBBRequestFactory implements BBBApiConstants {

    private static final String TAG = BBBRequestFactory.class.getSimpleName();

	private static BBBRequestFactory sInstance;
	
	/**
	 * Singleton getter
	 * 
	 * @return the BBBRequestFactory singleton object
	 */
	public static BBBRequestFactory getInstance() {
		
		if(sInstance == null) {
			sInstance = new BBBRequestFactory();
		}
		
		return sInstance;
	}
	
	private String mHostDefault;
	private String mHostAuthentication;
	private String mHostKeyServer;
	private String mHostCatalogue;
	private String mHostSearch;
	private String mHostPromotion;
	private String mHostLibrary;
	private String mHostBookmark;
	private String mHostBasket;
	private String mHostPayments;
	private String mHostCreditcards;
	private String mHostClubcard;
	private String mHostCredit;
	
	private BBBRequestFactory() {}
	
	/**
	 * Sets the default host and deploy path to use for request objects. This should be set before any requests are created.
	 *
	 * @param host the host name and the deploy path
	 */
	public void setHostDefault(String host) {
		mHostDefault = host;
	}
	
	/**
	 * Returns the server base url currently set
	 * 
	 * @return the server base url
	 */
	public String getHostDefault() {
		return mHostDefault;
	}
	
	/**
	 * Sets the host to use for authentication and registering. This should be set before any authentication related requests are created
	 * @param host the host name
	 */
	public void setHostAuthentication(String host) {
		mHostAuthentication = host;
	}
	
	/**
	 * Sets the host to use for retrieving keys. 
	 * @param host the host name
	 */
	public void setHostKeyServer(String host) {
		mHostKeyServer = host;
	}
	
	/**
	 * Set if the catalogue service has been deployed to a different url to the default host
	 * 
	 * @param hostCatalogue the host name
	 */
	public void setHostCatalogue(String hostCatalogue) {
		mHostCatalogue = hostCatalogue;
	}
	
	/**
	 * Set if the search service has been deployed to a different url to the default host
	 * 
	 * @param hostSearch the host name
	 */
	public void setHostSearch(String hostSearch) {
		mHostSearch = hostSearch;
	}
	
	/**
	 * Set if the marketing service has been deployed to a different url to the default host
	 * 
	 * @param hostPromotion the host name
	 */
	public void setHostMarketing(String hostPromotion) {
		mHostPromotion = hostPromotion;
	}
	
	/**
	 * Set if the catalogue service has been deployed to a different url to the default host
	 * 
	 * @param hostLibrary the host name
	 */
	public void setHostLibrary(String hostLibrary) {
		mHostLibrary = hostLibrary;
	}	
	
	/**
	 * Set if the bookmark service has been deployed to a different url to the default host
	 * 
	 * @param hostBookmark the host name
	 */
	public void setHostBookmark(String hostBookmark) {
		mHostBookmark = hostBookmark;
	}	
	
	/**
	 * Set if the basket service has been deployed to a different url to the default host
	 * 
	 * @param hostBasket the host name
	 */
	public void setHostBasket(String hostBasket) {
		mHostBasket = hostBasket;
	}	
	
	/**
	 * Set if the creditcards service has been deployed to a different url to the default host
	 * 
	 * @param hostCreditcards the host name
	 */
	public void setHostCreditcards(String hostCreditcards) {
		mHostCreditcards = hostCreditcards;
	}
	
	/**
	 * Set if the clubcard service has been deployed to a different url to the default host
	 * 
	 * @param hostClubcard the host name
	 */
	public void setHostClubcard(String hostClubcard) {
		mHostClubcard = hostClubcard;
	}
	
	/**
	 * Set if the payments service has been deployed to a different url to the default host
	 * 
	 * @param hostPayments the host name
	 */
	public void setHostPayments(String hostPayments) {
		mHostPayments = hostPayments;
	}
	
	/**
	 * Set if the credit service has been deployed to a different url to the default host
	 * 
	 * @param hostCredit the host name
	 */
	public void setHostCredit(String hostCredit) {
		mHostCredit = hostCredit;
	}	
	
	/**
	 * Returns the host being used for the catalogue service.
	 * 
	 * @return the host
	 */
	public String getHostCatalogue() {
		return mHostCatalogue != null ? mHostCatalogue : checkAndGetDefaultHost();
	}
	
	/**
	 * Returns the host being used for the search service.
	 * 
	 * @return the host
	 */
	public String getHostSearch() {
		return mHostSearch != null ? mHostSearch : checkAndGetDefaultHost();
	}
	
	/**
	 * Returns the host being used for the marketing service.
	 * 
	 * @return the host
	 */
	public String getHostMarketing() {
		return mHostPromotion != null ? mHostPromotion : checkAndGetDefaultHost();
	}
	
	/**
	 * Returns the host being used for the library service.
	 * 
	 * @return the host
	 */
	public String getHostLibrary() {
		return mHostLibrary != null ? mHostLibrary : checkAndGetDefaultHost();
	}	
	
	/**
	 * Returns the host being used for the bookmark service.
	 * 
	 * @return the host
	 */
	public String getHostBookmark() {
		return mHostBookmark != null ? mHostBookmark : checkAndGetDefaultHost();
	}	
	
	/**
	 * Returns the host being used for the basket service.
	 * 
	 * @return the host
	 */
	public String getHostBasket() {
		return mHostBasket != null ? mHostBasket : checkAndGetDefaultHost();
	}	
	
	/**
	 * Returns the host being used for the credit cards service.
	 * 
	 * @return the host
	 */
	public String getHostCreditcards() {
		return mHostCreditcards != null ? mHostCreditcards : checkAndGetDefaultHost();
	}	
	
	/**
	 * Returns the host being used for the club card service.
	 * 
	 * @return the host
	 */
	public String getHostClubcards() {
		return mHostClubcard != null ? mHostClubcard : checkAndGetDefaultHost();
	}	

	/**
	 * Returns the host being used for the payments service.
	 * 
	 * @return the host
	 */
	public String getHostPayments() {
		return mHostPayments != null ? mHostPayments : checkAndGetDefaultHost();
	}	
	
	/**
	 * Returns the host being used for the credit service.
	 * 
	 * @return the host
	 */
	public String getHostCredit() {
		return mHostCredit != null ? mHostCredit : checkAndGetDefaultHost();
	}	
	//Misc API
	
	/**
	 * Creates a HEAD request to check the size of a file
	 * 
	 * @param fileUrl
	 * @return
	 */
	public BBBRequest createCheckFileSizeHeadRequest(String fileUrl) {
		return new BBBRequest(fileUrl, null, BBBHttpRequestMethod.HEAD, false);
	}
	
	//Purchase API
	
	/**
	 * Gets all the items in the users basket
	 * 
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetBasketRequest() {
		return new BBBRequest(getHostBasket(), PATH_MY_BASKETS, true);
	}
	
	/**
	 * Clears all the items in the users basket
	 * 
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createClearBasketRequest() {
		return new BBBRequest(getHostBasket(), PATH_MY_BASKETS, BBBHttpRequestMethod.DELETE, true);
	}
	
	/**
	 * Adds an item to the users basket
	 * 
	 * @param isbn the isbn of the book you want to add to the basket
	 * 
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createAddBasketItemRequest(String isbn) {
		BBBRequest request = new BBBRequest(getHostBasket(), PATH_MY_BASKETS_ITEMS, BBBHttpRequestMethod.POST, true);
		
		request.setHeader(HEADER_CONTENT_TYPE, CONTENTTYPE_APPLICATION_BBB_JSON);
		
		String requestBody = getSimpleJSONObjectString(PARAM_ISBN, isbn);
		request.setPostData(requestBody);
		
		return request;
	}
	
	/**
	 * Gets a specific item from the users basket
	 * 
	 * @param id the id of the basket item you want to retrieve
	 * 
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetBasketItemRequest(String id) {
		return new BBBRequest(getHostBasket(), String.format(PATH_MY_BASKETS_ITEMS_, id), true);
	}
	
	/**
	 * Deletes a specific basket item
	 * 
	 * @param id the id of the basket item you want to delete
	 * 
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createDeleteBasketItemRequest(String id) {
		return new BBBRequest(getHostBasket(), String.format(PATH_MY_BASKETS_ITEMS_, id), BBBHttpRequestMethod.DELETE, true);
	}
	
	/**
	 * Purchases the current contents of the users basket with a credit card which has already been registered
	 * 
	 * @param token the credit card token. pass null to use account credit
	 * 
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createPurchaseBasketRequest(String token) {
		BBBRequest request = new BBBRequest(getHostPayments(), PATH_MY_PAYMENTS, BBBHttpRequestMethod.POST, true);
		
		request.setHeader(HEADER_CONTENT_TYPE, CONTENTTYPE_APPLICATION_BBB_JSON);
		
		String requestBody = getSimpleJSONObjectString(PARAM_TOKEN, token);
		request.setPostData(requestBody);
				
		return request;
	}
	
	/**
	 * Purchases the current contents of the users basket with a new credit card
	 * 
	 * @param encryptionKey the braintree encryption key
	 * @param number the card number
	 * @param cvv the card CVV
	 * @param expirationMonth card expiration month
	 * @param expirationYear card expiration year
	 * @param cardHolderName card holders name
	 * @param addressLine1 address line 1
	 * @param addressLine2 address line 2
	 * @param locality town
	 * @param postCode postcode
	 * 
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createPurchaseBasketRequest(String encryptionKey, String number, String cvv, String expirationMonth, String expirationYear,
			String cardHolderName, String addressLine1, String addressLine2, String locality, String postCode) {
		
		BBBRequest request = new BBBRequest(getHostPayments(), PATH_MY_PAYMENTS, BBBHttpRequestMethod.POST, true);
		request.setHeader(HEADER_CONTENT_TYPE, CONTENTTYPE_APPLICATION_BBB_JSON);
		
		Braintree braintree = new Braintree(encryptionKey);
		
		JsonObject addressObject = new JsonObject();
		addressObject.addProperty(PARAM_LINE1, addressLine1);
		addressObject.addProperty(PARAM_LINE2, addressLine2);
		addressObject.addProperty(PARAM_LOCALITY, locality);
		addressObject.addProperty(PARAM_POSTCODE, postCode);
		
		JsonObject cardObject = new JsonObject();
		cardObject.addProperty(PARAM_NUMBER, braintree.encrypt(number));
		cardObject.addProperty(PARAM_CVV, braintree.encrypt(cvv));
		cardObject.addProperty(PARAM_EXPIRATION_MONTH, braintree.encrypt(expirationMonth));
		cardObject.addProperty(PARAM_EXPIRATION_YEAR, braintree.encrypt(expirationYear));
		cardObject.addProperty(PARAM_CARDHOLDER_NAME, cardHolderName);
		cardObject.add(PARAM_BILLING_ADDRESS, addressObject);		
		
		JsonObject requestBody = new JsonObject();
		requestBody.add(BBBApiConstants.PARAM_CREDIT_CARD, cardObject);
		
		request.setPostData(requestBody.toString());
		
		return request;
	}
	 
	/**
	 * Gets the amount of credit the user has on their account
	 * 
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetCreditOnAccountRequest() {
		return new BBBRequest(getHostCredit(),PATH_MY_CREDIT, true);
	}
	
	/**
	 * Gets the users purchase history
	 * 
	 * @param offset
	 * @param count
	 * @param start
	 * @param end
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetPurchaseHistoryRequest(Integer offset, Integer count, Integer start, Integer end) {
		BBBRequest request = new BBBRequest(getHostPayments(), PATH_MY_PAYMENTS, true);
		
		appendParameters(request, PARAM_OFFSET, offset, PARAM_COUNT, count, PARAM_START, start, PARAM_END, end);
		
		return request;
	}
	
	//Credit card API
	
	/**
	 * Gets a list of the users credit cards
	 * 
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetCreditCardsRequest() {
		return new BBBRequest(getHostCreditcards(), PATH_MY_CREDITCARDS, true);
	}
	
	/**
	 * Gets a single credit card identified by its token
	 * 
	 * @param token the credit card token
	 * 
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetCreditCardRequest(String token) {
		return new BBBRequest(getHostCreditcards(), String.format(PATH_MY_CREDITCARDS_, token), true);
	}
	
	/**
	 * Deletes a credit card identified by its token
	 * 
	 * @param token the credit card token
	 * 
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createDeleteCreditCardRequest(String token) {
		return new BBBRequest(getHostCreditcards(), String.format(PATH_MY_CREDITCARDS_, token), BBBHttpRequestMethod.DELETE, true);
	}
	
	/**
	 * Creates a request to add a credit card
	 * 
	 * @param encryptionKey the braintree encryption key
	 * @param isDefault will this be the default card
	 * @param number the card number
	 * @param cvv the card CVV
	 * @param expirationMonth card expiration month
	 * @param expirationYear card expiration year
	 * @param cardHolderName card holders name
	 * @param addressLine1 address line 1
	 * @param addressLine2 address line 2
	 * @param locality town
	 * @param postCode postcode
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createAddCreditCardRequest(String encryptionKey, boolean isDefault, String number, String cvv, String expirationMonth, String expirationYear, String cardHolderName,
			String addressLine1, String addressLine2, String locality, String postCode) {
		
		BBBRequest request = new BBBRequest(getHostCreditcards(), PATH_MY_CREDITCARDS, BBBHttpRequestMethod.POST, true);
		request.setHeader(HEADER_CONTENT_TYPE, CONTENTTYPE_APPLICATION_BBB_JSON);
		
		Braintree braintree = new Braintree(encryptionKey);
				
		JsonObject addressObject = new JsonObject();
		addressObject.addProperty(PARAM_LINE1, addressLine1);
		addressObject.addProperty(PARAM_LINE2, addressLine2);
		addressObject.addProperty(PARAM_LOCALITY, locality);
		addressObject.addProperty(PARAM_POSTCODE, postCode);
		
		JsonObject cardObject = new JsonObject();
		cardObject.addProperty(PARAM_DEFAULT, isDefault);
		cardObject.addProperty(PARAM_NUMBER, braintree.encrypt(number));
		cardObject.addProperty(PARAM_CVV, braintree.encrypt(cvv));
		cardObject.addProperty(PARAM_EXPIRATION_MONTH, braintree.encrypt(expirationMonth));
		cardObject.addProperty(PARAM_EXPIRATION_YEAR, braintree.encrypt(expirationYear));
		cardObject.addProperty(PARAM_CARDHOLDER_NAME, cardHolderName);
		cardObject.add(PARAM_BILLING_ADDRESS, addressObject);		
		
		String requestBody = cardObject.toString();
		request.setPostData(requestBody);
		
		return request;
	}
	
	/**
	 * Creates a request to update a credit card
	 * 
	 * @param encryptionKey the braintree encryption key
	 * @param token the credit card token
	 * @param isDefault will this be the default card
	 * @param number the card number
	 * @param cvv the card CVV
	 * @param expirationMonth card expiration month
	 * @param expirationYear card expiration year
	 * @param cardHolderName card holders name
	 * @param addressLine1 address line 1
	 * @param addressLine2 address line 2
	 * @param locality town
	 * @param postCode postcode
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createUpdateCreditCardRequest(String encryptionKey, String token, boolean isDefault, String number, String cvv, String expirationMonth, String expirationYear, String cardHolderName,
			String addressLine1, String addressLine2, String locality, String postCode) {
		
		BBBRequest request = new BBBRequest(getHostCreditcards(), String.format(PATH_MY_CREDITCARDS, token), BBBHttpRequestMethod.POST, true);
		request.setHeader(HEADER_CONTENT_TYPE, CONTENTTYPE_APPLICATION_BBB_JSON);
		
		Braintree braintree = new Braintree(encryptionKey);
		
		JsonObject addressObject = new JsonObject();
		addressObject.addProperty(PARAM_LINE1, addressLine1);
		addressObject.addProperty(PARAM_LINE2, addressLine2);
		addressObject.addProperty(PARAM_LOCALITY, locality);
		addressObject.addProperty(PARAM_POSTCODE, postCode);
		
		JsonObject cardObject = new JsonObject();
		cardObject.addProperty(PARAM_DEFAULT, isDefault);
		cardObject.addProperty(PARAM_NUMBER, braintree.encrypt(number));
		cardObject.addProperty(PARAM_CVV, braintree.encrypt(cvv));
		cardObject.addProperty(PARAM_EXPIRATION_MONTH, braintree.encrypt(expirationMonth));
		cardObject.addProperty(PARAM_EXPIRATION_YEAR, braintree.encrypt(expirationYear));
		cardObject.addProperty(PARAM_CARDHOLDER_NAME, cardHolderName);
		cardObject.add(PARAM_BILLING_ADDRESS, addressObject);		
		
		request.setPostData(cardObject.toString());
		
		return request;
	}

	//Bookmark API
	
	/**
	 * Gets a list of bookmarks
	 * 
	 * @param isbn The isbn of the book you want to retrieve bookmarks for. Optional.
	 * @param position 
	 * @param lastSyncDate if specified, will only return bookmarks added/deleted/updated after this date.
	 * @param bookmarkTypes the types of bookmark you want to retrieve
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetBookmarksRequest(String isbn, String position, String lastSyncDate, String... bookmarkTypes) {
		BBBRequest request = new BBBRequest(getHostBookmark(), PATH_READING_MY_BOOKMARKS, true);
	
		appendParameters(request, PARAM_BOOK, isbn, PARAM_POSITION, position, PARAM_LAST_SYNC_DATE_TIME, lastSyncDate);
			
		if(bookmarkTypes != null && bookmarkTypes.length > 0) {
			appendMultipleParameters(request, PARAM_BOOKMARK_TYPE, bookmarkTypes);
		}
		
		return request;
	}
	
	/**
	 * Request for getting a single bookmark
	 * 
	 * @param id the id of the bookmark you want to retrieve
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetBookmarkRequest(String id) {
		return new BBBRequest(getHostBookmark(), String.format(PATH_READING_MY_BOOKMARKS_ITEM, id), true);
	}
	
	/**
	 * Request for deleting multiple bookmarks.
	 * 
	 * @param isbn deletes bookmarks belonging to this isbn
	 * @param deletedBy the user who is deleting these bookmarks
	 * @param bookmarkTypes filter to specify which types of bookmark to delete
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createDeleteBookmarksRequest(String isbn, String deletedBy, String... bookmarkTypes) {
		BBBRequest request = new BBBRequest(getHostBookmark(), PATH_READING_MY_BOOKMARKS, BBBHttpRequestMethod.DELETE, true);
						
		appendParameters(request, PARAM_BOOK, isbn, PARAM_DELETED_BY, deletedBy);
			
		if(bookmarkTypes != null && bookmarkTypes.length > 0) {
			appendMultipleParameters(request, PARAM_BOOKMARK_TYPE, bookmarkTypes);
		}
		
		return request;
	}
	
	/**
	 * Request for deleting a single bookmark.
	 * 
	 * @param id the id of the bookmark
	 * @param deletedBy the use who is deleting this bookmark
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createDeleteBookmarkRequest(String id, String deletedBy) {
		BBBRequest request = new BBBRequest(getHostBookmark(), String.format(PATH_READING_MY_BOOKMARKS_ITEM, id), BBBHttpRequestMethod.DELETE, true);
				
		appendParameters(request, PARAM_DELETED_BY, deletedBy);
		
		return request;
	}
	
	/**
	 * Request for adding a new bookmark.
	 * 
	 * @param isbn the isbn of the bookmark
	 * @param bookmarkType the type of bookmark
	 * @param createdBy the user who is creating this bookmark
	 * @param position the CFI position of the bookmark
	 * @param name the optional name of the bookmark
	 * @param annotation an optional annotation for this bookmark
	 * @param style optional style of bookmark
	 * @param colour optional colour of bookmark
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createAddBookmarkRequest(String isbn, String bookmarkType, String createdBy, String position, String name, String annotation, String style, String colour,
			Integer readingPercentage, String preview) {
		BBBRequest request = new BBBRequest(getHostBookmark(), PATH_READING_MY_BOOKMARKS, BBBHttpRequestMethod.POST, true);
				
		request.setHeader(HEADER_CONTENT_TYPE, CONTENTTYPE_APPLICATION_BBB_JSON);
		
		String requestBody = getSimpleJSONObjectString(PARAM_BOOK, isbn, PARAM_BOOKMARK_TYPE, bookmarkType, PARAM_CREATED_BY, createdBy, PARAM_POSITION, position, PARAM_NAME, name,
				PARAM_ANNOTATION, annotation, PARAM_STYLE, style, PARAM_COLOUR, colour, PARAM_READING_PERCENTAGE, readingPercentage, PARAM_PREVIEW, preview);
		
		request.setPostData(requestBody);
				
		return request;
	}
	
	/**
	 * Request for updating an existing bookmark.
	 * 
	 * @param id the id of the bookmark
	 * @param bookmarkType the type of bookmark
	 * @param updatedBy the user who is updating this bookmark
	 * @param position the CFI position of the bookmark
	 * @param name the optional name of the bookmark
	 * @param annotation an optional annotation for this bookmark
	 * @param style optional style of bookmark
	 * @param colour optional colour of bookmark
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createUpdateBookmarkRequest(String id, String bookmarkType, String updatedBy, String position, String name, String annotation, String style, String colour,
			int readingPercentage, String preview) {
		BBBRequest request = new BBBRequest(getHostBookmark(), String.format(PATH_READING_MY_BOOKMARKS_ITEM, id), BBBHttpRequestMethod.PUT, true);
				
		request.setHeader(HEADER_CONTENT_TYPE, CONTENTTYPE_APPLICATION_BBB_JSON);
		
		String requestBody = getSimpleJSONObjectString(PARAM_BOOKMARK_TYPE, bookmarkType, PARAM_UPDATED_BY, updatedBy, PARAM_POSITION, position, PARAM_NAME, name,
				PARAM_ANNOTATION, annotation, PARAM_STYLE, style, PARAM_COLOUR, colour, PARAM_DELETE_NAME, BBBTextUtils.isEmpty(name) ? PARAM_TRUE:PARAM_FALSE,
						PARAM_DELETE_STYLE, BBBTextUtils.isEmpty(style) ? PARAM_TRUE:PARAM_FALSE, PARAM_DELETE_ANNOTATION, BBBTextUtils.isEmpty(annotation) ? PARAM_TRUE:PARAM_FALSE,
								PARAM_DELETE_COLOUR, BBBTextUtils.isEmpty(colour) ? PARAM_TRUE:PARAM_FALSE, PARAM_READING_PERCENTAGE, readingPercentage, PARAM_PREVIEW, preview);
		
		request.setPostData(requestBody);
		
		return request;
	}
	
	//Keyserver API
	
	/**
	 * Request for getting a decryption key for an encrypted epub
	 * 
	 * @param url the full path to the key file
	 * @param key the base64 encoded public key
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetKeyRequest(String url, String key) {		
		BBBRequest request = new BBBRequest(url, null, BBBHttpRequestMethod.POST, true);
				
		String postData = getParameterString(false,PARAM_KEY, key);
		request.setPostData(postData);
		
		return request;
	}
	
	//Clubcard API
	
	/**
	 * Request for associating a clubcard with a user
	 * 
	 * @param number the clubcard number
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createAddClubcardRequest(String number) {
		BBBRequest request = new BBBRequest(getHostClubcards(), PATH_MY_CLUBCARDS, BBBHttpRequestMethod.POST, true);
				
		request.setHeader(HEADER_CONTENT_TYPE, CONTENTTYPE_APPLICATION_BBB_JSON);
				
		String requestBody = getSimpleJSONObjectString(PARAM_TYPE, URN_CLUBCARD, PARAM_NUMBER, number);
		request.setPostData(requestBody);
		
		return request;
	}
	
	/**
	 * Returns a list of a users clubcards
	 * 
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetClubcardsRequest() {
		return new BBBRequest(getHostClubcards(), PATH_MY_CLUBCARDS, true);
	}
	
	/**
	 * Deletes a users clubcard
	 * 
	 * @param id the id of the card
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createDeleteClubcardRequest(String id) {
		return new BBBRequest(getHostClubcards(), PATH_MY_CLUBCARDS+'/'+id, BBBHttpRequestMethod.DELETE, true);
	}
	
	//Library API
	
	public BBBRequest createAddSampleRequest(String isbn) {
		BBBRequest request = new BBBRequest(getHostLibrary(), PATH_MY_LIBRARY_SAMPLES, BBBHttpRequestMethod.POST, true);
				
		String requestBody = getSimpleJSONObjectString(PARAM_ISBN, isbn);
		
		request.setPostData(requestBody);
				
		request.setHeader(HEADER_CONTENT_TYPE, CONTENTTYPE_APPLICATION_BBB_JSON);
		return request;
	}
	
	public BBBRequest createAssociateSampleRequest(String isbn) {
		BBBRequest request = new BBBRequest(getHostLibrary(), PATH_MY_LIBRARY_ONDEVICE_SAMPLES, BBBHttpRequestMethod.POST, true);
		
		String requestBody = getSimpleJSONObjectString(PARAM_ISBN, isbn);
		
		request.setPostData(requestBody);
				
		request.setHeader(HEADER_CONTENT_TYPE, CONTENTTYPE_APPLICATION_BBB_JSON);
		return request;
	}
	
	/**
	 * Request for getting all library items. Can specify lastSyncTime which will only return changes passed
	 * 
	 * @param lastSyncTime
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetAllLibraryItemsRequest(Long lastSyncTime) {		
		BBBRequest request = new BBBRequest(getHostLibrary(), PATH_MY_LIBRARY, true);
		
		if(lastSyncTime != null) {
			appendParameters(request, PARAM_LAST_SYNC_DATE_TIME, BBBCalendarUtil.formatTime(lastSyncTime, BBBCalendarUtil.FORMAT_TIME_STAMP));
		}
				
		return request;
	}
	
	/**
	 * Request for getting all library items. Can specify lastSyncTime which will only return changes passed
	 * 
	 * @param lastSyncTime
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetAllLibraryItemsRequest(String lastSyncTime) {
		BBBRequest request = new BBBRequest(getHostLibrary(), PATH_MY_LIBRARY, true);
				
		if(lastSyncTime != null) {
			appendParameters(request, PARAM_LAST_SYNC_DATE_TIME, lastSyncTime);
		}
		
		return request;
	}
	
	/**
	 * Request for getting a single library item.
	 * 
	 * @param id the ID of the library item you want to retrieve
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetLibraryItemRequest(String id) {
		BBBRequest request = new BBBRequest(getHostLibrary(), PATH_MY_LIBRARY_ITEM, true);
		
		request.formatUrl(id);
				
		return request;
	}
	
	/** 
	 * Request for updating the reading status of a book
	 * 
	 * @param id The ID of the library item you want to update
	 * @param readingStatus The status you want to set
	 * 
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createUpdateLibraryItemRequest(String id, BBBReadingStatus readingStatus) {
		BBBRequest request = new BBBRequest(getHostLibrary(), PATH_MY_LIBRARY_ITEM, BBBHttpRequestMethod.PUT, true);
		
		request.formatUrl(id);
		
		String requestBody = getSimpleJSONObjectString(PARAM_READING_STATUS, readingStatus.toString());
		
		request.setPostData(requestBody);
		
		request.setHeader(HEADER_CONTENT_TYPE, CONTENTTYPE_APPLICATION_BBB_JSON);
		
		return request;
	}
	
	/**
	 * Request for retrieving samples and purchased items that are not deleted or archived
	 * 
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetCurrentLibraryRequest(Long lastSyncTime) {
		BBBRequest request = new BBBRequest(getHostLibrary(), PATH_MY_LIBRARY_CURRENT, true);
				
		if(lastSyncTime != null) {
			appendParameters(request, PARAM_LAST_SYNC_DATE_TIME, BBBCalendarUtil.formatTime(lastSyncTime, BBBCalendarUtil.FORMAT_TIME_STAMP));
		}
		
		return request;
	}
	
	/**
	 * Request for retrieving purchased items 
	 * 
	 * @return The {@link BBBRequest} object
	 */	
	public BBBRequest createGetCurrentLibraryPurchasedRequest() {
		return new BBBRequest(getHostLibrary(), PATH_MY_LIBRARY_CURRENT_PURCHASED, true);
	}
	
	/**
	 * Request for retrieving sample items
	 * 
	 * @return The {@link BBBRequest} object
	 */	
	public BBBRequest createGetCurrentLibrarySampledRequest() {
		return new BBBRequest(getHostLibrary(), PATH_MY_LIBRARY_CURRENT_SAMPLED, true);
	}
	
	/**
	 * Request for retrieving items on a device.
	 * 
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetLibraryOnDeviceRequest() {
		return new BBBRequest(getHostLibrary(), PATH_MY_LIBRARY_ONDEVICE, true);
	}
	
	/**
	 * Request for retrieving archived items.
	 * 
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetArchivedItemsRequest() {
		return new BBBRequest(getHostLibrary(), PATH_MY_LIBRARY_ARCHIVED, true);
	}
	
	/**
	 * Request for retrieving deleted items.
	 * 
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetDeletedItemsRequest() {
		return new BBBRequest(getHostLibrary(), PATH_MY_LIBRARY_DELETED, true);
	}
	
	/**
	 * Request for adding a library item to the archive
	 * 
	 * @param id The ID of the item you want to archive
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createArchiveItemRequest(String id) {
		BBBRequest request = new BBBRequest(getHostLibrary(), PATH_MY_LIBRARY_ARCHIVED, BBBHttpRequestMethod.POST, true);
		
		String requestBody = getSimpleJSONObjectString(PARAM_LIBRARY_ITEM_ID, id);
		
		request.setPostData(requestBody);
				
		request.setHeader(HEADER_CONTENT_TYPE, CONTENTTYPE_APPLICATION_BBB_JSON);
		
		return request;
	}
	
	/**
	 * Request for restoring a library item from the archive
	 * 
	 * @param id The ID of the item you want to restore
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createRestoreFromArchiveRequest(String id) {
		BBBRequest request = new BBBRequest(getHostLibrary(), PATH_MY_LIBRARY_CURRENT, BBBHttpRequestMethod.POST, true);
		
		String requestBody = getSimpleJSONObjectString(PARAM_LIBRARY_ITEM_ID, id);
		
		request.setPostData(requestBody);
				
		request.setHeader(HEADER_CONTENT_TYPE, CONTENTTYPE_APPLICATION_BBB_JSON);
		
		return request;		
	}
	
	/**
	 * Request for deleting a library item. 
	 * 
	 * @param id The ID of the library item you want to delete
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createDeleteLibraryItemRequest(String id) {	
		BBBRequest request = new BBBRequest(getHostLibrary(), PATH_MY_LIBRARY_ITEM, BBBHttpRequestMethod.DELETE, true);
		
		request.formatUrl(id);
		
		return request;
	}
		
	//Authentication API
	
	/**
	 * Request for authenticating a user. Upon success you will get back an access_token and a refresh_token
	 * 
	 * @param username The username
	 * @param password The password
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createAuthenticateRequest(String username, String password, String client_id, String client_secret) {
		BBBRequest request = new BBBRequest(checkAndGetAuthenticationHost(), PATH_OAUTH2_TOKEN, BBBHttpRequestMethod.POST, false);
		
		request.setHeader(HEADER_CONTENT_TYPE, CONTENTTYPE_APPLICATION_FORM_URLENCODED);
				
		String requestBody = null;
		
		if(BBBTextUtils.isEmpty(client_id) || BBBTextUtils.isEmpty(client_secret)) {
			requestBody = getParameterString(true,PARAM_GRANT_TYPE, PARAM_PASSWORD, PARAM_USERNAME, username, PARAM_PASSWORD, password);
		} else {
			requestBody = getParameterString(true,PARAM_GRANT_TYPE, PARAM_PASSWORD, PARAM_USERNAME, username, PARAM_PASSWORD, password,
					PARAM_CLIENT_ID, client_id, PARAM_CLIENT_SECRET, client_secret);
		}
        request.setPostData(requestBody);
		
		return request;
	}
	
	/**
	 * Request for getting a new auth token with a refresh token
	 * 
	 * @param refreshToken the refresh token
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetRefreshAuthTokenRequest(String refreshToken, String client_id, String client_secret) {
		BBBRequest request = new BBBRequest(checkAndGetAuthenticationHost(), PATH_OAUTH2_TOKEN, BBBHttpRequestMethod.POST, false);
		
		request.setHeader(HEADER_CONTENT_TYPE, CONTENTTYPE_APPLICATION_FORM_URLENCODED);
		
		String requestBody = null;
		
		if(BBBTextUtils.isEmpty(client_id) || BBBTextUtils.isEmpty(client_secret)) {
			requestBody = getParameterString(true,PARAM_GRANT_TYPE, PARAM_REFRESH_TOKEN, PARAM_REFRESH_TOKEN, refreshToken);
		} else {
			requestBody = getParameterString(true,PARAM_GRANT_TYPE, PARAM_REFRESH_TOKEN, PARAM_REFRESH_TOKEN, refreshToken,
					PARAM_CLIENT_ID, client_id, PARAM_CLIENT_SECRET, client_secret);
		}
		request.setPostData(requestBody);
		
		return request;
	}
	
	/**
	 * Request for registering a new client
	 * 
	 * @param clientName the client name
	 * @param clientModel the client model
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createRegisterClientRequest(String clientName, String clientBrand, String clientModel, String clientOs) {
		BBBRequest request = new BBBRequest(checkAndGetAuthenticationHost(), PATH_CLIENTS, BBBHttpRequestMethod.POST, true);
				
		request.setHeader(HEADER_CONTENT_TYPE, CONTENTTYPE_APPLICATION_FORM_URLENCODED);
		
		String requestBody = getParameterString(true,PARAM_CLIENT_NAME, clientName, PARAM_CLIENT_BRAND, clientBrand, PARAM_CLIENT_MODEL, clientModel, PARAM_CLIENT_OS, clientOs);
        request.setPostData(requestBody);
		
		return request;
	}
	
	/**
	 * Request for unregistering an existing client
	 * 
	 * @param clientId the client id
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createUnregisterClientRequest(String clientId) {
		BBBRequest request = new BBBRequest(checkAndGetAuthenticationHost(), String.format(PATH_CLIENT_ID, clientId), BBBHttpRequestMethod.DELETE, true);
				
		request.setHeader(HEADER_CONTENT_TYPE, CONTENTTYPE_APPLICATION_FORM_URLENCODED);
		
		return request;
	}
	
	/**
	 * Request for updating the details of a registered client
	 * 
	 * @param clientName the optional client name
	 * @param clientModel the optional client model
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createUpdateRegisteredClientRequest(String clientName, String clientBrand, String clientModel, String clientOs) {
		BBBRequest request = new BBBRequest(checkAndGetAuthenticationHost(), PATH_CLIENTS, BBBHttpRequestMethod.PATCH, true);
				
		request.setHeader(HEADER_CONTENT_TYPE, CONTENTTYPE_APPLICATION_FORM_URLENCODED);
		
		String requestBody = getParameterString(true,PARAM_CLIENT_NAME, clientName, PARAM_CLIENT_BRAND, clientBrand, PARAM_CLIENT_MODEL, clientModel, PARAM_CLIENT_OS, clientOs);
        request.setPostData(requestBody);
		
		return request;
	}
	
	/**
	 * Request for registering a new user
	 * 
	 * @param username users email address
	 * @param firstName users first name
	 * @param lastName users last name
	 * @param password users desired password
	 * @param acceptedMarketingCommunications has the user accepted the marketing communications
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createRegisterAccountRequest(String username, String firstName, String lastName, String password, boolean acceptedMarketingCommunications,
			String clientName, String clientBrand, String clientModel, String clientOs) {
		BBBRequest request = new BBBRequest(checkAndGetAuthenticationHost(), PATH_OAUTH2_TOKEN, BBBHttpRequestMethod.POST, false);
		
		request.setHeader(HEADER_CONTENT_TYPE, CONTENTTYPE_APPLICATION_FORM_URLENCODED);
		
		String requestBody = getParameterString(true,PARAM_GRANT_TYPE, URN_REGISTRATION, PARAM_USERNAME, username, PARAM_FIRST_NAME, firstName, PARAM_LAST_NAME, lastName,
				PARAM_PASSWORD, password, PARAM_ACCEPTED_TERMS_AND_CONDITIONS, PARAM_TRUE, PARAM_ALLOW_MARKETING_COMMUNICATIONS, acceptedMarketingCommunications ? PARAM_TRUE:PARAM_FALSE,
						PARAM_CLIENT_NAME, clientName, PARAM_CLIENT_BRAND, clientBrand, PARAM_CLIENT_MODEL, clientModel, PARAM_CLIENT_OS, clientOs);
        request.setPostData(requestBody);
						
		return request;
	}
	
	/**
	 * Request for getting a list of devices registered to a user
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetClientsRequest() {
		BBBRequest request = new BBBRequest(checkAndGetAuthenticationHost(), PATH_CLIENTS, true);
				
		request.setHeader(HEADER_CONTENT_TYPE, CONTENTTYPE_APPLICATION_FORM_URLENCODED);
		
		return request;
	}
	
	/**
	 * Request for triggering a password reset email
	 * 
	 * @param username
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createPasswordResetRequest(String username) {
		BBBRequest request = new BBBRequest(checkAndGetAuthenticationHost(), PATH_PASSWORD_RESET, BBBHttpRequestMethod.POST, false);
		
		String requestBody = getParameterString(false,PARAM_USERNAME, username);
		request.setPostData(requestBody);
		
		return request;
	}
	
	/**
	 * Request for revoking a refresh token so that it can no longer be used
	 * 
	 * @param refreshToken
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createRevokeRefreshTokenRequest(String refreshToken) {
		BBBRequest request = new BBBRequest(checkAndGetAuthenticationHost(), PATH_TOKENS_REVOKE, BBBHttpRequestMethod.POST, false);
	
		String requestBody = getParameterString(false,PARAM_REFRESH_TOKEN, refreshToken);
		request.setPostData(requestBody);
		
		return request;				
	}
	
	//Book API
	
	/**
	 * Gets books belonging to a promotion identified by the given id
	 * 
	 * @param promotionId
	 * @param order
	 * @param count
	 * @return
	 */
	public BBBRequest createGetBooksForPromotionRequest(int promotionId, Integer count, Boolean desc, String order) {
		BBBRequest request = new BBBRequest(getHostCatalogue(), PATH_CATALOGUE_BOOKS, false);
		
		appendParameters(request, PARAM_PROMOTION, promotionId, PARAM_DESC, desc, PARAM_ORDER, order, PARAM_COUNT, count);
	
		return request;
	}
	
	/**
	 * Request for getting a list of books that match the supplied search query
	 * 
	 * @param query The search query
	 * @param offset The start index from which the returned results should begin
	 * @param count The maximum number of results to return
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createSearchBooksRequest(String query, Integer offset, Integer count) {
		BBBRequest request = new BBBRequest(getHostCatalogue(), PATH_CATALOGUE_BOOKS, false);

		appendParameters(request, PARAM_QUERY, query, PARAM_OFFSET, offset, PARAM_COUNT, count);
		
		return request;
	}
	
	/**
	 * Gets a list of books that match the supplied contributor. 
	 * 
	 * @param contributorId The id of the contributor for whose books you want to retrieve. 
	 * @param offset The start index from which the returned results should begin
	 * @param count The maximum number of results to return
	 * @param desc Whether the books should be returned in descending order
	 * @param order the order by which books should be returned (TITLE, SALES_RANK, PUBLICATION_DATE, SEQUENTIAL, PRICE, AUTHOR)
	 * @param minPublicationDate the minimum publication date
	 * @param maxPublicationDate the maximum publication date 
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetBooksForContributorIdRequest(String contributorId, Integer offset, Integer count, Boolean desc, String order, String minPublicationDate, String maxPublicationDate) {
		BBBRequest request = new BBBRequest(getHostCatalogue(), PATH_CATALOGUE_BOOKS, false);
		
		request.appendParameter(PARAM_CONTRIBUTOR, contributorId.toString());
		appendParameters(request, PARAM_OFFSET, offset, PARAM_COUNT, count, PARAM_DESC, desc, PARAM_ORDER, order, PARAM_MIN_PUBLICATION_DATE, minPublicationDate, PARAM_MAX_PUBLICATION_DATE, maxPublicationDate);

		return request;
	}
	
	/**
	 * Gets a list of books that match the supplied category. 
	 * 
	 * @param categoryId The id of the category for which you want to retrieve
	 * @param offset The start index from which the returned results should begin
	 * @param count The maximum number of results to return
	 * @param desc Whether the books should be returned in descending order
	 * @param order the order by which books should be returned (TITLE, SALES_RANK, PUBLICATION_DATE, SEQUENTIAL, PRICE, AUTHOR)
	 * @param minPublicationDate the minimum publication date
	 * @param maxPublicationDate the maximum publication date
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetBooksForCategoryIdRequest(Integer categoryId, Integer offset, Integer count, Boolean desc, String order, String minPublicationDate, String maxPublicationDate) {
		BBBRequest request = new BBBRequest(getHostCatalogue(), PATH_CATALOGUE_BOOKS, false);
		
		appendParameters(request, PARAM_CATEGORY, categoryId.toString(), PARAM_OFFSET, offset, PARAM_COUNT, count, PARAM_DESC, desc, PARAM_ORDER, order, PARAM_MIN_PUBLICATION_DATE, minPublicationDate, PARAM_MAX_PUBLICATION_DATE, maxPublicationDate);
		
		return request;
	}
	
	/**
	 * Gets a list of books that match the supplied category location. 
	 * 
	 * @param categoryLocation The location id of the category for which you want to retrieve
	 * @param offset The start index from which the returned results should begin
	 * @param count The maximum number of results to return
	 * @param desc Whether the books should be returned in descending order
	 * @param order the order by which books should be returned (TITLE, SALES_RANK, PUBLICATION_DATE, SEQUENTIAL, PRICE, AUTHOR)
	 * @param minPublicationDate the minimum publication date
	 * @param maxPublicationDate the maximum publication date
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetBooksForCategoryLocationRequest(Integer categoryLocation, Integer offset, Integer count, Boolean desc, String order, String minPublicationDate, String maxPublicationDate) {
		BBBRequest request = new BBBRequest(getHostCatalogue(), PATH_CATALOGUE_BOOKS, false);
		
		appendParameters(request, PARAM_CATEGORY_LOCATION, categoryLocation.toString(), PARAM_OFFSET, offset, PARAM_COUNT, count, PARAM_DESC, desc, PARAM_ORDER, order, PARAM_MIN_PUBLICATION_DATE, minPublicationDate, PARAM_MAX_PUBLICATION_DATE, maxPublicationDate);
		
		return request;
	}
	
	/**
	 * Gets a list of books that match the supplied publisher. 
	 * 
	 * @param publisherId the id of the publisher
	 * @param offset The start index from which the returned results should begin
	 * @param count The maximum number of results to return
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetBooksForPublisherIdRequest(Integer publisherId, Integer offset, Integer count) {
		BBBRequest request = new BBBRequest(getHostCatalogue(), PATH_CATALOGUE_BOOKS, false);
		
		appendParameters(request, PARAM_PUBLISHER, publisherId.toString(), PARAM_OFFSET, offset, PARAM_COUNT, count);	
		
		return request;
	}
	
	/**
	 * Gets a list of books specified by the list of ISBNs
	 * 
	 * @param count the maximum number of results to return
	 * @param offset the offset at which the results should start
	 * @param id array of ISBNs
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetBooksRequest(Integer count, Integer offset, String... id) {
		BBBRequest request = new BBBRequest(getHostCatalogue(), PATH_CATALOGUE_BOOKS, false);
		
		appendMultipleParameters(request, PARAM_ID, id);		
		appendParameters(request, PARAM_COUNT, count, PARAM_OFFSET, offset);
		
		return request;
	}
	
	/**
	 * Gets a single BookInfo object based on its id
	 * 
	 * @param id The id of the book
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetBookRequest(String id) {
		BBBRequest request = new BBBRequest(getHostCatalogue(), PATH_CATALOGUE_BOOK, false);
		
		request.formatUrl(id);
		
		return request;
	}
	 
	/**
	 * Gets a synopsis for a book based on its id
	 * 
	 * @param id The id of the book for which we want a synopsis
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetSynopsisRequest(String id) {
		BBBRequest request = new BBBRequest(getHostCatalogue(), PATH_CATALOGUE_BOOK_SYNOPSIS, false);
		
		request.formatUrl(id);
		
		return request;
	}
		 
	/**
	 * Gets a list of books related to the book identified by id
	 * 
	 * @param id The id of the book for which we want related books
	 * @param offset The start index from which the returned results should begin
	 * @param count The maximum number of results to return
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetRelatedBooksRequest(String id, Integer offset, Integer count) {
		BBBRequest request = new BBBRequest(getHostCatalogue(), PATH_CATALOGUE_BOOK_RELATED, false);
		
		request.formatUrl(id);
		
		return request;
	}
	 	
	//Publisher API
	
	/**
	 * Retrieves one or more publishers.
	 * 
	 * @param ids A list of ids for publishers we want to retrieve. If this array is null or empty all publishers are retrieved.
	 * @param offset The start index from which the returned results should begin
	 * @param count The maximum number of results to return
	 * @param descending Set the sort order to be descending if true otherwise sort order will be ascending.
	 * @param sortOrder The criteria by which to sort by. Currently supports: 'NAME'
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetPublishersRequest(Boolean descending, String sortOrder, Integer offset, Integer count, Integer... ids) {
		BBBRequest request = new BBBRequest(getHostCatalogue(), PATH_CATALOGUE_PUBLISHERS, false);
		
		if(ids != null) {				
			request.appendParameterArray(PARAM_ID, (Object[])ids);			
			appendParameters(request, PARAM_OFFSET, offset, PARAM_COUNT, count, PARAM_DESC, descending, PARAM_ORDER, sortOrder);
		} 
				
		return request;
	}
	
	/**
	 * Retrieves a single publisher. 
	 * 
	 * @param publisherId The id of the publisher we want to retrieve
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetPublisherRequest(int publisherId) {
		BBBRequest request = new BBBRequest(getHostCatalogue(), PATH_CATALOGUE_PUBLISHER, false);
		
		request.formatUrl(publisherId);
		
		return request;
	}
	
	//Contributor API
	
	/**
	 * Retrieves one or more contributors.
	 * 
	 * @param ids A list of ids for contributors we want to retrieve. The array must not be null and contain at least one element.
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetContributorsRequest(String... ids) {
		BBBRequest request = new BBBRequest(getHostCatalogue(), PATH_CATALOGUE_CONTRIBUTORS, false);
	
		request.appendParameterArray(PARAM_ID, (Object[])ids);		
		
		return request;
	}
	
	/**
	 * Retrieves a single contributor
	 * 
	 * @param contributorId The id of the contributor we want to retrieve
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetContributorRequest(String contributorId) {
		BBBRequest request = new BBBRequest(getHostCatalogue(), PATH_CATALOGUE_CONTRIBUTOR, false);
		
		request.formatUrl(contributorId);
		
		return request;
	}
		
	//Category API
	
	/**
	 * Returns all listed, top level categories.
	 * 
	 * @param recommended Optional parameter. If set to true, only retrieves categories which are recommended else if set to false, return categories which are not recommended
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetAllCategoriesRequest(Boolean recommended) {
		BBBRequest request = new BBBRequest(getHostCatalogue(), PATH_CATALOGUE_CATEGORIES, false);
		
		appendParameters(request, PARAM_RECOMMENDED, recommended);
				
		return request;
	}
	
	/**
	 * Returns a single category based on its id.
	 * 
	 * @param id The id of the category we want to retrieve.
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetCategoryWithIdRequest(int id) {
		BBBRequest request = new BBBRequest(getHostCatalogue(), PATH_CATALOGUE_CATEGORY, false);
		
		request.formatUrl(id);
		
		return request;
	}
	
	/**
	 * Returns a single category based on its location id.
	 * 
	 * @param locationId The location id of the category we want to retrieve.
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetCategoryWithLocationIdRequest(int locationId) {
		BBBRequest request = new BBBRequest(getHostCatalogue(), PATH_CATALOGUE_CATEGORY, false);
		
		appendParameters(request, PARAM_LOCATION, locationId);
		
		return request;
	}
	
	/**
	 * Returns a list of categories based on its name. If name is null will return all top level categories
	 * 
	 * @param name The name of the category
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetCategoryRequest(String name) {
		BBBRequest request = new BBBRequest(getHostCatalogue(), PATH_CATALOGUE_CATEGORIES, false);

		appendParameters(request, PARAM_SLUG, name);

		return request;
	}
	
	//Book Price API
	
	/**
	 * Gets the prices for the books identified by the array of ISBNs for a particular country
	 * 
	 * @param isbn An array of one or more ISBNs for which we want prices for. Must contain at least one element
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetPricesRequest(String... isbn) {
		BBBRequest request = new BBBRequest(getHostCatalogue(), PATH_CATALOGUE_PRICES, false);
		
		request.appendParameterArray(PARAM_BOOK, (Object[])isbn);
		
		return request;
	}
	
	//Search Service API
	
	/**
	 * Retrieves auto-complete suggestions for the given search term
	 * 
	 * @param query The query to search for.
	 * @param limit The maximum number of results to return
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetSuggestionsRequest(String query, int limit) {
		BBBRequest request = new BBBRequest(getHostSearch(), PATH_SEARCH_SUGGESTIONS, false);
		
		appendParameters(request, PARAM_QUERY, query, PARAM_LIMIT, limit);
		
		return request;
	}
	
	public BBBRequest createSearchRequest(String query, String order, Boolean desc, Integer offset, Integer count) {
		BBBRequest request = new BBBRequest(getHostSearch(), PATH_SEARCH_BOOKS, false);
		
		appendParameters(request, PARAM_QUERY, query, PARAM_ORDER, order, PARAM_DESC, desc, PARAM_OFFSET, offset, PARAM_COUNT, count);
		
		return request;
	}
	
	//Promotions API
	
	/**
	 * Gets a list of promotions for the location
	 * 
	 * @param locationId The location id
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetPromotionsByLocationRequest(Integer locationId, Integer offset, Integer count) {
		BBBRequest request = new BBBRequest(getHostMarketing(), PATH_MARKETING_PROMOTIONS, false);
		
		appendParameters(request, PARAM_LOCATION, locationId);
		
		return request;
	}
	
	/**
	 * Gets the promotion identified by id
	 * 
	 * @param promotionId The id of the promotion we want
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetPromotionByIdRequest(int promotionId, Integer offset, Integer count) {
		BBBRequest request = new BBBRequest(getHostMarketing(), PATH_MARKETING_PROMOTION, false);
		
		request.formatUrl(promotionId);
		
		return request;
	}
	
	/**
	 * Gets the promotion identified by its name
	 * 
	 * @param promotionName The name of the promotion we want
	 * @return The {@link BBBRequest} object
	 */
	public BBBRequest createGetPromotionByNameRequest(String promotionName, Integer offset, Integer count) {
		BBBRequest request = new BBBRequest(getHostMarketing(), PATH_MARKETING_PROMOTIONS, false);
		
		appendParameters(request, PARAM_NAME, promotionName);
		
		return request;
	}
		
	/*
	 * Helper method for simplifying the addition of multiple parameters to a request. The value at index 0 in the array is treated as a 'name' and the next element is the 'value' for that name.
	 * Each subsequent pair of elements is a name/value pairing therefore the number of parameters passed in the array must be even
	 */
	private void appendParameters(BBBRequest request, Object... values) {
		
		if(values == null || values.length % 2 != 0) {
			return;
		}
		
		String name;
		Object value;
		
		for(int i=0; i<values.length; i=i+2) {
			name = (String)values[i];
			value = values[i+1];
			
			if(value != null) {
				request.appendParameter(name, value.toString());
			}
		}
	}

	private void appendMultipleParameters(BBBRequest request, String name, String[] values) {

		if(name == null || values == null) {
			return;
		}
		
		for(int i=0; i<values.length; i++) {
			
			if(values[i] != null) {
				request.appendParameter(name, values[i].toString());
			}
		}
	}
		
	private String getSimpleJSONObjectString(Object... values) {
		
		if(values == null || values.length % 2 != 0) {
			return null;
		}
		
		JsonObject object = new JsonObject();
				
		String name;
		Object value;
		
		for(int i=0; i<values.length; i=i+2) {
			name = (String)values[i];
			value = values[i+1];
			
			if(value != null) {
				
				if(value instanceof Character) {
					object.addProperty(name, (Character)value);
				} else if(value instanceof Boolean) {
					object.addProperty(name, (Boolean)value);
				} else if(value instanceof Number) {
					object.addProperty(name, (Number)value);
				} else {
					object.addProperty(name, value.toString());
				}
			}
		}
		
		return object.toString();
	}
		
	private String getParameterString(boolean URLEncoded, String... parameters) {
		StringBuilder builder = new StringBuilder();		
				
		for(int i=0; i<parameters.length; i++) {
			
			if(i > 0) {
				builder.append('&');
			}
			
			builder.append(parameters[i]);
			builder.append('=');
            if (URLEncoded){
                try {
                    builder.append(URLEncoder.encode(parameters[++i], "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    LogUtils.d(TAG, e.getMessage(), e);
                }
            } else {
                builder.append(parameters[++i]);
            }

		}
				
		return builder.toString();
	}
	
	private String checkAndGetDefaultHost() {
		
		if(mHostDefault == null) {
			throw new RuntimeException("Host not set. Use setDefaultHost() to set a host before creating requests.");
		}
		
		return mHostDefault;
	}
	
	private String checkAndGetAuthenticationHost() {
		
		if(mHostAuthentication == null) {
			throw new RuntimeException("Authentication host not set. Use setAuthenticationHost() to set a host before creating requests.");
		}
		
		return mHostAuthentication;
	}
	
	public String checkAndGetKeyHost() {
				
		if(mHostKeyServer == null) {
			throw new RuntimeException("Key server host not set. Use setKeyServerHost() to set a host before creating requests.");
		}
		
		return mHostKeyServer;
	}
}