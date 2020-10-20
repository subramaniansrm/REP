/**
 * 
 */
package com.srm.coreframework.util;

import com.srm.coreframework.exception.CoreException;
import com.srm.coreframework.exception.ErrorMessageCode;

/**
 *
 */
public class CommonValidator {
	
	/**
	 * 
	 * @param accessToken
	 * @param screenId
	 */
	public static void validateTokenAndScreenId(String accessToken, String screenId) throws CoreException {

		if (accessToken != null && "".equalsIgnoreCase(accessToken)) {
			throw new CoreException(ErrorMessageCode.INVALID_REQUEST);
		}

		if (screenId != null && "".equalsIgnoreCase(screenId)) {
			throw new CoreException(ErrorMessageCode.INVALID_REQUEST);
		}
	}
}
