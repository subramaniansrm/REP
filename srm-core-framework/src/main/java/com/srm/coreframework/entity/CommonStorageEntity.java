/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 */
@Entity
@Table(name = "common_storage",schema = "common_rta_2_local")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@EqualsAndHashCode(callSuper=false)
public class CommonStorageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "COMMON_ID")
	private Integer commonId;
	@Column(name = "ITEM_REFERENCE_ID")
	private String itemReferenceId;
	@Column(name = "ITEM_NAME")
	private String itemName;
	@Column(name = "ITEM_VALUE")
	private String itemValue;
	
	@Column(name = "rin_ma_entity_id")
	private Integer entityId;
	
}
