package com.srm.coreframework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;

/**
 * 
 *
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuditLogVO {
	
	
	private Long auditLogId;
	private Long screenId;
	private String screenName;
	private Long userId;
	private String userName;
	private Long activityId;
	private String activityType;
	private String ipAddress;
	private Long keyFieldId;
	private String itemCode;
	private JsonNode oldValue;
	private JsonNode newValue;
	private JsonNode changedValue;
	private String eventTime;
	private String createdAt;
	
	
	@Override
	public String toString() {
		return "AuditLogDTO [auditLogId=" + auditLogId + ", screenId=" + screenId + ", screenName=" + screenName
				+ ", userId=" + userId + ", userName=" + userName + ", activityId=" + activityId + ", activityType="
				+ activityType + ", ipAddress=" + ipAddress + ", keyFieldId=" + keyFieldId + ", oldValue=" + oldValue
				+ ", newValue=" + newValue + ", changedValue=" + changedValue + ", eventTime=" + eventTime + "]";
	}


	
	}
