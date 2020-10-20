package com.srm.coreframework.util;

import lombok.Data;

@Data
public class ReportFieldsDTO {

	private String name;
	private String viewName;
	private Integer size;
	private String fieldClass;

	public ReportFieldsDTO() {

	}

	/**
	 * @param name
	 * @param viewName
	 * @param size
	 * @param fieldClass
	 */
	public ReportFieldsDTO(String name, String viewName, Integer size, String fieldClass) {
		super();
		this.name = name;
		this.viewName = viewName;
		this.size = size;
		this.fieldClass = fieldClass;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the viewName
	 */
	public String getViewName() {
		return viewName;
	}

	/**
	 * @param viewName the viewName to set
	 */
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	/**
	 * @return the size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(Integer size) {
		this.size = size;
	}

	/**
	 * @return the fieldClass
	 */
	public String getFieldClass() {
		return fieldClass;
	}

	/**
	 * @param fieldClass the fieldClass to set
	 */
	public void setFieldClass(String fieldClass) {
		this.fieldClass = fieldClass;
	}

}