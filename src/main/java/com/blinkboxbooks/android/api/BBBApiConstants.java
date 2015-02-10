/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.api;

/**
 * Holder class for all constants used throughout the SDK
 */
public interface BBBApiConstants {

	//Content Types
	public static final String CONTENTTYPE_APPLICATION_BBB_JSON = "application/vnd.blinkboxbooks.data.v1+json";
	public static final String CONTENTTYPE_APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";

	//Headers
	public static final String HEADER_CONTENT_TYPE = "Content-Type";
	public static final String HEADER_AUTHORIZATION = "Authorization";
	public static final String HEADER_WWW_AUTHENTICATE = "WWW-Authenticate";

	//Header fields
	public static final String HEADER_FIELD_BEARER_ERROR = "Bearer error";

	//Paths to resources
	public static final String PATH_OAUTH2_TOKEN = "/oauth2/token";
	public static final String PATH_CLIENTS = "/clients";
	public static final String PATH_CLIENT_ID = "/clients/%s";

	public static final String PATH_PASSWORD_RESET = "/password/reset";

	public static final String PATH_CATALOGUE_BOOKS = "/catalogue/books";
	public static final String PATH_CATALOGUE_BOOK = "/catalogue/books/%s";
	public static final String PATH_CATALOGUE_BOOK_RELATED = "/catalogue/books/%s/related";
	public static final String PATH_CATALOGUE_BOOK_SYNOPSIS = "/catalogue/books/%s/synopsis";
	public static final String PATH_CATALOGUE_CATEGORIES = "/catalogue/categories";
	public static final String PATH_CATALOGUE_CATEGORY = "/catalogue/categories/%s";
	public static final String PATH_CATALOGUE_CONTRIBUTORS = "/catalogue/contributors";
	public static final String PATH_CATALOGUE_CONTRIBUTOR = "/catalogue/contributors/%s";
	public static final String PATH_CATALOGUE_PRICES = "/catalogue/prices";
	public static final String PATH_CATALOGUE_PUBLISHERS = "/catalogue/publishers";
	public static final String PATH_CATALOGUE_PUBLISHER = "/catalogue/publishers/%s";
	public static final String PATH_CATALOGUE_SUGGESTIONS = "/catalogue/suggestions";

	public static final String PATH_SEARCH_SUGGESTIONS = "/search/suggestions";
	public static final String PATH_SEARCH_BOOKS = "/search/books";

	public static final String PATH_MARKETING_PROMOTIONS = "/marketing/promotions";
	public static final String PATH_MARKETING_PROMOTION = "/marketing/promotions/%s";

	public static final String PATH_MY_LIBRARY = "/my/library";
	public static final String PATH_MY_LIBRARY_ITEM = "/my/library/%s";
	public static final String PATH_MY_LIBRARY_CURRENT = "/my/library/current";
	public static final String PATH_MY_LIBRARY_CURRENT_PURCHASED = "/my/library/current/purchases";
	public static final String PATH_MY_LIBRARY_CURRENT_SAMPLED = "/my/library/current/samples";
	public static final String PATH_MY_LIBRARY_SAMPLES = "/my/library/samples";
	public static final String PATH_MY_LIBRARY_ONDEVICE = "/my/library/ondevice";
	public static final String PATH_MY_LIBRARY_ONDEVICE_SAMPLES = "/my/library/ondevice/samples";
	public static final String PATH_MY_LIBRARY_ARCHIVED = "/my/library/archived";
	public static final String PATH_MY_LIBRARY_DELETED = "/my/library/deleted";
	public static final String PATH_MY_CLUBCARDS = "/my/clubcards";

	public static final String PATH_READING_MY_BOOKMARKS = "/my/bookmarks";
	public static final String PATH_READING_MY_BOOKMARKS_ITEM = "/my/bookmarks/%s";

	public static final String PATH_MY_BASKETS = "/my/baskets";
	public static final String PATH_MY_BASKETS_ITEMS = "/my/baskets/items";
	public static final String PATH_MY_BASKETS_ITEMS_ = "/my/baskets/items/%s";

	public static final String PATH_MY_PAYMENTS = "/my/payments";

	public static final String PATH_MY_CREDIT = "/my/credit";
	public static final String PATH_MY_CREDITCARDS = "/my/creditcards";
	public static final String PATH_MY_CREDITCARDS_ = "/my/creditcards/%s";

	public static final String PATH_TOKENS_REVOKE = "/tokens/revoke";
	
