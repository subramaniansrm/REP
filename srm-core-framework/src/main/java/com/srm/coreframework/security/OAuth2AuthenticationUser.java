package com.srm.coreframework.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import com.srm.coreframework.response.CustomUserDetails;


public class OAuth2AuthenticationUser extends OAuth2Authentication {

	private static final long serialVersionUID = 1L;
	private CustomUserDetails customUserDetails;

	public OAuth2AuthenticationUser(OAuth2Request storedRequest, Authentication userAuthentication) {
		super(storedRequest, userAuthentication);
	}

	public CustomUserDetails getCustomUserDetails() {
		return customUserDetails;
	}

	public void setCustomUserDetails(CustomUserDetails customUserDetails) {
		this.customUserDetails = customUserDetails;
	}
}