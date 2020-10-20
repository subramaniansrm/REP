package com.srm.coreframework.vo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScreenAuthenticationVO extends CommonVO  {
	private Integer screenAuthenticationId;
	private String level;
	private Character deleteFlag;
	/*private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;*/
	private Integer createBy;
	private Date createDate;
	private Integer updateBy;
	private Date updateDate;


	private Integer screenId;
	private Integer subScreenId;
	private Integer departmentId;
	private Integer cdcCenterId;
	private Integer subLocationId;

	private Integer reportingToDepartmentId;
	private Integer roleId;
	private Integer reportingToUserId;
	private Integer userId;

	private String departmentName;
	private String cdcCenterName;
	private String subLocationName;

	private String screenName;
	private String subScreenName;
	private String userList;
	private String roleName;

	private List<UserRoleMasterListDisplayVo> userRoleList;

	private List<ScreenAuthenticationVO> screenAuthenticationMasterList;

	private Integer[] deleteItem;

	// used
	private List<FieldAuthenticationVO> fieldAuthenticationMasterList;
	private List<FunctionAuthenticationVO> functionAuthenticationMasterList;
	private List<String> screenMenuDisplayList;
	private List<ScreenVO> screenVoList;
	private List<AuthenticationListComboVO> screenComboList;
	private List<AssignedScreenMaster> assignedScreenMasterList;
	private List<ScreenMaster> screenMasterList;
	private ScreenJsonVO screenJson;
}
