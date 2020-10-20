/**
 * 
 */
package com.srm.coreframework.util;

import java.io.Serializable;

import lombok.Data;

/**
 *
 */
@Data
public class Error implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String message;

	
}
