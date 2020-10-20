package com.srm.coreframework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonStorageVO {

	private Integer commonId;
	private String itemReferenceId;
	private String itemName;	 
	private String itemValue;
	
}
