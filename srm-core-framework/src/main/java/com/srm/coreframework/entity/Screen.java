/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.srm.coreframework.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 *
 */
@Entity
@Table(name = "screen",schema = "common_rta_2_local")
@Data
public class Screen  {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "screen_pk_id")
    private Integer screenPkId;
    @Column(name = "SCREEN_ID")
    private Integer screenId;
    @Column(name = "SCREEN_NAME")
    private String screenName;
    
    @Column(name = "SCREEN_NAME_JP")
    private String screenNameJp;
    
    @Column(name = "SCREEN_TYPE_FLAG")
    private Character screenTypeFlag;
    
    @Column(name = "SCREEN_URL")
    private String screenUrl;
    
    @Column(name = "SCREEN_ICON")
    private String screenIcon;
    
    @Column(name = "ACTIVE_FLAG")
    private Character activeFlag;
    
    @JoinColumn(name = "rin_ma_entity_id", referencedColumnName = "idrin_ma_entity_id")
	@ManyToOne
	private EntityLicense entityLicenseEntity;
    public Screen() {
    }

    public Screen(Integer screenId) {
        this.screenId = screenId;
    }
    
	
}
