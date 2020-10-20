package com.srm.coreframework.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.config.MailMessages;
import com.srm.coreframework.config.PicturePath;
import com.srm.coreframework.controller.CommonController;
import com.srm.coreframework.dao.UserMasterDAO;
import com.srm.coreframework.entity.EntityLicense;
import com.srm.coreframework.entity.SubLocation;
import com.srm.coreframework.entity.UserDepartment;
import com.srm.coreframework.entity.UserEntity;
import com.srm.coreframework.entity.UserLocation;
import com.srm.coreframework.entity.UserRole;
import com.srm.coreframework.repository.UserEntityMappingRepository;
import com.srm.coreframework.repository.UserMasterRepository;
import com.srm.coreframework.response.JSONResponse;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.EmailVo;
import com.srm.coreframework.vo.UserMasterVO;
import com.sun.mail.util.MailSSLSocketFactory;

@Component
public class UserMasterService extends CommonController<UserMasterVO> {
	Logger logger = LoggerFactory.getLogger(UserMasterService.class);

	@Autowired
	private UserMasterRepository userMasterRepository;
	
	@Autowired
	private UserMasterDAO userMasterDAO;

	@Autowired
	private UserEntityMappingRepository userEntityMappingRepository;
	
	@Autowired
	private MailMessages mailMessages;
	
	
	@Autowired
	private PicturePath picturePath;

	
	
	/**
	 * Method is used to Get all the user details (only active and non delete
	 * user).
	 * 
	 * @return userMasterVo List<UserMasterVo>
	 */
	@Transactional
	public List<UserMasterVO> getAll(AuthDetailsVo authDetailsVo) {

		List<UserMasterVO> listUserMasterVo = new ArrayList<UserMasterVO>();

		List<Object[]> userList = null;

		// To get all the user Details from DB
		try {

			userList = userMasterDAO.getAll(authDetailsVo);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dbFailure",authDetailsVo));
		}

		// Set all the fields of user details
		if (userList != null && userList.size() > 0) {

			try {

				listUserMasterVo = getAllList(userList);

			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new CommonException(e.getMessage());
			}

		}
		return listUserMasterVo;

	}

	/**
	 * Method is used to Get all list the user details.
	 * 
	 * @param userList
	 *            List<UserEntity>
	 * @return userMasterVoList List<UserMasterVo>
	 */
	public List<UserMasterVO> getAllList(List<Object[]> userList) {

		List<UserMasterVO> userMasterVoList = new ArrayList<UserMasterVO>();
		for (Object[] userEntity : userList) {
			
			UserMasterVO userMasterVo = new UserMasterVO();

		/*	if(null != (String)((Object[]) userEntity)[6]){
			// Password decryption
			try {
				decryptedPassword = CodeSecurity.decrypt((String)((Object[]) userEntity)[6]);
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new CommonException(getMessage("passwordEncription"));
			}
			}*/
			if(0 != (int)((Object[]) userEntity)[0]){
			userMasterVo.setId((int)((Object[]) userEntity)[0]);
			}
			if(null != ((Object[]) userEntity)[1]){
			userMasterVo.setUserLoginId((String)((Object[]) userEntity)[1]);
			}
			if (null != ((Object[]) userEntity)[2]) {
				userMasterVo.setUserEmployeeId((String)((Object[]) userEntity)[2]);
			}

			/*if (null != decryptedPassword) {
				userMasterVo.setPassword(decryptedPassword);
			}*/

			if (null != ((Object[]) userEntity)[3]) {
				userMasterVo.setFirstName((String)((Object[]) userEntity)[3]);
			}

			if (null != ((Object[]) userEntity)[4]) {
				userMasterVo.setMiddleName((String)((Object[]) userEntity)[4]);
			}

			if (null != ((Object[]) userEntity)[5]) {
				userMasterVo.setLastName((String)((Object[]) userEntity)[5]);
			}

			if (null != ((Object[]) userEntity)[7]) {
				userMasterVo.setEmailId((String)((Object[]) userEntity)[7]);
			}

			if (null !=((Object[]) userEntity)[9]) {
				userMasterVo.setPhoneNumber((String)((Object[]) userEntity)[9]);
			}

			if (null != ((Object[]) userEntity)[8]) {
				userMasterVo.setMobile((String)((Object[]) userEntity)[8]);
			}

			if (null != ((Object[]) userEntity)[10]) {
				userMasterVo.setUserLocationName((String)((Object[]) userEntity)[10]);
			}
		
			if (null != ((Object[]) userEntity)[11]) {
				userMasterVo.setSubLocationName((String)((Object[]) userEntity)[11]);
			}

			if (null != ((Object[]) userEntity)[12]) {
				userMasterVo.setUserDepartmentName((String)((Object[]) userEntity)[12]);
			}
		
			if (null != ((Object[]) userEntity)[13]) {
				userMasterVo.setUserRoleName((String)((Object[]) userEntity)[13]);
			}

			if (null != ((Object[]) userEntity)[14]) {
				
				String lang = (String)((Object[]) userEntity)[14];
				if(lang.equals(CommonConstant.en)){
					userMasterVo.setLangCode(CommonConstant.ENGLISH);
				}else if(lang.equals(CommonConstant.jp)){
					userMasterVo.setLangCode(CommonConstant.JAPANESE);
				}							
			}
			
			userMasterVoList.add(userMasterVo);
		}
		return userMasterVoList;
	}

