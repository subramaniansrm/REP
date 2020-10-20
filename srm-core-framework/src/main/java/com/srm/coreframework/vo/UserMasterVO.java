package com.srm.coreframework.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserMasterVO extends CommonVO {

	
	private Integer id;
	private String userEmployeeId;
	private String accessToken;
	private String firstName;
	private String middleName;
	private String lastName;
	private String password;
	private String currentAddress;
	private String permanentAddress;
	private String mobile;
	private String phoneNumber;
	private String emailId;
	
	private String skypeId;
	private String userProfile;
	private String activeFlag;
	private Character deleteFlag;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;
	private String userLoginId;
	private String url;
	private Date tokenDate;
	private String tokenContextKey;
	private Thread thread;
	private String langCode;
	
	private Integer userDepartment;
	private Integer userRole;
	private Integer userLocation;
	private Integer subLocation;
	private Integer division;	
	private String userDepartmentName;
	private String userRoleName;
	private String userLocationName;
	private String subLocationName;
	private String divisionName; 
	private UserDepartmentVO userDepartmentMaster;
	private UserRoleVO userRoleMaster;
	private UserLocationVO userLocationMaster;
	private SubLocationVO subLocationMaster;
	private DivisionMasterVO divisionMaster;	
	private Integer[] deleteItem;
	private String status;	
	private EntityLicenseVO entityLicenseMstr;
	
	private Integer screenId;
	private Integer subScreenId;
	private Integer sublocationId;
	private Date changePasswordDate;
	private Character firstLogin;
}
