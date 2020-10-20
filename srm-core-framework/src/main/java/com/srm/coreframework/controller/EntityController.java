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
import com.srm.coreframework.service.EntityService;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.CommonVO;
import com.srm.coreframework.vo.EntityLicenseVO;
import com.srm.coreframework.vo.ScreenJsonVO;

@RestController
public class EntityController extends CommonController<EntityController> {

	private static final Logger logger = LogManager.getLogger(EntityController.class);	
	
	@Autowired
	EntityService entityService;
	 
	@PostMapping(FilePathConstants.ENTITY_MASTER_LIST)
	@ResponseBody
	public ResponseEntity<JSONResponse> getAll(@RequestBody ScreenJsonVO screenJson)
			throws IOException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = screenJson.getAccessToken();

		AuthDetailsVo authDetailsVo = null;
		try {

			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			
			CommonVO commonVO = getScreenFields(screenJson,authDetailsVo);

			List<EntityLicenseVO> entityLicenseVoList = new ArrayList<EntityLicenseVO>();
			if (null != commonVO) {
				entityLicenseVoList = entityService.getAll(authDetailsVo);
			}
					
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setAuthSuccesObject(commonVO);
			jsonResponse.setSuccesObject(entityLicenseVoList);
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
		
	@PostMapping(FilePathConstants.ENTITY_MASTER_VIEW)
	@ResponseBody
	public ResponseEntity<JSONResponse> view(@RequestBody EntityLicenseVO entityLicenseVO) {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = entityLicenseVO.getAccessToken();
		
		AuthDetailsVo authDetailsVo = null;
		try {
			 authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				//get from message.properties
				throw new LoginAuthException("Token Expired / Already Logined");
			}
		
			CommonVO commonVO = getScreenFields(entityLicenseVO.getScreenJson(),authDetailsVo);

			EntityLicenseVO entityLicense = new EntityLicenseVO();

			entityLicense = entityService.view(entityLicenseVO,authDetailsVo);			

			jsonResponse.setSuccesObject(entityLicense);
			jsonResponse.setAuthSuccesObject(commonVO);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			e.printStackTrace();
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
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}
	
	@PostMapping(FilePathConstants.ENTITY__REST_TEMPLATE_ADD)
	@ResponseBody
	public ResponseEntity<JSONResponse> add(@RequestBody EntityLicenseVO entityLicenseVO) {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = entityLicenseVO.getAccessToken();
		
		AuthDetailsVo authDetailsVo = null;
		try {
			 authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				//get from message.properties
				throw new LoginAuthException("Token Expired / Already Logined");
			}
		
			CommonVO commonVO = getScreenFields(entityLicenseVO.getScreenJson(),authDetailsVo);

			jsonResponse.setAuthSuccesObject(commonVO);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			e.printStackTrace();
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
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}
	
	@PostMapping(FilePathConstants.ENTITY_MASTER_SEARCH)
	@ResponseBody
	public ResponseEntity<JSONResponse> searchEntity(@RequestBody EntityLicenseVO entityLicenseVO)
			throws IOException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = entityLicenseVO.getAccessToken();
		 
		AuthDetailsVo authDetailsVo = null;
		try {

			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			
		 
			List<EntityLicenseVO> entityLicenseVoList = new ArrayList<EntityLicenseVO>();
		 
			entityLicenseVoList = entityService.searchEntity(entityLicenseVO,authDetailsVo);
		 
					
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(entityLicenseVoList);
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


	@PostMapping(FilePathConstants.ENTITY_MASTER_RENEWAL_REST_TEMPLATE)
	@ResponseBody
	public ResponseEntity<JSONResponse> renewalEntity(@RequestBody EntityLicenseVO entityLicenseVO) throws IOException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = entityLicenseVO.getAccessToken();

		AuthDetailsVo authDetailsVo = null;
		try {

			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}

			List<EntityLicenseVO> entityLicenseVoList = new ArrayList<EntityLicenseVO>();

			if (null != entityLicenseVO.getEntityId()) {
				entityService.renewalEntity(entityLicenseVO, authDetailsVo);
			}
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(entityLicenseVoList);
			jsonResponse.setResponseMessage(getMessage("entityLicenseUpdated", authDetailsVo));
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
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage", authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}
 	
	
	
	@PostMapping(FilePathConstants.ENTITY_MASTER_UPDATE_REST_TEMPLATE)
	@ResponseBody
	public ResponseEntity<JSONResponse> update(@RequestBody EntityLicenseVO entityLicenseVO) throws IOException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = entityLicenseVO.getAccessToken();
		String message = "";
		AuthDetailsVo authDetailsVo = null;
		try {

			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}

			List<EntityLicenseVO> entityLicenseVoList = new ArrayList<EntityLicenseVO>();

			if (null != entityLicenseVO.getEntityId()) {
			  message = entityService.update(entityLicenseVO, authDetailsVo);
			}
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(entityLicenseVoList);
			jsonResponse.setResponseMessage(message);
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
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage", authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}	
	
	@PostMapping(FilePathConstants.ENTITY_MASTER_ALLLIST)
	@ResponseBody
	public ResponseEntity<JSONResponse> getAllEntity(@RequestBody ScreenJsonVO screenJson)
			throws IOException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = screenJson.getAccessToken();

		AuthDetailsVo authDetailsVo = null;
		try {

			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			
			CommonVO commonVO = getScreenFields(screenJson,authDetailsVo);

			List<EntityLicenseVO> entityLicenseVoList = new ArrayList<EntityLicenseVO>();
			if (null != commonVO) {
				entityLicenseVoList = entityService.getAllEntity(authDetailsVo);
			}
					
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setAuthSuccesObject(commonVO);
			jsonResponse.setSuccesObject(entityLicenseVoList);
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
	
  }
