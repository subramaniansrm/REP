package com.srm.coreframework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubScreenVO extends CommonVO  {
    private Integer subScreenId;
    private String subScreenName;
    private Character activeFlag;
 
    private ScreenVO screenMaster;

	
   
}