	public String getDefaultProfilePath() {
		String fileName = picturePath.getDefaultImage();
		String path = picturePath.getUserFilePath();
		File fileToCreate = new File(path);

		if (fileToCreate.exists()) {
			path = path + CommonConstant.SLASH;

			if (!fileToCreate.exists()) {
				fileToCreate.mkdir();
			}
		} else {
			fileToCreate.mkdir();
			path = path + CommonConstant.SLASH;
			fileToCreate = new File(path);
			fileToCreate.mkdir();
		}

		fileToCreate = new File(path + fileName);
		path = path + fileName;
		return path;
	}
	
	
	/**
	 * Method is used to create the user details
	 * 
	 * @param userMasterVo
	 *            UserMasterVo
	 * @throws Exception
	 */
	@Transactional
	public UserMasterVO create(UserMasterVO userMasterVo,AuthDetailsVo authDetailsVo) throws CommonException ,Exception {
			
		UserEntity userEntity = new UserEntity();
		String encryptedPassword = null;
		List<UserEntity> userEntityList = new ArrayList<>();

		List<UserEntity> employeeList = new ArrayList<>();
		
		// Set all the fields of user details to create

		// userMasterVo.setPassword(passwordGenerator());
		userEntity.setUserEmployeeId(userMasterVo.getUserEmployeeId());
		userEntity.setFirstName(userMasterVo.getFirstName());
		userEntity.setMiddleName(userMasterVo.getMiddleName());
		userEntity.setLastName(userMasterVo.getLastName());
		
		String path = getDefaultProfilePath();			
		userEntity.setUserProfile(path);
		
		//password validation
		passwordLicenseValidation(userMasterVo.getPassword(),authDetailsVo);
		
		try {
			//encryptedPassword = CodeSecurity.encrypt(userMasterVo.getPassword());
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			encryptedPassword = passwordEncoder.encode(userMasterVo.getPassword());					 
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("passwordEncription",authDetailsVo));
		}

		/*// Level check for department
		if (userMasterVo.getCommonId() == 1) {
			int levelCheck = 0;
			try {
				levelCheck = userDao.levelCheck(userMasterVo);
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new CommonException(getMessage("dbFailure"));
			}

			if (levelCheck != 0) {
				throw new CommonException(userMessages.getLevelCheck());
			}
		}*/
		
		if(null != userMasterVo.getLangCode()){
			if(userMasterVo.getLangCode().equals(CommonConstant.en)){
				userEntity.setLangCode(CommonConstant.en);
			}else if(userMasterVo.getLangCode().equals(CommonConstant.jp)){
				userEntity.setLangCode(CommonConstant.jp);
			}
		}
		
		if(null == userEntity.getLangCode()){
			userEntity.setLangCode(CommonConstant.en);
		}
		userEntity.setPassword(encryptedPassword);
		//userEntity.setSalutationId(userMasterVo.getSalutationId());
		userEntity.setEmailId(userMasterVo.getEmailId());
		userEntity.setPhoneNumber(userMasterVo.getPhoneNumber());
		userEntity.setMobile(userMasterVo.getMobile());
		userEntity.setCurrentAddress(userMasterVo.getCurrentAddress());
		userEntity.setPermanentAddress(userMasterVo.getPermanentAddress());
		if(null != userMasterVo.getDivision()){
			userEntity.setDivisionId(userMasterVo.getDivision());
			}

