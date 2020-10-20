
package com.srm.coreframework.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

 
import lombok.Data;

@Data
@Entity
@Table(name = "system_configuration", schema = "common_rta_2_local")
public class SystemConfigurationEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sys_id")
	private int sysId;
	
	@Column(name = "entity_id")
	private int entityId;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "configuration")
	private String configuration;
		
	@Column(name = "configuration_detail")
	private String configurationDetail;		 		 
		
	@Column(name = "update_date")
	private Date updateDate;
	
	@Column(name = "create_date")
	private Date createDate;
	
	@Column(name = "create_by")
	private int createBy;
	
	@Column(name = "update_by")
	private int updateBy;
	 
	@Column(name = "active")
	private int active; 
		
}
