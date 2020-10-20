/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.srm.coreframework.entity;


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
@Table(name = "rin_ma_entity_license_details",schema="common_rta_2_local")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@EqualsAndHashCode(callSuper=false)
public class EntityLicenseDetails   {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idrin_ma_entity_details_id")
	private Integer id;

	@Column(name = "rin_ma_entity_id")
	private Integer entityId;

	@Column(name = "rin_ma_entity_license_user_count")
	private Integer userCount;

	@Column(name = "rin_ma_entity_from_date")
	private Date fromDate;
	
	@Column(name = "rin_ma_entity_to_date")
	private Date toDate;

	@Column(name = "rin_ma_entity_license_transaction_count")
	private Integer transactionCount;

	@Column(name = "rin_ma_entity_used_transaction_license")
	private String transactionLicense;

	@Column(name = "rin_ma_entity_used_user_license")
	private String userLicense;
	
	@Column(name = "entity_plan")
	private Integer entityPlanId;
	
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
	private boolean deleteFlag;

}
