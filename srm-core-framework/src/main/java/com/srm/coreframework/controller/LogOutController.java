package com.srm.coreframework.controller;

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
import com.srm.coreframework.constants.FilePathConstants;
import com.srm.coreframework.response.JSONResponse;
import com.srm.coreframework.service.CommonService;
import com.srm.coreframework.service.LogOutService;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.UserMasterVO;

@RestController
public class LogOutController extends CommonController<UserMasterVO>{
	
	@Autowired
	LogOutService logOutService;
	
	@Autowired
	CommonService commonService;
	
	private static final Logger logger = LogManager.getLogger(LogOutController.class);
	
	
	@PostMapping(FilePathConstants.LOGOUT)
	@ResponseBody
	public ResponseEntity<JSONResponse> logOut(HttpServletRequest request,@RequestBody UserMasterVO userMasterVO) {

		JSONResponse jsonResponse = new JSONResponse();
		//String accessToken = getHeaderAccessToken(request);
		AuthDetailsVo authDetailsVo = null;
		try {
			
		
			/*authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}*/
			
			logger.info(getMessage("processValidation",authDetailsVo));
			logger.info(getMessage("processValidationCompleted",authDetailsVo));
					
			logOutService.logout(userMasterVO);
						
		 
			//if(null != userMasterVO && null != userMasterVO.getUserRole()){
				
				logOutService.updateSuperAdminUserEntity(userMasterVO);
			/*else{
			
			logOutService.updateUserEntity(authDetailsVo);
			}*/
			 		
				commonService.updateLoginAudit(userMasterVO.getId());
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("logout",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

		} catch (CommonException e) {
			e.printStackTrace();
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (LoginAuthException e) {
			e.printStackTrace();
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}  catch (Exception e) {
			e.printStackTrace();
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("saveErroMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}

		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

    }

}
