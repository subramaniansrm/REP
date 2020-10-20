package com.srm.coreframework.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonVO {

	private List<String> screenFieldDisplayVoList;
	private List<String> screenFunctionDisplayList;
	private List<String> screenMenuDisplayList;
    private List<ScreenVO> screenVoList;


    private Integer limit;
    private Integer userId;
    private Integer entityId;
	private String userName;
	private ScreenAuthorizationVO screenAuthorizationMaster;
	private ScreenJsonVO screenJson;
	private String accessToken;

}