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
@Table(name = "screen_field",schema = "common_rta_2_local")
@Data
public class ScreenField  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "FIELD_ID")
	private Integer fieldId;

	@Column(name = "FIELD_NAME")
	private String fieldName;
	@Column(name = "ACTIVE_FLAG")
	private Character activeFlag;
	@Column(name = "SEQUENCE")
	private Integer sequence;
	@Column(name = "SUB_SCREEN_ID")
	private Integer subScreenId;
	@Column(name = "CONTROL_TYPE")
	private String controlType;
	@JoinColumn(name = "rin_ma_entity_id", referencedColumnName = "idrin_ma_entity_id")
	@ManyToOne
	private EntityLicense entityLicenseEntity;
	@Column(name = "MANDATORY")
	private String mandatory;
	@Column(name = "NUMERIC_ONLY")
	private String numericOnly;
	@Column(name = "DECIMAL_VALUE")
	private Integer decimal;
	@Column(name = "LENGTH")
	private Integer length;

	public ScreenField() {
	}

	public ScreenField(Integer fieldId) {
		this.fieldId = fieldId;
	}

}
