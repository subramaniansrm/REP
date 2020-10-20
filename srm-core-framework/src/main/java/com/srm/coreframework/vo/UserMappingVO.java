package com.srm.coreframework.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserMappingVO extends CommonVO{

	
	private Integer userMappingId;

	private Integer userLocationId;
	
	private Integer subLocationId;

	private Integer userDepartmentId;

	private Integer userRoleId;

	private Integer reportingUserDepartment;

	private Integer reportingToUser;

	private String userLocationName;
	
	private String subLocationName;
	
	private Integer reportingLocationId;
	
	private Integer reportingSubLocationId;
	
	private String reportingLocationName;
	
	private String reportingSubLocationName;
	

	private String userDepartmentName;

	private String userRoleName;

	private String reportingDepartmentName;

	private String reportingToUserName;

	private Integer userId;

	private String userName;
	
	private Integer sysAppId;

	private Integer levelId;

	private String levelName;

	private List<Integer> userMappingList;

	
	
	
}
