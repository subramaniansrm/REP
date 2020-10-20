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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author aarthimp
 */
@Entity
@Table(name = "user_mapping", schema = "common_rta_2_local")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@EqualsAndHashCode(callSuper = false)
public class UserMappingEntity {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USER_MAPPING_ID")
    private Integer userMappingId;
    
    @JoinColumn(name = "LEVEL", referencedColumnName = "COMMON_ID")
    @ManyToOne
    private CommonStorageEntity level;
    
    @JoinColumn(name = "REPORTING_TO", referencedColumnName = "USER_ID")
    @ManyToOne
    private UserEntity reportingToUser;
    
    @JoinColumn(name = "USER_DEPARTMENT_ID", referencedColumnName = "USER_DEPARTMENT_ID")
    @ManyToOne
    private UserDepartment userDepartmentEntity;
    
    @JoinColumn(name = "USER_LOCATION_ID", referencedColumnName = "USER_LOCATION_ID")
    @ManyToOne
    private UserLocation userLocationEntity;
    
    
    @JoinColumn(name = "USER_SUBLOCATION_ID", referencedColumnName = "idrin_ma_sublocation_sublocationId")
    @ManyToOne
    private SubLocation subLocationEntity;
    
    @JoinColumn(name = "REPORTING_TO_LOCATION", referencedColumnName = "USER_LOCATION_ID")
    @ManyToOne
    private UserLocation reportingLocationEntity;
    
    @JoinColumn(name = "REPORTING_TO_SUBLOCATION", referencedColumnName = "idrin_ma_sublocation_sublocationId")
    @ManyToOne
    private SubLocation reportingSublocationEntity;
    
    @JoinColumn(name = "REPORTING_DEPARTMENT", referencedColumnName = "USER_DEPARTMENT_ID")
    @ManyToOne
    private UserDepartment reportingUserDepartmentEntity;
    @JoinColumn(name = "USER_ROLE_ID", referencedColumnName = "ROLE_ID")
    @ManyToOne
    private UserRole userRoleEntity;
    @JoinColumn(name = "rin_ma_entity_id", referencedColumnName = "idrin_ma_entity_id")
	@ManyToOne
	private EntityLicense entityLicenseEntity;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne
    private UserEntity userEntity;
    
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
