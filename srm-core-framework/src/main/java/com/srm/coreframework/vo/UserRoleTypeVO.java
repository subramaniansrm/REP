package com.srm.coreframework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRoleTypeVO extends CommonVO {

	private Integer userTypeId;

	private String typeOfUser;

	
}
