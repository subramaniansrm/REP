package com.srm.coreframework.controller;

import java.io.IOException;

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
import com.srm.coreframework.service.CommonDropDownService;
import com.srm.coreframework.service.UserEntityMappingService;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.CommonVO;
import com.srm.coreframework.vo.DropdownUserMasterVO;
import com.srm.coreframework.vo.EntityLicenseVO;
import com.srm.coreframework.vo.ScreenJsonVO;
import com.srm.coreframework.vo.UserEntityMappingVo;
import com.srm.coreframework.vo.UserMasterVO;

@RestController
public class UserEntityMappingController extends CommonController<UserMasterVO>{

	private static final Logger logger = LogManager.getLogger(UserEntityMappingController.class);

	@Autowired
	UserEntityMappingService userEntityMappingService;
	
	@Autowired
	CommonDropDownService commonDropDownService;
	
	
	@PostMapping(FilePathConstants.REST_TEMPLATE_ENTITY_LIST)
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
			UserMasterVO  userMasterVO = new UserMasterVO();
			CommonVO commonVO = userEntityMappingService.getScreenFields(screenJson,authDetailsVo);

			List<DropdownUserMasterVO> userMasterVoList = new ArrayList<DropdownUserMasterVO>();
			if (null != commonVO) {
				// Load Role based User List
				 userMasterVoList = commonDropDownService.getAllUser(userMasterVO,authDetailsVo);
			}

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			jsonResponse.setAuthSuccesObject(commonVO);
			jsonResponse.setSuccesObject(userMasterVoList);
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
	
	@PostMapping(FilePathConstants.REST_TEMPLATE_ENTITY)
	@ResponseBody
	public ResponseEntity<JSONResponse> loadEntity(@RequestBody ScreenJsonVO screenJsonVO) throws IOException {
		String accessToken = screenJsonVO.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		JSONResponse jsonResponse = new JSONResponse();
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			List<EntityLicenseVO> stateMasterVoList = userEntityMappingService.getEntity(authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(stateMasterVoList);
			jsonResponse.setResponseMessage(getMessage("saveSuccessMessage",authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}
	
	
	@PostMapping(FilePathConstants.REST_TEMPLATE_ENTITY_SAVE)
	@ResponseBody
	public ResponseEntity<JSONResponse> saveEntity(@RequestBody UserEntityMappingVo userEntityMappingVo) throws IOException {
		String accessToken = userEntityMappingVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		JSONResponse jsonResponse = new JSONResponse();
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			userEntityMappingService.saveEntity(userEntityMappingVo,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("saveSuccessMessage",authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			e.printStackTrace();
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (LoginAuthException e) {
			e.printStackTrace();
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}
	
	@PostMapping(FilePathConstants.REST_TEMPLATE_USER_ENTITY_DELETE)
	@ResponseBody
	public ResponseEntity<JSONResponse> deleteEntity(@RequestBody UserEntityMappingVo userEntityMappingVo) throws IOException {
		String accessToken = userEntityMappingVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		JSONResponse jsonResponse = new JSONResponse();
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			userEntityMappingService.deleteUserEntity(userEntityMappingVo.getUserId());

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}
	
}
