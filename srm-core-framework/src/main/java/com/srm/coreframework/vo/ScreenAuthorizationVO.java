package com.srm.coreframework.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScreenAuthorizationVO extends CommonVO {

	private Integer screenAuthorizationId;
	private Character deleteFlag;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;
	
	private Integer screenId;
	private Integer subScreenId;
	private Integer departmentId;
	private Integer cdcCenterId;
	private Integer roleId;

	private ScreenVO screenMaster;
	private SubScreenVO subScreenMaster;
	private Map<String, String> fieldMap;
	
	private List<FieldAuthenticationVO> fieldAuthenticationMasterList;
	private List<FunctionAuthenticationVO> functionAuthenticationMasterList;
	private List<ScreenVO> screenMasterList;
	private List<SubScreenVO> subScreenMasterList;
	private List<ScreenFunctionVO> screenFunctionMasterList;
	private List<ScreenFieldVO> screenFieldMasterList;
	
	private List<ScreenAuthorizationMasterListDisplayVO> tableListMap;
	
	
	
	
	
	
}
