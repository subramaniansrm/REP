package com.srm.coreframework.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import com.srm.coreframework.service.UserMappingService;
//import com.srm.coreframework.service.UserMasterService;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.util.ValidationUtil;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.CommonVO;
import com.srm.coreframework.vo.ScreenJsonVO;
//import com.srm.coreframework.vo.SubLocationVO;
import com.srm.coreframework.vo.UserMappingVO;

@RestController
public class UserMappingController extends CommonController<T>{

	@Autowired
	UserMappingService userMappingService;
	
	

	@Autowired
	private MessageSource messageSource;

	private static final Logger logger = LogManager.getLogger(UserMappingController.class);

	@PostMapping(FilePathConstants.USER_MAPPING_RT_LIST)
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
			CommonVO commonVO = userMappingService.getScreenFields(screenJson,authDetailsVo);
			List<UserMappingVO> userMappingVoList = userMappingService.getAll(authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setAuthSuccesObject(commonVO);
			jsonResponse.setSuccesObject(userMappingVoList);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			logger.info(getMessage("successMessage",authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			jsonResponse.setResponseMessage(e.getMessage());
		}catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);

		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.USER_MAPPING_RT_ADD)
	@ResponseBody
	public ResponseEntity<JSONResponse> addUserMapping(@RequestBody UserMappingVO userMappingVO)
			throws CoreException, CommonException, IllegalAccessException, InvocationTargetException {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = userMappingVO.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			CommonVO commonVO = userMappingService.getScreenFields(userMappingVO.getScreenJson(),authDetailsVo);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setAuthSuccesObject(commonVO);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.USER_MAPPING_RT_CREATE)
	@ResponseBody
	public ResponseEntity<JSONResponse> create(@RequestBody UserMappingVO userMappingVo) {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = userMappingVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			logger.info(getMessage("processValidation",authDetailsVo));
			saveValidation(userMappingVo,authDetailsVo);
			logger.info(getMessage("processValidationCompleted",authDetailsVo));
			userMappingService.UserMapping(userMappingVo,authDetailsVo);
			findDuplicateUser(userMappingVo,authDetailsVo );
			userMappingService.create(userMappingVo,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("saveSuccessMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
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
		}catch (Exception e) {
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("saveErroMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.USER_MAPPING_RT_UPDATE)
	@ResponseBody
	public ResponseEntity<JSONResponse> update(@RequestBody UserMappingVO userMappingVo) {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = userMappingVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			userMappingService.UserMapping(userMappingVo,authDetailsVo);
			findDuplicateUser(userMappingVo,authDetailsVo);
			userMappingService.update(userMappingVo,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("updateSuccessMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	private void findDuplicateUser(UserMappingVO userMappingVo,AuthDetailsVo authDetailsVo) {

		userMappingService.findDuplicate(userMappingVo,authDetailsVo);
	}

	@PostMapping(FilePathConstants.USER_MAPPING_RT_DELETE)
	@ResponseBody
	public ResponseEntity<JSONResponse> delete(@RequestBody UserMappingVO userMappingVo) {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = userMappingVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			logger.info(getMessage("processValidation",authDetailsVo));
			deleteValidate(userMappingVo,authDetailsVo);
			logger.info(getMessage("processValidationCompleted",authDetailsVo));
			userMappingService.delete(userMappingVo,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("deleteSuccessMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.USER_MAPPING_RT_LOAD)
	@ResponseBody
	public ResponseEntity<JSONResponse> load(@RequestBody UserMappingVO userMappingVo) {
		String accessToken = userMappingVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		JSONResponse jsonResponse = new JSONResponse();
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			logger.info(getMessage("processValidation",authDetailsVo));

			// loadValidation(userMappingVo);

			logger.info(getMessage("processValidationCompleted",authDetailsVo));

			CommonVO commonVO = userMappingService.getScreenFields(userMappingVo.getScreenJson(),authDetailsVo);

			UserMappingVO usermappingVo = new UserMappingVO();

			usermappingVo = userMappingService.load(userMappingVo,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setAuthSuccesObject(commonVO);
			jsonResponse.setSuccesObject(usermappingVo);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			logger.info(getMessage("successMessage",authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			jsonResponse.setResponseMessage(e.getMessage());
		}catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);

		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.USER_MAPPING_RT_SEARCH)
	@ResponseBody
	public ResponseEntity<JSONResponse> getAllSearch(@RequestBody UserMappingVO userMappingVo) {
		String accessToken = userMappingVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		JSONResponse jsonResponse = new JSONResponse();
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			List<UserMappingVO> userMappingVoList = userMappingService.getAllSearch(userMappingVo,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(userMappingVoList);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			logger.info(getMessage("successMessage",authDetailsVo));
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
		}catch (Exception e) {
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);

		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	/**
	 * This method is used to validate the Create and Update user mapping
	 * detail.
	 * 
	 * @param userMappingVo
	 *            UserMappingVo
	 */
	private void saveValidation(UserMappingVO userMappingVo,AuthDetailsVo authDetailsVo) {

		for (String field : userMappingVo.getScreenFieldDisplayVoList()) {

			// User Id Validation
			if (ControlNameConstants.USER_MAPPING_USER.equals(field)) {

				if (ValidationUtil.isNullOrBlank(userMappingVo.getUserId())) {
					throw new CommonException(getMessage("usermapping.user.required",authDetailsVo));
				}
			}
			// Location Validation
			if (ControlNameConstants.USER_MAPPING_LOCATION.equals(field)) {

				if (ValidationUtil.isNullOrBlank(userMappingVo.getUserLocationId())) {
					throw new CommonException(getMessage("usermapping.location.required",authDetailsVo));
				}
			}
			// Department Validation
			if (ControlNameConstants.USER_MAPPING_DEPARTMENT.equals(field)) {

				if (ValidationUtil.isNullOrBlank(userMappingVo.getUserDepartmentId())) {
					throw new CommonException(getMessage("usermapping.department.required",authDetailsVo));
				}
			}
			// Level Validation
			if (ControlNameConstants.USER_MAPPING_LEVEL.equals(field)) {

				if (ValidationUtil.isNullOrBlank(userMappingVo.getLevelId())) {
					throw new CommonException(getMessage("usermapping.level.required",authDetailsVo));
				}
			}
			// Role Validation
			if (ControlNameConstants.USER_MAPPING_USERROLE.equals(field)) {

				if (ValidationUtil.isNullOrBlank(userMappingVo.getUserRoleId())) {
					throw new CommonException(getMessage("usermapping.role.required",authDetailsVo));
				}
			}
			// Reporting User Department
			if (ControlNameConstants.USER_MAPPING_REPORTING_USER_DEPARTMENT.equals(field)) {

				if (ValidationUtil.isNullOrBlank(userMappingVo.getReportingUserDepartment())) {
					throw new CommonException(getMessage("usermapping.reportingUserDepartment.required",authDetailsVo));
				}
			}
			// Reporting To User
			if (ControlNameConstants.USER_MAPPING_REPORTING_TO_USER.equals(field)) {

				if (ValidationUtil.isNullOrBlank(userMappingVo.getReportingToUser())) {
					throw new CommonException(getMessage("usermapping.reportingToUser.required",authDetailsVo));
				}
				if (userMappingVo.getUserId() == userMappingVo.getReportingToUser()) {
					throw new CommonException(getMessage("userandreportingtousercannotbesame",authDetailsVo));
				}
			}
			if(null != userMappingVo.getReportingToUser() && null != userMappingVo.getUserId()){
				if(userMappingVo.getReportingToUser().equals(userMappingVo.getUserId())){
					throw new CommonException(getMessage("userMappingSame",authDetailsVo));
				}
			}
		}
	}

	/**
	 * This method is used to validate the Update user mapping detail.
	 * 
	 * @param userMappingVo
	 *            UserMappingVo
	 */

	@SuppressWarnings("unused")
	private void updateValidation(UserMappingVO userMappingVo,AuthDetailsVo authDetailsVo) {

		for (String field : userMappingVo.getScreenFieldDisplayVoList()) {

			// User Mapping Id Validation

			if (ValidationUtil.isNullOrBlank(userMappingVo.getUserMappingId())) {
				throw new CommonException(getMessage("usermapping.usermapping.required",authDetailsVo));

			}
			// User Id Validation
			if (ControlNameConstants.USER_MAPPING_USER.equals(field)) {

				if (ValidationUtil.isNullOrBlank(userMappingVo.getUserId())) {
					throw new CommonException(getMessage("usermapping.user.required",authDetailsVo));
				}
			}
			// Location Validation
			if (ControlNameConstants.USER_MAPPING_LOCATION.equals(field)) {

				if (ValidationUtil.isNullOrBlank(userMappingVo.getUserLocationId())) {
					throw new CommonException(getMessage("usermapping.location.required",authDetailsVo));
				}
			}
			// Department Validation
			if (ControlNameConstants.USER_MAPPING_DEPARTMENT.equals(field)) {

				if (ValidationUtil.isNullOrBlank(userMappingVo.getUserDepartmentId())) {
					throw new CommonException(getMessage("usermapping.department.required",authDetailsVo));
				}
			}
			// Level Validation
			if (ControlNameConstants.USER_MAPPING_LEVEL.equals(field)) {

				if (ValidationUtil.isNullOrBlank(userMappingVo.getLevelId())) {
					throw new CommonException(getMessage("usermapping.level.required",authDetailsVo));
				}
			}
			// Role Validation
			if (ControlNameConstants.USER_MAPPING_USERROLE.equals(field)) {

				if (ValidationUtil.isNullOrBlank(userMappingVo.getUserRoleId())) {
					throw new CommonException(getMessage("usermapping.role.required",authDetailsVo));
				}
			}
			// Reporting User Department
			if (ControlNameConstants.USER_MAPPING_REPORTING_USER_DEPARTMENT.equals(field)) {

				if (ValidationUtil.isNullOrBlank(userMappingVo.getReportingUserDepartment())) {
					throw new CommonException(getMessage("usermapping.reportingUserDepartment.required",authDetailsVo));
				}
			}
			// Reporting To User
			if (ControlNameConstants.USER_MAPPING_REPORTING_TO_USER.equals(field)) {

				if (ValidationUtil.isNullOrBlank(userMappingVo.getReportingToUser())) {
					throw new CommonException(getMessage("usermapping.reportingToUser.required",authDetailsVo));
				}
			}
		}
	}

	/**
	 * This method is used to validate the Load of user Mapping details.
	 * 
	 * @param userMappingVo
	 *            UserMappingVo
	 */
	/*
	 * private void loadValidation(UserMappingVo userMappingVo) { if
	 * (userMappingVo == null) { throw new
	 * CommonException(getMessage("common.noRecord")); } }
	 */

	/**
	 * This method is used to validate the GetAllSearch of user mapping detail.
	 * 
	 * @param userMappingVo
	 *            UserMappingVo
	 */
	private void deleteValidate(UserMappingVO userMappingVo,AuthDetailsVo authDetailsVo) {

		if (null == userMappingVo.getUserMappingList()) {
			throw new CommonException(getMessage("delete.validation",authDetailsVo));
		}

	}

	/**
	 * This method is used to validate the GetAllsearch Id
	 * 
	 * @param userMappingVo
	 *            UserMappingVo
	 */
	@SuppressWarnings("unused")
	private void idValidation(UserMappingVO userMappingVo,AuthDetailsVo authDetailsVo) {

		if (userMappingVo.getUserMappingId() <= 0 || userMappingVo.getUserMappingId() == 0) {
			throw new CommonException(getMessage("search.validation",authDetailsVo));
		}
	}

	/**
	 * This method is used to validate the duplicate Id
	 * 
	 * @param userMappingVo
	 *            UserMappingVo
	 */
	public int UserMapping(UserMappingVO userMappingVo,AuthDetailsVo authDetailsVo) {

		if (userMappingVo.getUserMappingId() > 0) {
			throw new CommonException(getMessage("duplicate.validation",authDetailsVo));

		}

		return 0;
	}

	/*protected String getMessage(String code) {
		return getMessage(code, new Object[] {});
	}

	protected String getMessage(String code, Object args[]) {
		return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
	}*/
}
