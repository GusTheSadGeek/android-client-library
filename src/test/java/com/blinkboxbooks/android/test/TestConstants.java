/*******************************************************************************
 * Copyright (c) 2013 blinkbox Entertainment Limited. All rights reserved.
 *******************************************************************************/
package com.blinkboxbooks.android.test;

/**
 * Class for holding contstants used for unit tests
 */
public interface TestConstants {
		
	public static final boolean VOLLEY_ENABLED = false;
		
	//Custom hosts. Set these when a service has been deployed to a different url than the base
	public static final String HOST_CATALOGUE = null;//"http://backend-build-2:9128/service";
	public static final String HOST_SEARCH =  null;//"http://backend-build-2:9131/service";
	public static final String HOST_PROMOTION =  null;//"http://backend-build-2:9130/service";
	public static final String HOST_LIBRARY =  null;//"http://backend-build-2:9129/service";
	public static final String HOST_BOOKMARK =  null;//"http://backend-build-2:9136/service";
	public static final String HOST_BASKET =  null;//"http://backend-build-2:9132/service";
	public static final String HOST_CREDIT = null;
	public static final String HOST_PAYMENTS =  null;//"http://backend-build-2:9133/service";
	public static final String HOST_CREDIT_CARDS =  null;//"http://backend-build-2:9134/service";
	public static final String HOST_CLUBCARDS =  null;//"http://backend-build-2:9135/service";
	
	/* QA Environment parameters */
//	public static final String HOST = "https://qa.mobcastdev.com/service";
//	public static final String AUTHENTICATE_HOST = "https://auth.mobcastdev.com";
//	public static final String KEY_HOST = "http://keys.mobcastdev.com";
//	
//	public static final String USERNAME = "blinkbox_android_unittests@gmail.com";
//	public static final String PASSWORD = "blinkbox";
//	public static final String CLIENT_ID = "urn:blinkbox:zuul:client:5589";
//	public static final String CLIENT_SECRET = "ShuiRFAyvXjy9SAA8vTom950rZSuyPG59KpowkG4knA";
//	
//	public static final String BRAIN_TREE_ENCRYPTION_KEY = "MIIBCgKCAQEA3nQA3AwLu/EDRDWww7ocqM5PeC4tANAHzyqczWC9KTOqD1hvMjiP7T/wKZUnz81iNriYJIFMY47UKTdysvFBNHt75OJJbBRa1Hf+/b8uvUBHfkbA2n+JE5GQhRU+oM+TMJSJ86Q5phVda0ddTwS8lH8D+18nXQZspbyxpfwzDiyzG9n5lX8/27byrSDixguDNfbSoTwgvmo81Obhz/ZuXjWW9U22Ss9xSznlCcnIpGN/u0BwMaiTcGUFPFhThr0R9Ia0h1fRRobt+Uh9BvkE3D1GBRtkP7dPFs7nJJQdt6pSW7zEVNjLuctX3y/C3dNvb9DeyoGT0kRSyAB3+65gNQIDAQAB";
//	public static final String LIBRARY_ITEM_ID = "304+312";
//	
//	public static final String LIBRARY_ITEM_ID_RESTORE_DELETE = "305+313";
	/* -------------------------- */
	
	/* Production Environment parameters */
	public static final String HOST = "https://api.blinkboxbooks.com/service";
	public static final String AUTHENTICATE_HOST = "https://auth.blinkboxbooks.com";
	public static final String KEY_HOST = "http://keys.blinkboxbooks.com";
	
	public static final String USERNAME = "android_unittests@blinkbox.com";
	public static final String PASSWORD = "b%l^i!n&*bo(x)";
	public static final String CLIENT_ID = "urn:blinkbox:zuul:client:12143";
	public static final String CLIENT_SECRET = "jnJ9lF6iT01wTFt2PhqCmN2RcT4_iy7YW8aMgNtMWTU";	
	
