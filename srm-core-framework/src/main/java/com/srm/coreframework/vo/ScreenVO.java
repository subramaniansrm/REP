/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.srm.coreframework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScreenVO  {
    
    private Integer screenId;
    private String screenName;
    private Character screenTypeFlag;
    private Character activeFlag;
    private String screenUrl;
    private String screenIcon;

}