/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.srm.coreframework.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldAuthenticationVO extends CommonVO  {

    private Integer fieldAuthenticationId;
    private Boolean baseFilter;
    private Character deleteFlag;
   /* private Date createdDate;
    private String createdBy;
    private Date updatedDate;
    private String updatedBy;*/
    private ScreenFieldVO screenFieldMaster;
    private ScreenAuthorizationVO screenAuthorizationMaster;
    private Integer createBy;

	private Date createDate;

	private Integer updateBy;

	private Date updateDate;
    private Integer fieldId;
    private Integer screenAuthenticationId;
    
    private String baseFilterSearch;
    
    
    private String mandatory;
	private String numericOnly;
	private Integer decimal;
	private Integer length;
	private String fieldName;
	private String controlType;
	
	
  

}
