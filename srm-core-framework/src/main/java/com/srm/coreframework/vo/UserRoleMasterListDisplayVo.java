package com.srm.coreframework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * author Raathika
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRoleMasterListDisplayVo  {

	
	private Integer id;
	private String userRoleName;
	private String description;
	private String gfiLocationFlag;
	private String userDepartment;
	private String userLocation;
	private String roleType;

		
}
