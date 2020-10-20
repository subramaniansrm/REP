/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.srm.coreframework.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@Entity
@Table(name = "rin_ma_entity_license", schema = "common_rta_2_local")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@EqualsAndHashCode(callSuper = false)
public class EntityLicense  implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idrin_ma_entity_id")
	private Integer id;

	@Column(name = "rin_ma_entity_name")
	private String entityName;

	@Column(name = "rin_ma_entity_logo")
	private String entityLogo;

	@Column(name = "rin_ma_entity_address")
	private String entityAddress;

	@Column(name = "entity_admin_email")
	private String email;

	@Column(name = "entity_password_length")
	private Integer passwordLength;

	@Column(name = "entity_password_special_char")
	private String passwordSpecialChar;

	@Column(name = "entity_password_numeric")
	private String passwordNumeric;

	@Column(name = "entity_password_alphanumeric_caps")
	private String passwordAlphanumericCaps;

	@Column(name = "entity_password_expiry_days")
	private Integer expiryDays;

	@Column(name = "entity_password_check_count")
	private Integer passwordCheckCount;

	@Column(name = "entity_status")
	private boolean status;

	@Column(name = "entity_location")
	private String location;

	@Column(name = "entity_sublocation")
	private String subLocation;
	
	@Column(name = "email_active")
	private Character emailActive;
	
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
	
	@Column(name = "email_flag")
	private Integer emailFlag;
		
	@Column(name = "entity_lang")
	private String entityLang;
	
}
