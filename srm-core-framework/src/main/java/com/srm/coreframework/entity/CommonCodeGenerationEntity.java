package com.srm.coreframework.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "common_code_generation")
@Data
public class CommonCodeGenerationEntity  implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "COMMON_CODE_GENERATION_ID")
	private Integer codeGenerationId;

	@Column(name = "COMMON_CODE_NAME")
	private Integer commonCode;

	

	@Column(name = "COMMON_PREFIX")
	private String commonPrefix;
	
	
	@Column(name = "COMMON_COUNTER")
	private Integer commonCounter;
	
	
	@Column(name = "COMMON_STARTING_NUMBER")
	private Integer commonStartingNumber;
	
	@Column(name = "COMMON_ACTIVE_FLAG")
	private Integer activeFlag;

	

	

	
	

}
