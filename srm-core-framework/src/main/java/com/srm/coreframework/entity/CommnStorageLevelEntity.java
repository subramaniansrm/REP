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
@Table(name = "common_storage_level")
@Data
public class CommnStorageLevelEntity {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "COMMON_STORAGE_ID")
	private Integer commonStorageId;

	@Column(name = "COMMON_ITEM_VALUE")
	private String commonItemValue;
	
	@Column(name = "ACTIVE_FLAG")
	private Integer activeFlag;

}
