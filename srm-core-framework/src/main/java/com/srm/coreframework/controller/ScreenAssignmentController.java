package com.srm.coreframework.controller;

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
import com.srm.coreframework.constants.FilePathConstants;
import com.srm.coreframework.response.JSONResponse;
import com.srm.coreframework.service.ScreenAssignmentService;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.AuthenticationListComboVO;
import com.srm.coreframework.vo.ScreenAuthenticationVO;
import com.srm.coreframework.vo.ScreenJsonVO;
import com.srm.coreframework.vo.UserRoleMasterListDisplayVo;

@RestController
public class ScreenAssignmentController extends CommonController<ScreenAssignmentController> {

	@Autowired
	private ScreenAssignmentService screenAssignmentService;

	

	private static final Logger logger = LogManager.getLogger(ScreenAssignmentController.class);

	@PostMapping(FilePathConstants.REST_TEMPLATE_AUTHENTICATION_SEARCH)
	@ResponseBody
	public ResponseEntity<JSONResponse> searchScreenAssignmentList(
			@RequestBody ScreenAuthenticationVO screenAuthorizationMaster) {
		String accessToken = screenAuthorizationMaster.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		JSONResponse jsonResponse = new JSONResponse();
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			ScreenAuthenticationVO screenAuthenticationMaster = new ScreenAuthenticationVO();
			List<UserRoleMasterListDisplayVo> userRoleMasterListDisplayVos = new ArrayList<UserRoleMasterListDisplayVo>();
			userRoleMasterListDisplayVos = screenAssignmentService
					.searchScreenAuthentication(screenAuthorizationMaster,authDetailsVo);
			screenAuthenticationMaster.setUserRoleList(userRoleMasterListDisplayVos);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			jsonResponse.setSuccesObject(screenAuthenticationMaster);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}catch (Exception exe) {
			exe.printStackTrace();
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			logger.error(getMessage("errorMessage",authDetailsVo), exe.getMessage());
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	/**
	 * method to save Screen Assignment
	 * 
	 * @param screenAuthenticationMaster
	 * @param model
	 * @param result
	 * @return screenAuthenticationMaster
	 */
	@PostMapping(FilePathConstants.REST_TEMPLATE_AUTHENTICATION_CREATE)
	@ResponseBody
	public ResponseEntity<JSONResponse> saveScreenAssignment(
			@RequestBody ScreenAuthenticationVO screenAuthenticationMaster) {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = screenAuthenticationMaster.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			screenAssignmentService.saveScreenAssignment(screenAuthenticationMaster,authDetailsVo);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("saveSuccessMessage",authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.EMPTY);
		} catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}catch (Exception exe) {
			exe.printStackTrace();
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.REST_TEMPLATE_AUTHENTICATION_DELETE)
	@ResponseBody
	public ResponseEntity<JSONResponse> deleteScreenAssignment(
			@RequestBody ScreenAuthenticationVO screenAuthenticationMaster) throws CommonException {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = screenAuthenticationMaster.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			if (null != screenAuthenticationMaster.getRoleId()) {
				screenAssignmentService.deleteScreenAssignment(screenAuthenticationMaster,authDetailsVo);
			}
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("deleteSuccessMessage",authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.EMPTY);
		} catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}catch (Exception exe) {
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.EMPTY);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.REST_TEMPLATE_AUTHENTICATION_LIST)
	@ResponseBody
	public ResponseEntity<JSONResponse> listRoles(@RequestBody ScreenJsonVO screenJson) {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = screenJson.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			ScreenAuthenticationVO screenAuthenticationMaster = screenAssignmentService
					.getScreenFields(screenJson,authDetailsVo);

			List<UserRoleMasterListDisplayVo> userRoleMasterListDisplayVos = new ArrayList<UserRoleMasterListDisplayVo>();
			if (null != screenAuthenticationMaster) {
				// Load Role based User List
				userRoleMasterListDisplayVos = screenAssignmentService.getRoleList(authDetailsVo);
				screenAuthenticationMaster.setUserRoleList(userRoleMasterListDisplayVos);
			}

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			jsonResponse.setSuccesObject(screenAuthenticationMaster);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		}catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception exe) {
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			logger.error(getMessage("errorMessage",authDetailsVo), exe.getMessage());
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.REST_TEMPLATE_AUTHENTICATION_ADD)
	@ResponseBody
	public ResponseEntity<JSONResponse> load(@RequestBody ScreenAuthenticationVO screenAuthenticationMstr) {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = screenAuthenticationMstr.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			ScreenAuthenticationVO screenAuthenticationMaster = screenAssignmentService
					.getScreenFields(screenAuthenticationMstr.getScreenJson(),authDetailsVo);

			if (null != screenAuthenticationMstr.getRoleId()) {
				screenAuthenticationMaster.setRoleId(screenAuthenticationMstr.getRoleId());
			}

			screenAssignmentService.load(screenAuthenticationMaster,authDetailsVo);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			jsonResponse.setSuccesObject(screenAuthenticationMaster);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

		} catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}catch (Exception exe) {
			exe.printStackTrace();
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			logger.error(getMessage("errorMessage",authDetailsVo), exe.getMessage());
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}
	
	@PostMapping(FilePathConstants.REST_TEMPLATE_AUTHENTICATION_SUB_SCREEN)
	@ResponseBody
	public ResponseEntity<JSONResponse> loadRoleBasedSubScreenList(
			@RequestBody ScreenAuthenticationVO screenAuthenticationMaster) {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = screenAuthenticationMaster.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			List<AuthenticationListComboVO> authenticationListComboList = screenAssignmentService
					.loadRoleBasedSubScreenList(screenAuthenticationMaster,authDetailsVo);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			jsonResponse.setSuccesObject(authenticationListComboList);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}catch (Exception exe) {
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			logger.error(getMessage("errorMessage",authDetailsVo), exe.getMessage());
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.REST_TEMPLATE_AUTHENTICATION_FIELD)
	@ResponseBody
	public ResponseEntity<JSONResponse> loadRoleBasedScreenFieldsList(
			@RequestBody ScreenAuthenticationVO screenAuthenticationMaster) {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = screenAuthenticationMaster.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			List<AuthenticationListComboVO> authenticationListComboList = screenAssignmentService
					.loadRoleBasedScreenFieldsList(screenAuthenticationMaster,authDetailsVo);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			jsonResponse.setSuccesObject(authenticationListComboList);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}catch (Exception exe) {
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			logger.error(getMessage("errorMessage",authDetailsVo), exe.getMessage());
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.REST_TEMPLATE_AUTHENTICATION_FUNCTION)
	@ResponseBody
	public ResponseEntity<JSONResponse> loadRoleBasedFunctionList(
			@RequestBody ScreenAuthenticationVO screenAuthenticationMaster) {
		String accessToken = screenAuthenticationMaster.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		
		JSONResponse jsonResponse = new JSONResponse();
		
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			screenAssignmentService.loadRoleBasedFunctionList(screenAuthenticationMaster,authDetailsVo);
			List<AuthenticationListComboVO> authenticationListComboList = screenAssignmentService
					.loadRoleBasedFunctionList(screenAuthenticationMaster,authDetailsVo);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			jsonResponse.setSuccesObject(authenticationListComboList);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		}catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception exe) {
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			logger.error(getMessage("errorMessage",authDetailsVo), exe.getMessage());
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	

}
