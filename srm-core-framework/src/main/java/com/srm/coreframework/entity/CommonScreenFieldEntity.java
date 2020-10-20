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
@Table(name = "common_screen_field", schema = "common_rta_2_local")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@EqualsAndHashCode(callSuper = false)
public class CommonScreenFieldEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "FIELD_ID")
	private Integer filedId;
	
	
	@Column(name = "SUB_SCREEN_ID")
	private Integer subScreenId;
	
	
	@Column(name = "FIELD_NAME")
	private String filedName;
	
	
	@Column(name = "CONTROL_TYPE")
	private String controlType;
	
	@Column(name = "MANDATORY")
	private String mandatory;
	

	@Column(name = "NUMERIC_ONLY")
	private String numericOnly;
	
	

	@Column(name = "DECIMAL")
	private Integer decimal;
	

	@Column(name = "LENGTH")
	private Integer length;
	
	@Column(name = "ACTIVE_FLAG")
	private Character activeFlag;
	

	@Column(name = "SEQUENCE")
	private Integer sequence;
	

}
