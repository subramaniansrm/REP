package com.srm.coreframework.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.config.MailMessages;
import com.srm.coreframework.config.UserConstants;
import com.srm.coreframework.constants.CodeSecurity;
import com.srm.coreframework.constants.SystemConfigurationEnum;
import com.srm.coreframework.controller.CommonController;
import com.srm.coreframework.dao.SubLocationDAO;
import com.srm.coreframework.entity.CodeGenerationEntity;
import com.srm.coreframework.entity.CommnStorageLevelEntity;
import com.srm.coreframework.entity.CommonCodeGenerationEntity;
import com.srm.coreframework.entity.CommonScreenEntity;
import com.srm.coreframework.entity.CommonScreenFieldEntity;
import com.srm.coreframework.entity.CommonScreenFunctionEntity;
import com.srm.coreframework.entity.CommonStorageEntity;
import com.srm.coreframework.entity.CommonSubScreenEntity;
import com.srm.coreframework.entity.CommonUserTypeEntity;
import com.srm.coreframework.entity.EmailGeneralDetailsEntity;
import com.srm.coreframework.entity.EntityLicense;
import com.srm.coreframework.entity.EntityLicenseDetails;
import com.srm.coreframework.entity.EntityLicenseLogEntity;
import com.srm.coreframework.entity.FieldAuthentication;
import com.srm.coreframework.entity.FunctionAuthentication;
import com.srm.coreframework.entity.Screen;
import com.srm.coreframework.entity.ScreenAuthentication;
import com.srm.coreframework.entity.ScreenField;
import com.srm.coreframework.entity.ScreenFunction;
import com.srm.coreframework.entity.SubLocation;
import com.srm.coreframework.entity.SubScreen;
import com.srm.coreframework.entity.SystemConfigurationEntity;
import com.srm.coreframework.entity.UserDepartment;
import com.srm.coreframework.entity.UserEntity;
import com.srm.coreframework.entity.UserEntityMapping;
import com.srm.coreframework.entity.UserLocation;
import com.srm.coreframework.entity.UserRole;
import com.srm.coreframework.entity.UserType;
import com.srm.coreframework.repository.CodeGenerationRepository;
import com.srm.coreframework.repository.CommonCodeGenerationRepository;
import com.srm.coreframework.repository.CommonScreenFieldRepository;
import com.srm.coreframework.repository.CommonScreenFunctionRepository;
import com.srm.coreframework.repository.CommonScreenRepository;
import com.srm.coreframework.repository.CommonStorageLevelRepository;
import com.srm.coreframework.repository.CommonStorageRepository;
import com.srm.coreframework.repository.CommonSubScreenRepository;
import com.srm.coreframework.repository.CommonUserTypeRepository;
import com.srm.coreframework.repository.EntityLicenseDetailsRepository;
import com.srm.coreframework.repository.EntityLicenseLogRepository;
import com.srm.coreframework.repository.EntityLicenseRepository;
import com.srm.coreframework.repository.FieldAuthenticationRepository;
import com.srm.coreframework.repository.FunctionAuthenticationRepostiory;
import com.srm.coreframework.repository.ScreenAuthenticationRepository;
import com.srm.coreframework.repository.ScreenFieldRepositoy;
import com.srm.coreframework.repository.ScreenFunctionRepository;
import com.srm.coreframework.repository.ScreenRepository;
import com.srm.coreframework.repository.SubLocationRepository;
import com.srm.coreframework.repository.SubScreenRepository;
import com.srm.coreframework.repository.SystemConfigurationRepository;
import com.srm.coreframework.repository.UserDepartmentRepository;
import com.srm.coreframework.repository.UserEntityMappingRepository;
import com.srm.coreframework.repository.UserLocationRepository;
import com.srm.coreframework.repository.UserRepository;
import com.srm.coreframework.repository.UserRoleRepository;
import com.srm.coreframework.repository.UserTypeRepository;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.util.DateUtil;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.EmailVo;
import com.srm.coreframework.vo.EntityLicenseDetailsVo;
import com.srm.coreframework.vo.EntityLicenseVO;

@Service
public class EntityMasterService extends CommonController<EntityLicenseVO> {

	@Autowired
	EntityLicenseRepository entityLicenseRepository;

	@Autowired
	EntityLicenseDetailsRepository entityLicenseDetailsRepository;

	@Autowired
	private SubLocationDAO subLocationDao;
	
	@Autowired
	private CommonUserTypeRepository commonUserTypeRepository;

	@Autowired
	UserLocationRepository userLocationRepository;
	
	@Autowired
	CommonCodeGenerationRepository commonCodeGenerationRepository;
	
	
	@Autowired
	CommonStorageLevelRepository commonStorageLevelRepository;
	
	
	@Autowired
	UserTypeRepository  userTypeRepository;

	@Autowired
	SubLocationRepository subLocationRepository;

	@Autowired
	UserDepartmentRepository userDepartmentRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	MailMessages mailMessages;
	
	@Autowired
	UserEntityMappingRepository userEntityMappingRepository;
	
	@Autowired
	CommonStorageRepository commonStorageRepository;

	@Autowired
	ScreenRepository screenRepository;

	@Autowired
	SubScreenRepository subScreenRepository;

	@Autowired
	ScreenFieldRepositoy screenFieldRepositoy;

	@Autowired
	ScreenFunctionRepository screenFunctionRepository;

	@Autowired
	ScreenAuthenticationRepository screenAuthenticationRepository;

	@Autowired
	FieldAuthenticationRepository fieldAuthenticationRepository;

	@Autowired
	FunctionAuthenticationRepostiory functionAuthenticationRepostiory;

	@Autowired
	CommonScreenRepository commonScreenRepository;

	@Autowired
	CommonSubScreenRepository commonSubScreenRepository;

	@Autowired
	CommonScreenFieldRepository commonScreenFieldRepository;

	@Autowired
	CommonScreenFunctionRepository commonScreenFunctionRepository;

	@Autowired
	CodeGenerationRepository codeGenerationRepository;
	
	@Autowired
	UserMasterService userMasterService;
	
	@Autowired
	EntityLicenseLogRepository entityLicenseLogRepository;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	SystemConfigurationRepository systemConfigurationRepository;
		
	@Autowired
	UserConstants userconstants;

	@Autowired
	EmailGeneralDetailsService emailGeneralDetailsService;

 
	
	
	Logger logger = LoggerFactory.getLogger(EntityMasterService.class);

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
	
