package com.srm.coreframework.controller;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.config.LoginAuthException;
import com.srm.coreframework.constants.FilePathConstants;
import com.srm.coreframework.entity.CurrencyEntity;
import com.srm.coreframework.response.JSONResponse;
import com.srm.coreframework.service.CommonDropDownService;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.CityVO;
import com.srm.coreframework.vo.CountryVO;
import com.srm.coreframework.vo.DivisionMasterVO;
import com.srm.coreframework.vo.DropdownDepartmentVO;
import com.srm.coreframework.vo.DropdownLocationVO;
import com.srm.coreframework.vo.DropdownSubLocationVO;
import com.srm.coreframework.vo.DropdownUserMasterVO;
import com.srm.coreframework.vo.DropdownUserRoleVO;
import com.srm.coreframework.vo.EntityLicenseVO;
import com.srm.coreframework.vo.ScreenJsonVO;
import com.srm.coreframework.vo.StateVO;
import com.srm.coreframework.vo.SubLocationVO;
import com.srm.coreframework.vo.UserDepartmentVO;
import com.srm.coreframework.vo.UserMasterVO;
import com.srm.coreframework.vo.UserRoleTypeVO;
import com.srm.coreframework.vo.UserRoleVO;

@RestController
public class CommonDropDownController extends CommonController<T>{

	Logger logger = LoggerFactory.getLogger(CommonDropDownController.class);

	@Autowired
	private CommonDropDownService commonDropDownService;
	
