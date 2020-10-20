package com.srm.coreframework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScreenAuthorizationMasterListDisplayVO {


	private Integer screenAuthorizationId;
	private String screenId;
	private String subScreenId;
	private String departmentId;
	private String cdcCenterId;
	private String roleId;
	
	
}
