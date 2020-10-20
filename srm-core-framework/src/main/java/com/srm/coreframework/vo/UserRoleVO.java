package com.srm.coreframework.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRoleVO extends CommonVO{
	
	private Integer id;
	private String userRoleName;
	private String description;
	private Character gfiLocationFlag;
	private Character deleteFlag;
	private Integer createdBy;
	private Date createdDate;
	private Integer updatedBy;
	private Date updatedDate;
	private Integer userDepartment;
	private Integer userLocation;
	
	private Integer[] deleteItem;
	private Integer roleType;
	private String roleTypeName;
	private String userDepartmentName;
	private String userLocationName;
	private Integer sublocationId;
	private String sublocationName;


}
