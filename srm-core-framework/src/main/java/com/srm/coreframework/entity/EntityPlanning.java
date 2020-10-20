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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author priyankas
 *
 */
@Entity
@Table(name = "entity_planning", schema = "common_rta_2_local")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@EqualsAndHashCode(callSuper = false)
public class EntityPlanning implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "PLAN_ID")
	private Integer planId;
	@Column(name = "PLAN_NAME")
	private String planName;
	@Column(name = "DURATION")
	private Integer duration;
	@Column(name = "USER_COUNT")
	private Integer userCount;
	@Column(name = "TRANSACTION_COUNT")
	private Integer transactionCount;
	@Column(name = "ORGINAL_AMOUNT")
	private Double amount;
	@Column(name = "OFFER_AMOUNT")
	private Double offerAmount;
	@Column(name = "ACTIVE")
	private Character activeFlag;
	@Column(name = "PLAN_IMAGE")
	private String planImage;
	@Column(name = "FROM_DATE")
	private Date  fromDate;
	@Column(name = "TO_DATE")
	private Date  toDate;
	@JoinColumn(name = "CURRENCY", referencedColumnName = "CURRENCY_ID")
	@ManyToOne
	private  CurrencyEntity currencyEntity;
	@Column(name = "DELETE_FLAG")
	private Character deleteFlag;
	@Column(name = "CREATED_BY")
	private Integer  createdBy;
	@Column(name = "CREATED_DATE")
	private Date  createdDate;
	@Column(name = "UPDATED_BY")
	private Integer  updatedBy;
	@Column(name = "UPDATED_DATE")
	private Date  updatedDate;	
	@Column(name = "OFFER_REMARKS")
	private String offerRemarks;
}
