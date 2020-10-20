package com.srm.coreframework.entity;

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
@Table(name = "common_sub_screen", schema = "common_rta_2_local")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@EqualsAndHashCode(callSuper = false)
public class CommonSubScreenEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "SUB_SCREEN_ID")
	private Integer subScreenId;

	@Column(name = "SCREEN_ID")
	private Integer screenId;

	@Column(name = "SUB_SCREEN_NAME")
	private String subScreenName;

	@Column(name = "ACTIVE_FLAG")
	private Character activeFlag;

	@Column(name = "FIELD_ID")
	private Integer fieldId;
	
	
	@Column(name = "CONTROL_TYPE")
	private String controlType;

}
