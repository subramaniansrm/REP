package com.srm.coreframework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LanguageDropDownVo {

	private Integer languageId;
	
	private String languageCode;
	
	private String language;
	
	private Character active;
}
