package com.srm.coreframework.vo;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScreenJsonVO {
	private Integer screenId;
	private Integer subScreenId;
	private Integer id;//id for view method
	private String accessToken;
}
