package com.srm.coreframework.exception;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class CoreException extends Exception {
	private static final long serialVersionUID = 1L;
	private String code;
	private String data;

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

	public CoreException() {
		super();
	}
	
	public CoreException(String code) {
		this.code = code;
	}
	
	public CoreException(String data,String code) {
		this.code = code;
		this.data = data;
	}


	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	
	
}