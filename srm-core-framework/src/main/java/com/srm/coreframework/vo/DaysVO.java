package com.srm.coreframework.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.srm.coreframework.vo.CommonVO;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DaysVO  extends CommonVO{
	
	private Integer dayId;

	private Integer dayNumber;

	private String dayNameEn;

	private String dayNameJp;

	private Integer createBy;

	private Date createDate;

	private Integer updateBy;

	private Date updateDate;

	private Character deleteFlag;
	
	private String dayName;
	
}
