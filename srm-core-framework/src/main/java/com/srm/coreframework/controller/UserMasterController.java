package com.srm.coreframework.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.config.LoginAuthException;
import com.srm.coreframework.constants.ControlNameConstants;
import com.srm.coreframework.constants.FilePathConstants;
import com.srm.coreframework.exception.CoreException;
import com.srm.coreframework.response.JSONResponse;
import com.srm.coreframework.service.UserMasterService;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.util.ValidationUtil;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.CommonVO;
import com.srm.coreframework.vo.ScreenJsonVO;
import com.srm.coreframework.vo.UserMasterVO;

@RestController
public class UserMasterController extends CommonController<UserMasterVO> {

	@Autowired
	UserMasterService userMasterService;

	/*
	 * @Autowired private MessageSource messageSource;
	 */

	private static final Logger logger = LogManager.getLogger(UserMasterController.class);

	@PostMapping(FilePathConstants.USER_LIST)
	@ResponseBody
	public ResponseEntity<JSONResponse> getAll(@RequestBody ScreenJsonVO screenJson) {

		String accessToken = screenJson.getAccessToken();

		AuthDetailsVo authDetailsVo = null;

		JSONResponse jsonResponse = new JSONResponse();
		try {

			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			/*
			 * if(currentTokenValidate(screenJson.getAccessToken())){ throw new
			 * CommonException(getMessage("noAuthorizationAvailableForThisUser")
			 * ); }
			 */

			CommonVO commonVO = userMasterService.getScreenFields(screenJson, authDetailsVo);

			List<UserMasterVO> userMasterVoList = new ArrayList<UserMasterVO>();
			if (null != commonVO) {
				userMasterVoList = userMasterService.getAll(authDetailsVo);

			}

			logger.info(getMessage("processValidation", authDetailsVo));
			// loadValidation(userMasterVo.getUserMasterVoList());
			logger.info(getMessage("processValidationCompleted", authDetailsVo));

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setAuthSuccesObject(commonVO);
			jsonResponse.setSuccesObject(userMasterVoList);
			jsonResponse.setResponseMessage(getMessage("successMessage", authDetailsVo));
			logger.info(getMessage("successMessage", authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			jsonResponse.setResponseMessage(e.getMessage());
		} catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage", authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);

		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.USER_ACCESS_TOKEN)
	@ResponseBody
	public ResponseEntity<JSONResponse> accessToken(@RequestBody UserMasterVO userMasterVO) {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = userMasterVO.getAccessToken();

		AuthDetailsVo authDetailsVo = null;
		try {

			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}

			userMasterService.updateAccessToken(userMasterVO, authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("successMessage", authDetailsVo));
			logger.info(getMessage("successMessage", authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			jsonResponse.setResponseMessage(e.getMessage());
		} catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage", authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);

		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.USER_AUTH_ADD)
	@ResponseBody
	public ResponseEntity<JSONResponse> addUser(@RequestBody UserMasterVO userMasterVo)
			throws CoreException, CommonException, IllegalAccessException, InvocationTargetException {
		JSONResponse jsonResponse = new JSONResponse();
		ScreenJsonVO screenJson = new ScreenJsonVO();
		String accessToken = userMasterVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {

			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			getUserValidation(authDetailsVo);
			screenJson.setScreenId(userMasterVo.getScreenId());
			screenJson.setSubScreenId(userMasterVo.getSubScreenId());
			CommonVO commonVO = userMasterService.getScreenFields(screenJson, authDetailsVo);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setAuthSuccesObject(commonVO);
			jsonResponse.setResponseMessage(getMessage("successMessage", authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

		} catch (CommonException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.FAILURE_COUNT);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage", authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.USER_SAVE)
	@ResponseBody
	public ResponseEntity<JSONResponse> create(@RequestBody UserMasterVO userVo) {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = userVo.getAccessToken();

		AuthDetailsVo authDetailsVo = null;
		try {

			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
				getUserValidation(authDetailsVo);

				logger.info(getMessage("processValidation", authDetailsVo));

				saveValidation(userVo, authDetailsVo);

				logger.info(getMessage("processValidationCompleted", authDetailsVo));

				UserMasterVO userMasterVO =	userMasterService.create(userVo, authDetailsVo);

				addEntityUser(authDetailsVo);
				jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
				jsonResponse.setResponseMessage(getMessage("saveSuccessMessage", authDetailsVo));
				jsonResponse.setSuccesObject(userMasterVO);
				jsonResponse.setAuthSuccesObject(authDetailsVo);
				return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
			
		} catch (CommonException e) {
			e.printStackTrace();
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage", authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.USER_MODIFY)
	@ResponseBody
	public ResponseEntity<JSONResponse> update(@RequestBody UserMasterVO userVo) {
		String accessToken = userVo.getAccessToken();

		AuthDetailsVo authDetailsVo = null;
		JSONResponse jsonResponse = new JSONResponse();
		try {

			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			

			logger.info(getMessage("processValidation", authDetailsVo));

			updateValidation(userVo, authDetailsVo);

			logger.info(getMessage("processValidationCompleted", authDetailsVo));
			userMasterService.update(userVo, authDetailsVo);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("updateSuccessMessage", authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage", authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.USER_DELETE1)
	@ResponseBody
	public ResponseEntity<JSONResponse> delete(@RequestBody UserMasterVO userVo) {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = userVo.getAccessToken();

		AuthDetailsVo authDetailsVo = null;
		try {

			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}

			logger.info(getMessage("processValidation", authDetailsVo));

			deleteValidation(userVo, authDetailsVo);

			logger.info(getMessage("processValidationCompleted", authDetailsVo));

			userMasterService.delete(userVo, authDetailsVo);
			
			deleteEntityUser(authDetailsVo);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("deleteSuccessMessage", authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage", authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.USER_VIEW1)
	@ResponseBody
	public ResponseEntity<JSONResponse> view(@RequestBody UserMasterVO userVo) {
		String accessToken = userVo.getAccessToken();

		AuthDetailsVo authDetailsVo = null;
		JSONResponse jsonResponse = new JSONResponse();
		ScreenJsonVO screenJson = new ScreenJsonVO();
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			screenJson.setScreenId(userVo.getScreenId());
			screenJson.setSubScreenId(userVo.getSubScreenId());
			CommonVO commonVO = userMasterService.getScreenFields(screenJson, authDetailsVo);

			logger.info(getMessage("processValidation", authDetailsVo));

			idValidation(userVo, authDetailsVo);

			logger.info(getMessage("processValidationCompleted", authDetailsVo));

			UserMasterVO userMasterViewVo = new UserMasterVO();

			userMasterViewVo = userMasterService.load(userVo, authDetailsVo);
			logger.info(getMessage("processValidation", authDetailsVo));

			getAllSearchValidation(userMasterViewVo, authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(userMasterViewVo);
			jsonResponse.setAuthSuccesObject(commonVO);
			jsonResponse.setResponseMessage(getMessage("successMessage", authDetailsVo));
			logger.info(getMessage("successMessage", authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			jsonResponse.setResponseMessage(e.getMessage());
		} catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage", authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);

		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.USER_SEARCH1)
	@ResponseBody
	public ResponseEntity<JSONResponse> search(@RequestBody UserMasterVO userVo) {
		String accessToken = userVo.getAccessToken();

		AuthDetailsVo authDetailsVo = null;
		JSONResponse jsonResponse = new JSONResponse();
		ScreenJsonVO screenJson = new ScreenJsonVO();
		try {

			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}

			screenJson.setScreenId(userVo.getScreenId());
			screenJson.setSubScreenId(userVo.getSubScreenId());

			List<UserMasterVO> userList = userMasterService.getAllSearch(userVo, authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(userList);
			jsonResponse.setResponseMessage(getMessage("successMessage", authDetailsVo));
			logger.info(getMessage("successMessage", authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			jsonResponse.setResponseMessage(e.getMessage());
		} catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage", authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);

		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	/**
	 * This method is used to validate the Create and Update user detail.
	 * 
	 * @param userMasterVo
	 *            UserMasterVo
	 */
	private void saveValidation(UserMasterVO userMasterVo, AuthDetailsVo authDetailsVo) {

		for (String field : userMasterVo.getScreenFieldDisplayVoList()) {
			if (ControlNameConstants.USER_LOGIN.equals(field)) {
				// User Id Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getUserLoginId().trim())) {
					throw new CommonException(getMessage("user.userName.required", authDetailsVo));
				}
				if ((userMasterVo.getUserLoginId().length() > 25)) {
					throw new CommonException(getMessage("user.userName.limit", authDetailsVo));
				}
			}
			if (ControlNameConstants.USER_EMPLOYEE_ID.equals(field)) {

				// Employee Id Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getUserEmployeeId().trim())) {
					throw new CommonException(getMessage("user.employeeId.required", authDetailsVo));
				}
				if (ValidationUtil.isAlphanumeric(userMasterVo.getUserEmployeeId())) {
					throw new CommonException(getMessage("user.employeeId.required", authDetailsVo));
				}
				if ((userMasterVo.getUserEmployeeId().length() > 25)) {
					throw new CommonException(getMessage("user.employeeId.limit", authDetailsVo));
				}
				int count = userMasterService.getEmployeeIdUnique(userMasterVo, authDetailsVo);
				if (count > 0) {
					throw new CommonException(getMessage("user.employeeId.unique", authDetailsVo));
				}
			}
			if (ControlNameConstants.USER_FIRST_NAME.equals(field)) {

				// First Name Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getFirstName().trim())) {
					throw new CommonException(getMessage("user.firstName.required", authDetailsVo));
				}
				if ((userMasterVo.getFirstName().length() > 255)) {
					throw new CommonException(getMessage("user.firstName.limit", authDetailsVo));
				}
			}
			if (ControlNameConstants.USER_MIDDLE_NAME.equals(field)) {
				if (null != userMasterVo.getMiddleName()) {
					// Middle Name Validation

					if ((userMasterVo.getMiddleName().length() > 255)) {
						throw new CommonException(getMessage("user.middleName.limit", authDetailsVo));
					}
				}
			}
			if (ControlNameConstants.USER_LAST_NAME.equals(field)) {
				// Last Name Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getLastName().trim())) {
					throw new CommonException(getMessage("user.lasttName.required", authDetailsVo));
				}
				if ((userMasterVo.getLastName().length() > 255)) {
					throw new CommonException(getMessage("user.lasttName.limit", authDetailsVo));
				}
			}
			if (ControlNameConstants.USER_EMAIL_ID.equals(field)) {

				// Email Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getEmailId().trim())) {
					throw new CommonException(getMessage("user.email.required", authDetailsVo));
				}
				if ((userMasterVo.getEmailId().length() > 255)) {
					throw new CommonException(getMessage("user.email.limit", authDetailsVo));
				}
				if (ValidationUtil.isEmail(userMasterVo.getEmailId())) {
					throw new CommonException(getMessage("user.email.validation", authDetailsVo));
				}
				int count = userMasterService.getEmailUnique(userMasterVo, authDetailsVo);
				if (count > 0) {
					throw new CommonException(getMessage("user.email.unique", authDetailsVo));
				}

			}
			if (ControlNameConstants.USER_MOBILE_NUMBER.equals(field)) {

				// Mobile Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getMobile().trim())) {
					throw new CommonException(getMessage("user.mobile.required", authDetailsVo));
				}
				if (ValidationUtil.isContactNumber(userMasterVo.getMobile().trim())) {
					throw new CommonException(getMessage("user.mobile.validation", authDetailsVo));
				}
			}
			/*if (ControlNameConstants.USER_PHONE_NUMBER.equals(field)) {
				// Phone Number Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getPhoneNumber().trim())) {
					throw new CommonException(getMessage("user.phone.required", authDetailsVo));
				}
				if (ValidationUtil.isContactNumber(userMasterVo.getPhoneNumber().trim())) {
					throw new CommonException(getMessage("user.phone.validation", authDetailsVo));
				}
			}
			if (ControlNameConstants.USER_CURRENT_ADDRESS.equals(field)) {
				// Current Address Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getCurrentAddress().trim())) {
					throw new CommonException(getMessage("user.currentAddress.required", authDetailsVo));
				}
			}
			if (ControlNameConstants.USER_PERMANENT_ADDRESS.equals(field)) {

				// Permanent Address Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getPermanentAddress().trim())) {
					throw new CommonException(getMessage("user.permanentAddress.required", authDetailsVo));
				}
			}*/
			if (ControlNameConstants.USER_LOCATION.equals(field)) {
				// Location Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getUserLocation())) {
					throw new CommonException(getMessage("user.location.required", authDetailsVo));
				}
			}
			if (ControlNameConstants.USER_SUBLOCATION.equals(field)) {
				// SubLocation Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getSubLocation())) {
					throw new CommonException(getMessage("user.sublocation.required", authDetailsVo));
				}
			}
			if (ControlNameConstants.USER_ROLE.equals(field)) {
				// Role Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getUserRole())) {
					throw new CommonException(getMessage("user.role.required", authDetailsVo));
				}
			}
			if (ControlNameConstants.USER_DEPARTMENT.equals(field)) {
				// Department Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getUserDepartment())) {
					throw new CommonException(getMessage("user.department.required", authDetailsVo));
				}
			}

		}
	}

	/**
	 * This method is used to validate the Create and Update user detail.
	 * 
	 * @param userMasterVo
	 *            UserMasterVo
	 */
	private void updateValidation(UserMasterVO userMasterVo, AuthDetailsVo authDetailsVo) {

		for (String field : userMasterVo.getScreenFieldDisplayVoList()) {
			if (ControlNameConstants.USER_LOGIN.equals(field)) {
				// User Id Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getUserLoginId().trim())) {
					throw new CommonException(getMessage("user.userName.required", authDetailsVo));
				}
				if ((userMasterVo.getUserLoginId().length() > 25)) {
					throw new CommonException(getMessage("user.userName.limit", authDetailsVo));
				}
			}
			if (ControlNameConstants.USER_EMPLOYEE_ID.equals(field)) {

				// Employee Id Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getUserEmployeeId().trim())) {
					throw new CommonException(getMessage("user.employeeId.required", authDetailsVo));
				}
				if (ValidationUtil.isAlphanumeric(userMasterVo.getUserEmployeeId())) {
					throw new CommonException(getMessage("user.employeeId.required", authDetailsVo));
				}
				if ((userMasterVo.getUserEmployeeId().length() > 25)) {
					throw new CommonException(getMessage("user.employeeId.limit", authDetailsVo));
				}
				int count = userMasterService.getEmployeeIdUnique(userMasterVo, authDetailsVo);
				if (count > 1) {
					throw new CommonException(getMessage("user.employeeId.unique", authDetailsVo));
				}
			}
			if (ControlNameConstants.USER_FIRST_NAME.equals(field)) {

				// First Name Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getFirstName().trim())) {
					throw new CommonException(getMessage("user.firstName.required", authDetailsVo));
				}
				if ((userMasterVo.getFirstName().length() > 255)) {
					throw new CommonException(getMessage("user.firstName.limit", authDetailsVo));
				}
			}
			if (ControlNameConstants.USER_MIDDLE_NAME.equals(field)) {
				if (null != userMasterVo.getMiddleName()) {
					// Middle Name Validation

					if ((userMasterVo.getMiddleName().length() > 255)) {
						throw new CommonException(getMessage("user.middleName.limit", authDetailsVo));
					}
				}
			}
			if (ControlNameConstants.USER_LAST_NAME.equals(field)) {
				// Last Name Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getLastName().trim())) {
					throw new CommonException(getMessage("user.lasttName.required", authDetailsVo));
				}
				if ((userMasterVo.getLastName().length() > 255)) {
					throw new CommonException(getMessage("user.lasttName.limit", authDetailsVo));
				}
			}
			if (ControlNameConstants.USER_EMAIL_ID.equals(field)) {

				// Email Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getEmailId().trim())) {
					throw new CommonException(getMessage("user.email.required", authDetailsVo));
				}
				if ((userMasterVo.getEmailId().length() > 255)) {
					throw new CommonException(getMessage("user.email.limit", authDetailsVo));
				}
				if (ValidationUtil.isEmail(userMasterVo.getEmailId())) {
					throw new CommonException(getMessage("user.email.validation", authDetailsVo));
				}
				int count = userMasterService.getEmailUnique(userMasterVo, authDetailsVo);
				if (count > 1) {
					throw new CommonException(getMessage("user.email.unique", authDetailsVo));
				}

			}
			if (ControlNameConstants.USER_MOBILE_NUMBER.equals(field)) {

				// Mobile Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getMobile().trim())) {
					throw new CommonException(getMessage("user.mobile.required", authDetailsVo));
				}
				if (ValidationUtil.isContactNumber(userMasterVo.getMobile().trim())) {
					throw new CommonException(getMessage("user.mobile.validation", authDetailsVo));
				}
			}
			/*	if (ControlNameConstants.USER_PHONE_NUMBER.equals(field)) {
				// Phone Number Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getPhoneNumber().trim())) {
					throw new CommonException(getMessage("user.phone.required", authDetailsVo));
				}
				if (ValidationUtil.isContactNumber(userMasterVo.getPhoneNumber().trim())) {
					throw new CommonException(getMessage("user.phone.validation", authDetailsVo));
				}
			}
		 	if (ControlNameConstants.USER_CURRENT_ADDRESS.equals(field)) {
				// Current Address Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getCurrentAddress().trim())) {
					throw new CommonException(getMessage("user.currentAddress.required", authDetailsVo));
				}
			}
			if (ControlNameConstants.USER_PERMANENT_ADDRESS.equals(field)) {

				// Permanent Address Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getPermanentAddress().trim())) {
					throw new CommonException(getMessage("user.permanentAddress.required", authDetailsVo));
				}
			}*/
			if (ControlNameConstants.USER_LOCATION.equals(field)) {
				// Location Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getUserLocation())) {
					throw new CommonException(getMessage("user.location.required", authDetailsVo));
				}
			}
			if (ControlNameConstants.USER_SUBLOCATION.equals(field)) {
				// SubLocation Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getSubLocation())) {
					throw new CommonException(getMessage("user.sublocation.required", authDetailsVo));
				}
			}
			if (ControlNameConstants.USER_ROLE.equals(field)) {
				// Role Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getUserRole())) {
					throw new CommonException(getMessage("user.role.required", authDetailsVo));
				}
			}
			if (ControlNameConstants.USER_DEPARTMENT.equals(field)) {
				// Department Validation
				if (ValidationUtil.isNullOrBlank(userMasterVo.getUserDepartment())) {
					throw new CommonException(getMessage("user.department.required", authDetailsVo));
				}
			}

		}
	}

	/**
	 * This method is used to validate the GetAllSearch of user detail.
	 * 
	 * @param userList
	 *            UserMasterVo
	 */
	private void getAllSearchValidation(UserMasterVO userList, AuthDetailsVo authDetailsVo) {
		if (userList == null) {
			throw new CommonException(getMessage("common.noRecord", authDetailsVo));
		}
	}

	/**
	 * This method is used to validate the GetAllSearch of user detail.
	 * 
	 * @param userList
	 *            UserMasterVo
	 */
	private void deleteValidation(UserMasterVO userMasterVo, AuthDetailsVo authDetailsVo) {

		if (null == userMasterVo.getDeleteItem() || userMasterVo.getDeleteItem().length <= 0) {
			throw new CommonException(getMessage("delete.validation", authDetailsVo));
		}
	}

	/**
	 * This method is used to validate the GetAllsearch Id
	 * 
	 * @param userMasterVo
	 *            UserMasterVo
	 */
	private void idValidation(UserMasterVO userMasterVo, AuthDetailsVo authDetailsVo) {

		if (userMasterVo.getId() <= 0 || userMasterVo.getId() == null) {
			throw new CommonException(getMessage("search.validation", authDetailsVo));
		}
	}

	/*
	 * protected String getMessage(String code) { return getMessage(code, new
	 * Object[] {}); }
	 * 
	 * protected String getMessage(String code, Object args[]) { return
	 * messageSource.getMessage(code, args, LocaleContextHolder.getLocale()); }
	 */
}
