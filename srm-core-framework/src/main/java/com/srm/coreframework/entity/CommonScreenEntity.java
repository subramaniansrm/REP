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
@Table(name = "common_screen",schema = "common_rta_2_local")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@EqualsAndHashCode(callSuper=false)
public class CommonScreenEntity {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "SCREEN_ID")
	private Integer screenId;
	
	
	@Column(name = "SCREEN_NAME")
	private String screenName;
	
	@Column(name = "SCREEN_NAME_JP")
	private String screenNameJp;
	
	@Column(name = "SCREEN_TYPE_FLAG")
	private Character screenTypeFlag;
	
	
	@Column(name = "SCREEN_URL")
	private String screenUrl;
	
	@Column(name = "SCREEN_ICON")
	private String screenIcon;
	
	@Column(name = "ACTIVE_FLAG")
	private Character activeFlag;
	
	

}


