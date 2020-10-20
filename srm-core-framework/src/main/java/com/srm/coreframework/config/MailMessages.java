package com.srm.coreframework.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.srm.coreframework.entity.Division;
import com.srm.coreframework.entity.EntityLicense;
import com.srm.coreframework.entity.SubLocation;
import com.srm.coreframework.entity.UserDepartment;
import com.srm.coreframework.entity.UserLocation;
import com.srm.coreframework.entity.UserRole;

import lombok.Data;


@Data
@Configuration
@PropertySource("classpath:/properties/mail.properties")
public class MailMessages {

	@Value("${requestCode}")
	private String requestCode;
	
	@Value("${url}")
	private String url;
	
	@Value("${name}")
	private String name;
	
	@Value("${requestType}")
	private String requestType;
	
	@Value("${requestSubType}")
	private String requestSubType;
	
	@Value("${requestDetail}")
	private String requestDetail;
	
	@Value("${requestSubject}")
	private String requestSubject;
	
	@Value("${requestRaise}")
	private String requestRaise;
	
	@Value("${requestResolve}")
	private String requestResolve;
	
	@Value("${requestReject}")
	private String requestReject;
	
	@Value("${requestResubmit}")
	private String requestResubmit;
	
	@Value("${and}")
	private String and;
	
	@Value("${on}")
	private String on;
	
	@Value("${bestRegards}")
	private String bestRegards;
	
	@Value("${supportTeam}")
	private String supportTeam;
	
	@Value("${dear}")
	private String dear;
		
	@Value("${smtpPort}")
	private int smtpPort;
	
	@Value("${smtpHost}")
	private String smtpHost;
	
	@Value("${smtpUserName}")
	private String smtpUserName;
	
	@Value("${smtpPassword}")
	private String smtpPassword;
	
	@Value("${fromMailAddress}")
	private String fromMailAddress;	 
	
	@Value("${escalation}")
	private int escalation;
	
	@Value("${escalationBefore}")
	private int escalationBefore;
	
	@Value("${escalationBeforeApproval}")
	private int escalationBeforeApproval;
	
	@Value("${escalationBeforeResolver}")
	private int escalationBeforeResolver;
	
	@Value("${beforeEscalationMailMinutes}")
	private int beforeEscalationMailMinutes;
	
	@Value("${mailPendingList}")
	private int mailPendingList;
	
	@Value("${approval}")
	private int approval;
	
	@Value("${resolver}")
	private int resolver;
	
	@Value("${request}")
	private int request;
	
	@Value("${requestUpdate}")
	private int requestUpdate;
	
	@Value("${approved}")
	private int approved;
	
	@Value("${rejected}")
	private int rejected;
	
	@Value("${resubmit}")
	private int resubmit;
	
	@Value("${completed}")
	private int completed;
	
	@Value("${inprogress}")
	private int inprogress;
	
	@Value("${reassign}")
	private int reassign;
	
	@Value("${reassignUser}")
	private int reassignUser;
	
	@Value("${reopenExecuter}")
	private int reopenExecuter;
	
	@Value("${reopen}")
	private int reopen;
	
	@Value("${close}")
	private int close;
	
	@Value("${cancel}")
	private int cancel;
	
	@Value("${escalationPendingRequest}")
	private int escalationPendingRequest;
	
	@Value("${escalationResolverRequest}")
	private int escalationResolverRequest;
	
	@Value("${roomIsBookedUnderYour}")
	private String roomIsBookedUnderYour;
	
	@Value("${meetingRoom}")
	private String meetingRoom;
	
	
	
	
	
	
}
