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
@Table(name = "common_screen_function", schema = "common_rta_2_local")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@EqualsAndHashCode(callSuper = false)
public class CommonScreenFunctionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "SCREEN_FUNCTION_ID")
	private Integer screenFuctionId;

	@Column(name = "SUB_SCREEN_ID")
	private Integer subScreenId;

	@Column(name = "FUNCTION_NAME")
	private String functionName;

	@Column(name = "ACTIVE_FLAG")
	private Character activeFlag;

}
