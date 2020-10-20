package com.srm.coreframework.util;

import org.springframework.web.multipart.MultipartFile;

public class ValidationUtil {


	/**
	 * Check given input null or blank
	 * 
	 * @param input
	 *            String
	 * @return boolean
	 */

	public static boolean isNullOrBlank(String input) {
		if (input == null || input.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check given input null or blank
	 * 
	 * @param input
	 *            Integer
	 * @return boolean
	 */
	public static boolean isNullOrBlank(Integer input) {
		if (null == input) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check given input null or blank
	 * 
	 * @param input
	 *            int
	 * @return boolean
	 */
	public static boolean isNullOrBlank(int input) {
		if (input == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check the password validation for given input
	 * 
	 * @param input
	 *            String
	 * @return boolean
	 */
	public static boolean passwordvalidation(String input) {
		String password = input;
		String pattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$";
		if (password.matches(pattern)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Check the given input null or blank and no special characters
	 * 
	 * @param input
	 *            String
	 * @return boolean
	 */
	public static boolean isNullOrBlanksAndNoSpecialCharacters(String input) {
		String field = input;
		String pattern = "^.*(?=.*['@#$%^&+=]).*$";
		if (input == null || input.isEmpty()) {
			return true;
		} else if (field.matches(pattern)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check the given input null or blank and only digits
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isNullOrBlanksAndOnlyDigits(String input) {
		String field = input;
		String pattern = "^[0-9]*$";
		if (input == null || input.isEmpty()) {
			return true;
		} else if (field.matches(pattern)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Check the given input null or blank and only alphabets
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isNullOrBlanksAndOnlyAlphabets(String input) {
		String field = input;
		String pattern = "^[a-zA-Z]+$";
		if (input == null || input.isEmpty()) {
			return true;
		} else if (field.matches(pattern)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Check given input alphabets and numbers
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isAlphanumeric(String input) {
		String field = input;
		String pattern = "^[a-zA-Z0-9\\s]+$";
		if (input == null || input.isEmpty()) {
			return true;
		} else if (field.matches(pattern)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * check the email validation for given input
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmail(String input) {
		String field = input;
		String pattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		if (input == null || input.isEmpty()) {
			return true;
		} else if (field.matches(pattern)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * check the website validation for given input
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isWebsite(String input) {
		String field = input;
		String pattern = "^(http[s]?:\\/\\/){0,1}(www\\.){0,1}[a-zA-Z0-9\\.\\-]+\\.[a-zA-Z]{2,5}[\\.]{0,1}";
		// "(http:\/\/|https:\/\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?"
		// ;
		// "^(?!:\/\/)([a-zA-Z0-9-]+\.){0,5}[a-zA-Z0-9-][a-zA-Z0-9-]+\.[a-zA-Z]{2,64}?$";
		if (input == null || input.isEmpty()) {
			return true;
		} else if (field.matches(pattern)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * compare given inputs bigger and smaller validation
	 * 
	 * @param actAmt
	 * @param offAmt
	 * @return boolean
	 */
	public static boolean isBiggerSmallerValidation(Integer actAmt, Integer offAmt) {
		if (actAmt <= offAmt) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * check the contact number validation for given input
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isContactNumber(String input) {
		String field = input;
		String pattern = "^[0-9-]{10}$";
		if (field.matches(pattern)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * check the contact number validation for given input
	 * 
	 * @param inputR
	 * @return boolean
	 */
	public static boolean isMobileNumber(String input) {
		String field = input;
		String pattern = "^[0-9-]{10}$";
		if (field.matches(pattern)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * check given input no special characters
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean noSpecialCharacters(String input) {
		String field = input;
		String pattern = "^.*(?=.*[/|`~!(){}_,\"'*?:;<>,@#$%^&+=-]).*$";
		if (input == null || input.isEmpty()) {
			return true;
		} else if (field.matches(pattern)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * check given input only alphabets
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean onlyAlphabets(String input) {
		String field = input;
		// String pattern = "^[a-zA-Z]+$";
		String pattern = "^[A-Za-z ]{1,50}$";
		if (field.matches(pattern)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * check given input only digits
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean onlyDigits(String input) {
		String field = input;
		String pattern = "^[0-9]*$";
		if (field.matches(pattern)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Only Digits with Dots
	 * 
	 * @param input
	 * @return
	 */
	public static boolean onlyDigitsAndDot(String input) {
		String field = input;
		String pattern = "^.*(.[0-9]).*$";
		if (field.matches(pattern)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * IP Address Validation
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isIpAddress(String input) {
		String field = input;
		String pattern = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		if (field.matches(pattern)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * check given input no special characters and numbers
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isNoSpecialCharAndNumbers(String input) {
		String field = input;
		String pattern = "^.*(?=.*[/|`~!(){}_,\"'*?:;<>,@#$%^&+=-[0-9]]).*$";
		if (input == null || input.isEmpty()) {
			return true;
		} else if (field.matches(pattern)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	public static boolean noSpecialCharactersNotSpaceHypenSpace(String input) {
		String field = input;
		String pattern = "^.*(?=.*[/|`~!(){},\"'*?:;<>,@#$%^&+=]).*$";
		if (input == null || input.isEmpty()) {
			return true;
		} else if (field.matches(pattern)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * check given input no special characters for Log Path
	 * 
	 * @param input
	 * @return
	 */

	public static boolean pathIsAlphanumeric(String input) {
		String field = input;
		String pattern = "^[ .a-zA-Z0-9/_-]+$";
		if (input == null || input.isEmpty()) {
			return true;
		} else if (field.matches(pattern)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * check given input no special characters for Fota FTP Host name
	 * 
	 * 
	 * @param input
	 * @return
	 */

	public static boolean fotaHostIsAlphanumeric(String input) {
		String field = input;
		String pattern = "^[.a-zA-Z0-9]+$";
		if (input == null || input.isEmpty()) {
			return true;
		} else if (field.matches(pattern)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * check given input no special characters for Fota UserName
	 * 
	 * @param input
	 * @return
	 */

	public static boolean fotaUserIsAlphanumeric(String input) {
		String field = input;
		String pattern = "^[.a-zA-Z0-9-_]+$";
		if (input == null || input.isEmpty()) {
			return true;
		} else if (field.matches(pattern)) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isPostalCode(String input) {
		String field = input;
		String pattern = "^\\d{7}||\\d{3}-\\d{4}$";
		if (field.matches(pattern)) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isImage(MultipartFile[] uploadedFile) {
		for (MultipartFile file : uploadedFile) {

			String fileName = file.getOriginalFilename();

			if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
				switch (fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase()) {
				case CommonConstant.png:
					return false;

				case CommonConstant.jpg:
					return false;

				case CommonConstant.jpeg:
					return false;

				default:
					return true;
				}
			}
		}
		return false;

	}

}
