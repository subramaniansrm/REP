package com.srm.coreframework.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

 
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SystemConfigurationVo {	
			
	private int sysId;

	private int entityId;

	private String code;

	private String configuration;

	private String configurationDetail;

	private Date updateDate;

	private Date createDate;

	private int createBy;

	private int updateBy;
	 
	
}