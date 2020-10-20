package com.srm.coreframework.vo;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EntityLicenseLogVo {

	private Integer entityLicenseLogId;
	private Integer entityId;
	private Date fromDate;
	private Date toDate;
	private Integer userCount;
	private Integer  transactionCount;
	private String  usedUserCount;
	private String  usedTransactionCount;
	private String renewalFromDate;
	private String renewalToDate;
	private String  renewalUserCount;
	private String  renewalTransactionCount;
	private Integer  createdBy;
	private Date  createdDate;
	private String  createdDateStr;
	private String  createdByUser;
}
