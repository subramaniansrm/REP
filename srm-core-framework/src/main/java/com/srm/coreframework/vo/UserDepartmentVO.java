package com.srm.coreframework.vo;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDepartmentVO extends CommonVO {

	private Integer id;
	private String userDepartmentName;
	private String description;
	private UserLocationVO userLocationMaster;	 
	private Character gfiLocationFlag;
	private Character deleteFlag;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;
	
	private Integer userLocation;
	private Integer entityLicenseId;
	private Integer[] deleteItem;

	private Map<Integer, String> userLocationMasterMap;
	private String userLocationName;
	private Integer sublocationId;
	private String sublocationName;
	
}