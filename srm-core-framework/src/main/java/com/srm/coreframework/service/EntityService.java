package com.srm.coreframework.service;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.config.MailMessages;
import com.srm.coreframework.constants.CodeSecurity;
import com.srm.coreframework.dao.CommonDAO;
import com.srm.coreframework.dao.EntityDao;
import com.srm.coreframework.entity.EmailGeneralDetailsEntity;
import com.srm.coreframework.entity.EntityLicense;
import com.srm.coreframework.entity.EntityLicenseDetails;
import com.srm.coreframework.entity.EntityLicenseLogEntity;
import com.srm.coreframework.entity.EntityPlanning;
import com.srm.coreframework.entity.UserEntity;
import com.srm.coreframework.entity.UserRole;
import com.srm.coreframework.repository.EntityLicenseDetailsRepository;
import com.srm.coreframework.repository.EntityLicenseLogRepository;
import com.srm.coreframework.repository.EntityLicenseRepository;
import com.srm.coreframework.repository.EntityPlanningRepository;
import com.srm.coreframework.repository.UserRepository;
import com.srm.coreframework.repository.UserRoleRepository;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.EmailVo;
import com.srm.coreframework.vo.EntityLicenseDetailsVo;
import com.srm.coreframework.vo.EntityLicenseLogVo;
import com.srm.coreframework.vo.EntityLicenseVO;
import com.sun.mail.util.MailSSLSocketFactory;

@Service
public class EntityService extends CommonDAO {

	@Autowired
	EntityLicenseRepository entityLicenseRepository;

	@Autowired
	EntityLicenseDetailsRepository entityLicenseDetailsRepository;

	@Autowired
	EntityPlanningRepository entityPlanningRepository;
	
	@Autowired
	MailMessages mailMessages;
	
	@Autowired
	UserRoleRepository userRoleRepository;
	
	@Autowired
	EntityDao entityDao;

	@Autowired
	EntityLicenseLogRepository entityLicenseLogRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	EmailGeneralDetailsService emailGeneralDetailsService;
	
 
	
	Logger logger = LoggerFactory.getLogger(EntityService.class);

