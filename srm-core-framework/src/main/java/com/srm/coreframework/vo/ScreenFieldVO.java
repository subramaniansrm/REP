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
public class ScreenFieldVO extends CommonVO {
	private Integer fieldId;
	private String fieldName;
	private Character activeFlag;
	private SubScreenVO subScreenMaster;
	private String controlType;
	private String mandatory;
	private String numericOnly;
    private Integer decimal;
    private Integer length;
	
	

}
