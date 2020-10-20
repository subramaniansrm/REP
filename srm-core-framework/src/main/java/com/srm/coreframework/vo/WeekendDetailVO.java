package com.srm.coreframework.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeekendDetailVO extends CommonVO{
	 
	private Integer weekendDetailId;

	private Integer weekendId;
	
	private Integer locationId;
	
	private Integer sublocationId;
	 
	private Integer weekendDay;	
		 
	private Integer createBy;
 
	private Date createDate;
	 
	private Integer updateBy;
	 
	private Date updateDate;
	 
	private Character deleteFlag;
		 
	private Character active;

	private Integer entityId;
	
	private String location;
	
	private String sublocation;
	
	private String activeStatus;
	
	private Integer[] weekendDayArr;
}