	@Transactional
	public List<EntityLicenseVO> getAll(AuthDetailsVo authDetailsVo) {

		List<EntityLicense> entityLicenseEntityList = new ArrayList<EntityLicense>();
		List<EntityLicenseVO> entityLicenseVoList = new ArrayList<EntityLicenseVO>();
				
		try {

			//get Role Name of Login Person Role
			//UserRole userRoleEntity =	userRoleRepository.getRole(authDetailsVo.getRoleId());
			
		/*	if(userRoleEntity.getUserRoleName().equals(CommonConstant.SUPERADMIN)){
				entityLicenseEntityList = entityLicenseRepository.getAllEntity();
			}else{
				entityLicenseEntityList = entityLicenseRepository.getAll(authDetailsVo.getEntityId());
			}*/
					
			entityLicenseEntityList = entityLicenseRepository.getAll(authDetailsVo.getEntityId());
			
			for (EntityLicense entityLicenseEntity : entityLicenseEntityList) {
				EntityLicenseVO entityLicenseVo = new EntityLicenseVO();
				
				
				if(null != entityLicenseEntity.getEntityName()){
				entityLicenseVo.setEntityName(entityLicenseEntity.getEntityName());
				}
				
				if(null != entityLicenseEntity.getLocation()){
				entityLicenseVo.setLocation(entityLicenseEntity.getLocation());
				}
				
				if(null != entityLicenseEntity.getSubLocation()){
				entityLicenseVo.setSubLocation(entityLicenseEntity.getSubLocation());
				}
										
				if(entityLicenseEntity.isStatus()){
					entityLicenseVo.setStatusValue(CommonConstant.Active);	
				}else{
					entityLicenseVo.setStatusValue(CommonConstant.InActive);
				}
										
				entityLicenseVo.setEntityId(entityLicenseEntity.getId());
								
				entityLicenseVoList.add(entityLicenseVo);
			}

		}  catch (CommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entityLicenseVoList;
	}
	
	
	@Transactional
	public EntityLicenseVO view(EntityLicenseVO entityLicenseVo, AuthDetailsVo authDetailsVo) throws Exception {

		EntityLicense entityLicenseEntity = new EntityLicense();
		EntityLicenseVO entityLicense = new EntityLicenseVO();

		try {

			entityLicenseEntity = entityLicenseRepository.findOne(entityLicenseVo.getId());

			
			BeanUtils.copyProperties(entityLicenseEntity, entityLicense);

			
			List<EntityLicenseDetails> entityLicenseDetailList = new ArrayList<EntityLicenseDetails>();
			entityLicenseDetailList = entityLicenseDetailsRepository.getAll(entityLicenseVo.getId());

		  List<EntityLicenseDetailsVo> entityLicenseDetailVoList = new ArrayList<EntityLicenseDetailsVo>();								
			
			if(!entityLicenseDetailList.isEmpty()){
							 			
				EntityLicenseDetailsVo entityLicenseDetailsVo = new EntityLicenseDetailsVo();
				BeanUtils.copyProperties(entityLicenseDetailList.get(entityLicenseDetailList.size()-1), entityLicenseDetailsVo);
				if(null != entityLicenseDetailsVo.getEntityPlanId()){
					EntityPlanning entityPlanning = entityPlanningRepository.getEntityPlanningDetails(entityLicenseDetailsVo.getEntityPlanId());
					if(null != entityPlanning && null != entityPlanning.getPlanName()){
						entityLicense.setPlanName(entityPlanning.getPlanName());
					}
				}
				
				if (null != entityLicenseDetailList.get(entityLicenseDetailList.size()-1).getFromDate()) {
					entityLicenseDetailsVo.setFromDate(entityLicenseDetailList.get(entityLicenseDetailList.size()-1).getFromDate().toString());
				} else {
					entityLicenseDetailsVo.setFromDate("");
				}

				if (null != entityLicenseDetailList.get(entityLicenseDetailList.size()-1).getToDate()) {
					entityLicenseDetailsVo.setToDate(entityLicenseDetailList.get(entityLicenseDetailList.size()-1).getToDate().toString());
				} else {
					entityLicenseDetailsVo.setToDate("");
				}

				if (null != entityLicenseDetailList.get(entityLicenseDetailList.size()-1).getTransactionCount()) {
					entityLicenseDetailsVo.setTransactionLicense(entityLicenseDetailList.get(entityLicenseDetailList.size()-1).getTransactionCount());
				}

				if (null != entityLicenseDetailList.get(entityLicenseDetailList.size()-1).getUserCount()) {
					entityLicenseDetailsVo.setUserLicense(entityLicenseDetailList.get(entityLicenseDetailList.size()-1).getUserCount());
				}

				if (null != entityLicenseDetailList.get(entityLicenseDetailList.size()-1).getUserLicense()) {
					entityLicenseDetailsVo
							.setUserCount(Integer.parseInt(decrypt(entityLicenseDetailList.get(entityLicenseDetailList.size()-1).getUserLicense())));
				}

				if (null != entityLicenseDetailList.get(entityLicenseDetailList.size()-1).getTransactionLicense()) {
					entityLicenseDetailsVo.setTransactionCount(
							Integer.parseInt(decrypt(entityLicenseDetailList.get(entityLicenseDetailList.size()-1).getTransactionLicense())));
				}

				entityLicenseDetailVoList.add(entityLicenseDetailsVo);
		 
			}
			entityLicense.setEntityLicenseDetailsVoList(entityLicenseDetailVoList);

			List<EntityLicenseLogEntity> list = entityLicenseLogRepository.getAll(entityLicenseVo.getId());
			EntityLicenseLogVo entityLicenseLogVo = null;
			List<EntityLicenseLogVo> entityLicenseLogVoList = new ArrayList<EntityLicenseLogVo>();
			int chkCount = 0;
			for (EntityLicenseLogEntity entityLicenseLogEntity : list) {
				chkCount = chkCount + 1;
				
				entityLicenseLogVo = new EntityLicenseLogVo();
				BeanUtils.copyProperties(entityLicenseLogEntity, entityLicenseLogVo);
				if(null != entityLicenseLogEntity.getRenewalFromDate()){
					entityLicenseLogVo.setRenewalFromDate(CommonConstant.formatDatetoString(entityLicenseLogEntity.getRenewalFromDate(),
							CommonConstant.FORMAT_DD_MM_YYYY_HYPHEN));
				}else{
					entityLicenseLogVo.setRenewalFromDate(CommonConstant.NotApplicable);
				}
				if(null != entityLicenseLogEntity.getRenewalToDate()){
					entityLicenseLogVo.setRenewalToDate(CommonConstant.formatDatetoString(entityLicenseLogEntity.getRenewalToDate(),
							CommonConstant.FORMAT_DD_MM_YYYY_HYPHEN));
				}else{
					entityLicenseLogVo.setRenewalToDate(CommonConstant.NotApplicable);
				}
				if (null != entityLicenseLogEntity.getRenewalTransactionCount()) {
					entityLicenseLogVo.setRenewalTransactionCount(entityLicenseLogEntity.getRenewalTransactionCount().toString());
				}else{
					entityLicenseLogVo.setRenewalTransactionCount(CommonConstant.NotApplicable);
				}
				if (null != entityLicenseLogEntity.getRenewalUserCount()) {
					entityLicenseLogVo.setRenewalUserCount(entityLicenseLogEntity.getRenewalUserCount().toString());
				}else{
					entityLicenseLogVo.setRenewalUserCount(CommonConstant.NotApplicable);
				}
				
				if (chkCount == 1) {

					if (!entityLicenseDetailList.isEmpty()) {

						if (null != entityLicenseDetailList.get(entityLicenseDetailList.size() - 1)
								.getTransactionLicense()) {
							entityLicenseLogVo.setUsedTransactionCount(
									String.valueOf(Integer.parseInt(decrypt(entityLicenseDetailList
											.get(entityLicenseDetailList.size() - 1).getTransactionLicense()))));
						}
					}

				} else {
					if (null != entityLicenseLogEntity.getUsedTransactionCount()
							&& (entityLicenseLogEntity.getUsedTransactionCount().equals("0")
									|| entityLicenseLogEntity.getUsedTransactionCount().equals("1"))) {
						entityLicenseLogVo.setUsedTransactionCount(entityLicenseLogEntity.getUsedTransactionCount());
					} else {
						entityLicenseLogVo
								.setUsedTransactionCount(decrypt(entityLicenseLogEntity.getUsedTransactionCount()));

					}

				}
				
				if (chkCount == 1) {
					if (!entityLicenseDetailList.isEmpty()) {
						if (null != entityLicenseDetailList.get(entityLicenseDetailList.size() - 1).getUserLicense()) {
							entityLicenseLogVo
									.setUsedUserCount(String.valueOf(Integer.parseInt(decrypt(entityLicenseDetailList
											.get(entityLicenseDetailList.size() - 1).getUserLicense()))));
						}
					}

				} else {
					if (null != entityLicenseLogEntity.getUsedUserCount()
							&& (entityLicenseLogEntity.getUsedUserCount().equals("0")
									|| entityLicenseLogEntity.getUsedUserCount().equals("1"))) {
						entityLicenseLogVo.setUsedUserCount(entityLicenseLogEntity.getUsedUserCount());
					} else {
						entityLicenseLogVo.setUsedUserCount(decrypt(entityLicenseLogEntity.getUsedUserCount()));

					}
				}
				if(null != entityLicenseLogEntity.getCreatedBy()){
					UserEntity userEntity = userRepository.getUserName(entityLicenseLogEntity.getCreatedBy());
					if(null != userEntity){
						if(null != userEntity.getFirstName() && null != userEntity.getLastName()){
							entityLicenseLogVo.setCreatedByUser(userEntity.getFirstName() + " " + userEntity.getLastName());
						}else if(null != userEntity.getFirstName()){
							entityLicenseLogVo.setCreatedByUser(userEntity.getFirstName());
						}
					}
				}
				if(null != entityLicenseLogEntity.getCreatedDate()){
					entityLicenseLogVo.setCreatedDateStr(CommonConstant.formatDatetoString(entityLicenseLogEntity.getCreatedDate(),
							CommonConstant.FORMAT_DD_MM_YYYY_HYPHEN));
				}
				
				entityLicenseLogVoList.add(entityLicenseLogVo);
			}
			entityLicense.setEntityLicenseLogVoList(entityLicenseLogVoList);
			
		} catch (CommonException e) {
			throw new CommonException("datafailure");
		}

		return entityLicense;

	}
	
	public static String decrypt(String value) throws Exception {
		byte[] decodedBytes = Base64.getDecoder().decode(value);
		return new String(decodedBytes);
	}	 
	
	@Transactional
	public List<EntityLicenseVO> searchEntity( EntityLicenseVO entityLicenseVO, AuthDetailsVo authDetailsVo) {
		 		
		List<EntityLicenseVO> entityLicenseVoList = new ArrayList<EntityLicenseVO>();
	 
		try {
 			
			List<Object[]> entityLicenseList = entityDao.searchEntity(entityLicenseVO,authDetailsVo);
			 
			for (Object approval : entityLicenseList) {
				EntityLicenseVO entityLicenseVo = new EntityLicenseVO();

				if (null != (Integer) ((Object[]) approval)[0]) {
					entityLicenseVo.setEntityId((int) ((Object[]) approval)[0]);

				}
				if (null != (String) ((Object[]) approval)[1]) {
					entityLicenseVo.setEntityName((String) ((Object[]) approval)[1]);
				}

				if (null != (String) ((Object[]) approval)[2]) {
					entityLicenseVo.setLocation((String) ((Object[]) approval)[2]);
				}

				if (null != (String) ((Object[]) approval)[3]) {
					entityLicenseVo.setSubLocation((String) ((Object[]) approval)[3]);
				}

				if (null != (String) ((Object[]) approval)[4]) {
					entityLicenseVo.setPlanName((String) ((Object[]) approval)[4]);
				}

				String active = ""+(byte) ((Object[]) approval)[5];
					if ( active.equals("1" ) ) {
						entityLicenseVo.setStatusValue(CommonConstant.Active);
					} else {
						entityLicenseVo.setStatusValue(CommonConstant.InActive);
				}					
				 
				entityLicenseVoList.add(entityLicenseVo);
			}
			

		} catch (CommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entityLicenseVoList;
	}
	
	public EntityLicenseLogEntity entityLogUpdate(EntityLicenseDetails entityLicenseDetails, EntityLicenseVO entityLicenseVO,
			AuthDetailsVo authDetailsVo) {
		EntityLicenseLogEntity entityLicenseLogEntity = new EntityLicenseLogEntity(); 
		
		List<EntityLicenseLogEntity> logList = entityLicenseLogRepository.getAll(entityLicenseVO.getEntityId());
		EntityLicenseLogEntity licenseToUpdateEntity = new EntityLicenseLogEntity();
		if(logList.size() > 0){
			licenseToUpdateEntity = logList.get(0);
		}
		
		licenseToUpdateEntity.setUsedUserCount(entityLicenseDetails.getUserLicense());
		licenseToUpdateEntity.setUsedTransactionCount(entityLicenseDetails.getTransactionLicense());
		
		entityLicenseLogRepository.save(licenseToUpdateEntity);
				
		if (null != entityLicenseVO) {

			entityLicenseLogEntity.setEntityId(entityLicenseVO.getEntityId());
			if (null != entityLicenseVO.getEntityLicenseDetailsVo().getFromDate()) {
				entityLicenseLogEntity.setFromDate(CommonConstant.formatIsoStringtoDate(entityLicenseVO.getEntityLicenseDetailsVo().getFromDate(),
						CommonConstant.DATE_YYYY_MM_DD_HH_MI_SS));
				entityLicenseLogEntity.setRenewalFromDate(CommonConstant.formatIsoStringtoDate(entityLicenseVO.getEntityLicenseDetailsVo().getFromDate(),
						CommonConstant.DATE_YYYY_MM_DD_HH_MI_SS));
			}
			if (null != entityLicenseVO.getEntityLicenseDetailsVo().getToDate()) {
				entityLicenseLogEntity.setToDate(CommonConstant.formatIsoStringtoDate(entityLicenseVO.getEntityLicenseDetailsVo().getToDate(),
						CommonConstant.DATE_YYYY_MM_DD_HH_MI_SS));
			}
			if (null != entityLicenseVO.getEntityLicenseDetailsVo().getUserLicense()) {
				entityLicenseLogEntity.setUserCount(entityLicenseVO.getEntityLicenseDetailsVo().getUserLicense());
			}
			if (null != entityLicenseVO.getEntityLicenseDetailsVo().getTransactionLicense()) {
				entityLicenseLogEntity.setTransactionCount(entityLicenseVO.getEntityLicenseDetailsVo().getTransactionLicense());
			}
			//if (null != entityLicenseVO.getTransactionLicense()) {
				entityLicenseLogEntity.setUsedTransactionCount("0");
			//}
			//if (null != entityLicenseVO.getUserLicense()) {
				entityLicenseLogEntity.setUsedUserCount("0");
			//}
			if (null != entityLicenseVO.getEntityLicenseDetailsVo().getToDate()) {
				entityLicenseLogEntity.setRenewalToDate(
						CommonConstant.formatIsoStringtoDate(entityLicenseVO.getEntityLicenseDetailsVo().getToDate(),
								CommonConstant.DATE_YYYY_MM_DD_HH_MI_SS));
			}
			
			if (null != entityLicenseVO.getEntityLicenseDetailsVo().getFromDate()) {
				entityLicenseLogEntity.setRenewalFromDate(
						CommonConstant.formatIsoStringtoDate(entityLicenseVO.getEntityLicenseDetailsVo().getFromDate(),
								CommonConstant.DATE_YYYY_MM_DD_HH_MI_SS));
			}
			
			/*Integer renewalUserCount = 0;
			Integer renewalTransactionCount = 0;
			if (null != entityLicenseDetails.getUserCount()) {
				renewalUserCount = entityLicenseDetails.getUserCount();
			}
			if (null != entityLicenseDetails.getTransactionCount()) {
				renewalTransactionCount = entityLicenseDetails.getTransactionCount();
			}

			renewalUserCount = renewalUserCount + entityLicenseVO.getEntityLicenseDetailsVo().getUserLicense();
			renewalTransactionCount = renewalTransactionCount
					+ entityLicenseVO.getEntityLicenseDetailsVo().getTransactionLicense();*/
			entityLicenseLogEntity.setRenewalUserCount(entityLicenseVO.getEntityLicenseDetailsVo().getUserLicense());
			entityLicenseLogEntity.setRenewalTransactionCount(entityLicenseVO.getEntityLicenseDetailsVo().getTransactionLicense());
			if(null != authDetailsVo.getUserId()){
				entityLicenseLogEntity.setCreatedBy(authDetailsVo.getUserId());
			}
			entityLicenseLogEntity.setCreatedDate(CommonConstant.getCalenderDate());
			entityLicenseLogRepository.save(entityLicenseLogEntity);
		}
		return entityLicenseLogEntity;
	}
	
	public void renewalEntity(EntityLicenseVO entityLicenseVO, AuthDetailsVo authDetailsVo) {

		List<EntityLicenseDetails> entityLicenseDetailList = entityLicenseDetailsRepository
				.getAll(entityLicenseVO.getEntityId());
		
		if(entityLicenseDetailList.size() > 0 && entityLicenseDetailList.size() == 1 ){
			
			for(EntityLicenseDetails entityLicenseDetails : entityLicenseDetailList){
				
				// log update
				entityLogUpdate(entityLicenseDetails, entityLicenseVO, authDetailsVo);
				
				if (null != entityLicenseVO.getEntityLicenseDetailsVo().getFromDate()) {

					entityLicenseDetails.setFromDate(CommonConstant. formatIsoStringtoDate(
							entityLicenseVO.getEntityLicenseDetailsVo().getFromDate(),
							CommonConstant.FORMAT_DD_MM_YYYY_HYPHEN));
					entityLicenseDetails.setToDate(CommonConstant.formatIsoStringtoDate(
							entityLicenseVO.getEntityLicenseDetailsVo().getToDate(),
							CommonConstant.FORMAT_DD_MM_YYYY_HYPHEN));
					if(null != entityLicenseVO.getEntityLicenseDetailsVo().getUserLicense()){
						entityLicenseDetails.setUserCount(entityLicenseVO.getEntityLicenseDetailsVo().getUserLicense());
	
					}
					if(null != entityLicenseVO.getEntityLicenseDetailsVo().getTransactionLicense()){
						entityLicenseDetails.setTransactionCount(entityLicenseVO.getEntityLicenseDetailsVo().getTransactionLicense());
					}
					
					entityLicenseDetailsRepository.save(entityLicenseDetails);

				}													
			}
		}
							
	}
		
	@Transactional
	public EntityLicenseVO getEntityParam(EntityLicenseVO entityLicenseVo) throws Exception {

		EntityLicense entityLicenseEntity = new EntityLicense();
		EntityLicenseVO entityLicense = new EntityLicenseVO();

		try {

			entityLicenseEntity = entityLicenseRepository.findOne(entityLicenseVo.getId());

			if (null != entityLicenseEntity.getEmail()) {
				entityLicense.setEmail(entityLicenseEntity.getEmail());
			}

		} catch (Exception e) {

		}
		return entityLicense;
	}
	
	public String update(EntityLicenseVO entityLicenseVo,AuthDetailsVo authDetailsVo) throws CommonException, Exception{
		
		String message = "";
		
		try {
			
		Integer email = 0;	
		EntityLicense entityLicense = entityLicenseRepository.findOne(entityLicenseVo.getEntityId());
		if(null != entityLicense){
			 
			if(null != entityLicenseVo.getEmailActive()
					&& entityLicenseVo.getEmailActive().equals(CommonConstant.FLAG_ZERO )
					&& entityLicenseVo.getStatus().equals(true)){
				email = 1;
			}
			if(null !=entityLicenseVo.getStatus()){
				if(entityLicenseVo.getStatus() == true){
					entityLicense.setStatus(true);
					entityLicense.setEmailActive(CommonConstant.FLAG_ONE);
					message = getMessage("entityActivateMessage", authDetailsVo);
				}else{
					entityLicense.setStatus(false);
					message = getMessage("entityDeactivateMessage", authDetailsVo);
				}
			}
			if(null !=entityLicenseVo.getEntityName()){
				entityLicense.setEntityName(entityLicenseVo.getEntityName());
			}
			if(null != entityLicenseVo.getEmail()){
				entityLicense.setEmail(entityLicenseVo.getEmail());
			}
			if(null != entityLicenseVo.getEntityAddress()){
				entityLicense.setEntityAddress(entityLicenseVo.getEntityAddress());
			}
			entityLicense.setUpdateBy(authDetailsVo.getUserId());
			entityLicense.setUpdateDate(CommonConstant.getCalenderDate());
			
				entityLicenseRepository.save(entityLicense);

				if (entityLicenseVo.getStatus().equals(true)) {
					String genratePassword = generatePassword();

					String encryptedPassword = null;

					encryptedPassword = CodeSecurity.encrypt(genratePassword);
					BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					encryptedPassword = passwordEncoder.encode(genratePassword);

					List<UserEntity> userEntityList =  entityDao.getUserList(entityLicense.getId());
					UserEntity userEntity = new UserEntity();
					
					if (userEntityList.size() > 0) {
						userEntity = userEntityList.get(0);
					}
					
				//	UserEntity userEntity = userRepository.getByEntityId(entityLicense.getId());
					userEntity.setPassword(encryptedPassword);
					userEntity.setUpdateBy(authDetailsVo.getUserId());
					userEntity.setUpdateDate(CommonConstant.getCalenderDate());
					userRepository.save(userEntity);
							
				sendMail(genratePassword,authDetailsVo,userEntity,entityLicenseVo,entityLicense);
			}
		}
		
			if (null != entityLicenseVo.getModifyFlag() &&
					entityLicenseVo.getModifyFlag().equals(CommonConstant.CONSTANT_ONE)) {
				message = getMessage("updateSuccessMessage", authDetailsVo);
			}
		
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure", authDetailsVo));
		}
		return message;
	}		 
	
	public void sendMail(String generatedPassword, AuthDetailsVo authDetailsVo, UserEntity userEntity,
			EntityLicenseVO entityLicenseVo, EntityLicense entityLicense)
					throws GeneralSecurityException, AddressException, MessagingException {

		try {

			EmailVo emailVo = new EmailVo();
			emailVo.setToUserAddress(userEntity.getEmailId());
			emailVo.setEntityName(entityLicenseVo.getEntityName());
			emailVo.setPassword(generatedPassword);
			emailVo.setUserName(userEntity.getFirstName());
			emailVo.setEntityId(entityLicense.getId());
			emailVo.setTrEmailFlag(CommonConstant.CONSTANT_ONE);

			AuthDetailsVo authDetVo = new AuthDetailsVo();
			authDetVo.setEntityId(entityLicense.getId());
			emailVo.setSystemConfigurationVo(getSystemConfigurationDetails(authDetVo));

			if (null != entityLicense.getEntityLang() && entityLicense.getEntityLang().equals(CommonConstant.en)) {
				authDetVo.setLangCode(CommonConstant.en);
			} else {
				authDetVo.setLangCode(CommonConstant.jp);
			}
			emailVo.setUserLang(entityLicense.getEntityLang());
			emailVo.setGroupId(CommonConstant.ENA);
			EmailGeneralDetailsEntity emailGeneralDetailsEntity = emailGeneralDetailsService
					.getEmailGeneralDetails(emailVo, authDetVo);

			emailGeneralDetailsService.getEmailGeneralDetails(emailVo, emailGeneralDetailsEntity);

			emailVo.setMessageContent(emailVo.getMessageContent() + "\n" + getMessage("applicationUrl", authDetVo) + " "
					+ ":" + getMessage("url", authDetVo) + "\n" + getMessage("userName", authDetVo) + " " + ":"
					+ entityLicenseVo.getEntityName() + "\n" + getMessage("userLoginId", authDetVo) + " " + ":"
					+ userEntity.getFirstName() + "\n" + getMessage("userPassword", authDetVo) + " " + ":"
					+ generatedPassword + "\n" + "\n" + "\n" + getMessage("autoGenText", authDetVo));

			if (null != emailVo.getSystemConfigurationVo() && emailVo.getSystemConfigurationVo().size() > 0) {

				emailService.sendEmail(emailVo);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
/*	@Transactional
	public void sendMail(String generatedPassword, String emailId, AuthDetailsVo authDetailsVo,String userName,String entityName)
			throws GeneralSecurityException, AddressException, MessagingException {

		Properties props = new Properties();
		props.put("mail.smtp.host", mailMessages.getSmtpHost());
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");

		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		props.put("mail.smtp.ssl.socketFactory", sf);
		Authenticator auth = new SMTPAuthenticator();
		// Session mailSession = Session.getInstance(props, auth);
		Session session = Session.getInstance(props, auth);

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(mailMessages.getFromMailAddress()));
		if (null != emailId) {
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailId));
		}
		InternetAddress emailAddr = new InternetAddress(emailId);
		try {
			emailAddr.validate();
		} catch (AddressException ex) {
			throw new CommonException(getMessage("invalidEmail", authDetailsVo));
			// ex.printStackTrace();
		}
		message.setSubject("New Entity Created ");
		message.setText("Hi,"+"\n\n"+" Entity Name : " + entityName + "\n"
				+ " Application URL : " + mailMessages.getUrl() + "\n"
				+ " UserName : " + userName +"\n"
				+ " Password : " + generatedPassword
				+"\n\n\n"+"******This is auto generated Mail..Please do not reply to this mail..*****");
		
		message.setSubject("New Entity Activated Successfully");
		message.setText(
				"Dear Customer," + "\n" + "Your account has been Activated successfully, and the details are :"
						+ "\n" + "Application URL : 172.16.8.55 " + "\n" + "User Name : "
						+ entityName + "\n" + "User LoginId :  "
						+ userName + "\n" + "Password : " + generatedPassword);
		
		message.saveChanges();
		Transport.send(message);

	}
	private class SMTPAuthenticator extends Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(mailMessages.getSmtpUserName(), mailMessages.getSmtpPassword());
		}
	} */
	
	
	private String generatePassword() {
		final Random RANDOM = new Random();
		final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		int passwordLength = 10;
		StringBuilder returnValue = new StringBuilder(passwordLength);
		for (int i = 0; i < passwordLength; i++) {
			returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}

		return new String(returnValue);
	}
		
	@Transactional
	public List<EntityLicenseVO> getAllEntity(AuthDetailsVo authDetailsVo) {
		 
		List<EntityLicenseVO> entityLicenseVoList = new ArrayList<EntityLicenseVO>();
				
		try {
			 			
			List<Object[]> entityLicenseList = entityDao.getAllEntityList(authDetailsVo);
			 
			for (Object approval : entityLicenseList) {
				EntityLicenseVO entityLicenseVo = new EntityLicenseVO();

				if (null != (Integer) ((Object[]) approval)[0]) {
					entityLicenseVo.setEntityId((int) ((Object[]) approval)[0]);

				}
				if (null != (String) ((Object[]) approval)[1]) {
					entityLicenseVo.setEntityName((String) ((Object[]) approval)[1]);
				}

				if (null != (String) ((Object[]) approval)[2]) {
					entityLicenseVo.setLocation((String) ((Object[]) approval)[2]);
				}

				if (null != (String) ((Object[]) approval)[3]) {
					entityLicenseVo.setSubLocation((String) ((Object[]) approval)[3]);
				}

				if (null != (String) ((Object[]) approval)[4]) {
					entityLicenseVo.setPlanName((String) ((Object[]) approval)[4]);
				}

				//if (null != (byte) ((Object[]) approval)[5]) {

					String active = ""+(byte) ((Object[]) approval)[5];
					if ( active.equals("1" ) ) {
						entityLicenseVo.setStatusValue(CommonConstant.Active);
					} else {
						entityLicenseVo.setStatusValue(CommonConstant.InActive);
					}					
				//}
				entityLicenseVoList.add(entityLicenseVo);
			}
			
 
		}  catch (CommonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entityLicenseVoList;
	}
	
	
	
	
}
