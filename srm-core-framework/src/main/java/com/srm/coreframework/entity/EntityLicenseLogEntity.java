package com.srm.coreframework.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "entity_license_log", schema = "common_rta_2_local")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@EqualsAndHashCode(callSuper = false)
public class EntityLicenseLogEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ENTITY_LICENSE_LOG_ID")
	private Integer entityLicenseLogId;
	@Column(name = "ENTITY_ID")
	private Integer entityId;
	@Column(name = "FROM_DATE")
	private Date fromDate;
	@Column(name = "TO_DATE")
	private Date toDate;
	@Column(name = "USER_COUNT")
	private Integer userCount;
	@Column(name = "TRANSACTION_COUNT")
	private Integer  transactionCount;
	@Column(name = "USED_USER_COUNT")
	private String  usedUserCount;
	@Column(name = "USED_TRANSACTION_COUNT")
	private String  usedTransactionCount;
	@Column(name = "RENEWAL_FROM_DATE")
	private Date renewalFromDate;
	@Column(name = "RENEWAL_TO_DATE")
	private Date renewalToDate;
	@Column(name = "RENEWAL_USER_COUNT")
	private Integer  renewalUserCount;
	@Column(name = "RENEWAL_TRANSACTION_COUNT")
	private Integer  renewalTransactionCount;
	@Column(name = "PLAN")
	private Integer  plan;
	@Column(name = "CREATED_BY")
	private Integer  createdBy;
	@Column(name = "CREATED_DATE")
	private Date  createdDate;
	
}