	/*@Transactional
	public void sendMail(String generatedPassword, String emailId, AuthDetailsVo authDetailsVo)
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
		message.setSubject("New Password");
		message.setText("New Password generated is " + generatedPassword);
		message.saveChanges();
		Transport.send(message);

	}*/
	
	@Transactional
	public EntityLicense create(EntityLicenseVO entityLicenseVo, AuthDetailsVo authDetailsVo) throws CommonException , Exception{

		int entityId;

		EntityLicense entityLicenseEntity = new EntityLicense();

		if (entityLicenseVo.getEntityName() != null) {
			entityLicenseEntity.setEntityName(entityLicenseVo.getEntityName());
		}

		if (entityLicenseVo.getEmail() != null) {
			entityLicenseEntity.setEmail(entityLicenseVo.getEmail());
		}

		if (entityLicenseVo.getEntityAddress() != null) {
			entityLicenseEntity.setEntityAddress(entityLicenseVo.getEntityAddress());
		}
		if (entityLicenseVo.getPasswordLength() != null) {
			if(entityLicenseVo.getPasswordLength()>5){
				entityLicenseEntity.setPasswordLength(entityLicenseVo.getPasswordLength());
			}else{
				throw new CommonException("passwordLength");
			}
			
		}

		if (entityLicenseVo.getPasswordSpecialChar() != null) {
			entityLicenseEntity.setPasswordSpecialChar(entityLicenseVo.getPasswordSpecialChar());
		}

		if (entityLicenseVo.getPasswordNumeric() != null) {
			entityLicenseEntity.setPasswordNumeric(entityLicenseVo.getPasswordNumeric());
		}
		if (entityLicenseVo.getPasswordAlphanumericCaps() != null) {
			entityLicenseEntity.setPasswordAlphanumericCaps(entityLicenseVo.getPasswordAlphanumericCaps());
		}

		if (entityLicenseVo.getExpiryDays() != null) {
			entityLicenseEntity.setExpiryDays(entityLicenseVo.getExpiryDays());
		}
		if (entityLicenseVo.getPasswordCheckCount() != null) {
			entityLicenseEntity.setPasswordCheckCount(entityLicenseVo.getPasswordCheckCount());
		}

		if (entityLicenseVo.getStatus() == true) {
			entityLicenseEntity.setStatus(entityLicenseVo.getStatus());
			entityLicenseEntity.setEmailActive(CommonConstant.FLAG_ONE);
		}else {
			entityLicenseEntity.setStatus(entityLicenseVo.getStatus());
			entityLicenseEntity.setEmailActive(CommonConstant.FLAG_ZERO);
		}


		if (entityLicenseVo.getLocation() != null) {
			entityLicenseEntity.setLocation(entityLicenseVo.getLocation());
		}

		if (entityLicenseVo.getSubLocation() != null) {
			entityLicenseEntity.setSubLocation(entityLicenseVo.getSubLocation());
		}

		if (entityLicenseVo.getSubLocation() != null) {
			entityLicenseEntity.setSubLocation(entityLicenseVo.getSubLocation());
		}
		
		entityLicenseEntity.setEmailFlag(CommonConstant.CONSTANT_ONE);
		
		if (null != entityLicenseVo.getEntityLang()) {
			entityLicenseEntity.setEntityLang(entityLicenseVo.getEntityLang());
		}
				
		entityLicenseEntity.setCreateBy(authDetailsVo.getUserId());
		entityLicenseEntity.setCreateDate(DateUtil.getCalenderDate());
		entityLicenseEntity.setUpdateBy(authDetailsVo.getUserId());
		entityLicenseEntity.setUpdateDate(DateUtil.getCalenderDate());
		entityLicenseEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);

		// Entity creation
		try {
			entityLicenseEntity = entityLicenseRepository.save(entityLicenseEntity);
		} catch (Exception e) {

			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure", authDetailsVo));

		}

		// variable to store entity Id
		entityId = entityLicenseEntity.getId();
		EntityLicense licenseEntity = new EntityLicense();

		// Method used to create Entity details

