package com.srm.coreframework.entity;

import java.io.Serializable;
import java.util.Date;

/**
*
*/
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

@Entity
@Table(name = "rin_ma_sublocation", schema = "common_rta_2_local")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

@EqualsAndHashCode(callSuper = false)
public class SubLocation  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idrin_ma_sublocation_sublocationId")
	private Integer sublocationId;

	@Column(name = "rin_ma_sublocation_locationId")
	private Integer id;

	@Column(name = "rin_ma_sublocation_name")
	private String subLocationName;

	@Column(name = "rin_ma_sublocation_code")
	private String subLocationCode;

	@Column(name = "rin_ma_sublocation_subLocationIsActive")
	private boolean subLocationIsActive;

	@JoinColumn(name = "rin_ma_entity_id", referencedColumnName = "idrin_ma_entity_id")
	@ManyToOne
	private EntityLicense entityLicenseEntity;
	
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