	public static final String BRAIN_TREE_ENCRYPTION_KEY = "MIIBCgKCAQEAxKQ8pPwfcCRhU8ZBdwj5wSSPCvQYq35M++4dWo1n/1aABv/h/XAsOAeH0F3WCPguxvN+tzjWUS1yvYg55QQhRhuIjCL+LJkqRnbBSEFUng4ROsRVZT5bTS9yREl6wGmbqF57uESpN2auDM6yHcWyztV8WdGgdtO09HDHyUSYNGUuE2J+bp2UKc3sojl31xrC1Soym6qEShtCRKA1n6QNcXhsXk2T+py/g5UOHrwdNrqtwHvh92PmytUvTBlDlcuEt0QqgjVyKGyBWGgqQ/bCSbgWDeVJnuLjJIw6DiymPfa97G+vmnyXlZ6ds2WwJEP2jBvJE7q61V+wV+pgKve9CQIDAQAB";
	
	public static final String LIBRARY_ITEM_ID = "45032+48362";
	
	public static final String LIBRARY_ITEM_ID_RESTORE_DELETE = "45033+48363";
	/* -------------------------- */
	
	public static final String DOWNLOAD_EPUB_FILE = "http://media.blinkboxbooks.com/9781/444/765/588/c29874b3fdd35466148fa4725619c80f.epub";
	
	public static final String CLIENT_NAME = "android_unit_tests_name_2";
    public static final String CLIENT_NAME2 = "android%_unit$_tests£_name!_2";
	public static final String CLIENT_MODEL = "android_unit_tests_model_2";
	public static final String CLIENT_BRAND = "android_unit_tests_brand_2";
	public static final String CLIENT_OS = "android_unit_tests_os_2";
	
	public static final String TEST_CLUBCARD = "634004025014064256";
	
	public static final String QUERY = "Harry Potter";
	public static final String BOOK_BASKET_ITEM_ISBN = "9780007344086";
	public static final String BOOK_ID = "9781782110194";
	public static final String ISBN = "9781782110194";
	public static final String ISBN_2 = "9781782110194";
	public static final Integer LOCATION_ID = 1;
	public static final String PROMOTION_NAME = "books-in-the-news";
	public static final String CATEGORY_NAME = "Arts";
	public static final String SORT_ORDER_NAME = "NAME";
	
	
	public static final String CREDIT_CARD_NUMBER = "5555555555554444";
	public static final String CREDIT_CARD_CVV2 = "579";
	public static final String CREDIT_CARD_EXPIRATION_MONTH = "10";
	public static final String CREDIT_CARD_EXPIRATION_YEAR = "2014";
	public static final String CREDIT_CARD_HOLDER_NAME = "Jim Harry";
	
	public static final String ADDRESS_LINE_1 = "25 Bank Street";
	public static final String ADDRESS_LINE_2 = "Canary Wharf";
	public static final String LOCALITY = "London";
	public static final String POSTCODE = "EC1 5RF";
	public static final String POSTCODE_UPDATED = "EC1 5RF";
	
	public static final String[] CONTRIBUTOR_NAMES = {"Smith", "Robert"};
	
	public static final String CONTRIBUTOR_ID = "ff4c2c9d4f4d763595a224395ebe413bd9140f55";
	
	public static final Integer[] PUBLISHER_IDS = {485, 485, 485};
	public static final String[] CONTRIBUTOR_IDS = {CONTRIBUTOR_ID, CONTRIBUTOR_ID, CONTRIBUTOR_ID};
		
	public static final int CATEGORY_LOCATION = 100;
	public static final int CATEGORY_ID = 35;
	public static final int COUNT = 20;
	public static final int LIMIT = 10;
	public static final int OFFSET = 0;
	public static final int PROMOTION_ID = 1;
	public static final int PUBLISHER_ID = 485;
	
	public static Long LAST_SYNC_TIME = 1272319624608l;
}
