package com.srm.coreframework.controller;

import java.lang.reflect.InvocationTargetException;
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
import com.srm.coreframework.service.SubLocationService;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.util.ValidationUtil;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.CommonVO;
import com.srm.coreframework.vo.ScreenJsonVO;
import com.srm.coreframework.vo.SubLocationVO;


@RestController
public class SubLocationController extends CommonController<SubLocationVO>{

	@Autowired
	SubLocationService subLocationService;

	

	/*@Autowired
	private MessageSource messageSource;*/

	private static final Logger logger = LogManager.getLogger(SubLocationController.class);

	/**
	 * Method is used to get all the Request type.
	 * 
	 * @return response Response
	 * @throws CommonException
	 * @throws BusinessExceptionr
	 */

	
	@PostMapping(FilePathConstants.SUBLOCATION_LIST_RT)
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
			
			CommonVO commonVO = subLocationService.getScreenFields(screenJson,authDetailsVo);

			List<SubLocationVO> subLocationVoList = subLocationService.getAll(authDetailsVo);
			
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(subLocationVoList);
			jsonResponse.setAuthSuccesObject(commonVO);
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

	@PostMapping(FilePathConstants.SUBLOCATION_ADD_RT)
	@ResponseBody
	public ResponseEntity<JSONResponse> add(@RequestBody SubLocationVO subLocationVo)
			throws CoreException, CommonException, IllegalAccessException, InvocationTargetException {
		JSONResponse jsonResponse = new JSONResponse();
		ScreenJsonVO screenJson = new ScreenJsonVO();
		String accessToken = subLocationVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			screenJson.setScreenId(subLocationVo.getScreenId());
			screenJson.setSubScreenId(subLocationVo.getSubScreenId());
			CommonVO commonVO = subLocationService
					.getScreenFields(screenJson,authDetailsVo);
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
	
	@PostMapping(FilePathConstants.SUBLOCATION_CREATE_RT)
	@ResponseBody
	public ResponseEntity<JSONResponse> createsublocation(@RequestBody SubLocationVO subLocationVo) {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = subLocationVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {

			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			saveValidate(subLocationVo,authDetailsVo);
			subLocationService.duplicateSubLocation(subLocationVo,authDetailsVo);
			subLocationService.create(subLocationVo,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("saveSuccessMessage",authDetailsVo));
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
			jsonResponse.setResponseMessage(getMessage("saveErroMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}
	
	@PostMapping(FilePathConstants.SUBLOCATION_UPDATE_RT)
	@ResponseBody
	public ResponseEntity<JSONResponse> updatesublocation(@RequestBody SubLocationVO subLocationVo) {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = subLocationVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			updateValidate(subLocationVo,authDetailsVo);
			subLocationService.duplicateSubLocation(subLocationVo,authDetailsVo);
			subLocationService.updateSublocation(subLocationVo,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("updateSuccessMessage",authDetailsVo));
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
		}catch (Exception e) {
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("updateErrorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}
	
	@PostMapping(FilePathConstants.SUBLOCATION_DELETE_RT)
	@ResponseBody
	public ResponseEntity<JSONResponse> deleteSublocation(@RequestBody SubLocationVO subLocationVo) {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = subLocationVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		this.deleteValidate(subLocationVo,authDetailsVo);
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			subLocationService.deletesublocation(subLocationVo,authDetailsVo);

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
			jsonResponse.setResponseMessage(getMessage("deleteErrorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}
	
	
	@PostMapping(FilePathConstants.SUBLOCATION_VIEW_RT)
	@ResponseBody
	public ResponseEntity<JSONResponse> viewSublocation	(@RequestBody SubLocationVO subLocationVo) {
		JSONResponse jsonResponse = new JSONResponse();
		ScreenJsonVO screenJson = new ScreenJsonVO();
		String accessToken = subLocationVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			screenJson.setScreenId(subLocationVo.getScreenId());
			screenJson.setSubScreenId(subLocationVo.getSubScreenId());
			CommonVO commonVO = subLocationService
					.getScreenFields(screenJson,authDetailsVo);

			SubLocationVO subLocation = new SubLocationVO();
			
			subLocation = subLocationService.viewSublocation(subLocationVo,authDetailsVo);
			if (subLocation == null) {
				this.viewValidation(subLocation,authDetailsVo);
			}

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(subLocation);
			jsonResponse.setAuthSuccesObject(commonVO);
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
	
	
	@PostMapping(FilePathConstants.SUBLOCATION_SEARCH_RT)
	@ResponseBody
	public ResponseEntity<JSONResponse> getAllSearch	(@RequestBody SubLocationVO subLocationVo) {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = subLocationVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			List<SubLocationVO> subLocationVoList = subLocationService.getAllSearch(subLocationVo,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(subLocationVoList);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			logger.info(getMessage("successMessage",authDetailsVo));
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
	
	/**
	 * Method is to Save Validation.
	 * 
	 * @param subLocationVo
	 *            SubLocationVo
	 */
	private void saveValidate(SubLocationVO subLocationVo,AuthDetailsVo authDetailsVo) {

		// Iterate the fields from ScreenDisplayvo
		for (String field : subLocationVo.getScreenFieldDisplayVoList()) {

			if (field.equals(ControlNameConstants.SUBLOCATION_SUBLOCATIONNAME)) {

				// SubLocation Name Validation
				if (ValidationUtil.isNullOrBlank(subLocationVo.getSubLocationName().trim())) {
					throw new CommonException(getMessage("subLocation.subLocationName.required",authDetailsVo));
				}
				if ((subLocationVo.getSubLocationName().length() > 250)) {
					throw new CommonException(getMessage("subLocation.subLocationName.limit",authDetailsVo));
				}
			}
			// Location Validation
			if (field.equals(ControlNameConstants.SUBLOCATION_LOCATIONNAME)) {

				if (ValidationUtil.isNullOrBlank(subLocationVo.getId())) {
					throw new CommonException(getMessage("subLocation.location.required",authDetailsVo));
				}
			}
			// SubLocation Flag Validation

			if (field.equals(ControlNameConstants.SUBLOCATION_STATUS)) {

				if (!subLocationVo.isSubLocationIsActive()) {
					throw new CommonException(getMessage("subLocation.activeFlag.required",authDetailsVo));
				}
			}

		}

	}

	/**
	 * Method is for update validation
	 * 
	 * @param subLocationVo
	 *            SubLocationVo
	 */
	private void updateValidate(SubLocationVO subLocationVo,AuthDetailsVo authDetailsVo) {

		// Code Validation
		if (ValidationUtil.isNullOrBlank(subLocationVo.getSubLocationCode())) {
			throw new CommonException(getMessage("subLocation.code.required",authDetailsVo));
		}
		if ((subLocationVo.getSubLocationCode().length() > 10)) {
			throw new CommonException(getMessage("subLocation.code.limit",authDetailsVo));
		}

		// SubLocation Name Validation
		if (ValidationUtil.isNullOrBlank(subLocationVo.getSubLocationName().trim())) {
			throw new CommonException(getMessage("subLocation.subLocationName.required",authDetailsVo));
		}
		if ((subLocationVo.getSubLocationName().length() > 250)) {
			throw new CommonException(getMessage("subLocation.subLocationName.limit",authDetailsVo));
		}

		// Location Validation
		if (ValidationUtil.isNullOrBlank(subLocationVo.getId())) {
			throw new CommonException(getMessage("subLocation.location.required",authDetailsVo));
		}

	}

	/**
	 * Method is for Delete Validation
	 * 
	 * @param subLocationVo
	 *            SubLocationVo
	 */
	private void deleteValidate(SubLocationVO subLocationVo,AuthDetailsVo authDetailsVo) {

		if (null == subLocationVo.getDeleteItem() || subLocationVo.getDeleteItem().length <= 0) {
			throw new CommonException(getMessage("delete.validation",authDetailsVo));
		}

	}

	/**
	 * Method is for Load validation
	 * 
	 * @param subLocationVoList
	 *            List<SubLocationVo>
	 */
	/*
	 * private void loadValidation(List<SubLocationVo> subLocationVoList) { if
	 * (subLocationVoList == null || subLocationVoList.size() <= 0) { throw new
	 * CommonException(getMessage("common.noRecord")); }
	 * 
	 * }
	 */

	/**
	 * Method is for View validation
	 * 
	 * @param subLocationVo
	 *            SubLocationVo
	 */
	private void viewValidation(SubLocationVO subLocationVo,AuthDetailsVo authDetailsVo) {
		if (subLocationVo == null) {
			throw new CommonException(getMessage("common.noRecord",authDetailsVo));
		}

	}

	/**
	 * Method is to validate the id.
	 * 
	 * @param subLocationVo
	 *            SubLocationVo
	 */
	@SuppressWarnings("unused")
	private void idValidate(SubLocationVO subLocationVo,AuthDetailsVo authDetailsVo) {
		if (subLocationVo.getSublocationId() == 0) {
			throw new CommonException(getMessage("search.validation",authDetailsVo));
		}

	}
	/*protected String getMessage(String code) {
		return getMessage(code, new Object[] {});
	}

	protected String getMessage(String code, Object args[]) {
		return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
	}*/
}
