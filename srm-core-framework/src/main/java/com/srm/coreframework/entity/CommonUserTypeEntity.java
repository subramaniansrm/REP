package com.srm.coreframework.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "common_user_type")
@Data
public class CommonUserTypeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "COMMON_TYPE_ID")
	private Integer commonTypeId;

	@Column(name = "TYPE_OF_USER")
	private String typeOfUser;

	

	@Column(name = "ACTIVE_FLAG")
	private String activeFlag;
	
	
	
	

}
