package com.srm.coreframework.auth;

import java.io.Serializable;

public class AuthInfo implements Serializable {

	private static final long serialVersionUID = 3566346322168434117L;
	
	private String screenCd;
	
    private String operationCd;
    
    private boolean tokenCheck;

	public String getScreenCd() {
		return screenCd;
	}

	public void setScreenCd(String screenCd) {
		this.screenCd = screenCd;
	}

	public String getOperationCd() {
		return operationCd;
	}

	public void setOperationCd(String operationCd) {
		this.operationCd = operationCd;
	}

	public boolean isTokenCheck() {
		return tokenCheck;
	}

	public void setTokenCheck(boolean tokenCheck) {
		this.tokenCheck = tokenCheck;
	}
}
