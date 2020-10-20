/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.srm.coreframework.entity;

import java.io.Serializable;
import java.util.Date;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@Entity
@Table(name = "weekend_details", schema = "common_rta_2_local")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@EqualsAndHashCode(callSuper = false)
public class WeekendDetailsEntity  implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idrin_ma_weekend_details_id")
	private Integer weekendDetailId;

	@Column(name = "rin_ma_weekend_id")
	private Integer weekendId;

	@JoinColumn(name = "rin_ma_location_id", referencedColumnName = "USER_LOCATION_ID")
	@ManyToOne
	private UserLocation userLocationEntity;
	
	@Column(name = "rin_ma_sublocation_id")
	private Integer sublocationId;

	@Column(name = "rin_ma_weekend_day")
	private Integer weekendDay;	
	
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
	
	@Column(name = "rinma_active")
	private Character active;
	
	@Column(name = "rin_ma_entity_id")
	private Integer entityId;
}
