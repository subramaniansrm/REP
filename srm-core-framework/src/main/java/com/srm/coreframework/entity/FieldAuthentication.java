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
@Table(name = "field_validiation",schema = "common_rta_2_local")
@Data
public class FieldAuthentication  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "FIELD_VALIDATION_ID")
	private Integer fieldAuthenticationId;

	@Column(name = "BASE_FILTER")
	private String baseFilter;
	
	@Column(name = "FIELD_ID")
	private Integer screenFieldId;
	@Column(name = "SCREEN_AUTHENTICATION_ID")
	private Integer screenAuthenticationId;
    @JoinColumn(name = "rin_ma_entity_id", referencedColumnName = "idrin_ma_entity_id")
	@ManyToOne
	private EntityLicense entityLicenseEntity;

	public FieldAuthentication() {
	}

	public FieldAuthentication(Integer fieldAuthenticationId) {
		this.fieldAuthenticationId = fieldAuthenticationId;
	}

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
	
}
