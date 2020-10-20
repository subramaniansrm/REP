package com.srm.coreframework.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.entity.UserEntity;
import com.srm.coreframework.service.CommonService;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.CommonVO;
import com.srm.coreframework.vo.EntityLicenseVO;
import com.srm.coreframework.vo.ScreenJsonVO;
import com.srm.coreframework.vo.SystemConfigurationVo;
import com.srm.coreframework.vo.UserMasterVO;

@RestController
public class CommonController<T> {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CommonService commonService;

	protected String MODEL_ATTRIBUTE = null;

	/*
	 * protected String getMessage(String code) { return getMessage(code, new
	 * Object[] {}); }
	 * 
	 * protected String getMessage(String code, Object args[]) { return
	 * messageSource.getMessage(code, args, LocaleContextHolder.getLocale()); }
	 */

	public CommonVO getScreenFields(ScreenJsonVO screenJson, AuthDetailsVo authDetailsVo) {
		return commonService.getScreenFields(screenJson, authDetailsVo);

	}

	protected void getUserValidation(AuthDetailsVo authDetailsVo)
			throws IllegalAccessException, InvocationTargetException, ParseException {
		commonService.getUserValidation(authDetailsVo);

	}
	
	protected void licenseDateValidation(AuthDetailsVo authDetailsVo)
			throws IllegalAccessException, InvocationTargetException, ParseException {
		commonService.licenseDateValidation(authDetailsVo);

	}
	public void addEntityUser(AuthDetailsVo authDetailsVo) {

		commonService.addEntityUser(authDetailsVo);

	}

	public void deleteEntityUser(AuthDetailsVo authDetailsVo) {

		commonService.deleteEntityUser(authDetailsVo);

	}
	
	
	public void getTransactionValidation(AuthDetailsVo authDetailsVo)
			throws IllegalAccessException, InvocationTargetException, ParseException {

		commonService.getTransactionValidation(authDetailsVo);

	}
	
	
	public void addEntityRequest(AuthDetailsVo authDetailsVo) {

		commonService.addEntityRequest(authDetailsVo);

	}

	public void deleteEntityRequest(AuthDetailsVo authDetailsVo) {

		commonService.deleteEntityRequest(authDetailsVo);

	}

	/*public Map<String, Integer> getCount(int entityId) {
		return commonService.getCount(entityId);

	}*/

	public String getMessage(String code, AuthDetailsVo authDetailsVo) {
		return commonService.getMessage(code, authDetailsVo);
	}

	/*public String getMessage(String code, Object args[], AuthDetailsVo authDetailsVo) {

		String langCode = null;
		if (null != authDetailsVo && null != authDetailsVo.getLangCode()) {
			langCode = authDetailsVo.getLangCode();
		}

		Locale locale = null;
		if (CommonConstant.LANG_CODE_JP.equalsIgnoreCase(langCode)) {
			locale = Locale.JAPANESE;
		} else if (CommonConstant.LANG_CODE_EN.equalsIgnoreCase(langCode)) {
			locale = Locale.ENGLISH;
		} else {
			locale = Locale.ENGLISH;
		}
		return messageSource.getMessage(code, args, locale);
	}*/

	public String getMessageBylangCode(String code, String langCode) {
		return getMessageBylangCode(code, langCode, new Object[] {});
	}

	public String getMessageBylangCode(String code, String langCode, Object args[]) {
		Locale locale = null;
		if (CommonConstant.LANG_CODE_JP.equalsIgnoreCase(langCode)) {
			locale = Locale.JAPANESE;
		} else if (CommonConstant.LANG_CODE_EN.equalsIgnoreCase(langCode)) {
			locale = Locale.ENGLISH;
		} else {
			locale = Locale.ENGLISH;
		}
		return messageSource.getMessage(code, args, locale);
	}

	public String getMediaType1(String filename) {
		return commonService.getMediaType1(filename);
	}

	public boolean currentTokenValidate(String accessToken) throws CommonException {
		return commonService.currentTokenValidate(accessToken);

	}

	/**
	 * Method to get accessToken from UI headers
	 * 
	 * @param request
	 * @return String
	 */
	public String getHeaderAccessToken(HttpServletRequest request) {
		return commonService.getHeaderAccessToken(request);
	}

	// Validate token
	public AuthDetailsVo tokenValidate(String accessToken) {
		return commonService.tokenValidate(accessToken);
	}

	public byte[] imageLoading(String fileName) throws IOException {
		return commonService.imageLoading(fileName);

	}

	public byte[] defaultImage(String fileName) throws IOException {
		return commonService.defaultImage(fileName);

	}
	
	public Date getHolidaySla(AuthDetailsVo authDetailsVo,Date requestDate, Integer slaMinutes){
		return commonService.getHolidaySla(authDetailsVo,requestDate,slaMinutes);
	}
	public void fileSizeValidation(MultipartFile filePath) throws CommonException {
		 commonService.fileSizeValidation(filePath);
	}

	public boolean passwordLicenseValidation(String newPassword,AuthDetailsVo authDetailsVo) throws CommonException {
		return commonService.passwordLicenseValidation( newPassword, authDetailsVo);
	}

	public String passwordExpiryValidationCheck(AuthDetailsVo authDetailsVo,String messageCode) {
		return commonService.passwordExpiryValidationCheck(authDetailsVo,messageCode);

	}
	public void insertLoginAudit(Integer userId) {
		commonService.insertLoginAudit(userId);
	}
	public void updateLoginAudit(Integer userId) {
		commonService.updateLoginAudit(userId);
	}

	public String firstTimeLoginException(AuthDetailsVo authDetailsVo, String messageCode) {
		return commonService.firstTimeLoginException(authDetailsVo, messageCode);

	}
	@Transactional
	public String getRequestCode(Integer requestId,Integer entityId) {

		return commonService.getRequestCode(requestId,entityId);

	}
	
	@Transactional
	public String getRequestStatus(Integer requestId,Integer entityId) {

		return commonService.getRequestStatus(requestId,entityId);

	}
	
	@Transactional
	public UserMasterVO getEmailAddress(Integer userId, AuthDetailsVo authDetailsVo) {

		return commonService.getEmailAddress(userId, authDetailsVo);

	}
	
	public List<SystemConfigurationVo>  getSystemConfigurationDetails(AuthDetailsVo authDetailsVo){
		
		return commonService.getSystemConfigurationDetails( authDetailsVo);
	}
	
	public UserEntity getUser(String userName) {
		return commonService.getUser(userName);
		
	}
		
	public EntityLicenseVO getEntityDetails(int entityId) {

		return commonService.getEntityDetails(entityId);
		
	}
	
}
