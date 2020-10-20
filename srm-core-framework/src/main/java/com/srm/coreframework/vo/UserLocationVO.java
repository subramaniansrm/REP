package com.srm.coreframework.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLocationVO extends CommonVO{
	private Integer id;
	private String userLocationName;
	private String userLocationDetails;
	private String primaryLocation;
	private String zip;
	private String phone;
	private String fax;
	private String email;
	private String contactName;
	private String gfiLocationFlag;
	private Character deleteFlag;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;
	private String activeFlag; 
	private Integer cityId;
	private Integer stateId;
	private Integer countryId;
	private Integer sysAppId;
	private String city;
	private String state;
	private String country;
	private Integer[] deleteItem;
	private Integer screenId;
	private Integer subScreenId;
	
}
