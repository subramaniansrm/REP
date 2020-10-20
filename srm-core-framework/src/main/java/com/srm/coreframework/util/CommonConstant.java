/**
 * 
 */
package com.srm.coreframework.util;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 */
public interface CommonConstant {
	public static final int TWO_MB = 2097152;
	
	public static final String AUTHORITY = "authority";
	public static final String SCREEN_ID = "screen_id";
	public static final String USER_NAME = "username";
	public static final String LOOK_UP_END_POINT = "/lookup";
	public static final String DUMMY = "DUMMY";
	public static final String ITEM_NAME = "item_name";
	
	
	public static final String EXCEL = "EXCEL";
	public static final String PDF = "PDF";
	


	public static final Integer CONSTANT_ZERO = 0;
	public static final Integer CONSTANT_ONE = 1;
	public static final Integer CONSTANT_TWO = 2;
	public static final Integer CONSTANT_THREE = 3;
	public static final Integer CONSTANT_FOUR = 4;
	public static final Integer CONSTANT_FIVE = 5;
	public static final Integer CONSTANT_SIX = 6;
	public static final Integer CONSTANT_SEVEN = 7;
	public static final Integer CONSTANT_EIGHT = 8;
	public static final Integer CONSTANT_NINE = 9;
	public static final Integer CONSTANT_TEN = 10;
	public static final Integer CONSTANT_ELEVEN = 11;
	public static final Integer CONSTANT_THIRTEEN = 13;
	public static final Integer CONSTANT_FOURTEEN = 14;

	public static final Integer CONSTANT_FIFTEEN = 15;

	public static final Integer CONSTANT_TWELVE = 12;
	public static final Integer NEW_PASSWORD_LENGTH = 5;

	public static final Integer NOT_WORKING = 9;

	public static final String REQUEST_ESCALATED = "Request Escalated ";

	public static final String SUCCESS_CODE = "200";
	public static final String ERROR_CODE = "500";
	public static final Integer RESULT_FAULURE = 303;
	public static final Integer RESULT_VALIDATION = 302;
	public static final String FAILURE_CODE = "400";
	public static final String FAILURE_COUNT = "301";
	public static final String NULL = null;

	public static final Character FLAG_ZERO = '0';
	public static final Character FLAG_ONE = '1';

	public static final String STRING_Y = "Y";
	public static final String STRING_N = "N";
	public static final String STRING_A = "A";

	public static final String STRING_ZERO = "0";
	public static final String STRING_ONE = "1";
	public static final String STRING_TWO = "2";
	public static final String STRING_THREE = "3";

	public static final String FORMAT_DD_MM_YYYY_HYPHEN = "dd-MM-yyyy";
	public static final String FORMAT_DD_MM_YYYY_HH_MM_SS_HYPHEN = "dd-MM-yyyy h:mm:ss";

	public static final String ONE = "1";
	public static final String TWO = "2";
	public static final String ZERO = "0";
	public static final Integer TEN = 10;

	public static final Boolean BOOLEAN_TRUE = true;
	public static final Boolean BOOLEAN_FALSE = false;

	public static final String CAPITAL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String SMALL_CHARS = "abcdefghijklmnopqrstuvwxyz";
	public static final String NUMBERS = "0123456789";
	public static final String SYMBOLS = "!@#$%^&*_=+-/.?<>)";
	public static final String HYPHEN = "-";
	public static final String SLASH = "/";
	public static final String EMPTY = " ";
	public static final String BLANK = "";

	public static final String MAIL_TO = "aruljothia@srmtech.com";
	public static final String MAIL_FROM = "noreply@srmtech.com";
	public static final String MAIL_HOST = "mail.srmtech.com";

	public static final String MAIL_HOST_USER = "chn.srmtech.com";

	public static final Integer TRANSACTION_LOG_TIMELIMIT = 30;

	public static final String SUPERADMIN = "SuperAdmin";
	public static final String ADMINROLE = "AdminRole";
	public static final String ENGLISH = "English";
	public static final String JAPANESE = "Japanese";
	
	public static final String en = "en";
	public static final String jp = "jp";
		
	public static final Integer SUPER_ADMIN_ID = 1;
	public static final String SUPER_ADMIN_LIST = "LIST";
	public static final String SUPER_ADMIN_VIEW = "VIEW";
	public static final String ENTITY_VIEW = "ENTITY VIEW";
	public static final String ENTITY_MODIFY = "MODIFY";
	public static final String ENTITY_RENEWAL = "RENEWAL";
	public static final String ENTITY_LIST = "ENTITY LIST";
	public static final String RENEWAL_CREATE = "CREATE";
	public static final String RENEWAL_ACTIVE = "ACTIVE";
	public static final String RENEWAL_INACTIVE = "INACTIVE";
		
	public static final String LEVEL2 = "LEVEL2";
	public static final String MQ = "MQ==";
	public static final String MA = "MA==";
	public static final String AdminDep = "AdminDep";	
	public static final String NotApplicable ="N/A";
	public static final String newPasswordMsg ="New password has been send to the registered Email Id";
	
	public static final Integer PLAN_SCREEN = 32;
	public static final Integer PLAN_SUBSCREEN_LIST = 60;
	public static final Integer PLAN_SUBSCREEN_ADD = 61;
	
	public static final Integer ENTITY_MASTER = 29;
	public static final Integer PLAN_MASTER = 32;
	 	
	public static Date getCalenderDate() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

	public static String formatDatetoString(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String dateString = sdf.format(date);
		return dateString;
	}

