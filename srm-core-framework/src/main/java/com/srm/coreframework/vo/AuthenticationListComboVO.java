package com.srm.coreframework.vo;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * author Raathika
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationListComboVO {

	private Integer id;
	private String name;
	private Boolean result;
	private Boolean baseResult;
	
	//Dashboard
	private BigDecimal value;
	

	
}
