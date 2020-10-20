package com.srm.coreframework.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.config.LoginAuthException;
import com.srm.coreframework.constants.FilePathConstants;
import com.srm.coreframework.entity.EntityPlanning;
import com.srm.coreframework.response.JSONResponse;
import com.srm.coreframework.service.EntityPlanningService;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.CommonVO;
import com.srm.coreframework.vo.EntityPlanningVo;
import com.srm.coreframework.vo.PhoneBookVO;
import com.srm.coreframework.vo.ScreenJsonVO;

/**
 * @author priyankas
 *
 */
@RestController
public class EntityPlanningController extends CommonController<EntityPlanningVo>{

	@Autowired
	EntityPlanningService entityPlanningService;
	
	
	/**
	 * @return jsonResponse
	 * @throws IOException
	 */
	@PostMapping(FilePathConstants.ENTITY_PLANNING_LIST)
	@ResponseBody
	public ResponseEntity<JSONResponse> getEntityPlanningList()
			throws IOException {

		JSONResponse jsonResponse = new JSONResponse();
	

		try {

			List<EntityPlanningVo> entityPlanningVoList = entityPlanningService.getEntityPlanningList();

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(entityPlanningVoList);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			Log.info("Entity Planning Controller getEntityPlanningList Common Exception",e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			Log.info("Entity Planning Controller getEntityPlanningList Exception",e);
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}
	
	
	/**
	 * @return jsonResponse
	 * @throws IOException
	 */
	@PostMapping(FilePathConstants.ENTITY_PLANNING_LOAD)
	@ResponseBody
	public ResponseEntity<JSONResponse> getEntityPlanningDetails(@RequestBody EntityPlanningVo entityPlanningVo) throws IOException {

		JSONResponse jsonResponse = new JSONResponse();

		try {
			EntityPlanningVo entityPlanningVo2 = null;
			if(null != entityPlanningVo.getPlanId()){
				
				entityPlanningVo2 = entityPlanningService.getEntityPlanningDetails(entityPlanningVo.getPlanId());
			}

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(entityPlanningVo2);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			Log.info("Entity Planning Controller getEntityPlanningDetails Common Exception", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			Log.info("Entity Planning Controller getEntityPlanningDetails Exception", e);
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}
	
	
	@PostMapping(FilePathConstants.REST_TEMPLATE_ENTITY_PLANNING_MASTER_LIST)
	@ResponseBody
	public ResponseEntity<JSONResponse> list(HttpServletRequest request,@RequestBody ScreenJsonVO screenJson)
			throws IOException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = screenJson.getAccessToken();

		AuthDetailsVo authDetailsVo = null;
		try {

			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			
			CommonVO commonVO = entityPlanningService.getScreenFields(screenJson,authDetailsVo);

			List<EntityPlanningVo> entityPlanningList = new ArrayList<EntityPlanningVo>();
			if (null != commonVO) {
				entityPlanningList = entityPlanningService.list(authDetailsVo);
			}

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setAuthSuccesObject(commonVO);
			jsonResponse.setSuccesObject(entityPlanningList);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			Log.info("Entity Planning Controller list Common Exception", e);
			e.printStackTrace();
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}catch (LoginAuthException e) {
			Log.info("Entity Planning Controller list Login Auth Exception", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			Log.info("Entity Planning Controller list Exception", e);
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}
	@PostMapping(FilePathConstants.REST_TEMPLATE_ENTITY_PLANNING_MASTER_ADD)
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
			CommonVO commonVO = entityPlanningService.getScreenFields(screenJson,authDetailsVo);
			EntityPlanningVo entityPlanningVo = entityPlanningService.defatultImageLoad();
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setAuthSuccesObject(commonVO);
			jsonResponse.setSuccesObject(entityPlanningVo);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			e.printStackTrace();
			Log.info("Entity Planning Controller list Exception", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (LoginAuthException e) {
			Log.info("Entity Planning Controller list Exception", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}catch (Exception e) {
			e.printStackTrace();
			Log.info("Entity Planning Controller list Exception", e);
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}
	
	
	/*@PostMapping(FilePathConstants.REST_TEMPLATE_ENTITY_PLANNING_MASTER_SAVE)
	@ResponseBody
	public ResponseEntity<JSONResponse> save(@RequestBody EntityPlanningVo entityPlanningVo) throws IOException {
		
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = entityPlanningVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}

			entityPlanningService.save(entityPlanningVo,authDetailsVo);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			jsonResponse.setResponseMessage(getMessage("saveSuccessMessage",authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			e.printStackTrace();
			Log.info("Entity Planning Controller save Common Exception", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (LoginAuthException e) {
			Log.info("Entity Planning Controller save login auth Exception", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}catch (Exception e) {
			e.printStackTrace();
			Log.info("Entity Planning Controller save Exception", e);
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}*/
	
	@RequestMapping(value = FilePathConstants.REST_TEMPLATE_ENTITY_PLANNING_MASTER_SAVE, method = RequestMethod.POST, consumes = {
			"multipart/form-data" })
	@ResponseBody
	public ResponseEntity<JSONResponse> save(HttpServletRequest request,
			@RequestParam("file") MultipartFile[] uploadingFiles, @RequestParam("action") String str) {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = getHeaderAccessToken(request);

		AuthDetailsVo authDetailsVo = null;

		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			for (MultipartFile uploadedFile : uploadingFiles) {
				// File size Validation
				fileSizeValidation(uploadedFile);

			}
			ObjectMapper mapper = new ObjectMapper();

			EntityPlanningVo entityPlanningVo = mapper.readValue(str, EntityPlanningVo.class);

			entityPlanningService.save(entityPlanningVo,authDetailsVo,uploadingFiles);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("planCreateMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

		} catch (CommonException e) {
			e.printStackTrace();
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (LoginAuthException e) {
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("saveErroMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	/*@PostMapping(FilePathConstants.REST_TEMPLATE_ENTITY_PLANNING_MASTER_UPDATE)
	@ResponseBody
	public ResponseEntity<JSONResponse> update(@RequestBody EntityPlanningVo entityPlanningVo) throws IOException {
		
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = entityPlanningVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			if(null != entityPlanningVo.getPlanId()){
				entityPlanningService.update(entityPlanningVo,authDetailsVo);
			}

			
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			jsonResponse.setResponseMessage(getMessage("updateSuccessMessage",authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			e.printStackTrace();
			Log.info("Entity Planning Controller update Common Exception", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (LoginAuthException e) {
			Log.info("Entity Planning Controller update login auth Exception", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}catch (Exception e) {
			e.printStackTrace();
			Log.info("Entity Planning Controller update Exception", e);
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}*/
	
	@RequestMapping(value = FilePathConstants.REST_TEMPLATE_ENTITY_PLANNING_MASTER_UPDATE, method = RequestMethod.POST, consumes = {
			"multipart/form-data" })
	@ResponseBody
	public ResponseEntity<JSONResponse> update(HttpServletRequest request,
			@RequestParam("file") MultipartFile[] uploadingFiles, @RequestParam("action") String str) {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = getHeaderAccessToken(request);

		AuthDetailsVo authDetailsVo = null;

		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			for (MultipartFile uploadedFile : uploadingFiles) {
				// File size Validation
				fileSizeValidation(uploadedFile);

			}
			ObjectMapper mapper = new ObjectMapper();

			EntityPlanningVo entityPlanningVo = mapper.readValue(str, EntityPlanningVo.class);

			if(null != entityPlanningVo.getPlanId()){
				entityPlanningService.update(entityPlanningVo,authDetailsVo,uploadingFiles);
			}

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("saveSuccessMessage", authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

		} catch (CommonException e) {
			e.printStackTrace();
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (LoginAuthException e) {
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("saveErroMessage", authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}
	@PostMapping(FilePathConstants.REST_TEMPLATE_ENTITY_PLANNING_MASTER_VIEW)
	@ResponseBody
	public ResponseEntity<JSONResponse> view(@RequestBody EntityPlanningVo entityPlanningVo) throws IOException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = entityPlanningVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			CommonVO commonVO = entityPlanningService.getScreenFields(entityPlanningVo.getScreenJson(), authDetailsVo);

			EntityPlanningVo entityPlanning  = entityPlanningService.view(entityPlanningVo,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(entityPlanning);
			jsonResponse.setAuthSuccesObject(commonVO);
			jsonResponse.setResponseMessage(getMessage("successMessage", authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			Log.info("Entity Planning Controller view Common Exception", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (LoginAuthException e) {
			Log.info("Entity Planning Controller view Login auth Exception", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}catch (Exception e) {
			Log.info("Entity Planning Controller view Exception", e);
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage", authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}
	@PostMapping(FilePathConstants.REST_TEMPLATE_ENTITY_PLANNING_MASTER_DELETE)
	@ResponseBody
	public ResponseEntity<JSONResponse> delete(@RequestBody EntityPlanningVo entityPlanningVo) throws IOException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = entityPlanningVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			deleteValidate(entityPlanningVo,authDetailsVo);
			entityPlanningService.delete(entityPlanningVo,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(CommonConstant.NULL);
			jsonResponse.setResponseMessage(getMessage("deleteSuccessMessage", authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			Log.info("Entity Planning Controller delete common Exception", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (LoginAuthException e) {
			Log.info("Entity Planning Controller delete login auth Exception", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}catch (Exception e) {
			Log.info("Entity Planning Controller delete Exception", e);
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
	private void deleteValidate(EntityPlanningVo entityPlanningVo,AuthDetailsVo authDetailsVo) {

		if (null == entityPlanningVo.getDeleteItem() || entityPlanningVo.getDeleteItem().length == 0) {
			throw new CommonException(getMessage("userRole.roleId.required", authDetailsVo));
		}

	}
	
	
	@PostMapping(FilePathConstants.REST_TEMPLATE_ENTITY_PLANNING_MASTER_SEARCH)
	@ResponseBody
	public ResponseEntity<JSONResponse> search(@RequestBody EntityPlanningVo entityPlanningVo) throws IOException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = entityPlanningVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			List<EntityPlanningVo> list = entityPlanningService.search(entityPlanningVo,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(list);
			jsonResponse.setResponseMessage(getMessage("successMessage", authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			Log.info("Entity Planning Controller search  Common Exception", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		} catch (LoginAuthException e) {
			Log.info("Entity Planning Controller search login auth Exception", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}catch (Exception e) {
			Log.info("Entity Planning Controller search Exception", e);
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage", authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

		 
		@PostMapping(FilePathConstants.REST_TEMPLATE_ENTITY_PLANNING_DRP)
		@ResponseBody
		public ResponseEntity<JSONResponse> entityPlanningDropDown(@RequestBody EntityPlanningVo entityPlanningVo) throws IOException {

			JSONResponse jsonResponse = new JSONResponse();
			String accessToken = entityPlanningVo.getAccessToken();
			AuthDetailsVo authDetailsVo = null;
			try {
				authDetailsVo = tokenValidate(accessToken);
				if (null == authDetailsVo) {
					throw new LoginAuthException("Token Expired / Already Logined");
				}
				List<EntityPlanningVo> list = entityPlanningService.entityPlanningDropDown();

				jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
				jsonResponse.setSuccesObject(list);
				jsonResponse.setResponseMessage(getMessage("successMessage", authDetailsVo));
				return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
			} catch (CommonException e) {
				Log.info("Entity Planning Controller search  Common Exception", e);
				jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
				jsonResponse.setResponseMessage(e.getMessage());
				jsonResponse.setSuccesObject(CommonConstant.NULL);
			} catch (LoginAuthException e) {
				Log.info("Entity Planning Controller search login auth Exception", e);
				jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
				jsonResponse.setResponseMessage(e.getMessage());
				jsonResponse.setSuccesObject(CommonConstant.NULL);
			}catch (Exception e) {
				Log.info("Entity Planning Controller search Exception", e);
				jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
				jsonResponse.setResponseMessage(getMessage("errorMessage", authDetailsVo));
				jsonResponse.setSuccesObject(CommonConstant.NULL);
			}
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		}
		
				
		@PostMapping("/sr/check/expiryplanschedular")
		public ResponseEntity<JSONResponse> updatePlanExpiry() throws IOException {

			JSONResponse jsonResponse = new JSONResponse();

			try {
				entityPlanningService.updatePlanExpiry();

				jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);

				jsonResponse.setResponseMessage("success");
				return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
			} catch (CommonException e) {
				e.printStackTrace();				 
				jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
				jsonResponse.setResponseMessage(e.getMessage());
				jsonResponse.setSuccesObject(CommonConstant.NULL);
			} catch (LoginAuthException e) {				 
				jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
				jsonResponse.setResponseMessage(e.getMessage());
				jsonResponse.setSuccesObject(CommonConstant.NULL);
			} catch (Exception e) {				 
				jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
				jsonResponse.setResponseMessage("error");
				jsonResponse.setSuccesObject(CommonConstant.NULL);
			}
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

		}
		
}
