package com.srm.coreframework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScreenMaster extends CommonVO {
    private Integer screenId;
    private String screenName;
    private Character screenTypeFlag;
    private Character activeFlag;
    private String screenUrl;
	

   
}