package com.srm.coreframework.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EntityPlanningVo extends CommonVO{
	
	private Integer planId;
	private String planName;
	private Integer duration;
	private Integer userCount;
	private Integer transactionCount;
	private Double amount;
	private boolean activeFlag;
	private Integer  createdBy;
	private Date  createdDate;
	private Integer  updatedBy;
	private Date  updatedDate;
	private Double offerAmount;
	private Date  fromDate;
	private Date  toDate;
	private Integer[]  deleteItem;
	private String planImage;
	private String currencyName;
	private Integer currencyId;
	
	private byte[] imageLoad;
	private String symbol;
	private String offerRemarks; 

}
