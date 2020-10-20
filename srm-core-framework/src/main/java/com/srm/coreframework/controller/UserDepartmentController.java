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
import com.srm.coreframework.service.UserDepartmentService;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.util.ValidationUtil;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.CommonVO;
import com.srm.coreframework.vo.ScreenJsonVO;
import com.srm.coreframework.vo.UserDepartmentVO;

@RestController
public class UserDepartmentController extends CommonController<UserDepartmentVO> {

	

	private static final Logger logger = LogManager.getLogger(UserDepartmentController.class);

	@Autowired
	UserDepartmentService userDepartmentService;
	
	@PostMapping(FilePathConstants.REST_TEMPLATE_LOAD_DEPARTMENT)
	@ResponseBody
	public ResponseEntity<JSONResponse> getAll(@RequestBody ScreenJsonVO screenJson)
			throws CoreException, CommonException {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = screenJson.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			CommonVO commonVO = userDepartmentService.getScreenFields(screenJson,authDetailsVo);

			List<UserDepartmentVO> userDepartmentMasterVo = new ArrayList<>();
			if (commonVO != null) {

				userDepartmentMasterVo = userDepartmentService.loadDepartment(authDetailsVo);

			}

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			jsonResponse.setSuccesObject(userDepartmentMasterVo);
			jsonResponse.setAuthSuccesObject(commonVO);

			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

		} catch (CommonException e) {
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
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
	
	@PostMapping(FilePathConstants.REST_TEMPLATE_SEARCH_DEPARTMENT)
	@ResponseBody
	public ResponseEntity<JSONResponse> searchUserDepartment(@RequestBody UserDepartmentVO userDepartmentVO)
			throws CoreException, CommonException {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = userDepartmentVO.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			List<UserDepartmentVO> departmentMasterVo = userDepartmentService.searchDepartment(userDepartmentVO,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			jsonResponse.setSuccesObject(departmentMasterVo);

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
	
	@PostMapping(FilePathConstants.REST_TEMPLATE_ADD_DEPARTMENT)
	@ResponseBody
	public ResponseEntity<JSONResponse> createUserDepartment(@RequestBody UserDepartmentVO userDepartmentVO)
			throws CoreException, CommonException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = userDepartmentVO.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			logger.info(getMessage("processValidation",authDetailsVo));

			saveValidate(userDepartmentVO,authDetailsVo);

			logger.info(getMessage("processValidationCompleted",authDetailsVo));

			userDepartmentService.duplicateDepartment(userDepartmentVO,authDetailsVo);
			userDepartmentService.addDepartment(userDepartmentVO,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("saveSuccessMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);

			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			e.printStackTrace();
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
			e.printStackTrace();
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}

	/**
	 * Method is used to save validation
	 * 
	 * @param userDepartmentMasterVo
	 * @throws CommonException
	 */

	private void saveValidate(UserDepartmentVO userDepartmentMasterVo, AuthDetailsVo authDetailsVo) throws CommonException {

		// Iterate and validate fields based on the Authentication
		for (String field : userDepartmentMasterVo.getScreenFieldDisplayVoList()) {

			// Department Name Validation
			if (field.equals(ControlNameConstants.DEPARTMENT_NAME)) {

				if (ValidationUtil.isNullOrBlank(userDepartmentMasterVo.getUserDepartmentName().trim())) {
					throw new CommonException(getMessage("department_departmentName_required",authDetailsVo));
				}

				if (userDepartmentMasterVo.getUserDepartmentName().length() > 255) {
					throw new CommonException(getMessage("department_departmentName_lengthVal",authDetailsVo));

				}
			}

			// Department Location Validation
			if (field.equals(ControlNameConstants.DEPARTMENT_LOCATION)) {
				if (ValidationUtil.isNullOrBlank(userDepartmentMasterVo.getUserLocation())) {
					throw new CommonException(getMessage("department_locationId_required",authDetailsVo));
				}

			}

			// Department Description Validation
			if (field.equals(ControlNameConstants.DEPARTMENT_DESCRIPTION)) {

				if (userDepartmentMasterVo.getDescription().length() > 255) {
					throw new CommonException(getMessage("department_description_lengthVal",authDetailsVo));

				}
			}
		}

	}

	@PostMapping(FilePathConstants.REST_TEMPLATE_UPDATE_DEPARTMENT)
	@ResponseBody
	public ResponseEntity<JSONResponse> updateUserDepartment(@RequestBody UserDepartmentVO userDepartmentVO)
			throws CoreException, CommonException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = userDepartmentVO.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			logger.info(getMessage("processValidation",authDetailsVo));

			updateValidate(userDepartmentVO,authDetailsVo);

			logger.info(getMessage("processValidationCompleted",authDetailsVo));

			userDepartmentService.duplicateDepartment(userDepartmentVO,authDetailsVo);
			userDepartmentService.updateDepartment(userDepartmentVO,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("updateSuccessMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);

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

	/**
	 * Method is used to validate update validation
	 * 
	 * @param userDepartmentMasterVo
	 * @throws CommonException
	 */
	private void updateValidate(UserDepartmentVO userDepartmentMasterVo,AuthDetailsVo authDetailsVo) throws CommonException {

		if (ValidationUtil.isNullOrBlank(userDepartmentMasterVo.getId())) {
			throw new CommonException(getMessage("department.departmentId.required",authDetailsVo));
		}

		if (ValidationUtil.isNullOrBlank(userDepartmentMasterVo.getUserDepartmentName().trim())) {
			throw new CommonException(getMessage("department.departmentName.required",authDetailsVo));
		}
		if (userDepartmentMasterVo.getDescription().length() > 255) {
			throw new CommonException(getMessage("department.departmentName.lengthVal",authDetailsVo));

		}

	}

	@PostMapping(FilePathConstants.REST_TEMPLATE_DELETE_DEPARTMENT)
	@ResponseBody
	public ResponseEntity<JSONResponse> deleteUserDepartment(@RequestBody UserDepartmentVO userDepartmentVO)
			throws CoreException, CommonException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = userDepartmentVO.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			logger.info(getMessage("processValidation",authDetailsVo));

			deleteValidate(userDepartmentVO,authDetailsVo);

			logger.info(getMessage("processValidationCompleted",authDetailsVo));

			userDepartmentService.deleteDepartment(userDepartmentVO,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("deleteSuccessMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);

			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
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
	 * Method is used for delete validation validation
	 * 
	 * @param userDepartmentMasterVo
	 *            UserDepartmentMasterVo
	 * @throws CommonException
	 */
	private void deleteValidate(UserDepartmentVO userDepartmentMasterVo,AuthDetailsVo authDetailsVo) throws CommonException {
		if (null == userDepartmentMasterVo.getDeleteItem() || userDepartmentMasterVo.getDeleteItem().length == 0) {
			throw new CommonException(getMessage("department.delete",authDetailsVo));
		}
	}

	@PostMapping(FilePathConstants.REST_TEMPLATE_VIEW_DEPARTMENT)
	@ResponseBody
	public ResponseEntity<JSONResponse> viewUserDepartment(@RequestBody UserDepartmentVO userDepartmentVO) {
		String accessToken = userDepartmentVO.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		JSONResponse jsonResponse = new JSONResponse();
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			logger.info(getMessage("processValidation",authDetailsVo));

			viewValidate(userDepartmentVO,authDetailsVo);

			logger.info(getMessage("processValidationCompleted",authDetailsVo));

			CommonVO commonVO = userDepartmentService
					.getScreenFields(userDepartmentVO.getScreenJson(),authDetailsVo);
			
			UserDepartmentVO userDepartment = new UserDepartmentVO();

			userDepartment = userDepartmentService.viewDepartment(userDepartmentVO,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			jsonResponse.setSuccesObject(userDepartment);
			jsonResponse.setAuthSuccesObject(commonVO);
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

	/**
	 * Method is used for view validation.
	 * 
	 * @param userDepartmentMasterVo
	 *            UserDepartmentMasterVo
	 * @throws CommonException
	 */
	private void viewValidate(UserDepartmentVO userDepartmentMasterVo,AuthDetailsVo authDetailsVo) throws CommonException {
		if (ValidationUtil.isNullOrBlank(userDepartmentMasterVo.getId())) {
			throw new CommonException(getMessage("department.departmentId.required",authDetailsVo));
		}

	}

	/**
	 * This method used to display the fields based on user Authentication
	 * 
	 * @param departmentVo
	 * @return
	 */

	@PostMapping(FilePathConstants.REST_TEMPLATE_DEPARTMENT_AUTH_ADD)
	@ResponseBody
	public ResponseEntity<JSONResponse> addUserDepartment(@RequestBody UserDepartmentVO userDepartmentVO)
			throws CoreException, CommonException, IllegalAccessException, InvocationTargetException {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = userDepartmentVO.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			CommonVO commonVO = userDepartmentService
					.getScreenFields(userDepartmentVO.getScreenJson(),authDetailsVo);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setAuthSuccesObject(commonVO);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
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
}