		for (EntityLicenseDetailsVo entityLicenseDetails : entityLicenseVo.getEntityLicenseDetailsVoList()) {
			EntityLicenseDetails entityLicenseDetailsEntity = new EntityLicenseDetails();

			entityLicenseDetailsEntity.setEntityId(entityId);
			if (null != entityLicenseDetails.getUserCount()) {
				entityLicenseDetailsEntity.setUserCount(entityLicenseDetails.getUserCount());

			} else {
				entityLicenseDetailsEntity.setUserCount(0);

			}
			if(null!= entityLicenseDetails.getTransactionCount()){
				entityLicenseDetailsEntity.setTransactionCount(entityLicenseDetails.getTransactionCount());
			}else{
				entityLicenseDetailsEntity.setTransactionCount(0);
			}
			
			if(null != entityLicenseDetails.getFromDate() && !entityLicenseDetails.getFromDate().isEmpty()){
				entityLicenseDetailsEntity.setFromDate(CommonConstant.formatIsoStringtoDate(entityLicenseDetails.getFromDate(),CommonConstant.DATE_YYYY_MM_DD_HH_MI_SS));
			}
			if(null != entityLicenseDetails.getToDate() && !entityLicenseDetails.getToDate().isEmpty()){
				entityLicenseDetailsEntity.setToDate(CommonConstant.formatIsoStringtoDate(entityLicenseDetails.getToDate(),CommonConstant.DATE_YYYY_MM_DD_HH_MI_SS));

			}
			
			//if (null != entityLicenseDetails.getUserCount()) {
				entityLicenseDetailsEntity.setUserLicense(CommonConstant.MQ);
			//}
			//if (null != entityLicenseDetails.getTransactionCount()) {
				entityLicenseDetailsEntity.setTransactionLicense(CommonConstant.MA);
			//}
			entityLicenseDetailsEntity.setCreateBy(authDetailsVo.getUserId());
			entityLicenseDetailsEntity.setUpdateBy(authDetailsVo.getUserId());
			entityLicenseDetailsEntity.setCreateDate(DateUtil.getCalenderDate());
			entityLicenseDetailsEntity.setUpdateDate(DateUtil.getCalenderDate());
			entityLicenseDetailsEntity.setDeleteFlag(false);
			if(null != entityLicenseDetails.getPlanId()){
				entityLicenseDetailsEntity.setEntityPlanId(entityLicenseDetails.getPlanId());
			}
			try {
				entityLicenseDetailsRepository.save(entityLicenseDetailsEntity);

			} catch (Exception e) {

				logger.error(e.getMessage());
				throw new CommonException(getMessage("dataFailure", authDetailsVo));

			}
			
			//update log
			EntityLicenseLogEntity entityLicenseLogEntity = new EntityLicenseLogEntity();

			if (null != entityLicenseDetailsEntity) {

				entityLicenseLogEntity.setEntityId(entityLicenseDetailsEntity.getEntityId());
				if (null != entityLicenseDetails.getFromDate()&& !entityLicenseDetails.getFromDate().equals("")) {
					entityLicenseLogEntity.setFromDate(CommonConstant.formatIsoStringtoDate(entityLicenseDetails.getFromDate(),CommonConstant.DATE_YYYY_MM_DD_HH_MI_SS));
					entityLicenseLogEntity.setRenewalFromDate(CommonConstant.formatIsoStringtoDate(entityLicenseDetails.getFromDate(),CommonConstant.DATE_YYYY_MM_DD_HH_MI_SS));
				}
				if (null != entityLicenseDetails.getToDate()&& !entityLicenseDetails.getToDate().equals("")) {
					entityLicenseLogEntity.setToDate(CommonConstant.formatIsoStringtoDate(entityLicenseDetails.getToDate(),CommonConstant.DATE_YYYY_MM_DD_HH_MI_SS));
				}
				if (null != entityLicenseDetails.getUserCount()) {
					entityLicenseLogEntity.setUserCount(entityLicenseDetails.getUserCount());
				}
				if (null != entityLicenseDetails.getTransactionCount()) {
					entityLicenseLogEntity.setTransactionCount(entityLicenseDetails.getTransactionCount());
				}
					entityLicenseLogEntity.setUsedTransactionCount("0");
					entityLicenseLogEntity.setUsedUserCount("1");
				if (null != entityLicenseDetails.getToDate() && !entityLicenseDetails.getToDate().equals("")) {
					entityLicenseLogEntity.setRenewalToDate(CommonConstant.formatIsoStringtoDate(entityLicenseDetails.getToDate(),CommonConstant.DATE_YYYY_MM_DD_HH_MI_SS));
				}
				
				if (null != entityLicenseDetails.getUserCount()) {
					entityLicenseLogEntity.setRenewalUserCount(entityLicenseDetails.getUserCount());				
					}
				if (null != entityLicenseDetails.getTransactionCount()) {
					entityLicenseLogEntity.setRenewalTransactionCount(entityLicenseDetails.getTransactionCount());
				}
				
				if(null != authDetailsVo.getUserId()){
					entityLicenseLogEntity.setCreatedBy(authDetailsVo.getUserId());
				}
				if(null != entityLicenseDetailsEntity && null != entityLicenseDetailsEntity.getEntityPlanId()){
					entityLicenseLogEntity.setPlan(entityLicenseDetailsEntity.getEntityPlanId());
				}
				entityLicenseLogEntity.setCreatedDate(CommonConstant.getCalenderDate());
				entityLicenseLogRepository.save(entityLicenseLogEntity);
			}
		}
		
		
		
	/*	List<CodeGenerationEntity> codeList = null;

		try {
			codeList = codeGenerationRepository.getAllCode();
		} catch (Exception e) {

			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure", authDetailsVo));

		}

		for (CodeGenerationEntity codeGen : codeList) {

			CodeGenerationEntity codeGenerationEntity = new CodeGenerationEntity();

			codeGenerationEntity.setCode(codeGen.getCode());
			codeGenerationEntity.setPrefix(codeGen.getPrefix());
			codeGenerationEntity.setCounter(codeGen.getCounter());
			codeGenerationEntity.setStartingNumber(CommonConstant.CONSTANT_ZERO);
			codeGenerationEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);
			codeGenerationEntity.setCreateBy(authDetailsVo.getUserId());
			codeGenerationEntity.setCreateDate(CommonConstant.getCalenderDate());
			codeGenerationEntity.setUpdateBy(authDetailsVo.getUserId());
			codeGenerationEntity.setUpdateDate(CommonConstant.getCalenderDate());
			licenseEntity.setId(entityId);
			codeGenerationEntity.setEntityLicenseId(entityId);
			codeGenerationRepository.save(codeGenerationEntity);
		}

	*/
		
		
		//Method used for User type
		List<CommonUserTypeEntity> commonUserType = null;
		try {
			commonUserType = commonUserTypeRepository.getAllCommonUserType();
		} catch (Exception e) {

			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure", authDetailsVo));

		}

		// Loop used to iterate for all user type from user type
		for (CommonUserTypeEntity userType : commonUserType) {
			UserType type = new UserType();

			

			if (null != userType.getTypeOfUser()) {

				type.setTypeOfUser(userType.getTypeOfUser());;
			}
			
			
			
			if(0!=entityId){
				type.setEntityLicenseEntity(entityLicenseEntity);
				
			}

			userTypeRepository.save(type);
		}


		
		
		//Method used to create level for entity
		
		List<CommnStorageLevelEntity> commonStorageEntity = null;
		try{
			commonStorageEntity = commonStorageLevelRepository.getAlllevel();
		} catch (Exception e) {

			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure", authDetailsVo));

		}
		for (CommnStorageLevelEntity storageLevel : commonStorageEntity) {
			CommonStorageEntity levelEntity = new CommonStorageEntity();

			

			if(null!=storageLevel.getCommonItemValue()){
				levelEntity.setItemValue(storageLevel.getCommonItemValue());
			}
			
			if(entityId!=0){
				levelEntity.setEntityId(entityId);
			}
			try{
			
			commonStorageRepository.save(levelEntity);}
			catch (Exception e) {

				logger.error(e.getMessage());
				throw new CommonException(getMessage("dataFailure", authDetailsVo));

			}
		}
		
		
		
		
		//Method used to create  common code generation
		List<CommonCodeGenerationEntity> codeGen = null;
		try {
			codeGen = commonCodeGenerationRepository.getAllCode();
		} catch (Exception e) {

			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure", authDetailsVo));

		}

