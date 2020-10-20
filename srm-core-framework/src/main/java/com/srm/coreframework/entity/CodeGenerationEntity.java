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

import lombok.Data;


/**
 *
 * @author rajalakshmib
 */
@Entity
@Table(name = "code_generation", schema = "common_rta_2_local")
@Data
public class CodeGenerationEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "CODE_GENERATION_ID")
	private Integer codeGenerationId;
	@Column(name = "DIVISION_STATUS")
	private Integer divisionStatus;
	@Column(name = "DIVISION_COUNT")
	private Integer divisionCount;
	@Column(name = "NAME_PREFIX")
	private Integer namePrefix;	
	
	@Column(name = "APPLICATION_STATUS")
	private Integer applicationStatus;

	@Column(name = "APPLICATION_COUNT")
	private Integer applicationCount;
	@Column(name = "PREFIX")
	private String prefix;
	
	@Column(name = "PREFIX_JP")
	private String prefixJp;
	@Column(name = "COUNTER")
	private Integer counter;
	@Column(name = "STARTING_NUMBER")
	private Integer startingNumber;

	@Column(name = "CODE_NAME")
	private Integer code;
	
	@Column(name = "rin_ma_entity_id")
	private Integer entityLicenseId;

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
