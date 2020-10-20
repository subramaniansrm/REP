/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.srm.coreframework.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 *
 */
@Entity
@Table(name = "function_authentication",schema = "common_rta_2_local")
@Data
public class FunctionAuthentication   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FUNCTION_AUTHENTICATION_ID")
    private Integer functionAuthenticationId;
    
    @Column(name = "SCREEN_AUTHENTICATION_ID")
    private Integer screenAuthenticationId;
    @Column(name = "SCREEN_FUNCTION_ID")
    private Integer screenFunctionId;
    @Column(name = "MAIL_NAME")
    private Integer mailName;
    
    @JoinColumn(name = "rin_ma_entity_id", referencedColumnName = "idrin_ma_entity_id")
	@ManyToOne
	private EntityLicense entityLicenseEntity;
 
    @Column(name = "create_by")
	private Integer createBy;

	@Column(name = "create_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(name = "update_by")
	private Integer updateBy;

	@Column(name = "update_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	@Column(name = "delete_flag")
	private Character deleteFlag;
	
	public FunctionAuthentication() {
    }

    public FunctionAuthentication(Integer functionAuthenticationId) {
        this.functionAuthenticationId = functionAuthenticationId;
    }

	
}