	//Parameters
	public static final String PARAM_ACCEPTED_TERMS_AND_CONDITIONS = "accepted_terms_and_conditions";
	public static final String PARAM_ACCESS_TOKEN = "access_token";
	public static final String PARAM_LINE1 = "line1";
	public static final String PARAM_LINE2 = "line2";
	public static final String PARAM_ALLOW_MARKETING_COMMUNICATIONS = "allow_marketing_communications";
	public static final String PARAM_ANNOTATION = "annotation";
	public static final String PARAM_AUTHTOKEN_TYPE = "authtoken_type";
	public static final String PARAM_BEARER_ = "Bearer ";
	public static final String PARAM_BILLING_ADDRESS = "billingAddress";
	public static final String PARAM_BOOK = "book";
	public static final String PARAM_BOOKMARK_TYPE = "bookmarkType";
	public static final String PARAM_CARDHOLDER_NAME = "cardholderName";
	public static final String PARAM_CATEGORY = "category";
	public static final String PARAM_CATEGORY_LOCATION = "categoryLocation";
	public static final String PARAM_CLIENT_BRAND = "client_brand";
	public static final String PARAM_CLIENT_ID = "client_id";
	public static final String PARAM_CLIENT_NAME = "client_name";
	public static final String PARAM_CLIENT_MODEL = "client_model";
	public static final String PARAM_CLIENT_OS = "client_os";
	public static final String PARAM_CLIENT_SECRET = "client_secret";
	public static final String PARAM_CLUBCARD = "clubcard";
	public static final String PARAM_COLOUR = "colour";
	public static final String PARAM_CONTRIBUTOR = "contributor";
	public static final String PARAM_COUNT = "count";
	public static final String PARAM_COUNTRY = "country";
	public static final String PARAM_CREATED_BY = "createdBy";
	public static final String PARAM_CREDIT_CARD = "creditCard";
	public static final String PARAM_CVV = "cvv";
	public static final String PARAM_DEFAULT = "default";
	public static final String PARAM_DELETE_ANNOTATION = "deleteAnnotation";
	public static final String PARAM_DELETE_COLOUR = "deleteColour";
	public static final String PARAM_DELETE_NAME = "deleteName";
	public static final String PARAM_DELETE_STYLE = "deleteStyle";
	public static final String PARAM_DELETED_BY = "deletedBy";
	public static final String PARAM_DESC = "desc";
	public static final String PARAM_END = "end";
	public static final String PARAM_ERROR_REASON = "error_reason";
	public static final String PARAM_EXPIRATION_MONTH = "expirationMonth";
	public static final String PARAM_EXPIRATION_YEAR = "expirationYear";
	public static final String PARAM_FALSE = "false";
	public static final String PARAM_FIRST_NAME = "first_name";
	public static final String PARAM_GRANT_TYPE = "grant_type";
	public static final String PARAM_GUID = "guid";
	public static final String PARAM_HREF = "href";
	public static final String PARAM_ID = "id";
	public static final String PARAM_IMAGES = "images";
	public static final String PARAM_ISBN = "isbn";
	public static final String PARAM_ITEMS = "items";
	public static final String PARAM_KEY = "key";
	public static final String PARAM_LAST_NAME = "last_name";
	public static final String PARAM_LAST_SYNC_DATE = "lastSyncDate";
	public static final String PARAM_LAST_SYNC_DATE_TIME = "lastSyncDateTime";
	public static final String PARAM_LIBRARY_ITEM_ID = "libraryItemId";
	public static final String PARAM_LIMIT = "limit";
	public static final String PARAM_LINKS = "links";
	public static final String PARAM_LOCALITY = "locality";
	public static final String PARAM_LOCATION = "location";
	public static final String PARAM_MIN_PUBLICATION_DATE = "minPublicationDate";
	public static final String PARAM_MAX_PUBLICATION_DATE = "maxPublicationDate";
	public static final String PARAM_NAME = "name";
	public static final String PARAM_SLUG = "slug";
	public static final String PARAM_NUMBER = "number";
	public static final String PARAM_NUMBER_OF_RESULTS = "numberOfResults";
	public static final String PARAM_OFFSET = "offset";
	public static final String PARAM_ORDER = "order";
	public static final String PARAM_PASSWORD = "password";
	public static final String PARAM_POSITION = "position";
	public static final String PARAM_POSTCODE = "postcode";
	public static final String PARAM_PREVIEW = "preview";
	public static final String PARAM_PROMOTION = "promotion";
	public static final String PARAM_PUBLISHED = "published";
	public static final String PARAM_PUBLISHER = "publisher";
	public static final String PARAM_QUERY = "q";
	public static final String PARAM_READING_PERCENTAGE = "readingPercentage";
	public static final String PARAM_READING_STATUS = "readingStatus";
	public static final String PARAM_RECOMMENDED = "recommended";
	public static final String PARAM_REFRESH_TOKEN = "refresh_token";
	public static final String PARAM_REL = "rel";
	public static final String PARAM_SAMPLE_AVAILABLE = "sampleAvailable";
	public static final String PARAM_SRC = "src";
	public static final String PARAM_START = "start";
	public static final String PARAM_STATUS = "status";
	public static final String PARAM_STYLE = "style";
	public static final String PARAM_TARGET_GUID = "targetGuid";
	public static final String PARAM_TEXT = "text";
	public static final String PARAM_TITLE = "title";
	public static final String PARAM_TOKEN = "token";
	public static final String PARAM_TRUE = "true";
	public static final String PARAM_TYPE = "type";
	public static final String PARAM_UPDATED_BY = "updatedBy";
	public static final String PARAM_USERNAME = "username";
	public static final String PARAM_USER_ID = "userid";
	public static final String PARAM_CARDTYPE_VISA = "Visa";
	public static final String PARAM_CARDTYPE_MASTERCARD = "MasterCard";
	public static final String PARAM_CARDTYPE_AMERICANEXPRESS = "AmericanExpress";

