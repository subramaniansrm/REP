package com.srm.coreframework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DropdownDepartmentVO {

	private Integer id;

	private String userDepartmentName;
	
	private String toolTipName;

}
