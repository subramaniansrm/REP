/**
 * 
 */
package com.srm.coreframework.exception;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class NoRecordFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	private String code;

	private List<Error> errorList = new ArrayList<>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Error> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<Error> errorList) {
		this.errorList = errorList;
	}

	public NoRecordFoundException() {
		super();
	}

	public NoRecordFoundException(String code) {
		this.code = code;
	}

}
