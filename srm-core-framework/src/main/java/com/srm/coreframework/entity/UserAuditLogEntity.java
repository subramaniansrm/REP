package com.srm.coreframework.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "user_audit_table",schema = "common_rta_2_local")
@Data
public class UserAuditLogEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "USER_LOG_ID")
	private Integer userLogId;
	
	@Column(name = "USER_ID")
	private Integer userId;
	
	@Column(name = "LOGIN_DATE")
	private Date loginDate;
	
	@Column(name = "lOGOUT_DATE")
	private Date logoutDate;
	
	@Column(name = "CREATED_BY")
	private Integer createdBy;

	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Column(name = "UPDATED_BY")
	private Integer updatedBy;

	@Column(name = "UPDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
}
