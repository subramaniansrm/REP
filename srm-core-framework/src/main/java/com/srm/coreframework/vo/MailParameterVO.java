package com.srm.coreframework.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
/**
 * 
 * @author vigneshs
 *
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MailParameterVO {

	private Integer mailParameterId;

	private String messageCode;

	private Integer mailLogId;

	private Integer userId;

	private Integer requestId;

	private Integer assignUserId;

	private String message;

	private String title;

	private Integer emailFlag;

	private Date requestDate;

	private Integer pageLimit;

	private Integer pageNo;

	private Integer totalRecords;

	private Integer createBy;

}
