package com.srm.coreframework.constants;

import java.util.Base64;

/**
 * This class is used to encrypt and decrypt password
 * 
 * @author Priya [SRM]
 */
public class CodeSecurity {

	/**
	 * This Method is used to encrypt password
	 * 
	 * @param String
	 *            value
	 * @return encryptedValue64
	 */
	public static String encrypt(String value) throws Exception {
		byte[] encodedBytes = Base64.getEncoder().encode(value.getBytes());
		return new String(encodedBytes);
	}

	/**
	 * This Method is used to decrypt password
	 * 
	 * @param String
	 *            value
	 * @return decryptedValue
	 */
	public static String decrypt(String value) throws Exception {
		byte[] decodedBytes = Base64.getDecoder().decode(value);
		return new String(decodedBytes);
	}

}
