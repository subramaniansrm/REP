package com.srm.coreframework.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)

public class ValidationErrorResponse {
	private String message;
	private String fieldName;
	private String rejectedValue;

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the rejectedValue
	 */
	public String getRejectedValue() {
		return rejectedValue;
	}

	/**
	 * @param rejectedValue the rejectedValue to set
	 */
	public void setRejectedValue(String rejectedValue) {
		this.rejectedValue = rejectedValue;
	}

}
