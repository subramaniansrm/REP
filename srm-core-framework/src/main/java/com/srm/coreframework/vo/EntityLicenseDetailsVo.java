
package com.srm.coreframework.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EntityLicenseDetailsVo {

	private Integer id;
	private Integer entityId;
	private String fromDate;	
	private String toDate;
	private Integer entityCount;
	
	private Integer userCount;
	private Integer transactionCount;
	
	private Integer transactionLicense;
	private Integer userLicense;
	
	private Integer planId;
	private Integer entityPlanId;
}
