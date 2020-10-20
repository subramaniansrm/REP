package com.srm.coreframework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserMasterListDisplayVO  {

	
	private Integer id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String currentAddress;
	private String permanentAddress;
	private String mobile;
	private String phoneNumber;
	private String emailId;
	private String theme;
	private String prefix;
	private String activeFlag;
	private String salutation;
	private String infoUser;
	
	private String userDepartment;
	private String userRole;
	private String userLocation;
	private String division;
	private String applicationProfileId;
	private String costBook;
	private String userLoginId;
	
	
	


}