	//Error codes
	public static final int ERROR_CONNECTION_FAILED = 600;
	public static final int ERROR_PARSER = 601;
	public static final int ERROR_UNKNOWN = 602;

	//Errors
	public static final String ERROR_BOOKMARKS_NOT_FOUND = "bookmarks_not_found";
	public static final String ERROR_REQUIRED_FIELD = "required_field";
	public static final String ERROR_BOOKMARK_ALREAD_EXISTS = "bookmark_already_exist";
	public static final String ERROR_INVALID_ISBN = "invalid_isbn";
	public static final String ERROR_COUNTRY_GEOBLOCKED = "country_geoblocked";
	public static final String ERROR_USERNAME_ALREADY_TAKEN = "username_already_taken";
	public static final String ERROR_CLIENT_LIMIT_REACHED = "client_limit_reached";
	public static final String ERROR_FUTURE_PUBLICATION_DATE = "future_publication_date";
	public static final String ERROR_INVALID_CLIENT = "invalid_client";
	public static final String ERROR_INVALID_GRANT = "invalid_grant";
	public static final String ERROR_INVALID_REQUEST = "invalid_request";
	public static final String ERROR_INVALID_TOKEN = "invalid_token";
	public static final String ERROR_ITEM_ALREADY_IN_THE_BASKET = "item_already_in_the_basket";
	public static final String ERROR_UNAUTHORIZED_CLIENT = "unauthorized_client";
	public static final String ERROR_UNVERIFIED_IDENTITY = "unverified_identity";
	public static final String ERROR_BOOK_ALREADY_PURCHASED = "book_already_purchased";
	public static final String ERROR_BASKET_ITEMS_LIMIT_REACHED = "basket_items_limit_reached";
	public static final String ERROR_NO_VALID_PAYMENT_METHODS = "no_valid_payment_methods";
	public static final String ERROR_INVALID_EXPIRY_DATE = "invalid_expiry_date";
	public static final String ERROR_MISSING_CREDIT_CARD_DATA = "missing_credit_card_data";
	public static final String ERROR_MISSING_ADDRESS = "missing_address";
	public static final String ERROR_UNDISTRIBUTED_BOOK = "undistributed_book";
	
	//Authentication
	public static final String ACCOUNT_TYPE = "com.blinkbox.books";
	public static final String AUTHTOKEN_TYPE = "com.blinkbox.books";

	//URNS
	public static final String URN_REGISTRATION = "urn:blinkbox:oauth:grant-type:registration";
	public static final String URN_IMAGE_COVER = "urn:blinkboxbooks:image:cover";
	public static final String URN_IMAGE_CONTRIBUTOR = "urn:blinkboxbooks:image:contributor";
	public static final String URN_CONTRIBUTOR = "urn:blinkboxbooks:schema:contributor";
	public static final String URN_BOOKS = "urn:blinkboxbooks:schema:books";
	public static final String URN_BOOKMARK = "urn:blinkboxbooks:schema:bookmark";
	public static final String URN_PUBLISHER = "urn:blinkboxbooks:schema:publisher";
	public static final String URN_FULL_MEDIA = "urn:blinkboxbooks:schema:fullmedia";
	public static final String URN_MEDIA_KEY = "urn:blinkboxbooks:schema:mediakey";
	public static final String URN_SAMPLE_MEDIA = "urn:blinkboxbooks:schema:samplemedia";
	public static final String URN_SUGGESTION_BOOK = "urn:blinkboxbooks:schema:suggestion:book";
	public static final String URN_SUGGESTION_CONTRIBUTOR = "urn:blinkboxbooks:schema:suggestion:contributor";
	public static final String URN_ERROR = "urn:blinkboxbooks:schema:error";
	public static final String URN_CLUBCARD = "urn:blinkboxbooks:schema:clubcard";

	//Bookmark types
	public static final String BOOKMARK_TYPE_BOOKMARK = "BOOKMARK";
	public static final String BOOKMARK_TYPE_HIGHLIGHT = "HIGHLIGHT";
	public static final String BOOKMARK_TYPE_NOTE = "NOTE";
	public static final String BOOKMARK_TYPE_CROSS_REFERENCE = "CROSS_REFERENCE";
	public static final String BOOKMARK_TYPE_LAST_READ_POSITION = "LAST_READ_POSITION";

	//Order values
	public static final String ORDER_TITLE = "TITLE";
	public static final String ORDER_SALES_RANK = "SALES_RANK";
	public static final String ORDER_PUBLICATION_DATE = "PUBLICATION_DATE";
	public static final String ORDER_SEQUENTIAL = "SEQUENTIAL";
	public static final String ORDER_PRICE = "PRICE";
	public static final String ORDER_AUTHOR = "AUTHOR";
	public static final String ORDER_RELEVANCE = "RELEVANCE";
	public static final String ORDER_POPULARITY = "POPULARITY";
}