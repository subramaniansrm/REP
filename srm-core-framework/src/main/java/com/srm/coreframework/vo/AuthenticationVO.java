package com.srm.coreframework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationVO {

	// Field Authentication
	private Integer authenticationId;
	private Integer screenId;
	private Integer roleId;
	private Integer addFlag;
	private Integer modifyFlag;
	private Integer deleteFlag;
	private Integer viewFlag;
	private String roleName;
	private String screenName;

	

}
