package com.srm.coreframework.vo;


import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailGeneralDetailsEntityVO {

	private int emailDetailsId;

	private String groupId;

	private String messageCode;

	private String title;

	private String titleJp;

	private String messageContent;

	private String messageContentJp;

	private String email_code;

	private int entityId;

	private Date updateDate;

	private Date createDate;

	private int createBy;

	private int updateBy;
}
