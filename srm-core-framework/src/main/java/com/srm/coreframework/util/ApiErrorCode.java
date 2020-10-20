/**
 * 
 */
package com.srm.coreframework.util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public enum ApiErrorCode{
	SUCCESS(200),CREATED(201),UNDEFINED_CODE(0),DATA_ACCESS_PROBLEM(3001), INTERNAL_SERVER_ERROR(500),
	BAD_REQUEST(400),UNAUTHORIZED(401),NOT_FOUND(404),NO_RECORDS_FOUND(1001),DUPLICATE_CODE(1003),DUPLICATE_CODE_MULTIPLE(1004);
	
	private final int code1;
	private ApiErrorCode(final int code) {
		this.code1 = code;
	}
	
	private static final Map<String, ApiErrorCode> LOOKUP = new HashMap<String, ApiErrorCode>();

	
	static {
		for (ApiErrorCode responseCode : ApiErrorCode.values()) {
			LOOKUP.put(responseCode.name(), responseCode);
		}
	}

	public static ApiErrorCode getResponseObject(final String name) {
		final ApiErrorCode responseCode = LOOKUP.get(name);
		if (responseCode == null) {
			return UNDEFINED_CODE;
		} else {
			return responseCode;
		}
	}
	
	public static int getResponseCode(final String name) {
		ApiErrorCode responseCode = LOOKUP.get(name);
		if (responseCode == null) {
			return 0;
		} else {
			return responseCode.code1;
		}
	}
	
	public static String getAPIResponseCode(final String name) {
		ApiErrorCode responseCode = LOOKUP.get(name);
		if (responseCode == null) {
			return "";
		} else {
			return String.valueOf(responseCode.code1);
		}
	}
}
