package com.blinkboxbooks.android.api.net;

public interface BBBRequestManagerInterface {

	public void setAccessToken(String accessToken);

	public void clientInvalidated();
	
	public void elevationRequired();
	
	public String getAccessToken();
	
	public String getRefreshToken();
	
	public String getClientId();
	
	public String getClientSecret();
}
