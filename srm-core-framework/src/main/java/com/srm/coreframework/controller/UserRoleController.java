package com.srm.coreframework.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.srm.coreframework.response.JSONResponse;
import com.srm.coreframework.service.UserRoleService;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.util.ValidationUtil;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.CommonVO;
import com.srm.coreframework.vo.ScreenJsonVO;
import com.srm.coreframework.vo.UserRoleVO;

/**
 * 
 * @author vigneshs
 *
 */
@RestController
public class UserRoleController extends CommonController<UserRoleVO> {

	private static final Logger logger = LogManager.getLogger(UserRoleController.class);

	@Autowired
	private UserRoleService userRoleService;

	

	@PostMapping(FilePathConstants.REST_TEMPLATE_GET_USER_ROLE_LIST)
	@ResponseBody
	public ResponseEntity<JSONResponse> listUserRole(HttpServletRequest request,@RequestBody ScreenJsonVO screenJson)
			throws IOException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = screenJson.getAccessToken();

		AuthDetailsVo authDetailsVo = null;
		try {

			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			
			CommonVO commonVO = userRoleService.getScreenFields(screenJson,authDetailsVo);

			List<UserRoleVO> userRoleVoList = new ArrayList<UserRoleVO>();
			if (null != commonVO) {
				userRoleVoList = userRoleService.getUserRoleList(authDetailsVo);
			}

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setAuthSuccesObject(commonVO);
			jsonResponse.setSuccesObject(userRoleVoList);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
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
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}

