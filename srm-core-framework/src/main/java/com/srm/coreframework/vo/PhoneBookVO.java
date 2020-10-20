package com.srm.coreframework.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;




@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhoneBookVO extends CommonVO {
	
private Integer phoneBookId;// Id of phone book
	
	private String employeeId;
	
	private String employeeName;

	private Integer userDepartmentId;// user department id

	private String mobileNumberC;

	private String extensionNumber;// Extension Number

	private Integer userLocationId;// Location Id

	private String skypeId;// Skype Id

	private Integer sublocationId;// USb location Id
	
	private String emailId;
	
	private String mobileNumberP;
	
	private String phoneNumber;

	private Boolean phoneBookIsActive;// 1-Active,0-Inactive

	private List<Integer> idList;// List of phoneBook Id

		
	private String userDepartment;// user department id

	private String subLocation;// USb location Id

	private String location;// Location Id

	private Integer[] deleteItem;
	
	private UserDepartmentVO userDepartmentMaster;
	
	private UserLocationVO userLocationMaster;
	
	private SubLocationVO subLocationMaster;
	
	private String phoneBookProfile;
	
	private String status;

	private byte[] imageLoad;
	
}