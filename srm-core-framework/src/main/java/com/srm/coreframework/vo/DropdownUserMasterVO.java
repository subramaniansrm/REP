package com.srm.coreframework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DropdownUserMasterVO extends CommonVO {

	
	private Integer id;
	private String firstName;
	
}
