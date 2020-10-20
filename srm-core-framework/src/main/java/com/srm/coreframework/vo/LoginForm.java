package com.srm.coreframework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginForm {

	private Integer userId;
	private String userLoginId;
	private String userPassword;
	private String loginStatus;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	private String changePasswordStatus;

	//for login accesstoken
	private String accessToken;
}
