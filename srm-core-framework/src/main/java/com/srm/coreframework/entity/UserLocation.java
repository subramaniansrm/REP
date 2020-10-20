/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.srm.coreframework.entity;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@Entity
@Table(name = "user_location", schema = "common_rta_2_local")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@EqualsAndHashCode(callSuper = false)
public class UserLocation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "USER_LOCATION_ID")
	private Integer id;
	@Column(name = "USER_LOCATION_NAME")
	private String userLocationName;
	@Column(name = "USER_LOCATION_DETAILS")
	private String userLocationDetails;
	@Column(name = "PRIMARY_LOCATION")
	private String primaryLocation;
	@Column(name = "GFI_LOCATION_FLAG")
	private Character gfiLocationFlag;
	@Column(name = "ZIP")
	private String zip;
	@Column(name = "PHONE")
	private String phone;
	@Column(name = "FAX")
	private String fax;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "CONTACT_NAME")
	private String contactName;
	
	@Column(name = "CITY")
	private Integer cityId;
	@Column(name = "COUNTRY")
	private Integer countryId;
	@Column(name = "STATE")
	private Integer stateId;
	@JoinColumn(name = "rin_ma_entity_id", referencedColumnName = "idrin_ma_entity_id")
	@ManyToOne
	private EntityLicense entityLicenseEntity;
	
	@Column(name = "ACTIVE")
	private Character activeFlag;
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
