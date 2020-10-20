/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.srm.coreframework.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "user_role", schema = "common_rta_2_local")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@EqualsAndHashCode(callSuper = false)
public class UserRole {

	/**
	 * 
	 */

	/**
	 * 
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ROLE_ID")
	private Integer id;

	@Column(name = "USER_ROLE_NAME")
	private String userRoleName;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "GFI_LOCATION_FLAG")
	private Character gfiLocationFlag;

	@JoinColumn(name = "rin_ma_entity_id", referencedColumnName = "idrin_ma_entity_id")
	@ManyToOne
	private EntityLicense entityLicenseEntity;

	@JoinColumn(name = "USER_DEPARTMENT_ID", referencedColumnName = "USER_DEPARTMENT_ID")
	@ManyToOne
	private UserDepartment userDepartmentEntity;

	@JoinColumn(name = "USER_LOCATION_ID", referencedColumnName = "USER_LOCATION_ID")
	@ManyToOne
	private UserLocation userLocationEntity;

	@JoinColumn(name = "SUBLOCATION_ID", referencedColumnName = "idrin_ma_sublocation_sublocationId")
	@ManyToOne
	private SubLocation subLocationEntity;

	@JoinColumn(name = "ROLE_TYPE", referencedColumnName = "USER_TYPE_ID")
	@ManyToOne
	private UserType userTypeEntity;
	
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
