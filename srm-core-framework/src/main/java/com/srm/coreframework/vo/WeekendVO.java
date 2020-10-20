package com.srm.coreframework.vo;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeekendVO extends CommonVO{
	
	private Integer weekendId;

	private Integer locationId;
	private String subLocation;
	private Integer subLocationId;
	private String active;
	 	 
	private Integer createBy;
	 
	private Date createDate;
	
	private Integer updateBy;

	private Date updateDate;
	 
	private Character deleteFlag;

	private List<WeekendDetailVO> weekendDetailList;
	
	private Integer entityId;
	
	private String location;
	
	private String activeStatus;
	
	private Integer[] deleteItem;
	
	private WeekendDetailVO weekendDetailVo;
	
	private Integer[] weekendDetailArr;
	 
	private String status;
	
	private String weekendDays;
}