	@PostMapping(FilePathConstants.REST_TEMPLATE_GET_USER_ROLE_ADD)
	@ResponseBody
	public ResponseEntity<JSONResponse> addScreenFields(@RequestBody ScreenJsonVO screenJson) throws IOException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = screenJson.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			CommonVO commonVO = userRoleService.getScreenFields(screenJson,authDetailsVo);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setAuthSuccesObject(commonVO);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			e.printStackTrace();
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
			e.printStackTrace();
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.REST_TEMPLATE_SAVE_USER_ROLE)
	@ResponseBody
	public ResponseEntity<JSONResponse> saveUserRole(@RequestBody UserRoleVO userRoleVO) throws IOException {
		
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = userRoleVO.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			saveValidate(userRoleVO,authDetailsVo);
			userRoleService.duplicateRole(userRoleVO,authDetailsVo);

			userRoleService.saveUserRole(userRoleVO,authDetailsVo);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			jsonResponse.setResponseMessage(getMessage("saveSuccessMessage",authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			e.printStackTrace();
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
			e.printStackTrace();
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}

	private void saveValidate(UserRoleVO saveUserRoleMaster,AuthDetailsVo authDetailsVo) {

		for (String field : saveUserRoleMaster.getScreenFieldDisplayVoList()) {
			if (ControlNameConstants.USER_ROLE_NAME.equals(field)) {

				if (ValidationUtil.isNullOrBlank(saveUserRoleMaster.getUserRoleName().trim())) {
					throw new CommonException(getMessage("userRole.userName.required",authDetailsVo));
				}

				if (saveUserRoleMaster.getUserRoleName().length() > 255) {
					throw new CommonException(getMessage("userRole.userName.lengthVal",authDetailsVo));
				}
			}
			if (ControlNameConstants.USER_ROLE_DEPARTMENT.equals(field)) {

				if (ValidationUtil.isNullOrBlank(saveUserRoleMaster.getUserDepartment())) {
					throw new CommonException(getMessage("userRole.dept.required",authDetailsVo));
				}
			}
			if (ControlNameConstants.USER_ROLE_LOCATION.equals(field)) {

				if (ValidationUtil.isNullOrBlank(saveUserRoleMaster.getUserLocation())) {
					throw new CommonException(getMessage("userRole.loc.required",authDetailsVo));
				}
			}
		}
	}

	@PostMapping(FilePathConstants.REST_TEMPLATE_UPDATE_USER_ROLE)
	@ResponseBody
	public ResponseEntity<JSONResponse> updateUserRole(@RequestBody UserRoleVO userRoleVO) throws IOException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = userRoleVO.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			updateValidate(userRoleVO,authDetailsVo);
			userRoleService.duplicateRole(userRoleVO,authDetailsVo);

			userRoleService.updateUserRole(userRoleVO,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			jsonResponse.setResponseMessage(getMessage("updateSuccessMessage", authDetailsVo));
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
			jsonResponse.setResponseMessage(getMessage("errorMessage", authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}

	private void updateValidate(UserRoleVO updateUserRoleMaster,AuthDetailsVo authDetailsVo) {

		if (ValidationUtil.isNullOrBlank(updateUserRoleMaster.getId())) {
			throw new CommonException(getMessage("userRole.roleId.required", authDetailsVo));
		}
		for (String field : updateUserRoleMaster.getScreenFieldDisplayVoList()) {
			if (ControlNameConstants.USER_ROLE_NAME.equals(field)) {
				if (ValidationUtil.isNullOrBlank(updateUserRoleMaster.getUserRoleName().trim())) {
					throw new CommonException(getMessage("userRole.userName.required", authDetailsVo));
				}

				if (updateUserRoleMaster.getUserRoleName().length() > 255) {
					throw new CommonException(getMessage("userRole.userName.lengthVal", authDetailsVo));
				}
			}
			if (ControlNameConstants.USER_ROLE_DEPARTMENT.equals(field)) {

				if (ValidationUtil.isNullOrBlank(updateUserRoleMaster.getUserDepartment())) {
					throw new CommonException(getMessage("userRole.dept.required", authDetailsVo));
				}
			}
			if (ControlNameConstants.USER_ROLE_LOCATION.equals(field)) {

				if (ValidationUtil.isNullOrBlank(updateUserRoleMaster.getUserLocation())) {
					throw new CommonException(getMessage("userRole.loc.required", authDetailsVo));
				}
			}
		}
	}

	@PostMapping(FilePathConstants.REST_TEMPLATE_DELETE_USER_ROLE)
	@ResponseBody
	public ResponseEntity<JSONResponse> deleteUserRole(@RequestBody UserRoleVO userRoleVO) throws IOException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = userRoleVO.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			deleteValidate(userRoleVO,authDetailsVo);
			userRoleService.deleteUserRole(userRoleVO,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			jsonResponse.setResponseMessage(getMessage("deleteSuccessMessage", authDetailsVo));
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
			jsonResponse.setResponseMessage(getMessage("errorMessage", authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}

	/**
	 * Method used to validate for delete
	 * 
	 * @param deleteUserRoleMaster
	 */
	private void deleteValidate(UserRoleVO deleteUserRoleMaster,AuthDetailsVo authDetailsVo) {

		if (null == deleteUserRoleMaster.getDeleteItem() || deleteUserRoleMaster.getDeleteItem().length == 0) {
			throw new CommonException(getMessage("userRole.roleId.required", authDetailsVo));
		}

	}

	@PostMapping(FilePathConstants.REST_TEMPLATE_SEARCH_USER_ROLE)
	@ResponseBody
	public ResponseEntity<JSONResponse> search(@RequestBody UserRoleVO userRoleVO) throws IOException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = userRoleVO.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			List<UserRoleVO> userRoleMasterVoList = userRoleService.search(userRoleVO,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(userRoleMasterVoList);
			jsonResponse.setResponseMessage(getMessage("successMessage", authDetailsVo));
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
			jsonResponse.setResponseMessage(getMessage("errorMessage", authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.REST_TEMPLATE_VIEW_USER_ROLE)
	@ResponseBody
	public ResponseEntity<JSONResponse> viewUserRole(@RequestBody UserRoleVO userRoleVO) throws IOException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = userRoleVO.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			CommonVO commonVO = userRoleService.getScreenFields(userRoleVO.getScreenJson(), authDetailsVo);

			this.viewValidate(userRoleVO,authDetailsVo);
			
			UserRoleVO userRole = new UserRoleVO();
			
			userRole = userRoleService.viewUserRole(userRoleVO,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(userRole);
			jsonResponse.setAuthSuccesObject(commonVO);
			jsonResponse.setResponseMessage(getMessage("successMessage", authDetailsVo));
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
			jsonResponse.setResponseMessage(getMessage("errorMessage", authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}

	/**
	 * Method used to validate view
	 * 
	 * @param userRoleMaster
	 */
	private void viewValidate(UserRoleVO userRoleMaster,AuthDetailsVo authDetailsVo) {

		if (ValidationUtil.isNullOrBlank(userRoleMaster.getId())) {
			throw new CommonException(getMessage("userRole.roleId.required", authDetailsVo));
		}

	}

	@PostMapping(FilePathConstants.REST_TEMPLATE_COPY_USER_ROLE)
	@ResponseBody
	public ResponseEntity<JSONResponse> copyUserRole(@RequestBody UserRoleVO userRoleVO) throws IOException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = userRoleVO.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			saveValidate(userRoleVO,authDetailsVo);
			
			userRoleService.saveUserRole(userRoleVO,authDetailsVo);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			jsonResponse.setResponseMessage(getMessage("successMessage", authDetailsVo));
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
			jsonResponse.setResponseMessage(getMessage("errorMessage", authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

}
