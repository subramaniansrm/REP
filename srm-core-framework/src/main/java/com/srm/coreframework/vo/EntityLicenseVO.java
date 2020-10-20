package com.srm.coreframework.vo;


import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EntityLicenseVO extends CommonVO {

	private Integer id;

	private String entityName;

	private String entityLogo;

	private String entityAddress;

	private String email;

	private Integer passwordLength;

	private String passwordSpecialChar;

	private String passwordNumeric;

	private String passwordAlphanumericCaps;

	private Integer expiryDays;

	private Integer passwordCheckCount;

	private Date createdDate;

	private Integer createdBy;

	private Boolean status;

	private Integer transaction;

	private String location;

	private String subLocation;
	
	private String statusValue;
	
	private List<EntityLicenseDetailsVo> entityLicenseDetailsVoList;
	
	private Boolean result;
	
	
	//For User entity Save
	private Integer entityId;
	
	private Integer userId;
		
	private Character defaultValue;
	private String userLoginId;
	
	private EntityLicenseDetailsVo entityLicenseDetailsVo;
	
	private List<EntityLicenseLogVo> entityLicenseLogVoList;
	
	private String mobile;

	private Integer emailFlag;
	
	private String activeFlag;
	
	private boolean active;
	private String planName;
	private Character emailActive;
	private String entityLang;
	private Integer modifyFlag;
}
