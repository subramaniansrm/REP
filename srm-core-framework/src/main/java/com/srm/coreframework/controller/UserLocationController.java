package com.srm.coreframework.controller;

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
import com.srm.coreframework.service.UserLocationService;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.util.ValidationUtil;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.CommonVO;
import com.srm.coreframework.vo.ScreenJsonVO;
import com.srm.coreframework.vo.UserLocationVO;

/**
 * Class used to get all location details, save ,update and delete
 * 
 * @author raathikaabm
 *
 */

@RestController
public class UserLocationController extends CommonController<UserLocationController> {

	@Autowired
	UserLocationService userLocationService;

	

	private static final Logger logger = LogManager.getLogger(UserLocationController.class);

	/**
	 * Method used to get all values of User Location
	 * 
	 * @param screenAuthorizationMaster
	 * @return
	 * @throws CoreException
	 * @throws CommonException
	 * @throws CommonException
	 */
	@PostMapping(FilePathConstants.LOCATION_LIST_RT)
	@ResponseBody
	public ResponseEntity<JSONResponse> getAll(@RequestBody ScreenJsonVO screenJson)
			throws CoreException,  CommonException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = screenJson.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			
			CommonVO commonVO= userLocationService.getScreenFields(screenJson,authDetailsVo);

			if (commonVO == null) {
				throw new CommonException(getMessage("auth.no.rights",authDetailsVo));
			}

