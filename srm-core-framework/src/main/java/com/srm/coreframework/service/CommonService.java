package com.srm.coreframework.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.config.LoginAuthException;
import com.srm.coreframework.controller.CommonMessage;
import com.srm.coreframework.dao.CommonDAO;
import com.srm.coreframework.entity.EntityLicenseDetails;
import com.srm.coreframework.entity.UserAuditLogEntity;
import com.srm.coreframework.entity.UserEntity;
import com.srm.coreframework.repository.UserAuditLogRepository;
import com.srm.coreframework.repository.UserRepository;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.CommonVO;
import com.srm.coreframework.vo.EntityLicenseVO;
import com.srm.coreframework.vo.ScreenAuthenticationVO;
import com.srm.coreframework.vo.ScreenAuthorizationVO;
import com.srm.coreframework.vo.ScreenJsonVO;
import com.srm.coreframework.vo.ScreenVO;
import com.srm.coreframework.vo.SystemConfigurationVo;
import com.srm.coreframework.vo.UserMasterVO;
 

@Service
public class CommonService extends CommonMessage {

	@Autowired
	CommonDAO commonDAO;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserAuditLogRepository userAuditLogRepository;
	
	@Transactional
	public ScreenAuthorizationVO getScreenAuthorization(ScreenJsonVO screenJson, AuthDetailsVo authDetailsVo) {
		Integer roleId = authDetailsVo.getRoleId();
		ScreenAuthorizationVO screenAuthorizationMaster = new ScreenAuthorizationVO();
		try {
			Integer screenAuthenticationId = commonDAO.getScreenAuthenticationId(roleId, screenJson.getScreenId(),
					screenJson.getSubScreenId(), authDetailsVo);
			if (screenAuthenticationId != null) {
				List<Object[]> listFieldObject = commonDAO.getScreenField(screenAuthenticationId);
				if (listFieldObject != null) {
					List<String> screenFieldDisplayVoList = new ArrayList<String>();
					for (Object[] object : listFieldObject) {

						screenFieldDisplayVoList.add((String) (((Object[]) object)[1]));
					}
					screenAuthorizationMaster.setScreenFieldDisplayVoList(screenFieldDisplayVoList);
				}
				List<Object[]> listFunctionObject = commonDAO.getScreenFunction(screenAuthenticationId);
				if (listFunctionObject != null) {
					List<String> screenFunctionDisplayList = new ArrayList<String>();
					for (Object[] object : listFunctionObject) {

						screenFunctionDisplayList.add((String) (((Object[]) object)[1]));
					}
					screenAuthorizationMaster.setScreenFunctionDisplayList(screenFunctionDisplayList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return screenAuthorizationMaster;

	}

	@Transactional
	public ScreenAuthenticationVO getScreenAuhentication(AuthDetailsVo authDetailsVo) {
		ScreenAuthenticationVO screenAuthentication = new ScreenAuthenticationVO();
		List<ScreenVO> screenVoList = new ArrayList<ScreenVO>();
		int oldValue = 0;
		int newValue = 0;
		try {
			List<Object[]> screenAuthenticationEntities = commonDAO.getScreenAuhentication(authDetailsVo);
			if (null != screenAuthenticationEntities) {
				for (Object[] object : screenAuthenticationEntities) {
					ScreenVO screenVo = new ScreenVO();
					newValue = (Integer) (((Object[]) object)[0]);
					if (oldValue != newValue) {

						if (null != (((Object[]) object)[1])) {
							screenVo.setScreenName((String) (((Object[]) object)[1]));
						}
						if (null != (((Object[]) object)[2])) {
							screenVo.setScreenTypeFlag((Character) (((Object[]) object)[2]));
						}
						if (null != (((Object[]) object)[3])) {
							screenVo.setScreenUrl((String) (((Object[]) object)[3]));
						}
						if (null != (((Object[]) object)[4])) {
							screenVo.setScreenIcon((String) (((Object[]) object)[4]));
						}
						screenVoList.add(screenVo);
					}
					oldValue = newValue;
				}
				screenAuthentication.setScreenVoList(screenVoList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return screenAuthentication;
	}
	
	@Transactional
	public void licenseDateValidation(AuthDetailsVo authDetailsVo)
			throws IllegalAccessException, InvocationTargetException, ParseException {

		List<EntityLicenseDetails> entityLicenseDetailsEntity = commonDAO.getDetaillicenseDateValidation(authDetailsVo);

		if(entityLicenseDetailsEntity.size()==0){
			throw new LoginAuthException(getMessage("entityActiveMessage", authDetailsVo));
		}
		for (EntityLicenseDetails entityLicenseDetails : entityLicenseDetailsEntity) {
			SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String currentDate = "";
			currentDate = (sdformat.format(date));
			String licensceDate = "";
			if (null != entityLicenseDetails.getToDate()) {
				licensceDate = (sdformat.format(entityLicenseDetails.getToDate()));
			}
			if (null != entityLicenseDetails.getToDate()) {

				if (currentDate.equals(licensceDate)) {

				} else if (CommonConstant.getCalenderDate().compareTo(entityLicenseDetails.getToDate()) > 0) {
					String dateStr = CommonConstant.formatDatetoString(entityLicenseDetails.getToDate(), CommonConstant.FORMAT_DD_MM_YYYY_HYPHEN);
					throw new LoginAuthException(getMessage("dateTo.validation", authDetailsVo)+dateStr);
				}

			}
		}
	}
	
	@Transactional
	public void getUserValidation(AuthDetailsVo authDetailsVo)
			throws IllegalAccessException, InvocationTargetException, ParseException {

		List<EntityLicenseDetails> entityLicenseDetailsEntity = commonDAO.getDetail(authDetailsVo.getEntityId());

		for (EntityLicenseDetails entityLicenseDetails : entityLicenseDetailsEntity) {
			SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String currentDate = "";
			currentDate = (sdformat.format(date));
			String licensceDate = "";
			if(null != entityLicenseDetails.getToDate()){
				licensceDate = (sdformat.format(entityLicenseDetails.getToDate()));
			}
			if (null != entityLicenseDetails.getToDate()) {
				
				if(currentDate.equals(licensceDate)){
					
				}else if (CommonConstant.getCalenderDate().compareTo(entityLicenseDetails.getToDate()) > 0) {
					throw new CommonException(getMessage("dateTo.validation", authDetailsVo));
				}
				

			}
			if(null != entityLicenseDetails.getUserCount() && entityLicenseDetails.getUserCount()>0){
			if (null != entityLicenseDetails.getUserLicense()) {

				String requestCount = new String(
						Base64.getDecoder().decode(entityLicenseDetails.getUserLicense()));

				int count = Integer.parseInt(requestCount);
						
				List<Object[]> logList = commonDAO.getEntityLogList(entityLicenseDetails.getEntityId());

			    int usedUserCount = count - calculateNewUserCount(logList);
				
				if (null != entityLicenseDetails.getUserCount()) {

					if (usedUserCount >= entityLicenseDetails.getUserCount()) {
						throw new CommonException(getMessage("userLimitMessage", authDetailsVo));
					}

					}
				}
			}
		}

	}

	@Transactional
	public void getTransactionValidation(AuthDetailsVo authDetailsVo)
			throws IllegalAccessException, InvocationTargetException, ParseException {

		List<EntityLicenseDetails> entityLicenseDetailsEntity = commonDAO.getDetail(authDetailsVo.getEntityId());

		for (EntityLicenseDetails entityLicenseDetails : entityLicenseDetailsEntity) {
			SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String currentDate = "";
			currentDate = (sdformat.format(date));

			String licensceDate = "";
			if(null != entityLicenseDetails.getToDate()){
				licensceDate = (sdformat.format(entityLicenseDetails.getToDate()));
			}
			
			if (null != entityLicenseDetails.getToDate()) {
				if (currentDate.equals(licensceDate)) {
					
				} else if (CommonConstant.getCalenderDate().compareTo(entityLicenseDetails.getToDate()) > 0) {
					throw new CommonException(getMessage("dateTo.validation", authDetailsVo));
				}

			}

			if (null != entityLicenseDetails.getTransactionCount() && entityLicenseDetails.getTransactionCount() > 0) {
				if (null != entityLicenseDetails.getTransactionLicense()) {

					String requestCount = new String(
							Base64.getDecoder().decode(entityLicenseDetails.getTransactionLicense()));

					int reqcount = Integer.parseInt(requestCount);

					List<Object[]> logList = commonDAO.getEntityLogList(entityLicenseDetails.getEntityId());

					int usedTransactionCount = calculateNewTransactionCount(logList);

					usedTransactionCount = reqcount - usedTransactionCount;

					if (null != entityLicenseDetails.getTransactionCount()) {

						if (usedTransactionCount >= entityLicenseDetails.getTransactionCount()) {
							throw new CommonException(getMessage("transactionLimitMessage", authDetailsVo));
						}

					}
				}
			}
		}
	}	
	
	public int calculateNewTransactionCount(List<Object[]> logList) {
		 
		int count = 0;
		int usedTransactionCount = 0;

		try {

			if (logList.size() > 0) {
				for (Object obj : logList) {

					if (count == 1) {

						if (null != (String) ((Object[]) obj)[1]) {
							usedTransactionCount = Integer.parseInt(decrypt((String) ((Object[]) obj)[1]));
						}						 
					}
					count = count + 1;
				}
			} else {
				for (Object obj : logList) {
					if (null != (String) ((Object[]) obj)[1]) {
						usedTransactionCount = Integer.parseInt(decrypt((String) ((Object[]) obj)[1]));
					}								 
				}
			}			
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usedTransactionCount;
	}
	
	
	public int calculateNewUserCount(List<Object[]> logList) {
		 
		int count = 0;
		int usedTransactionCount = 0;

		try {

			if (logList.size() > 0) {
				for (Object obj : logList) {

					if (count == 1) {

						if (null != (String) ((Object[]) obj)[2]) {
							usedTransactionCount = Integer.parseInt(decrypt((String) ((Object[]) obj)[2]));
						}						 
					}
					count = count + 1;
				}
			} else {
				for (Object obj : logList) {
					if (null != (String) ((Object[]) obj)[2]) {
						usedTransactionCount = Integer.parseInt(decrypt((String) ((Object[]) obj)[2]));
					}								 
				}
			}			
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usedTransactionCount;
	}
	

/*	@Transactional
	public void getUpdateEntityUser(String count) {

		commonDAO.updateEntityUser(count);

	}*/

	public static String decrypt(String value) throws Exception {
		byte[] decodedBytes = Base64.getDecoder().decode(value);
		return new String(decodedBytes);
	}
	
	@Transactional
	public void getTransactionCountUpdate(String count,EntityLicenseDetails entityLicenseDetails) {

		commonDAO.getTransactionCountUpdate(count,entityLicenseDetails);

	}
	
	
	@Transactional
	public void getUserCountUpdate(String count,EntityLicenseDetails entityLicenseDetails) {

		commonDAO.getUserCountUpdate(count,entityLicenseDetails);

	}
	
	@Transactional
	public void addEntityRequest(AuthDetailsVo authDetailsVo){
		
		EntityLicenseDetails entityLicenseDetails =commonDAO.deleteEntityRequest(authDetailsVo.getEntityId());
		
		String requestCount = new String(Base64.getDecoder().decode(entityLicenseDetails.getTransactionLicense()));

		int reqcount = Integer.parseInt(requestCount);

		String usercount = String.valueOf(reqcount + 1);

		requestCount = new String(Base64.getEncoder().encodeToString(usercount.getBytes()));

		getTransactionCountUpdate(requestCount,entityLicenseDetails);
	}
	
	@Transactional
	public void deleteEntityRequest(AuthDetailsVo authDetailsVo){
		
		EntityLicenseDetails entityLicenseDetails =commonDAO.deleteEntityRequest(authDetailsVo.getEntityId());
		
		String requestCount = new String(Base64.getDecoder().decode(entityLicenseDetails.getTransactionLicense()));

		int reqcount = Integer.parseInt(requestCount);

		String usercount = String.valueOf(reqcount - 1);

		requestCount = new String(Base64.getEncoder().encodeToString(usercount.getBytes()));
		
		getTransactionCountUpdate(requestCount,entityLicenseDetails);
	}
	
	
	
	@Transactional
	public void addEntityUser(AuthDetailsVo authDetailsVo){
		
		EntityLicenseDetails entityLicenseDetails =commonDAO.addEntityUser(authDetailsVo.getEntityId());
		
		String requestCount = new String(Base64.getDecoder().decode(entityLicenseDetails.getUserLicense()));

		int reqcount = Integer.parseInt(requestCount);

		String usercount = String.valueOf(reqcount + 1);

		requestCount = new String(Base64.getEncoder().encodeToString(usercount.getBytes()));

		getUserCountUpdate(requestCount,entityLicenseDetails);
	}
	
	@Transactional
	public void deleteEntityUser(AuthDetailsVo authDetailsVo){
		
		EntityLicenseDetails entityLicenseDetails =commonDAO.addEntityUser(authDetailsVo.getEntityId());
		
		String requestCount = new String(Base64.getDecoder().decode(entityLicenseDetails.getUserLicense()));

		int reqcount = Integer.parseInt(requestCount);

		String usercount = String.valueOf(reqcount - 1);

		requestCount = new String(Base64.getEncoder().encodeToString(usercount.getBytes()));
		
		getUserCountUpdate(requestCount,entityLicenseDetails);
	}

	/*@Transactional
	public Map<String, Integer> getCount(int entityId) {

		Map<String, Integer> map = new HashMap<String, Integer>();

		List<Object> userEntity = commonDAO.getTotalUserCount(entityId);

		map.put("UserCount", userEntity.size());

		List<Object> requestEntity = commonDAO.getTotalRequestCount(entityId);

		map.put("RequestCount", requestEntity.size());

		return map;

	}*/

	@Transactional
	public CommonVO getScreenFields(ScreenJsonVO screenAuthorizationMaster, AuthDetailsVo authDetailsVo) {

		CommonVO commonVO = new CommonVO();

		ScreenAuthorizationVO screenAuthorizationMasterVo = getScreenAuthorization(screenAuthorizationMaster,
				authDetailsVo);
		if (null != screenAuthorizationMasterVo) {

			// Get the Fields List
			commonVO.setScreenFieldDisplayVoList(screenAuthorizationMasterVo.getScreenFieldDisplayVoList());

			// Get the Functions & Side Tab List
			commonVO.setScreenFunctionDisplayList(screenAuthorizationMasterVo.getScreenFunctionDisplayList());

		} else {
			throw new CommonException(getMessage("noAuthorizationAvailableForThisUser", authDetailsVo));

		}

		ScreenAuthenticationVO screenAuthenticationMaster = getScreenAuhentication(authDetailsVo);

		if (null != screenAuthenticationMaster) {
			commonVO.setScreenVoList(screenAuthenticationMaster.getScreenVoList());

		} else {
			throw new CommonException(getMessage("noScreenAvailableForThisUser", authDetailsVo));

		}

		commonVO.setUserName(authDetailsVo.getUserName());

		return commonVO;
	}

	public String getMediaType1(String filename) {
		return commonDAO.getMediaType1(filename);
	}

	@Transactional
	public void updateAccessToken(String accesstoken, String loginId) {

		UserEntity user = commonDAO.getUser(loginId);

		user.setAccessToken(accesstoken);

		userRepository.save(user);

	}
	
	@Transactional
	public Integer getUserId(String userName) {
		Integer userId =0 ;
/*		Object[] obj = userRepository.getUserId(userName);
		if(null != obj && null != obj[0]){
			userId = (Integer)obj[0];
		}*/
		UserEntity userEntity = userRepository.getUserId(userName);
		if(null != userEntity && null != userEntity.getId()){
			userId = userEntity.getId();
		}
		return userId;
	}

		
	public boolean currentTokenValidate(String accessToken) throws CommonException {

		/*
		 * UserEntity user = userRepository.findOne(authDetailsVo.getUserId());
		 * 
		 * if (user.getAccessToken().equals(accessToken)) { return false;
		 * 
		 * }
		 */
		return true;
	}

	public UserEntity getAcessTokenUser(String accessToken) throws CommonException {
		return commonDAO.getAcessTokenUser(accessToken);
	}

	/**
	 * Method to get accessToken from UI headers
	 * 
	 * @param request
	 * @return String
	 */
	public String getHeaderAccessToken(HttpServletRequest request) {
		return commonDAO.getHeaderAccessToken(request);
	}

	public AuthDetailsVo tokenValidate(String accessToken) {
		return commonDAO.tokenValidate(accessToken);

	}

	public byte[] imageLoading(String fileName) throws IOException {
		return commonDAO.imageLoading(fileName);
	}

	public byte[] defaultImage(String fileName) throws IOException {
		return commonDAO.defaultImage(fileName);
	}
	public Date getHolidaySla(AuthDetailsVo authDetailsVo,Date requestDate,Integer slaMinutes){
		return commonDAO.getHolidaySla(authDetailsVo,requestDate, slaMinutes);
	}
	public void fileSizeValidation(MultipartFile filePath) throws CommonException {
		commonDAO.fileSizeValidation(filePath);
	}
	
	public boolean passwordLicenseValidation(String newPassword, AuthDetailsVo authDetailsVo) throws CommonException {
		boolean result = false;
		EntityLicenseVO entityLicenseVO = null;
		if (null != authDetailsVo.getEntityId()) {
			entityLicenseVO = commonDAO.getEntityDetails(authDetailsVo.getEntityId());
		}
		if (null != entityLicenseVO) {

			Integer upper = 0;
			Integer lower = 0;
			int number = 0;
			int special = 0;

			for (int i = 0; i < newPassword.length(); i++) {
				char ch = newPassword.charAt(i);
				if (ch >= 'A' && ch <= 'Z')
					upper++;
				else if (ch >= 'a' && ch <= 'z')
					lower++;
				else if (ch >= '0' && ch <= '9')
					number++;
				else
					special++;
			}

			String message = getMessage("passwordMinimum",authDetailsVo);
			
			if(null != entityLicenseVO.getPasswordLength()){
				message = message + getMessage("passwordMinLength",authDetailsVo) + entityLicenseVO.getPasswordLength()+ ",";
			}
			if(null != entityLicenseVO.getPasswordSpecialChar() && !entityLicenseVO.getPasswordSpecialChar().isEmpty()){
				message = message + entityLicenseVO.getPasswordSpecialChar() + getMessage("passwordSplChar", authDetailsVo);
			}
			if(null != entityLicenseVO.getPasswordNumeric() && !entityLicenseVO.getPasswordNumeric().isEmpty()){
				message = message + entityLicenseVO.getPasswordNumeric() + getMessage("numericChar",authDetailsVo);
			}
			if(null != entityLicenseVO.getPasswordAlphanumericCaps() && !entityLicenseVO.getPasswordAlphanumericCaps().isEmpty()){
				message = message + entityLicenseVO.getPasswordAlphanumericCaps() + getMessage("lowerUpperCase",authDetailsVo);
			}
			
			// Password Length Check
			if (null != entityLicenseVO.getPasswordLength()) {
				if (!(newPassword.length() >= entityLicenseVO.getPasswordLength())) {
					result = true;
					throw new CommonException(message);
				}
			}

			// Password Special Character
			if (null != entityLicenseVO.getPasswordSpecialChar() && !entityLicenseVO.getPasswordSpecialChar().isEmpty()) {
				int i = Integer.parseInt(entityLicenseVO.getPasswordSpecialChar());
				if (!(special >= i)) {
					result = true;
					throw new CommonException(message);
				}
			}

			// Password Numeric Check
			if (null != entityLicenseVO.getPasswordNumeric() && !entityLicenseVO.getPasswordNumeric().isEmpty()) {
				int i = Integer.parseInt(entityLicenseVO.getPasswordNumeric());
				if (!(number >= i)) {
					result = true;
					throw new CommonException(message);
				}
			}

			// Password Alphabets Check
			if (null != entityLicenseVO.getPasswordAlphanumericCaps() && !entityLicenseVO.getPasswordAlphanumericCaps().isEmpty()) {
				int i = Integer.parseInt(entityLicenseVO.getPasswordAlphanumericCaps());
				
				if (!(upper >= i)) {
					result = true;
					throw new CommonException(message);
				} if (!(lower >= i)) {
					result = true;
					throw new CommonException(message);
				}
			}
		}
		return result;
	}
	
	
	
	public String passwordExpiryValidationCheck(AuthDetailsVo authDetailsVo, String messageCode) throws CommonException{

		EntityLicenseVO entityLicenseVO = null;
		UserMasterVO userMasterVO = null;
		if (null != authDetailsVo.getEntityId()) {
			entityLicenseVO = commonDAO.getEntityDetails(authDetailsVo.getEntityId());
		}
		if (null != authDetailsVo.getUserId()) {
			userMasterVO = commonDAO.getUserDetails(authDetailsVo);
		}
		if (null != entityLicenseVO && null != userMasterVO && null != userMasterVO.getChangePasswordDate()
				&& null != entityLicenseVO.getExpiryDays()) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String curDate = (formatter.format(date));
			String crdDate = (formatter.format(userMasterVO.getChangePasswordDate()));
			
			LocalDate createdDate = LocalDate.parse(crdDate);
			LocalDate currentDate = LocalDate.parse(curDate);
			long days = ChronoUnit.DAYS.between(createdDate,currentDate);
			 
			if (entityLicenseVO.getExpiryDays() < days) {
				messageCode = "412";
			}
		}

		return messageCode;

	}
	
	
	public String firstTimeLoginException(AuthDetailsVo authDetailsVo, String messageCode) throws CommonException {
		UserMasterVO userMasterVO =null;
		if (null != authDetailsVo.getUserId()) {
			userMasterVO = commonDAO.getUserDetails(authDetailsVo);
		}
		
		if(null != userMasterVO && null != userMasterVO.getFirstLogin()){
			if(userMasterVO.getFirstLogin().equals(CommonConstant.FLAG_ZERO)){
				messageCode = "413";
			}
		}
		return messageCode;

	}

	public void insertLoginAudit(Integer userId) {

		UserAuditLogEntity userAuditLogEntity = new UserAuditLogEntity();
		userAuditLogEntity.setUserId(userId);
		userAuditLogEntity.setLoginDate(CommonConstant.getCalenderDate());
		userAuditLogEntity.setLogoutDate(null);
		userAuditLogEntity.setCreatedBy(userId);
		userAuditLogEntity.setCreatedDate(CommonConstant.getCalenderDate());
		userAuditLogEntity.setUpdatedBy(userId);
		userAuditLogEntity.setUpdatedDate(CommonConstant.getCalenderDate());
		userAuditLogRepository.save(userAuditLogEntity);
	}

	public void updateLoginAudit(Integer userId) {
		List<UserAuditLogEntity> list = new ArrayList<UserAuditLogEntity>();
		if (null != userId) {
			list = userAuditLogRepository.getAuditListByUser(userId);
		}
		if (list.size() > 0) {
			UserAuditLogEntity userAuditLogEntity = new UserAuditLogEntity();

			BeanUtils.copyProperties(list.get(list.size()-1), userAuditLogEntity);
			userAuditLogEntity.setLogoutDate(CommonConstant.getCalenderDate());
			userAuditLogEntity.setUpdatedBy(userId);
			userAuditLogEntity.setUpdatedDate(CommonConstant.getCalenderDate());
			userAuditLogRepository.save(userAuditLogEntity);
		}
	}
	@Transactional
	public String getRequestCode(Integer requestId,Integer entityId) {

		return commonDAO.getRequestCode(requestId,entityId);

	}
	
	@Transactional
	public String getRequestStatus(Integer requestId,Integer entityId) {

		return commonDAO.getRequestStatus(requestId,entityId);

	}
	
	@Transactional
	public UserMasterVO getEmailAddress(Integer userId, AuthDetailsVo authDetailsVo) {

		return commonDAO.getEmailAddress(userId, authDetailsVo);

	}
	
	 
	public List<SystemConfigurationVo>  getSystemConfigurationDetails(AuthDetailsVo authDetailsVo){
		
		return commonDAO.getSystemConfigurationDetails( authDetailsVo);
	}


	public UserEntity getUser(String userName) {
		return commonDAO.getUser(userName);
		
	}
	
	public String getMessage(String code,AuthDetailsVo authDetailsVo) {

		return commonDAO.getMessage(code, authDetailsVo);
		
	}

	
	public EntityLicenseVO getEntityDetails(int entityId) {

		return commonDAO.getEntityDetails(entityId);
		
	}
	
	
}
