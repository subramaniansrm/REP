package com.srm.coreframework.controller;

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
import com.srm.coreframework.constants.FilePathConstants;
import com.srm.coreframework.entity.EntityLicense;
import com.srm.coreframework.response.JSONResponse;
import com.srm.coreframework.service.EmailService;
import com.srm.coreframework.service.EntityMasterService;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.EmailVo;
import com.srm.coreframework.vo.EntityLicenseVO;

@RestController
public class EntityMasterController extends CommonController<EntityLicenseVO>{
	
	@Autowired
	EntityMasterService entityMasterService;

	@Autowired
	EmailService emailService;
	
	private static final Logger logger = LogManager.getLogger(EntityMasterController.class);

	@PostMapping(FilePathConstants.ENTITY_MASTER_CREATE)
	@ResponseBody
	public ResponseEntity<JSONResponse> create(@RequestBody EntityLicenseVO entityLicenseVo,AuthDetailsVo authDetailsVo) {

		JSONResponse jsonResponse = new JSONResponse();
		try {

			logger.info(getMessage("processValidation",authDetailsVo));
			logger.info(getMessage("processValidationCompleted",authDetailsVo));

			EntityLicense entityLicenseEntity = entityMasterService.create(entityLicenseVo,authDetailsVo);

			String msg =  entityLicenseEntity.getEntityName() + " " + getMessage("entityCreationSuccess",authDetailsVo);
			
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(msg);
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

		} catch (CommonException e) {
			e.printStackTrace();
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			if(e.getMessage().equals("duplicateUserName")){
				jsonResponse.setResponseCode(CommonConstant.FAILURE_COUNT);
			}			
			jsonResponse.setResponseMessage(getMessage(e.getMessage(),authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("saveErroMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}

		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}	 
	
	@PostMapping(FilePathConstants.PASSWORD_EXPIRY)
	@ResponseBody
	public ResponseEntity<JSONResponse> passwordExpirySendMail(@RequestBody EmailVo emailVo) {

		JSONResponse jsonResponse = new JSONResponse();

		try {

			emailService.sendEmail(emailVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage("success");
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

		} catch (CommonException e) {
			e.printStackTrace();
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);			 
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}

		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}
	
	
	@PostMapping(FilePathConstants.ENTITY_EXPIRY)
	@ResponseBody
	public ResponseEntity<JSONResponse> entityExpirySendMail(@RequestBody EmailVo emailVo) {

		JSONResponse jsonResponse = new JSONResponse();

		try {

			emailService.sendEmail(emailVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage("success");
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

		} catch (CommonException e) {
			e.printStackTrace();
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}

		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}

	@PostMapping(FilePathConstants.TRN_ENTITY_EXPIRY)
	@ResponseBody
	public ResponseEntity<JSONResponse> trnEntityExpirySendMail(@RequestBody EmailVo emailVo) {

		JSONResponse jsonResponse = new JSONResponse();

		try {

			emailService.sendEmail(emailVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage("success");
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

		} catch (CommonException e) {
			e.printStackTrace();
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}

		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}
	@PostMapping(FilePathConstants.ESCALATION_MAIL)
	@ResponseBody
	public ResponseEntity<JSONResponse> escalationdMail(@RequestBody EmailVo emailVo) {

		JSONResponse jsonResponse = new JSONResponse();

		try {

			emailService.sendEmail(emailVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage("success");
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

		} catch (CommonException e) {
			e.printStackTrace();
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}

		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}
	
}