		try {
			Integer entityId = authDetailsVo.getEntityId();
			//String loginId = null;
			userEntityList = userMasterRepository.uniqueUserLoginId(entityId,userMasterVo.getUserLoginId());

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dbFailure",authDetailsVo));
		}

		try{
			employeeList = userMasterRepository.uniqueUserEmployeeId(authDetailsVo.getEntityId(),userMasterVo.getUserEmployeeId());
			
		}catch(Exception e){
			throw new CommonException(getMessage("dbFailure",authDetailsVo));
		}
		if(employeeList == null || employeeList.size() == 0){
			userEntity.setUserEmployeeId(userMasterVo.getUserEmployeeId());
		}else{
			throw new CommonException(getMessage("user.employeeId.unique",authDetailsVo));
		}
		if (userEntityList == null || userEntityList.size() == 0) {
			userEntity.setUserName(userMasterVo.getUserLoginId());
		} else {
			throw new CommonException(getMessage("uniqueLoginId",authDetailsVo));
		}
		UserLocation userLocationEntity = new UserLocation();
		userLocationEntity.setId(userMasterVo.getUserLocation());
		userEntity.setUserLocationEntity(userLocationEntity);

		SubLocation subLocationEntity = new SubLocation();
		subLocationEntity.setSublocationId(userMasterVo.getSubLocation());
		userEntity.setSubLocationEntity(subLocationEntity);

		UserDepartment userDepartmentEntity = new UserDepartment();
		userDepartmentEntity.setId(userMasterVo.getUserDepartment());
		userEntity.setUserDepartmentEntity(userDepartmentEntity);

		UserRole userRoleEntity = new UserRole();
		userRoleEntity.setId(userMasterVo.getUserRole());
		userEntity.setUserRoleEntity(userRoleEntity);

		//default Role
		userEntity.setDefaultRole(userRoleEntity);
		
		userEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);
		userEntity.setActiveFlag(CommonConstant.FLAG_ONE);
		userEntity.setCreateBy(authDetailsVo.getUserId());
		userEntity.setCreateDate(CommonConstant.getCalenderDate());
		userEntity.setUpdateBy(authDetailsVo.getUserId());
		userEntity.setUpdateDate(CommonConstant.getCalenderDate());

		
		subLocationEntity.setCreateBy(authDetailsVo.getUserId());
		subLocationEntity.setCreateDate(CommonConstant.getCalenderDate());
		subLocationEntity.setUpdateBy(authDetailsVo.getUserId());
		subLocationEntity.setUpdateDate(CommonConstant.getCalenderDate());
		// sendMail(userMasterVo.getPassword(), userEntity.getEmailId());
		
		
		EntityLicense entityLicenseEntity =  new EntityLicense();
		entityLicenseEntity.setId(authDetailsVo.getEntityId());
		userEntity.setEntityLicenseEntity(entityLicenseEntity);

		userEntity.setChangePasswordDate(CommonConstant.getCalenderDate());
		userEntity.setFirstLogin(CommonConstant.FLAG_ZERO);
		// Create new user
		try {
			userMasterRepository.save(userEntity);
		//	userMasterVo.setUserId(userEntity.getId());
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
		
		//send email for New Registration
		//createEmail(userEntity);
		
		
		return userMasterVo ; 
	}

	/**
	 * Method is used to update the user details
	 * 
	 * @param userMasterVo
	 *            UserMasterVo
	 */
	@Transactional
	public void update(UserMasterVO userMasterVo,AuthDetailsVo authDetailsVo) {

		UserEntity userEntity = new UserEntity();
		 

		// Get the data from DB using user ID
		try {

			userEntity = findId(userMasterVo.getId());

		} catch (NoResultException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("noResultFound",authDetailsVo));
		} catch (NonUniqueResultException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("noUniqueFound",authDetailsVo));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}

		// To set all the fields in entity

		if (userEntity != null) {

			try {
				//encryptedPassword = CodeSecurity.encrypt(userMasterVo.getPassword());
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new CommonException(getMessage("passwordEncription", authDetailsVo));
			}

			//userEntity.setPassword(encryptedPassword);
/*
			// Level check for department
			if (userMasterVo.getCommonId() == 1) {
				int levelCheck = 0;
				try {
					levelCheck = userDao.levelCheck(userMasterVo);
				} catch (Exception e) {
					logger.error(e.getMessage());
					throw new CommonException(getMessage("dbFailure"));
				}

				if (levelCheck != 0) {
					throw new CommonException(userMessages.getLevelCheck());
				}
			}*/
			userEntity.setUserEmployeeId(userMasterVo.getUserEmployeeId());
			userEntity.setFirstName(userMasterVo.getFirstName());
			userEntity.setMiddleName(userMasterVo.getMiddleName());
			userEntity.setLastName(userMasterVo.getLastName());
			userEntity.setEmailId(userMasterVo.getEmailId());
			userEntity.setPhoneNumber(userMasterVo.getPhoneNumber());
			userEntity.setMobile(userMasterVo.getMobile());
			userEntity.setCurrentAddress(userMasterVo.getCurrentAddress());
			userEntity.setPermanentAddress(userMasterVo.getPermanentAddress());
			userEntity.setUserName(userMasterVo.getUserLoginId());
			if(null != userMasterVo.getDivision()){
				userEntity.setDivisionId(userMasterVo.getDivision());
				}
			
			UserLocation userLocationEntity = new UserLocation();
			userLocationEntity.setId(userMasterVo.getUserLocation());
			userEntity.setUserLocationEntity(userLocationEntity);

			SubLocation subLocationEntity = new SubLocation();
			subLocationEntity.setSublocationId(userMasterVo.getSubLocation());
			userEntity.setSubLocationEntity(subLocationEntity);

			UserDepartment userDepartmentEntity = new UserDepartment();
			userDepartmentEntity.setId(userMasterVo.getUserDepartment());
			userEntity.setUserDepartmentEntity(userDepartmentEntity);

			UserRole userRoleEntity = new UserRole();
			userRoleEntity.setId(userMasterVo.getUserRole());
			userEntity.setUserRoleEntity(userRoleEntity);
			userEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);
			userEntity.setActiveFlag(CommonConstant.FLAG_ONE);
			/*CommonStorageEntity commonStorageEntity = new CommonStorageEntity();
			commonStorageEntity.setCommonId(userMasterVo.getCommonId());
			commonStorageEntity.setItemValue(userMasterVo.getItemValue());
			userEntity.setLevelId(userMasterVo.getCommonId());*/

			/*SystemApplicationEntity systemApplicationEntity = new SystemApplicationEntity();
			systemApplicationEntity.setSysAppId(CommonConstant.CONSTANT_TWO);
			userEntity.setSystemApplicationEntity(systemApplicationEntity);*/

			//userEntity.setActiveFlag(userMasterVo.getActiveFlag());
			if (null != userMasterVo.getActiveFlag() && userMasterVo.getActiveFlag().equals(CommonConstant.STRING_ONE)) {
				userEntity.setActiveFlag(CommonConstant.FLAG_ONE);
			}
			userEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);
			userEntity.setUpdateBy(authDetailsVo.getUserId());
			userEntity.setUpdateDate(CommonConstant.getCalenderDate());
			EntityLicense entityLicenseEntity =  new EntityLicense();
			entityLicenseEntity.setId(authDetailsVo.getEntityId());
			userEntity.setEntityLicenseEntity(entityLicenseEntity);
			
			if(null != userMasterVo.getLangCode()){
				if(userMasterVo.getLangCode().equals(CommonConstant.en)){
					userEntity.setLangCode(CommonConstant.en);
				}else if(userMasterVo.getLangCode().equals(CommonConstant.jp)){
					userEntity.setLangCode(CommonConstant.jp);
				}
			}
		}

		// Update the User details
		try {
			userMasterRepository.save(userEntity);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
	}

	/**
	 * Method is used to find the details of user using id
	 * 
	 * @param id
	 *            Integer
	 * @return userEntity UserEntity
	 */
	@Transactional
	private UserEntity findId(Integer id) {

		UserEntity userEntity = null;

		userEntity = (UserEntity) userMasterRepository.findOne(id);

		return userEntity;

	}

	/**
	 * Method is used to delete the user detail
	 * 
	 * @param userMasterVo
	 *            UserMasterVo
	 */
	@Transactional
	public void delete(UserMasterVO userMasterVo,AuthDetailsVo authDetailsVo) {

		for (Integer id : userMasterVo.getDeleteItem()) {

			UserEntity userEntity = new UserEntity();

			// Get the data from DB using the user Id
			try {

				userEntity = findId(id);

			} catch (NoResultException e) {
				logger.error(e.getMessage());
				throw new CommonException(getMessage("noResultFound",authDetailsVo));
			} catch (NonUniqueResultException e) {
				logger.error(e.getMessage());
				throw new CommonException(getMessage("noUniqueFound",authDetailsVo));
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new CommonException(getMessage("dataFailure",authDetailsVo));
			}

			// Set the fields and Update for delete the Record
			try {

				if (userEntity != null) {
					userEntity.setDeleteFlag(CommonConstant.FLAG_ONE);
					userEntity.setUpdateBy(authDetailsVo.getUserId());
					userEntity.setUpdateDate(CommonConstant.getCalenderDate());
					userMasterRepository.save(userEntity);
				}

			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new CommonException(getMessage("dataFailure",authDetailsVo));
			}
		}

	}

	/**
	 * Method is used to Load of user detail
	 * 
	 * @param userMasterVo
	 *            UserMasterVo
	 * @return userMasterVo UserMasterVo
	 */
	@Transactional
	public UserMasterVO load(UserMasterVO userMasterVo,AuthDetailsVo authDetailsVo) {

		UserEntity userEntity = new UserEntity();

		String decryptedPassword = null;

		// Get the data from DB using the User Id
		try {

			userEntity = findId(userMasterVo.getId());

		} catch (NoResultException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("noResultFound",authDetailsVo));
		} catch (NonUniqueResultException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("noUniqueFound",authDetailsVo));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}

		// Password decryption
		try {
			//decryptedPassword = CodeSecurity.decrypt(userEntity.getPassword());
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("passwordEncription",authDetailsVo));
		}

		// Set all the fields

		if (userEntity != null) {
			userMasterVo.setId(userEntity.getId());
			userMasterVo.setUserLoginId(userEntity.getUserName());
			
			if (null != userEntity.getUserEmployeeId()) {
				userMasterVo.setUserEmployeeId(userEntity.getUserEmployeeId());
			}

			if (null != userEntity.getPassword()) {
				userMasterVo.setPassword(decryptedPassword);
			}

			if (null != userEntity.getFirstName()) {
				userMasterVo.setFirstName(userEntity.getFirstName());
			}

			if (null != userEntity.getMiddleName()) {
				userMasterVo.setMiddleName(userEntity.getMiddleName());
			}

			if (null != userEntity.getLastName()) {
				userMasterVo.setLastName(userEntity.getLastName());
			}

			if (null != userEntity.getUserLocationEntity() && null != userEntity.getUserLocationEntity().getId()) {
				userMasterVo.setUserLocation(userEntity.getUserLocationEntity().getId());
			}

			if (null != userEntity.getUserLocationEntity()
					&& null != userEntity.getUserLocationEntity().getUserLocationName()) {
				userMasterVo.setUserLocationName(userEntity.getUserLocationEntity().getUserLocationName());
			}

			if (null != userEntity.getSubLocationEntity()
					&& 0 != userEntity.getSubLocationEntity().getSublocationId()) {
				userMasterVo.setSubLocation(userEntity.getSubLocationEntity().getSublocationId());
			}

			if (null != userEntity.getSubLocationEntity()
					&& null != userEntity.getSubLocationEntity().getSubLocationName()) {
				userMasterVo.setSubLocationName(userEntity.getSubLocationEntity().getSubLocationName());
			}

			if (null != userEntity.getUserRoleEntity() && null != userEntity.getUserRoleEntity().getId()) {
				userMasterVo.setUserRole(userEntity.getUserRoleEntity().getId());
			}

			if (null != userEntity.getUserRoleEntity() && null != userEntity.getUserRoleEntity().getUserRoleName()) {
				userMasterVo.setUserRoleName(userEntity.getUserRoleEntity().getUserRoleName());
			}

			if (null != userEntity.getUserDepartmentEntity() && null != userEntity.getUserDepartmentEntity().getId()) {
				userMasterVo.setUserDepartment(userEntity.getUserDepartmentEntity().getId());
			}

			if (null != userEntity.getUserDepartmentEntity()
					&& null != userEntity.getUserDepartmentEntity().getUserDepartmentName()) {
				userMasterVo.setUserDepartmentName(userEntity.getUserDepartmentEntity().getUserDepartmentName());
			}

			if (null != userEntity.getDivisionId()) {
				userMasterVo.setDivision(userEntity.getDivisionId());
			}

			if (null != userEntity.getEmailId()) {
				userMasterVo.setEmailId(userEntity.getEmailId());
			}

			if (null != userEntity.getPhoneNumber()) {
				userMasterVo.setPhoneNumber(userEntity.getPhoneNumber());
			}

			if (null != userEntity.getMobile()) {
				userMasterVo.setMobile(userEntity.getMobile());
			}

			if (null != userEntity.getCurrentAddress()) {
				userMasterVo.setCurrentAddress(userEntity.getCurrentAddress());
			}

			if (null != userEntity.getPermanentAddress()) {
				userMasterVo.setPermanentAddress(userEntity.getPermanentAddress());
			}

			if(null != userEntity.getLangCode()){
				userMasterVo.setLangCode(userEntity.getLangCode());
			}
			
			//userMasterVo.setActiveFlag(userEntity.getActiveFlag());
			if (null != userEntity.getActiveFlag() && userEntity.getActiveFlag().equals(CommonConstant.FLAG_ONE)) {
				userMasterVo.setStatus(CommonConstant.Active);
			} else {
				userMasterVo.setStatus(CommonConstant.InActive);
			}

			

			/*if (null != userEntity.getCommonStorageEntity()
					&& null != userEntity.getCommonStorageEntity().getItemValue()) {
				userMasterVo.setItemValue(userEntity.getCommonStorageEntity().getItemValue());
			}*/
		}
		return userMasterVo;

	}

	/**
	 * Method is to search the user
	 * 
	 * @param userMasterVo
	 *            UserMasterVo
	 * @return userMasterVoList List<UserMasterVo>
	 */
	@Transactional
	public List<UserMasterVO> getAllSearch(UserMasterVO userMasterVo,AuthDetailsVo authDetailsVo) {

		List<UserMasterVO> userMasterVoList = new ArrayList<UserMasterVO>();

		List<Object[]> userList = null;

		// Get and Search the date from DB.
		try {

			userList = userMasterDAO.getAllSearch(userMasterVo,authDetailsVo);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dbFailure",authDetailsVo));
		}

		// Set all the fields
		try {

			if (userList != null && userList.size() > 0) {

				userMasterVoList = getAllList(userList);

			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("copyFailure",authDetailsVo));
		}

		return userMasterVoList;

	}

	public int getEmailUnique(UserMasterVO userMasterVo,AuthDetailsVo authDetailsVo) {
		try {
			
			Integer entityId = authDetailsVo.getEntityId();
			String emailId = null;
			
			int count = userMasterRepository.getEmailUnique(entityId,emailId);
			return count;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dbFailure",authDetailsVo));
		}

	}
	
	public int getEmployeeIdUnique(UserMasterVO userMasterVo,AuthDetailsVo authDetailsVo) {
		try {
			Integer entityId = authDetailsVo.getEntityId();
			String empId = null;
			
			int count = userMasterRepository.getEmployeeIdUnique(entityId,empId);
			return count;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dbFailure",authDetailsVo));
		}

	}

	/**
	 * Method is used to generate the password to the user.
	 * 
	 * @return password
	 */
	@Transactional
	public String passwordGenerator() {

		String values = CommonConstant.CAPITAL_CHARS + CommonConstant.SMALL_CHARS + CommonConstant.NUMBERS
				+ CommonConstant.SYMBOLS;
		Random rndm_method = new Random();
		char[] password = new char[CommonConstant.NEW_PASSWORD_LENGTH];

		for (int i = 0; i < CommonConstant.NEW_PASSWORD_LENGTH; i++) {
			password[i] = values.charAt(rndm_method.nextInt(values.length()));
		}
		return password.toString();
	}

	/**
	 * Method is used to send the mail to the users mail id with the auto
	 * generated password.
	 * 
	 * @param generatedPassword
	 * @param emailId
	 * @throws CommonException
	 * @throws Exception
	 */
/*	@Transactional
	public void sendMail(String generatedPassword, String emailId,AuthDetailsVo authDetailsVo) throws CommonException, Exception {

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
			throw new CommonException(getMessage("invalidEmail",authDetailsVo));
			// ex.printStackTrace();
		}
		message.setSubject("New Password");
		message.setText("New Password generated is " + generatedPassword);
		message.saveChanges();
		Transport.send(message);

	}


	private class SMTPAuthenticator extends Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(mailMessages.getSmtpUserName(), mailMessages.getSmtpPassword());
		}
	}*/


	@Transactional
	public void updateAccessToken(UserMasterVO userMasterVO,AuthDetailsVo authDetailsVo) {

		Object[] user = userMasterDAO.findByuserName(userMasterVO.getUserLoginId());

		if(!userMasterVO.getAccessToken().equals((String)user[0])){
			throw new CommonException(getMessage("user.validate.accessToken",authDetailsVo));
		}
	}	
	
}
