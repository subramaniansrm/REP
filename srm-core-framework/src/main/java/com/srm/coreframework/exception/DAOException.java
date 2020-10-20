/**
 * 
 */
package com.srm.coreframework.exception;

import java.util.ArrayList;
import java.util.List;
/**
 */
public class DAOException extends CoreException {
	private static final long serialVersionUID = 1L;
	private String code;

	private List<Error> errorList = new ArrayList<>();

	public DAOException(String code) {
		this.code = code;
	}

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public DAOException() {
		super();
	}
}
