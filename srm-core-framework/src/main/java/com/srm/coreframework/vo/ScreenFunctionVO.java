/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.srm.coreframework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScreenFunctionVO extends CommonVO  {
    private Integer screenFunctionId;
    private String functionName;
    private Character activeFlag;
    private SubScreenVO subScreenMaster;

  
}