	@PostMapping(FilePathConstants.REST_TEMPLATE_DIVISIONS_LOAD)
	@ResponseBody
	public ResponseEntity<JSONResponse> loadDivision(@RequestBody ScreenJsonVO screenJson) throws IOException {

		String accessToken = screenJson.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		JSONResponse jsonResponse = new JSONResponse();
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			List<DivisionMasterVO> divisionList = commonDropDownService.getAllDivision(authDetailsVo);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(divisionList);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}catch (LoginAuthException e) {
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

	@PostMapping(FilePathConstants.REST_TEMPLATE_LOCATION_LOAD)
	@ResponseBody
	public ResponseEntity<JSONResponse> loadLocation(@RequestBody ScreenJsonVO screenJson) throws IOException {
		String accessToken = screenJson.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		JSONResponse jsonResponse = new JSONResponse();
		try {
			System.out.println("entering into rest controller");
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			List<DropdownLocationVO> locationList = commonDropDownService.getAllLocation(screenJson,authDetailsVo);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(locationList);
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

	@PostMapping(FilePathConstants.REST_TEMPLATE_DEPARTMENT_LOAD)
	@ResponseBody
	public ResponseEntity<JSONResponse> loadDepartment(@RequestBody UserDepartmentVO departmentVo) throws IOException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = departmentVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			List<DropdownDepartmentVO> departmentList = commonDropDownService.getAllDepartment(departmentVo,authDetailsVo);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(departmentList);
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

	/**
	 * Method to load Role list
	 * 
	 * @param userRoleVo
	 * @return ResponseEntity
	 * @throws IOException
	 */
	@PostMapping(FilePathConstants.REST_TEMPLATE_ROLE_LOAD)
	@ResponseBody
	public ResponseEntity<JSONResponse> loadRole( @RequestBody UserRoleVO userRoleVo) throws IOException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = userRoleVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			List<DropdownUserRoleVO> roleList = commonDropDownService.getAllRole(userRoleVo,authDetailsVo);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(roleList);
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

	/**
	 * Method to get User List
	 * 
	 * @param userMasterVo
	 * @return ResponseEntity
	 * @throws IOException
	 */
	@PostMapping(FilePathConstants.REST_TEMPLATE_USER_DROPDOWN)
	@ResponseBody
	public ResponseEntity<JSONResponse> getAllUser(@RequestBody UserMasterVO userMasterVo) throws IOException {
		String accessToken = userMasterVo.getAccessToken();
		
		AuthDetailsVo authDetailsVo = null;
		JSONResponse jsonResponse = new JSONResponse();
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			
			List<DropdownUserMasterVO> userMasterVoList = commonDropDownService.getAllUser(userMasterVo,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(userMasterVoList);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			e.printStackTrace();
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
			e.printStackTrace();
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}

	@PostMapping(FilePathConstants.REST_TEMPLATE_SUBLOCATION_LOAD)
	@ResponseBody
	public ResponseEntity<JSONResponse> getAllSublocation(@RequestBody SubLocationVO subLocationVo) throws IOException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = subLocationVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			List<DropdownSubLocationVO> subLocationVolist = commonDropDownService
					.getAllsublocationList(subLocationVo.getId(),authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(subLocationVolist);
			jsonResponse.setResponseMessage(getMessage("successMessage",authDetailsVo));
			return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
		} catch (CommonException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}catch (LoginAuthException e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.FAILURE_CODE);
			jsonResponse.setResponseMessage(e.getMessage());
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}  catch (Exception e) {
			logger.error("error", e);
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("errorMessage",authDetailsVo));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

	}
	
	
	@PostMapping(FilePathConstants.REST_TEMPLATE_COUNTRY_LOAD)
	@ResponseBody
	public ResponseEntity<JSONResponse> loadCountry(@RequestBody ScreenJsonVO screenJson) throws IOException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = screenJson.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			List<CountryVO> countryMasterVoList = commonDropDownService.getAllCountry(authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(countryMasterVoList);
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
	@PostMapping(FilePathConstants.REST_TEMPLATE_USER_DEP)
	@ResponseBody
	public ResponseEntity<JSONResponse> getUserDep(@RequestBody UserMasterVO userMasterVo) throws IOException {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = userMasterVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			List<DropdownUserMasterVO> userMasterVoList = commonDropDownService.getUserDep(userMasterVo,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(userMasterVoList);
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
	
	@PostMapping(FilePathConstants.REST_TEMPLATE_USER_ROLE_LOAD_USER_LOCATION)
	@ResponseBody
	public ResponseEntity<JSONResponse> loadUserLocationDetails(@RequestBody UserMasterVO userMasterVo) throws IOException {
		String accessToken = userMasterVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		JSONResponse jsonResponse = new JSONResponse();
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			List<DropdownLocationVO> dropdownLocationVoList = commonDropDownService.getLoadUserLocationDetails(authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(dropdownLocationVoList);
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
	
	@PostMapping(FilePathConstants.REST_TEMPLATE_USER_ROLE_ROLETYPE)
	@ResponseBody
	public ResponseEntity<JSONResponse> getRoleType(@RequestBody UserRoleTypeVO userRoleTypeVo) throws IOException {
		String accessToken = userRoleTypeVo.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		JSONResponse jsonResponse = new JSONResponse();
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			List<UserRoleTypeVO> userRoleTypeVoList = commonDropDownService.getRoleType(userRoleTypeVo,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(userRoleTypeVoList);
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
	@PostMapping(FilePathConstants.REST_TEMPLATE_USER_ROLE_LOAD_USER_DEPARTMENT)
	@ResponseBody
	public ResponseEntity<JSONResponse> loadUserDepartmentDetails(@RequestBody ScreenJsonVO screenJsonVO) throws IOException {
		String accessToken = screenJsonVO.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		JSONResponse jsonResponse = new JSONResponse();
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			List<DropdownDepartmentVO> dropdownDepartmentVoList = commonDropDownService.getLoadUserDepartmentDetails(authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(dropdownDepartmentVoList);
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
	
	@PostMapping(FilePathConstants.REST_TEMPLATE_CITY_LOAD)
	@ResponseBody
	public ResponseEntity<JSONResponse> loadCity(@RequestBody ScreenJsonVO screenJsonVO) throws IOException {
		String accessToken = screenJsonVO.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		JSONResponse jsonResponse = new JSONResponse();
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			List<CityVO> cityMasterVoList = commonDropDownService.getAllCity(authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(cityMasterVoList);
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
	@PostMapping(FilePathConstants.REST_TEMPLATE_STATE_LOAD)
	@ResponseBody
	public ResponseEntity<JSONResponse> loadState(@RequestBody ScreenJsonVO screenJsonVO) throws IOException {
		String accessToken = screenJsonVO.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		JSONResponse jsonResponse = new JSONResponse();
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			List<StateVO> stateMasterVoList = commonDropDownService.getAllState(authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(stateMasterVoList);
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
	
	
	@PostMapping(FilePathConstants.REST_TEMPLATE_CURRENCY_LOAD)
	@ResponseBody
	public ResponseEntity<JSONResponse> currencyLoad(@RequestBody ScreenJsonVO screenJsonVO) throws IOException {
		String accessToken = screenJsonVO.getAccessToken();
		AuthDetailsVo authDetailsVo = null;
		JSONResponse jsonResponse = new JSONResponse();
		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			List<CurrencyEntity> stateMasterVoList = commonDropDownService.currencyLoad();

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(stateMasterVoList);
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