	public static Date formatStringDatetoDate(final String dateValue, String pattern) {
		Date date = null;
		try {
			if (null != dateValue) {
				DateFormat formatter = new SimpleDateFormat(pattern);
				date = (Date) formatter.parse(dateValue);
			}
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getNextDateAsDate(int noOfDays) {
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.DATE, +noOfDays);

		// Set time fields to zero
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		// Put it back in the Date object
		return cal.getTime();
	}

	public static Date formatIsoStringtoDate(final String dateValue, String pattern) {

		Date date = null;
		try {
			if (null != dateValue) {
				SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				SimpleDateFormat outputFormat = new SimpleDateFormat(pattern);
				date = inputFormat.parse(dateValue);
				String formattedDate = outputFormat.format(date);
				date = (Date) outputFormat.parse(formattedDate);
				date = addDays(date, 1);
			}
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date addDays(Date date, int j) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, j);

		return cal.getTime();
	}

	// DELETE_VALIDATION
	public static final String PROJECT = "Project";
	public static final String POTENTIAL_MARGIN = "Potential Margin";
	public static final String USER = "User";
	public static final String APPLICATION_MASTER = "Application";
	public static final String BASE = "Base";
	public static final String CATEGORY = "Category";
	public static final String DIVISION = "Division";
	public static final int ACTIVE = 1;

	public static final String NOT_DELETED = "false";

	public static final String FILE_NAME_FORMAT_DATE = "yyyyMMddHHmmss";

	public static final Integer REOPEN = 7;

	public static final String VALIDATION_SUCCESS = "SUCCESS";

	// DB
	
	public static final String USR = "USR";
	
	public static final String REQ = "REQ";

	public static final String RU = "RU";

	public static final String APP = "APP";

	public static final String REJ = "REJ";

	public static final String RS = "RS";

	public static final String COM = "COM";

	public static final String IP = "IP";

	public static final String RA = "RA";

	public static final String RAU = "RAU";

	public static final String ROE = "ROE";

	public static final String RO = "RO";

	public static final String CL = "CL";

	public static final String CAN = "CAN";
	
	public static final String EBA = "EBA";
	
	public static final String EBR = "EBR";

	public static final String EPA = "EPA";

	public static final String EPR = "EPR";

	public static final String APL = "APL";

	public static final String RSL = "RSL";

	public static final String DUS = "DUS";
	
	public static final String DMU = "DMU";
	
	public static final String ENC = "ENC";
	
	public static final String ENCTSP = "ENCTSP";
	
	public static final String ENA = "ENA";
	
	public static final String CPD = "CPD";
	
	public static final String FPD = "FPD";
				
	public static final String EXPD = "EXPD";
	
	public static final String RNW = "RNW";
	
	public static final String TRNW = "TRNW";
	
	public static final String APPROVAL = "APPROVAL";

	public static final String RESOLVER = "RESOLVER";

	public static final String REQUEST = "REQUEST";

	public static final String REQUEST_UPDATE = "REQUEST_UPDATE";

	public static final String APPROVED = "APPROVED";

	public static final String REJECTED = "REJECTED";

	public static final String RESUBMIT = "RESUBMIT";

	public static final String COMPLETED = "COMPLETED";

	public static final String INPROGRESS = "INPROGRESS";

	public static final String REASSIGN = "REASSIGN";

	public static final String REASSIGN_USER = "REASSIGN_USER";

	public static final String REOPEN_EXECUTER = "REOPEN_EXECUTER";
	
	public static final String DELEGATE_USER = "DELEGATE_USER";
	
	public static final String DELEGATE_MODIFY_USER = "DELEGATE_MODIFY_USER";

	public static final String REOPEN1 = "REOPEN";

	public static final String CLOSE = "CLOSE";

	public static final String CANCEL = "CANCEL";

	public static final String ESCALATION_BEFORE_APPROVAL = "ESCALATION_BEFORE_APPROVAL";
	
	public static final String ESCALATION_BEFORE_RESOLVER = "ESCALATION_BEFORE_RESOLVER";
	
	public static final String ESCALATION_PENDING_APPROVAL = "ESCALATION_PENDING_APPROVAL";

	public static final String ESCALATION_PENDING_RESOLVER = "ESCALATION_PENDING_RESOLVER";
	
	public static final String NEW_USER = "NEWUSER";
	
	public static final String DEL_APPROVER = "APPROVER";

	public static final String DEL_RESOLVER = "RESOLVER";
	

	public static final Integer RE_SUBMIT = 3;

	public static final String Request = "Request";
	
	public static final String Widgets = "Widgets";

	public static final String RequestConfiguration = "RequestConfiguration";

	public static final String RequestScreenConfiguration = "RequestScreenConfiguration";

	public static final String RequestType = "RequestType";

	public static final String RequestSubType = "RequestSubType";

	public static final String FlashNews = "FlashNews";
	public static final String SubLocation = "SubLocation";

	public static final String Active = "Active";

	public static final String InActive = "InActive";

	public static final String EmailPort = "Email Port";
	public static final String EmailUserName = "Email User Name";
	public static final String EmailPassword = "Email Password";
	public static final String EmailHost = "Email Host";	
	
	public static String format(String message, Object... arg) {

		return MessageFormat.format(message, arg);

	}
	
	public static final String png = "png";
	public static final String jpg = "jpg";
	public static final String jpeg = "jpeg";

	public static final String DATE_YYYY_MM_DD_HH_MI_SS = "yyyy/MM/dd HH:mm:ss";
	
	public static String getCurrentDateTimeAsString() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(DATE_YYYY_MM_DD_HH_MI_SS);
		return df.format(calendar.getTime());
	}

	public static final String LANG_CODE_JP = "jp";
	public static final String LANG_CODE_EN = "en";

}