			List<UserLocationVO> listUserLocationMasterVo = userLocationService.getLocation(authDetailsVo);
			/*
			 * if (listUserLocationMasterVo == null || listUserLocationMasterVo.size() <= 0)
			 * { this.loadValidation(listUserLocationMasterVo); }
			 */
			jsonResponse.setAuthSuccesObject(commonVO);
			jsonResponse.setSuccesObject(listUserLocationMasterVo);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
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

	
	@PostMapping(FilePathConstants.LOCATION_CREATE_RT)
	@ResponseBody
	public ResponseEntity<JSONResponse> create(@RequestBody UserLocationVO userLocationVo)
			throws CoreException, CommonException, CommonException {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = userLocationVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			logger.info(getMessage("processValidation",authDetailsVo));

			saveValidate(userLocationVo,authDetailsVo);

			logger.info(getMessage("processValidationCompleted",authDetailsVo));
			userLocationService.duplicateLocation(userLocationVo,authDetailsVo);

			userLocationService.create(userLocationVo,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("saveSuccessMessage",authDetailsVo));
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


	@PostMapping(FilePathConstants.LOCATION_UPDATE_RT)
	@ResponseBody
	public ResponseEntity<JSONResponse> update(@RequestBody UserLocationVO userLocationVo)
			throws CoreException, CommonException, CommonException {

		JSONResponse jsonResponse = new JSONResponse();

		String accessToken = userLocationVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;

		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			logger.info(getMessage("processValidation",authDetailsVo));

			updateValidate(userLocationVo,authDetailsVo);

			logger.info(getMessage("processValidationCompleted",authDetailsVo));
			userLocationService.duplicateLocation(userLocationVo,authDetailsVo);

			userLocationService.update(userLocationVo,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("updateSuccessMessage",authDetailsVo));
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
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);

		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	
	@PostMapping(FilePathConstants.LOCATION_DELETE_RT)
	@ResponseBody
	public ResponseEntity<JSONResponse> delete(@RequestBody UserLocationVO userLocationMasterVo)
			throws CoreException, CommonException, CommonException {

		JSONResponse jsonResponse = new JSONResponse();

		String accessToken = userLocationMasterVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;

		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			logger.info(getMessage("processValidation",authDetailsVo));

			deleteValidate(userLocationMasterVo,authDetailsVo);

			logger.info(getMessage("processValidationCompleted",authDetailsVo));

			userLocationService.delete(userLocationMasterVo,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("deleteSuccessMessage",authDetailsVo));
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
			jsonResponse.setResponseMessage(getMessage("deleteErrorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	
	@PostMapping(FilePathConstants.LOCATION_VIEW_RT)
	@ResponseBody
	public ResponseEntity<JSONResponse> view(@RequestBody UserLocationVO userLocationMasterVo)
			throws CoreException, CommonException {

		JSONResponse jsonResponse = new JSONResponse();
		
		ScreenJsonVO screenJson = new ScreenJsonVO();
		String accessToken = userLocationMasterVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			screenJson.setScreenId(userLocationMasterVo.getScreenId());
			screenJson.setSubScreenId(userLocationMasterVo.getSubScreenId());
			CommonVO commonVO = userLocationService
					.getScreenFields(screenJson,authDetailsVo);
			/*if (commonVO == null) {
				this.viewValidation(userLocationMasterVo);
			}*/
			
			UserLocationVO userLocation = new UserLocationVO();

			userLocation = userLocationService.view(userLocationMasterVo,authDetailsVo);

			jsonResponse.setSuccesObject(userLocation);
			jsonResponse.setAuthSuccesObject(commonVO);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

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

	
	@PostMapping(FilePathConstants.LOCATION_SEARCH_RT)
	@ResponseBody
	public ResponseEntity<JSONResponse> getAllSearch(@RequestBody UserLocationVO userLocationMasterVo)
			throws CoreException, CommonException, CommonException {

		String accessToken = userLocationMasterVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		JSONResponse jsonResponse = new JSONResponse();
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}

			List<UserLocationVO> userLocationList = userLocationService.search(userLocationMasterVo, authDetailsVo);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(userLocationList);

			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
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
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.LOCATION_ADD_RT)
	@ResponseBody
	public ResponseEntity<JSONResponse> addUserLocation(@RequestBody UserLocationVO userLocationMasterVo) {
		JSONResponse jsonResponse = new JSONResponse();

		ScreenJsonVO screenJson = new ScreenJsonVO();
		String accessToken = userLocationMasterVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			screenJson.setScreenId(userLocationMasterVo.getScreenId());
			screenJson.setSubScreenId(userLocationMasterVo.getSubScreenId());
			CommonVO commonVO  = userLocationService
					.getScreenFields(userLocationMasterVo.getScreenJson(),authDetailsVo);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("saveSuccessMessage",authDetailsVo));
			jsonResponse.setAuthSuccesObject(commonVO);
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
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
		}

		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	private void saveValidate(UserLocationVO userLocationMasterVo,AuthDetailsVo authDetailsVo) throws CommonException {

		for (String field : userLocationMasterVo.getScreenFieldDisplayVoList()) {

			// UserLocationDetails Validation
			if (ControlNameConstants.LOCATION_DETAILS.equals(field)) {

				/*
				 * if (ValidationUtil.isNullOrBlank(userLocationMasterVo.
				 * getUserLocationDetails().trim())) { throw new
				 * CommonException(getMessage( "location.userLocationDetails.required")); }
				 */
				if ((userLocationMasterVo.getUserLocationDetails().length() > 255)) {
					throw new CommonException(getMessage("location.userLocationDetails.limit",authDetailsVo));
				}
			}
			// UserLocationName Validation
			if (ControlNameConstants.LOCATION_NAME.equals(field)) {

				if (ValidationUtil.isNullOrBlank(userLocationMasterVo.getUserLocationName().trim())) {
					throw new CommonException(getMessage("location.userLocationName.required",authDetailsVo));
				}
				if ((userLocationMasterVo.getUserLocationName().length() > 255)) {
					throw new CommonException(getMessage("location.userLocationName.limit",authDetailsVo));
				}
			}
			/*
			 * // Zip Validation if (ControlNameConstants.LOCATION_ZIP.equals(field)) {
			 * 
			 * if (ValidationUtil.isNullOrBlank(userLocationMasterVo.getZip().trim( ))) {
			 * throw new CommonException(getMessage("location.zip.required")); } if
			 * ((userLocationMasterVo.getZip().length() < 4 ||
			 * userLocationMasterVo.getZip().length() > 8)) { throw new
			 * CommonException(getMessage("location.zip.limit")); } } // Phone
			 * Validation if (ControlNameConstants.LOCATION_PHONE.equals(field)) {
			 * 
			 * if (ValidationUtil.isNullOrBlank(userLocationMasterVo.getPhone())) { throw
			 * new CommonException(getMessage("location.phone.required")); } if
			 * (ValidationUtil.isContactNumber(userLocationMasterVo.getPhone())) { throw new
			 * CommonException(getMessage("location.phone.numberValidation" )); } } //
			 * Fax Validation if (ControlNameConstants.LOCATION_LAST_NAME.equals(field)) {
			 * 
			 * if (ValidationUtil.isNullOrBlank(userLocationMasterVo.getFax().trim( ))) {
			 * throw new CommonException(getMessage("location.fax.required")); } if
			 * ((userLocationMasterVo.getFax().length() > 255)) { throw new
			 * CommonException(getMessage("location.fax.limit")); } } // Email
			 * Validation if (ControlNameConstants.LOCATION_EMAIL.equals(field)) {
			 * 
			 * if (ValidationUtil.isNullOrBlank(userLocationMasterVo.getEmail(). trim())) {
			 * throw new CommonException(getMessage("location.email.required")); } if
			 * (ValidationUtil.isEmail(userLocationMasterVo.getEmail())) { throw new
			 * CommonException(getMessage("location.email.required")); } if
			 * ((userLocationMasterVo.getEmail().length() > 255)) { throw new
			 * CommonException(getMessage("location.email.limit")); } } // CountryId
			 * Validation if (ControlNameConstants.LOCATION_COUNTRY.equals(field)) {
			 * 
			 * if (ValidationUtil.isNullOrBlank(userLocationMasterVo.getCountryId() )) {
			 * throw new CommonException(getMessage("location.countryId.required")); }
			 * } // StateId Validation if
			 * (ControlNameConstants.LOCATION_STATE.equals(field)) {
			 * 
			 * if (ValidationUtil.isNullOrBlank(userLocationMasterVo.getStateId())) { throw
			 * new CommonException(getMessage("location.stateId.required")); } } //
			 * CityId Validation if (ControlNameConstants.LOCATION_COUNTRY.equals(field)) {
			 * 
			 * if (ValidationUtil.isNullOrBlank(userLocationMasterVo.getCityId())) { throw
			 * new CommonException(getMessage("location.cityId.required")); } } //
			 * Contact Name if (ControlNameConstants.LOCATION_COUNTRY.equals(field)) {
			 * 
			 * if (ValidationUtil.isNullOrBlank(userLocationMasterVo.getContactName
			 * ().trim())) { throw new
			 * CommonException(getMessage("location.ContactName.required")) ; } if
			 * ((userLocationMasterVo.getContactName().length() > 255)) { throw new
			 * CommonException(getMessage("location.ContactName.limit")); } }
			 */
		}
	}

	private void updateValidate(UserLocationVO userLocationMasterVo,AuthDetailsVo authDetailsVo) throws CommonException {
		for (String field : userLocationMasterVo.getScreenFieldDisplayVoList()) {

			// UserLocation Id Validation.
			if (ValidationUtil.isNullOrBlank(userLocationMasterVo.getId())) {
				throw new CommonException(getMessage("location.id.required",authDetailsVo));
			}
			// UserLocationDetails Validation
			if (ControlNameConstants.LOCATION_DETAILS.equals(field)) {

				/*
				 * if (ValidationUtil.isNullOrBlank(userLocationMasterVo.
				 * getUserLocationDetails().trim())) { throw new
				 * CommonException(getMessage( "location.userLocationDetails.required")); }
				 */
				if ((userLocationMasterVo.getUserLocationDetails().length() > 255)) {
					throw new CommonException(getMessage("location.userLocationDetails.limit",authDetailsVo));
				}
			}
			// UserLocationName Validation
			if (ControlNameConstants.LOCATION_NAME.equals(field)) {

				if (ValidationUtil.isNullOrBlank(userLocationMasterVo.getUserLocationName().trim())) {
					throw new CommonException(getMessage("location.userLocationName.required",authDetailsVo));
				}
				if ((userLocationMasterVo.getUserLocationName().length() > 255)) {
					throw new CommonException(getMessage("location.userLocationName.limit",authDetailsVo));
				}
			}
			// Zip Validation
			/*
			 * if (ControlNameConstants.LOCATION_ZIP.equals(field)) {
			 * 
			 * if (ValidationUtil.isNullOrBlank(userLocationMasterVo.getZip().trim( ))) {
			 * throw new CommonException(getMessage("location.zip.required")); } if
			 * ((userLocationMasterVo.getZip().length() < 4 ||
			 * userLocationMasterVo.getZip().length() > 8)) { throw new
			 * CommonException(getMessage("location.zip.limit")); } } // Phone
			 * Validation if (ControlNameConstants.LOCATION_PHONE.equals(field)) {
			 * 
			 * if (ValidationUtil.isNullOrBlank(userLocationMasterVo.getPhone())) { throw
			 * new CommonException(getMessage("location.phone.required")); } if
			 * (ValidationUtil.isContactNumber(userLocationMasterVo.getPhone())) { throw new
			 * CommonException(getMessage("location.phone.numberValidation" )); } } //
			 * Fax Validation if (ControlNameConstants.LOCATION_LAST_NAME.equals(field)) {
			 * 
			 * if (ValidationUtil.isNullOrBlank(userLocationMasterVo.getFax().trim( ))) {
			 * throw new CommonException(getMessage("location.fax.required")); } if
			 * ((userLocationMasterVo.getFax().length() > 255)) { throw new
			 * CommonException(getMessage("location.fax.limit")); } } // Email
			 * Validation if (ControlNameConstants.LOCATION_EMAIL.equals(field)) {
			 * 
			 * if (ValidationUtil.isNullOrBlank(userLocationMasterVo.getEmail(). trim())) {
			 * throw new CommonException(getMessage("location.email.required")); } if
			 * (ValidationUtil.isEmail(userLocationMasterVo.getEmail())) { throw new
			 * CommonException(getMessage("location.email.required")); } if
			 * ((userLocationMasterVo.getEmail().length() > 255)) { throw new
			 * CommonException(getMessage("location.email.limit")); } } // CountryId
			 * Validation if (ControlNameConstants.LOCATION_COUNTRY.equals(field)) {
			 * 
			 * if (ValidationUtil.isNullOrBlank(userLocationMasterVo.getCountryId() )) {
			 * throw new CommonException(getMessage("location.countryId.required")); }
			 * } // StateId Validation if
			 * (ControlNameConstants.LOCATION_STATE.equals(field)) {
			 * 
			 * if (ValidationUtil.isNullOrBlank(userLocationMasterVo.getStateId())) { throw
			 * new CommonException(getMessage("location.stateId.required")); } } //
			 * CityId Validation if (ControlNameConstants.LOCATION_COUNTRY.equals(field)) {
			 * 
			 * if (ValidationUtil.isNullOrBlank(userLocationMasterVo.getCityId())) { throw
			 * new CommonException(getMessage("location.cityId.required")); } } //
			 * Contact Name if (ControlNameConstants.LOCATION_COUNTRY.equals(field)) {
			 * 
			 * if (ValidationUtil.isNullOrBlank(userLocationMasterVo.getContactName
			 * ().trim())) { throw new
			 * CommonException(getMessage("location.ContactName.required")) ; } if
			 * ((userLocationMasterVo.getContactName().length() > 255)) { throw new
			 * CommonException(getMessage("location.ContactName.limit")); } }
			 */ }
	}

	private void deleteValidate(UserLocationVO userLocationMasterVo,AuthDetailsVo authDetailsVo) throws CommonException {

		if (null == userLocationMasterVo.getDeleteItem() || userLocationMasterVo.getDeleteItem().length <= 0) {
			throw new CommonException(getMessage("delete.validation",authDetailsVo));
		}

	}

	@SuppressWarnings("unused")
	private void viewValidation(UserLocationVO userLocationMasterVo,AuthDetailsVo authDetailsVo) throws CommonException {
		if (ValidationUtil.isNullOrBlank(userLocationMasterVo.getId())) {
			throw new CommonException(getMessage("user.id.required",authDetailsVo));
		}

	}

}
