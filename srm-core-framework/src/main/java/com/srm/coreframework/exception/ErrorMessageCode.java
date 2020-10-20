package com.srm.coreframework.exception;
/**
 * It holds error message code.
 *
 */
public interface ErrorMessageCode {

	public static final String USER_NOT_FOUND="framework.error-message_user_not_found-exception";
	 public static final String INVALID_USER="framework.error-message.invalid-user-exception";
	 public static final String INVALID_REQUEST="framework.error-message.invalid-request-parameter";
	 public static final String INVALID_405="framework.error-message.invalid-405-exception";
	 public static final String INVALID_404="framework.error-message.invalid-404-exception";
	 public static final String INVALID_403="framework.error-message.invalid-403-exception";
	 public static final String INVALID_502="framework.error-message.invalid-502-exception";
			
	 public static final String INTERNAL_SERVER_ERROR = "500";
	 public static final String NO_RECORDS_FOUND = "1001";
	 public static final String DATA_ACCESS_PROBLEM = "1002";
	 
	 public static final String NO_QUERY_FOUND = "5000";

	 public static final String FILE_UPLOAD_DOWNLOAD = "5001";//File Upload/Download Error Occurred
	 public static final String FILE_NOT_FOUND = "5002";//File Upload/Download Error Occurred

	 //For TwoFactor/OTP Service
	 public static final String MOBILE_NUMBER = "6000";
	 public static final String EMAIL = "6001";
	 public static final String USER_NAME = "6002";
	 public static final String INVALID_OTP = "6003";
	 public static final String AUTH_ERROR = "6004";
	 
	 // Dynamic Menu Loading Screen Path
	 public static final String SCREEN_PATH_NOT_FOUND = "7000"; 
	 public static final String ITEM_NOT_FOUND = "7001";
	 
	 
}
