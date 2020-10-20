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
@Table(name = "email_general_details", schema = "common_rta_2_local")
public class EmailGeneralDetailsEntity  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "email_details_id")
	private int emailDetailsId;
	
	@Column(name = "group_id")
	private String groupId;
	
	@Column(name = "message_code")
	private String messageCode;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "title_jp")
	private String titleJp;
	
	@Column(name = "message_content")
	private String messageContent;
	
	@Column(name = "message_content_jp")
	private String messageContentJp;
	
	@Column(name = "email_code")
	private String email_code;
		
	@Column(name = "entity_id")
	private int entityId;
	
	@Column(name = "update_date")
	private Date updateDate;
	
	@Column(name = "create_date")
	private Date createDate;
	
	@Column(name = "create_by")
	private int createBy;
	
	@Column(name = "update_by")
	private int updateBy;
	
}