		// Loop used to iterate for all code gener from common_code generation  table
		for (CommonCodeGenerationEntity code : codeGen) {
			CodeGenerationEntity codeEntity = new CodeGenerationEntity();

			

			if (null != code.getCommonCode()) {

				codeEntity.setCode(code.getCommonCode());
			}
			
			if (null != code.getCommonPrefix()) {

				codeEntity.setPrefix(code.getCommonPrefix());;
			}
			
			if (null != code.getCommonStartingNumber()) {

				codeEntity.setStartingNumber(code.getCommonStartingNumber());;
			}
			
			if (null != code.getCommonCounter()) {

				codeEntity.setCounter(code.getCommonCounter());;
			}
			
			if(0!=entityId){
				codeEntity.setEntityLicenseId(entityId);
				
			}
			
			codeEntity.setDeleteFlag('0');
			codeEntity.setCreateBy(1);
			codeEntity.setCreateDate(DateUtil.getCalenderDate());
			codeEntity.setUpdateBy(1);
			codeEntity.setUpdateDate(DateUtil.getCalenderDate());
			codeGenerationRepository.save(codeEntity);
		}

		UserLocation userLocationEntity = new UserLocation();

		userLocationEntity.setUserLocationName(entityLicenseVo.getLocation());
		userLocationEntity.setUserLocationDetails(entityLicenseVo.getLocation());
		licenseEntity.setId(entityId);
		userLocationEntity.setEntityLicenseEntity(licenseEntity);

		userLocationEntity.setActiveFlag('1');
		userLocationEntity.setDeleteFlag('0');
		userLocationEntity.setCreateBy(authDetailsVo.getUserId());
		userLocationEntity.setCreateDate(CommonConstant.getCalenderDate());
		userLocationEntity.setUpdateBy(authDetailsVo.getUserId());
		userLocationEntity.setUpdateDate(CommonConstant.getCalenderDate());

		// Used to insert Location
		try {
			userLocationRepository.save(userLocationEntity);
		} catch (Exception e) {

			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure", authDetailsVo));

		}

		// Method used to create sublocation

		SubLocation subLocationEntity = new SubLocation();
		subLocationEntity.setId(userLocationEntity.getId());
		subLocationEntity.setSubLocationName(entityLicenseVo.getSubLocation());
		licenseEntity.setId(entityId);
		subLocationEntity.setEntityLicenseEntity(licenseEntity);
		subLocationEntity.setSubLocationIsActive(true);
		authDetailsVo.setEntityId(entityId);

		String code = null;
		try {
			code = subLocationDao.findAutoGenericCode(CommonConstant.SubLocation, authDetailsVo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("autoCodeGenerationFailure", authDetailsVo));
		}
		subLocationEntity.setSubLocationCode(code);
		subLocationEntity.setCreateBy(authDetailsVo.getUserId());
		subLocationEntity.setUpdateBy(authDetailsVo.getUserId());
		subLocationEntity.setCreateDate(DateUtil.getCalenderDate());
		subLocationEntity.setUpdateDate(DateUtil.getCalenderDate());

		subLocationEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);

		// Used to insert subLocation

		try {
			subLocationRepository.save(subLocationEntity);
		} catch (Exception e) {

			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure", authDetailsVo));

		}

		// Method Used to create Department
		UserDepartment userDepartmentEntity = new UserDepartment();

		userDepartmentEntity.setUserDepartmentName(CommonConstant.AdminDep);
		userDepartmentEntity.setUserLocationEntity(userLocationEntity);
		userDepartmentEntity.setSubLocationEntity(subLocationEntity);
		userDepartmentEntity.setEntityLicenseEntity(licenseEntity);

		userDepartmentEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);

		userDepartmentEntity.setCreateBy(authDetailsVo.getUserId());
		userDepartmentEntity.setCreateDate(CommonConstant.getCalenderDate());
		userDepartmentEntity.setUpdateBy(authDetailsVo.getUserId());
		userDepartmentEntity.setUpdateDate(CommonConstant.getCalenderDate());

		// Insert Department

		try {
			userDepartmentRepository.save(userDepartmentEntity);
		} catch (Exception e) {

			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure", authDetailsVo));

		}

		// Method used to create Role

		 UserRole  userRoleEntity = createUserRole( userLocationEntity,  subLocationEntity
					,  userDepartmentEntity ,  entityLicenseEntity ,  licenseEntity,
					  authDetailsVo);
		
		// Method used to create User
		
		UserEntity userEntity = new UserEntity();
		userEntity.setUserName(entityLicenseVo.getUserLoginId());
		
		boolean result = false;
		if(null != entityLicenseVo.getUserLoginId()){
			result = subLocationDao.getUniqueLogin(entityLicenseVo.getUserLoginId());
		}
		if(result){
			throw new CommonException("duplicateUserName");
		}
		userEntity.setFirstName(entityLicenseVo.getUserLoginId());
		userEntity.setLastName(entityLicenseVo.getUserLoginId());
		userEntity.setUserEmployeeId(entityLicenseVo.getUserLoginId());
		String genratePassword = generatePassword();
		 
		String encryptedPassword = null;
		try {

			 encryptedPassword = CodeSecurity.encrypt(genratePassword); 
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			encryptedPassword = passwordEncoder.encode(genratePassword);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("passwordEncription", authDetailsVo));
		}

		userEntity.setPassword(encryptedPassword);
		// Entity License
		userEntity.setEmailId(entityLicenseEntity.getEmail());
		licenseEntity.setId(entityId);
		userEntity.setEntityLicenseEntity(licenseEntity);

		// Location Entity

		// userLocationEntity.setId(userLocationEntity.getId());
		userEntity.setUserLocationEntity(userLocationEntity);

		// Sublocation Entity

		// subLocationEntity.setSublocationId(subLocationEntity.getId());
		userEntity.setSubLocationEntity(subLocationEntity);

		// Department Entity
		userEntity.setUserDepartmentEntity(userDepartmentEntity);

		// Role Entity
		userEntity.setUserRoleEntity(userRoleEntity);

		//set default role
		userEntity.setDefaultRole(userRoleEntity);
		
		userEntity.setActiveFlag(CommonConstant.FLAG_ONE);
		userEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);

		userEntity.setCreateBy(authDetailsVo.getUserId());
		userEntity.setUpdateBy(authDetailsVo.getUserId());
		userEntity.setCreateDate(DateUtil.getCalenderDate());
		userEntity.setUpdateDate(DateUtil.getCalenderDate());
		if(null != entityLicenseVo.getEntityLang()){
		userEntity.setLangCode(entityLicenseVo.getEntityLang());	
		}
		userEntity.setUserProfile(userMasterService.getDefaultProfilePath());
		userEntity.setChangePasswordDate(CommonConstant.getCalenderDate());
		userEntity.setFirstLogin(CommonConstant.FLAG_ZERO);
		if(null != entityLicenseVo.getMobile() && !entityLicenseVo.getMobile().isEmpty()){
		userEntity.setMobile(entityLicenseVo.getMobile());
		}
		try {
			userRepository.save(userEntity);
		} catch (Exception e) {

			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure", authDetailsVo));
		}
		
		// Method used to create Entity Mapping
		createEntityMapping(entityId,userEntity);
		
		//Add System Configuration
		createSystemConfig( entityId , entityLicenseVo.getEmail() , userEntity);				 	 
	
		sendMail(genratePassword, userEntity.getEmailId(), authDetailsVo, userEntity.getFirstName(),entityLicenseVo,entityId);
				
		if (!entityLicenseVo.getStatus()) {		 
			sendMailToAdmin(entityLicenseEntity.getEntityName());
		}
		
		
		// To get all list of screen from common_screen table
		List<CommonScreenEntity> allScreen = null;
		try {
			allScreen = commonScreenRepository.getScreen();
		} catch (Exception e) {

			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure", authDetailsVo));

		}

		// Loop used to iterate for all screens from common_screen table
		for (CommonScreenEntity screen : allScreen) {
			Screen screenEntity = new Screen();

			int screenId = 0;
			// screenEntity.setScreenId((int) ((Object[]) screen)[0]);

			if (null != screen.getScreenId()) {

				screenEntity.setScreenId(screen.getScreenId());
				screenId = screen.getScreenId();
			}
			if (null != screen.getScreenName()) {
				screenEntity.setScreenName(screen.getScreenName());
			}
			if(null != screen.getScreenNameJp()){
				screenEntity.setScreenNameJp(screen.getScreenNameJp());
			}
			if (null != screen.getScreenTypeFlag()) {
				screenEntity.setScreenTypeFlag(screen.getScreenTypeFlag());
			}
			if (null != screen.getScreenUrl()) {
				screenEntity.setScreenUrl(screen.getScreenUrl());
			}
			if (null != screen.getScreenIcon()) {
				screenEntity.setScreenIcon(screen.getScreenIcon());
			}
			if (null != screen.getActiveFlag()) {
				screenEntity.setActiveFlag(screen.getActiveFlag());
			}

			licenseEntity.setId(entityId);
			screenEntity.setEntityLicenseEntity(licenseEntity);

			// Method used to create screen along with the entity Id in the //
			// screen table
			try {
				screenRepository.save(screenEntity);
			} catch (Exception e) {

				logger.error(e.getMessage());
				throw new CommonException(getMessage("dataFailure", authDetailsVo));

			}

			// Method used to get all subscreen for the corresponding screen
			List<CommonSubScreenEntity> allSubScreen = null;

			// Method used to find the subscreen for the screen
			try {
				allSubScreen = commonSubScreenRepository.findSubScreen(screenId);
			} catch (Exception e) {

				logger.error(e.getMessage());
				throw new CommonException(getMessage("dataFailure", authDetailsVo));

			}

			// Method used to insert the subscreen values in the sub_screen //
			// table
			for (CommonSubScreenEntity subScreen : allSubScreen) {

				int subScreenId = 0;
				SubScreen subScreenEntity = new SubScreen();

				subScreenId = subScreen.getSubScreenId();

				//Screen screenEntitySub = new Screen();
				subScreenEntity.setSubScreenId(subScreenId);
				subScreenEntity.setScreenId(screenEntity.getScreenId());

				if (null != subScreen.getSubScreenName()) {
					subScreenEntity.setSubScreenName(subScreen.getSubScreenName());
				}

				if (null != subScreen.getActiveFlag()) {
					subScreenEntity.setActiveFlag(subScreen.getActiveFlag());
				}

				licenseEntity.setId(entityId);
				subScreenEntity.setEntityLicenseEntity(licenseEntity);

				try {
					subScreenEntity = subScreenRepository.save(subScreenEntity);
				} catch (Exception e) {

					logger.error(e.getMessage());
					throw new CommonException(getMessage("dataFailure", authDetailsVo));

				}

				// Method used to find all the screen field for the //
				// corresponding
				// subscreen from common_screen_field

				List<CommonScreenFieldEntity> allScreenField = null;
				try {
					allScreenField = commonScreenFieldRepository.findScreenField(subScreenId);
				} catch (Exception e) {

					logger.error(e.getMessage());
					throw new CommonException(getMessage("dataFailure", authDetailsVo));

				}

				// Method used to insert screen field for the subscreen along //
				// with
				// the entity id
				for (CommonScreenFieldEntity screenField : allScreenField) {

					ScreenField screenFieldEntity = new ScreenField();

					screenFieldEntity.setSubScreenId(subScreenId);

					if (null != screenField.getFiledName()) {
						screenFieldEntity.setFieldName(screenField.getFiledName());
					}
					if (null != screenField.getControlType()) {
						screenFieldEntity.setControlType(screenField.getControlType());
					}
					if (null != screenField.getMandatory()) {
						screenFieldEntity.setMandatory(screenField.getMandatory());
					}
					if (null != screenField.getNumericOnly()) {
						screenFieldEntity.setNumericOnly(screenField.getNumericOnly());
					}
					if (null != screenField.getDecimal()) {
						screenFieldEntity.setDecimal(screenField.getDecimal());
					}

					if (null != screenField.getLength()) {
						screenFieldEntity.setLength(screenField.getLength());
					}
					if (null != screenField.getActiveFlag()) {
						screenFieldEntity.setActiveFlag(screenField.getActiveFlag());
					}
					if (null != screenField.getSequence()) {
						screenFieldEntity.setSequence(screenField.getSequence());
					}

					licenseEntity.setId(entityId);
					screenFieldEntity.setEntityLicenseEntity(licenseEntity);

					try {
						screenFieldRepositoy.save(screenFieldEntity);
					} catch (Exception e) {

						logger.error(e.getMessage());
						throw new CommonException(getMessage("dataFailure", authDetailsVo));

					}

				}

				// Method used to find the functions for the corresponding
				// subscreen
				List<CommonScreenFunctionEntity> allScreenFunction = null;

				try {
					allScreenFunction = commonScreenFunctionRepository.findScreenFunction(subScreenId);
				} catch (Exception e) {

					logger.error(e.getMessage());
					throw new CommonException(getMessage("dataFailure", authDetailsVo));

				}

				// Method used to insert screen function for the subscreen along
				// //
				// with the entity id

				for (CommonScreenFunctionEntity screenFunction : allScreenFunction) {

					ScreenFunction screenFunctionEntity = new ScreenFunction();

					screenFunctionEntity.setSubScreenId(subScreenId);

					if (null != screenFunction.getFunctionName()) {
						screenFunctionEntity.setFunctionName(screenFunction.getFunctionName());
					}
					if (null != screenFunction.getActiveFlag()) {
						screenFunctionEntity.setActiveFlag(screenFunction.getActiveFlag());
					}
					licenseEntity.setId(entityId);
					screenFunctionEntity.setEntityLicenseEntity(licenseEntity);
					try {
						screenFunctionRepository.save(screenFunctionEntity);
					} catch (Exception e) {

						logger.error(e.getMessage());
						throw new CommonException(getMessage("dataFailure", authDetailsVo));

					}
				}

			}

		}

		// Authentication

		List<Screen> allScreenAuth = null;
		try {
			allScreenAuth = screenRepository.getScreenEntity(entityId);
		} catch (Exception e) {

			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure", authDetailsVo));
		}

		for (Screen screenAuth : allScreenAuth) {

			int screenAuthId = 0;
			if (null != screenAuth.getScreenId()) {
				screenAuthId = screenAuth.getScreenId();
			}

			List<SubScreen> allSubScreenAuth = null;

			allSubScreenAuth = subScreenRepository.getSubScreenEntity(screenAuthId, entityId);

			for (SubScreen subScreenAuth : allSubScreenAuth) {

				int subScreenAuthId = 0;
				if (null != subScreenAuth.getSubScreenId()) {
					subScreenAuthId = subScreenAuth.getSubScreenId();
				}				
				
				//Save Authentication for Admin Role and Super admin Role				
				saveAuthentication( screenAuthId, subScreenAuthId , licenseEntity, 
						authDetailsVo, entityId ,userRoleEntity ,  subScreenAuth);
			} 

		} 
		return entityLicenseEntity;
	}

	
	 public void saveAuthentication(int screenAuthId,int subScreenAuthId ,
			EntityLicense licenseEntity,AuthDetailsVo authDetailsVo,int entityId  ,
			UserRole userRoleEntity ,SubScreen subScreenAuth){
		
		 int screenAuthenticationId = 0;
	 
		 //Create Admin Role and Super admin Role for each Entity	
		 List<UserRole> useRoleList = new ArrayList<UserRole>();
		 useRoleList.add(userRoleEntity);
		 UserRole superAdminRole = new UserRole();
		 superAdminRole.setId(CommonConstant.SUPER_ADMIN_ID);
		 useRoleList.add(superAdminRole);
		 
		 try {
		 
			 List<Integer> staticScreenList = userconstants.getStaticScreenIds();
							 			 	
		 for( UserRole eachRole: useRoleList){
			 
			 ScreenAuthentication screenAuthenticationEntity = new ScreenAuthentication();
				
				screenAuthenticationEntity.setScreenId(screenAuthId);
			    screenAuthenticationEntity.setSubScreenId(subScreenAuthId);			 
				screenAuthenticationEntity.setUserRoleEntity(eachRole);

				screenAuthenticationEntity.setEntityLicenseEntity(licenseEntity);
				
				if (!screenAuthenticationEntity.getUserRoleEntity().getId().equals(CommonConstant.SUPER_ADMIN_ID)) {
					if (screenAuthId == CommonConstant.ENTITY_MASTER || screenAuthId == CommonConstant.PLAN_MASTER) {
						screenAuthenticationEntity.setDeleteFlag(CommonConstant.FLAG_ONE);
					} else {
						screenAuthenticationEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);
					}
				} else {
					if (screenAuthId == CommonConstant.PLAN_MASTER) {
						screenAuthenticationEntity.setDeleteFlag(CommonConstant.FLAG_ONE);
					} else {
						screenAuthenticationEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);
					}
					 
				}
				
				screenAuthenticationEntity.setCreateBy(authDetailsVo.getUserId());
				screenAuthenticationEntity.setUpdateBy(authDetailsVo.getUserId());
				screenAuthenticationEntity.setCreateDate(CommonConstant.getCalenderDate());
				screenAuthenticationEntity.setUpdateDate(CommonConstant.getCalenderDate());

				// Used to persist

				screenAuthenticationRepository.save(screenAuthenticationEntity);
				
				
				screenAuthenticationId = screenAuthenticationEntity.getScreenAuthenticationId();
				
				// Used to getScreen fields

				List<ScreenField> allFieldsAuth = null;

				allFieldsAuth = screenFieldRepositoy.getSubScreenFieldsEntity(subScreenAuthId, entityId);

				for (ScreenField allFields : allFieldsAuth) {

					int fieldId = 0;
					if (null != allFields.getFieldId()) {
						fieldId = allFields.getFieldId();
					}

					FieldAuthentication fieldAuthenticationEntity = new FieldAuthentication();

					fieldAuthenticationEntity.setScreenFieldId(fieldId);
					fieldAuthenticationEntity.setEntityLicenseEntity(licenseEntity);
					fieldAuthenticationEntity.setScreenAuthenticationId(screenAuthenticationId);

					fieldAuthenticationEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);
					fieldAuthenticationEntity.setCreateBy(authDetailsVo.getUserId());
					fieldAuthenticationEntity.setUpdateBy(authDetailsVo.getUserId());
					fieldAuthenticationEntity.setCreateDate(CommonConstant.getCalenderDate());
					fieldAuthenticationEntity.setUpdateDate(CommonConstant.getCalenderDate());

					// Used to persit screen field
						fieldAuthenticationRepository.save(fieldAuthenticationEntity);
					
				}

				List<ScreenFunction> allFunctionAuth = null;

				allFunctionAuth = screenFunctionRepository.getSubScreenFunctionEntity(subScreenAuthId, entityId);

				for (ScreenFunction allFunction : allFunctionAuth) {

					int functionId = 0;
					if (null != allFunction.getScreenFunctionId()) {
						functionId = allFunction.getScreenFunctionId();
					}

					FunctionAuthentication functionAuthenticationEntity = new FunctionAuthentication();
					 
					functionAuthenticationEntity.setScreenFunctionId(functionId);
					functionAuthenticationEntity.setScreenAuthenticationId(screenAuthenticationId);

					functionAuthenticationEntity.setEntityLicenseEntity(licenseEntity);
					 		
					
					if (screenAuthenticationEntity.getUserRoleEntity().getId().equals(CommonConstant.SUPER_ADMIN_ID)) {
											
						
						if (staticScreenList.contains(screenAuthId)) {

							functionAuthenticationEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);

						} else {
						 
							if (allFunction.getFunctionName().equals(CommonConstant.SUPER_ADMIN_LIST)
									|| allFunction.getFunctionName().equals(CommonConstant.SUPER_ADMIN_VIEW)) {
								functionAuthenticationEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);

							} else {
								functionAuthenticationEntity.setDeleteFlag(CommonConstant.FLAG_ONE);

							}
						}				

					} else {

						if (subScreenAuth.getSubScreenName().equals(CommonConstant.ENTITY_LIST)
								&& (allFunction.getFunctionName().equals(CommonConstant.ENTITY_RENEWAL)
										|| allFunction.getFunctionName().equals(CommonConstant.RENEWAL_CREATE)
										|| allFunction.getFunctionName().equals(CommonConstant.RENEWAL_ACTIVE)
										|| allFunction.getFunctionName().equals(CommonConstant.RENEWAL_INACTIVE))) {
							functionAuthenticationEntity.setDeleteFlag(CommonConstant.FLAG_ONE);
						} else {
							functionAuthenticationEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);
						}
					}											
					
					functionAuthenticationEntity.setCreateBy(authDetailsVo.getUserId());
					functionAuthenticationEntity.setUpdateBy(authDetailsVo.getUserId());
					functionAuthenticationEntity.setCreateDate(CommonConstant.getCalenderDate());
					functionAuthenticationEntity.setUpdateDate(CommonConstant.getCalenderDate());

					// Used to insert

					functionAuthenticationRepostiory.save(functionAuthenticationEntity);

				}			 
		 }		
		 } catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
	
	
	//Method to create Entity Mapping
	public void createEntityMapping(Integer entityId, UserEntity userEntity) {

		int userArr[] = { userEntity.getId(), CommonConstant.SUPER_ADMIN_ID };
		try {

			for (int i = 0; i < userArr.length; i++) {
				UserEntityMapping userEntityMapping = new UserEntityMapping();
				userEntityMapping.setUserId(userArr[i]);

				if (entityId != 0) {
					userEntityMapping.setEntityId(entityId);
				}
				
				if(userEntityMapping.getUserId().equals(CommonConstant.SUPER_ADMIN_ID)){
					userEntityMapping.setDefaultId(CommonConstant.CONSTANT_ZERO);
				}else{
				userEntityMapping.setDefaultId(CommonConstant.CONSTANT_ONE);
				}
				
				
				userEntityMappingRepository.save(userEntityMapping);
			}

		} catch (Exception e) {
			e.getMessage();
			throw new CommonException("dataFailure");

		}

	}
	
	//Method to create User Role
	public UserRole createUserRole(UserLocation userLocationEntity, SubLocation subLocationEntity,
			UserDepartment userDepartmentEntity, EntityLicense entityLicenseEntity, EntityLicense licenseEntity,
			AuthDetailsVo authDetailsVo) {

		UserRole userRoleEntity = new UserRole();
		try {
			userRoleEntity.setUserRoleName(CommonConstant.ADMINROLE);
			userRoleEntity.setUserLocationEntity(userLocationEntity);
			userRoleEntity.setSubLocationEntity(subLocationEntity);
			userRoleEntity.setUserDepartmentEntity(userDepartmentEntity);

			UserType userTypeEntity = userTypeRepository.getAdminRoleType(entityLicenseEntity.getId());
			userRoleEntity.setUserTypeEntity(userTypeEntity);

			userRoleEntity.setEntityLicenseEntity(licenseEntity);
			userRoleEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);
			userRoleEntity.setCreateBy(authDetailsVo.getUserId());
			userRoleEntity.setCreateDate(CommonConstant.getCalenderDate());
			userRoleEntity.setUpdateBy(authDetailsVo.getUserId());
			userRoleEntity.setUpdateDate(CommonConstant.getCalenderDate());

			// Insert UserRole
			userRoleRepository.save(userRoleEntity);
			
		} catch (Exception e) {

			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure", authDetailsVo));
		}
		return userRoleEntity;
	}
	
	public void createSystemConfig(Integer entityId , String  emailId , UserEntity userEntity) {
		
		List<SystemConfigurationEntity> systemConfigurationEntityList = setSystemConfiguration(entityId,emailId);
		
		for(SystemConfigurationEntity systemConfigurationEntity  :systemConfigurationEntityList){
			
			systemConfigurationEntity.setEntityId(entityId);
			systemConfigurationEntity.setCreateBy(userEntity.getId());
			systemConfigurationEntity.setUpdateBy(userEntity.getId());
			systemConfigurationEntity.setCreateDate(CommonConstant.getCalenderDate());
			systemConfigurationEntity.setUpdateDate(CommonConstant.getCalenderDate());
			systemConfigurationEntity.setActive(CommonConstant.CONSTANT_ONE);
			
			systemConfigurationRepository.save(systemConfigurationEntity);			
		}					
	}
	
	public List<SystemConfigurationEntity> setSystemConfiguration( Integer entityId ,String emailId){
		
		List<SystemConfigurationEntity> systemConfigurationEntityList = new ArrayList<SystemConfigurationEntity>();
		
		/*SystemConfigurationEntity systemConfigurationEntity = new SystemConfigurationEntity();		
		systemConfigurationEntity.setCode("EID");
		systemConfigurationEntity.setConfiguration("Email Id");
		systemConfigurationEntity.setConfigurationDetail(emailId);
		systemConfigurationEntityList.add(systemConfigurationEntity);*/
		
		 SystemConfigurationEntity systemConfigurationPort = new SystemConfigurationEntity();		
		 systemConfigurationPort.setCode(SystemConfigurationEnum.EPT.toString());
		 systemConfigurationPort.setConfiguration(CommonConstant.EmailPort);
		 systemConfigurationPort.setConfigurationDetail(String.valueOf(mailMessages.getSmtpPort()));
		 systemConfigurationEntityList.add(systemConfigurationPort);
		 
		 SystemConfigurationEntity systemConfigurationUN = new SystemConfigurationEntity();		
		 systemConfigurationUN.setCode(SystemConfigurationEnum.EUN.toString());
		 systemConfigurationUN.setConfiguration(CommonConstant.EmailUserName);
		 systemConfigurationUN.setConfigurationDetail(mailMessages.getSmtpUserName());
		 systemConfigurationEntityList.add(systemConfigurationUN);
		 
		 SystemConfigurationEntity systemConfigurationPass = new SystemConfigurationEntity();		
		 systemConfigurationPass.setCode(SystemConfigurationEnum.EPW.toString());
		 systemConfigurationPass.setConfiguration(CommonConstant.EmailPassword);
		 systemConfigurationPass.setConfigurationDetail(mailMessages.getSmtpPassword());
		 systemConfigurationEntityList.add(systemConfigurationPass);
		 
		 SystemConfigurationEntity systemConfigurationHst = new SystemConfigurationEntity();		
		 systemConfigurationHst.setCode(SystemConfigurationEnum.EHT.toString());
		 systemConfigurationHst.setConfiguration(CommonConstant.EmailHost);
		 systemConfigurationHst.setConfigurationDetail(mailMessages.getSmtpHost());
		 systemConfigurationEntityList.add(systemConfigurationHst);
				
		return systemConfigurationEntityList;
	} 
		
	public void sendMail(String generatedPassword, String emailId, AuthDetailsVo authDetailsVo,String userName 
			, EntityLicenseVO entityLicenseVo , int entityId)
			throws  Exception {
		 
		try {
		EmailVo emailVo = new EmailVo();
		emailVo.setToUserAddress(emailId);
		emailVo.setEntityName(entityLicenseVo.getEntityName());
		emailVo.setPassword(generatedPassword);
		emailVo.setUserName(userName);
		emailVo.setEntityId(entityId);

		AuthDetailsVo authDetVo = new AuthDetailsVo();
		authDetVo.setEntityId(entityId);
		emailVo.setSystemConfigurationVo(getSystemConfigurationDetails(authDetVo));
		emailVo.setTrEmailFlag(CommonConstant.CONSTANT_ONE);

		if (null != entityLicenseVo.getEntityLang() && entityLicenseVo.getEntityLang().equals(CommonConstant.en)) {
			authDetVo.setLangCode(CommonConstant.en);
		} else {
			authDetVo.setLangCode(CommonConstant.jp);
		}
		emailVo.setUserLang(entityLicenseVo.getEntityLang());	
		emailVo.setGroupId(CommonConstant.ENC);
		EmailGeneralDetailsEntity emailGeneralDetailsEntity = emailGeneralDetailsService.getEmailGeneralDetails(emailVo,
				authDetailsVo);
		emailGeneralDetailsService.getEmailGeneralDetails(emailVo, emailGeneralDetailsEntity);

		if (entityLicenseVo.getStatus()) {

			emailVo.setMessageContent(emailVo.getMessageContent() + "\n" + getMessage("applicationUrl", authDetVo)+ " " + ":"
					+ getMessage("url", authDetVo) + "\n" + getMessage("userName", authDetVo)+ " " + ":"
					+ entityLicenseVo.getEntityName() + "\n" + getMessage("userLoginId", authDetVo)+ " " + ":"
					+ userName + "\n" + getMessage("userPassword", authDetVo)+ " " + ":" + generatedPassword
					+ "\n" + "\n" + "\n" + getMessage("autoGenText", authDetVo));

		} else {
			emailVo.setMessageContent(emailVo.getMessageContent() + "\n" + getMessage("applicationUrl", authDetVo)+ " " + ":"
					+ getMessage("url", authDetVo) + "\n" + "\n" + "\n" + getMessage("autoGenText", authDetVo));
		}
	
		if (null != emailVo.getSystemConfigurationVo()&& emailVo.getSystemConfigurationVo().size() > 0) {
		
			emailService.sendEmail(emailVo);
		}
		} catch (CommonException e) {			 
			e.printStackTrace();
		} catch (Exception e) {		 
			e.printStackTrace();
		}				
	}
	
	public void sendMailToAdmin(String entityName) throws Exception {
		 		
		try {
			
			EmailVo emailVo = new EmailVo();

			UserEntity userEntity = subLocationDao.getSuperAdminInfo();
			emailVo.setToUserAddress(userEntity.getEmailId());// super admin
			emailVo.setEntityName(entityName);
			emailVo.setUserLang(userEntity.getLangCode());
			emailVo.setGroupId(CommonConstant.ENCTSP);

			AuthDetailsVo authDetVo = new AuthDetailsVo();
			authDetVo.setEntityId(userEntity.getEntityLicenseEntity().getId());//
			emailVo.setSystemConfigurationVo(getSystemConfigurationDetails(authDetVo));
			emailVo.setTrEmailFlag(CommonConstant.CONSTANT_ONE);
			if (null != userEntity.getLangCode() && userEntity.getLangCode().equals(CommonConstant.en)) {
				authDetVo.setLangCode(CommonConstant.en);
			} else {
				authDetVo.setLangCode(CommonConstant.jp);
			}

			EmailGeneralDetailsEntity emailGeneralDetailsEntity = emailGeneralDetailsService
					.getEmailGeneralDetails(emailVo, authDetVo);

			emailGeneralDetailsService.getEmailGeneralDetails(emailVo, emailGeneralDetailsEntity);

			emailVo.setMessageContent(getMessage("dear", authDetVo) + " "+userEntity.getFirstName()+"," + "\n"
					+ entityName.concat(" " + emailVo.getMessageContent()) + "\n" + "\n" + "\n"
					+ getMessage("autoGenText", authDetVo));

			if (null != emailVo.getSystemConfigurationVo() && emailVo.getSystemConfigurationVo().size() > 0) {
				emailService.sendEmail(emailVo);
			}

		} catch (CommonException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
/*	@Transactional
	public void sendMail(String generatedPassword, String emailId, AuthDetailsVo authDetailsVo,String userName,String entityName, boolean status)
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
		
		message.setSubject("New Entity Created Successfully");
	
		if(status){
			
			message.setText(
					"Dear Customer," + "\n" + "Your account has been created successfully, and the details are :"
							+ "\n" + "Application URL : 172.16.8.55 " + "\n" + "User Name : "
							+ entityName + "\n" + "User LoginId :  "
							+ userName + "\n" + "Password : " + generatedPassword
			                + "\n\n\n"+"******This is auto generated Mail..Please do not reply to this mail..*****");
			
		}else{
			message.setText(
					"Dear Customer," + "\n" + "Your account has been created successfully, and the details are :"
							+ "\n" + "Application URL : 172.16.8.55 " + "\n" 
							+ "\n\n\n"+"******This is auto generated Mail..Please do not reply to this mail..*****");					
		}
		
		
		message.saveChanges();
		Transport.send(message);

	}
	private class SMTPAuthenticator extends Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(mailMessages.getSmtpUserName(), mailMessages.getSmtpPassword());
		}
	} */
}
	/**
	 * This class is used for SMTP Authentication
	 * 
	 *
	 */
/*	private class SMTPAuthenticator extends Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(mailMessages.getSmtpUserName(), mailMessages.getSmtpPassword());
		}
	}*/
 