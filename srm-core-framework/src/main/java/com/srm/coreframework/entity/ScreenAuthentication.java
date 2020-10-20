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
@Table(name = "screen_authentication",schema = "common_rta_2_local")
@Data
public class ScreenAuthentication   {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "SCREEN_AUTHENTICATION_ID")
	private Integer screenAuthenticationId;

	/*@Column(name = "DELETE_FLAG")
	private Character deleteFlag;
	@Column(name = "CREATED_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	@Column(name = "CREATED_BY", updatable = false)
	private String createdBy;
	@Column(name = "UPDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	@Column(name = "UPDATED_BY")
	private String updatedBy;*/
	@Column(name = "SCREEN_ID")
	private Integer screenId;
	@Column(name = "SUB_SCREEN_ID")
	private Integer subScreenId;
	@JoinColumn(name = "rin_ma_entity_id", referencedColumnName = "idrin_ma_entity_id")
	@ManyToOne
	private EntityLicense entityLicenseEntity;
	@JoinColumn(name = "USER_DEPARTMENT_ID", referencedColumnName = "USER_DEPARTMENT_ID")
	@ManyToOne
	private UserDepartment userDepartmentEntity;
	@JoinColumn(name = "USER_LOCATION_ID", referencedColumnName = "USER_LOCATION_ID")
	@ManyToOne
	private UserLocation userLocationEntity;
	@JoinColumn(name = "USER_ROLE_ID", referencedColumnName = "ROLE_ID")
	@ManyToOne
	private UserRole userRoleEntity;
	@JoinColumn(name = "USER_SUBLOCATION_ID", referencedColumnName = "idrin_ma_sublocation_sublocationId")
	@ManyToOne
	private SubLocation subLocationEntity;

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
