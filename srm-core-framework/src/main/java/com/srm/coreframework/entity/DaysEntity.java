package com.srm.coreframework.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "days", schema = "common_rta_2_local")
public class DaysEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rin_ma_day_id")
	private Integer dayId;
	
	@Column(name = "rin_ma_day_number")
	private Integer dayNumber;
	
	@Column(name = "rin_ma_day_name_en")
	private String dayNameEn;
	
	@Column(name = "rin_ma_day_name_jp")
	private String dayNameJp;
	
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
