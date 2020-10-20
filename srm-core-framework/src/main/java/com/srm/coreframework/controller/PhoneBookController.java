package com.srm.coreframework.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.srm.coreframework.constants.ControlNameConstants;
import com.srm.coreframework.constants.FilePathConstants;
import com.srm.coreframework.exception.CoreException;
import com.srm.coreframework.response.JSONResponse;
import com.srm.coreframework.service.PhoneBookService;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.util.ValidationUtil;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.CommonVO;
import com.srm.coreframework.vo.PhoneBookVO;
import com.srm.coreframework.vo.ScreenJsonVO;

@RestController
public class PhoneBookController extends CommonController<PhoneBookVO> {

	@Autowired
	PhoneBookService phoneBookService;

	@Autowired
	private MessageSource messageSource;

	private static final Logger logger = LogManager.getLogger(PhoneBookController.class);

	@PostMapping(FilePathConstants.PHONE_BOOKING_LOAD)
	@ResponseBody
	public ResponseEntity<JSONResponse> getAll(HttpServletRequest request, @RequestBody ScreenJsonVO screenJson) {

		JSONResponse jsonResponse = new JSONResponse();

		String accessToken = getHeaderAccessToken(request);

		AuthDetailsVo authDetailsVo = null;
		try {

			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}

			CommonVO commonVO = phoneBookService.getScreenFields(screenJson, authDetailsVo);
			List<PhoneBookVO> phoneBookVoList = new ArrayList<PhoneBookVO>();
			if (commonVO != null) {
				phoneBookVoList = phoneBookService.getAll(authDetailsVo);
			}
			logger.info(getMessage("processValidation"));

			logger.info(getMessage("processValidationCompleted"));

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(phoneBookVoList);
			jsonResponse.setAuthSuccesObject(commonVO);
			jsonResponse.setResponseMessage(getMessage("successMessage"));
			logger.info(getMessage("successMessage"));
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
			jsonResponse.setResponseMessage(getMessage("errorMessage"));
			jsonResponse.setSuccesObject(CommonConstant.NULL);

		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.PHONEBOOK_LOADADD)
	@ResponseBody
	public ResponseEntity<JSONResponse> addphonebook(HttpServletRequest request, @RequestBody PhoneBookVO phoneBookVo)
			throws CoreException, CommonException, IllegalAccessException, InvocationTargetException {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = getHeaderAccessToken(request);

		AuthDetailsVo authDetailsVo = null;

		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}

			CommonVO commonVO = phoneBookService.getScreenFields(phoneBookVo.getScreenJson(), authDetailsVo);
			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setAuthSuccesObject(commonVO);
			jsonResponse.setResponseMessage(getMessage("successMessage"));
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
			jsonResponse.setResponseMessage(getMessage("errorMessage"));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@RequestMapping(value = FilePathConstants.PHONEBOOK_PROFILECREATE, method = RequestMethod.POST, consumes = {
			"multipart/form-data" })
	@ResponseBody
	public ResponseEntity<JSONResponse> saveProfile(HttpServletRequest request,
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

			PhoneBookVO phoneBookVo = mapper.readValue(str, PhoneBookVO.class);

			// Method used to validate
			saveValidate(phoneBookVo, uploadingFiles);

			phoneBookService.saveAttachment(phoneBookVo, uploadingFiles,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("saveSuccessMessage"));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
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
			jsonResponse.setResponseMessage(getMessage("saveErroMessage"));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@RequestMapping(value = FilePathConstants.PHONEBOOK_PROFILEUPDATE, method = RequestMethod.POST, consumes = {
			"multipart/form-data" })
	@ResponseBody
	public ResponseEntity<JSONResponse> updateAttachment(HttpServletRequest request,
			@RequestParam("file") MultipartFile[] uploadingFiles, @RequestParam("action") String str) {

		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = getHeaderAccessToken(request);

		AuthDetailsVo authDetailsVo = null;

		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
			PhoneBookVO phoneBookVo = new PhoneBookVO();
			ObjectMapper mapper = new ObjectMapper();
			phoneBookVo = mapper.readValue(str, PhoneBookVO.class);

			for (MultipartFile uploadedFile : uploadingFiles) {
				// File size Validation
				fileSizeValidation(uploadedFile);

			}
			
			// Method used to validate
			saveValidate(phoneBookVo, uploadingFiles);
			phoneBookService.updateAttachment(phoneBookVo, uploadingFiles,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("updateSuccessMessage"));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
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
			e.printStackTrace();
			logger.error(e.getMessage());
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("updateErrorMessage"));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.PHONE_BOOKING_VIEW)
	@ResponseBody
	public ResponseEntity<JSONResponse> load(HttpServletRequest request, @RequestBody PhoneBookVO phoneBookVo) {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = getHeaderAccessToken(request);
		AuthDetailsVo authDetailsVo = null;
		try {

			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}

			CommonVO commonVO = phoneBookService.getScreenFields(phoneBookVo.getScreenJson(), authDetailsVo);

			PhoneBookVO phoneBook = new PhoneBookVO();

			phoneBook = phoneBookService.load(phoneBookVo, authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(phoneBook);
			jsonResponse.setAuthSuccesObject(commonVO);
			jsonResponse.setResponseMessage(getMessage("successMessage"));
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
			e.printStackTrace();
			jsonResponse.setResponseCode(CommonConstant.ERROR_CODE);
			jsonResponse.setResponseMessage(getMessage("saveErroMessage"));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.PHONE_BOOKING_SEARCH)
	@ResponseBody
	public ResponseEntity<JSONResponse> getAllSearch(HttpServletRequest request, @RequestBody PhoneBookVO phoneBookVo) {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = getHeaderAccessToken(request);

		AuthDetailsVo authDetailsVo = null;
		try {

			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}

			List<PhoneBookVO> PhoneBookList = phoneBookService.getAllSearch(phoneBookVo, authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setSuccesObject(PhoneBookList);
			jsonResponse.setResponseMessage(getMessage("successMessage"));
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
			jsonResponse.setResponseMessage(getMessage("saveErroMessage"));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	@PostMapping(FilePathConstants.PHONEBOOK_DELETE)
	@ResponseBody
	public ResponseEntity<JSONResponse> deletePhoneBook(HttpServletRequest request,
			@RequestBody PhoneBookVO phoneBookVo) {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = getHeaderAccessToken(request);

		AuthDetailsVo authDetailsVo = null;

		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}

			phoneBookService.delete(phoneBookVo,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("deleteSuccessMessage"));
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
			jsonResponse.setResponseMessage(getMessage("deleteErrorMessage"));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	/**
	 * Method used to download External Link Logo
	 * 
	 * @param request
	 * @param response
	 * @param externalLinkVo
	 * @return
	 */
	@PostMapping(FilePathConstants.PHONEBOOK_PROFILEDOWNLOAD)
	@ResponseBody
	public ResponseEntity<Resource> getDownload(HttpServletRequest request, HttpServletResponse response,
			@RequestBody PhoneBookVO phoneBookVo) {
		
		String accessToken = getHeaderAccessToken(request);

		AuthDetailsVo authDetailsVo = null;

		
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}
		phoneBookVo = phoneBookService.attachmentDownload(phoneBookVo,authDetailsVo);
		File file = new File(phoneBookVo.getPhoneBookProfile());
		try {
			if (file.exists()) {
				InputStreamResource fileInputStream = new InputStreamResource(new FileInputStream(file));
				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename =\"" + file + "\"")
						.contentLength(file.length()).contentType(getMediaType(file.getName())).body(fileInputStream);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Method used to get Media Type of files
	 * 
	 * @param filename
	 * @return
	 */
	public MediaType getMediaType(String filename) {

		String arr[] = filename.split("\\.");
		String type = arr[arr.length - CommonConstant.CONSTANT_ONE];
		switch (type.toLowerCase()) {
		case "txt":
			return MediaType.TEXT_PLAIN;
		case "png":
			return MediaType.IMAGE_PNG;
		case "jpg":
			return MediaType.IMAGE_JPEG;
		default:
			return MediaType.APPLICATION_OCTET_STREAM;
		}

	}

	/**
	 * Method is used to get all search for first letter
	 * 
	 * @param phoneBookVo
	 * @return response
	 */
	@PostMapping(FilePathConstants.PHONE_BOOKING_FIRST_SEARCH)
	@ResponseBody
	public ResponseEntity<JSONResponse> getAllFirstSearch(HttpServletRequest request,
			@RequestBody PhoneBookVO phoneBookVo) {
		JSONResponse jsonResponse = new JSONResponse();
		String accessToken = getHeaderAccessToken(request);

		AuthDetailsVo authDetailsVo = null;

		try {
			authDetailsVo = tokenValidate(accessToken);
			if (null == authDetailsVo) {
				throw new LoginAuthException("Token Expired / Already Logined");
			}

			List<PhoneBookVO> PhoneBookList = phoneBookService.getAllFirstSearch(phoneBookVo,authDetailsVo);

			jsonResponse.setResponseCode(CommonConstant.SUCCESS_CODE);
			jsonResponse.setResponseMessage(getMessage("successMessage"));
			jsonResponse.setSuccesObject(PhoneBookList);
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
			jsonResponse.setResponseMessage(getMessage("saveErroMessage"));
			jsonResponse.setSuccesObject(CommonConstant.NULL);
		}
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}

	/**
	 * Method used to validate Phone Book Details
	 * 
	 * @param phoneBookVo
	 * @param uploadingFiles
	 */
	private void saveValidate(PhoneBookVO phoneBookVo, MultipartFile[] uploadingFiles) {

		for (String field : phoneBookVo.getScreenFieldDisplayVoList()) {

			if (field.equals(ControlNameConstants.PHONE_BOOK_EMPLOYEE_ID)) {

				if (ValidationUtil.isNullOrBlank(phoneBookVo.getEmployeeId().trim())) {
					throw new CommonException(getMessage("phonebook_empId_req"));

				}
				if (phoneBookVo.getEmployeeId().length() > 10) {
					throw new CommonException(getMessage("phonebook_empId_val"));

				}

			}

			if (field.equals(ControlNameConstants.PHONE_BOOK_EMPLOYEE_NAME)) {
				if (ValidationUtil.isNullOrBlank(phoneBookVo.getEmployeeName().trim())) {
					throw new CommonException(getMessage(getMessage("phonebook_empName_req")));

				}

				if (ValidationUtil.onlyAlphabets(phoneBookVo.getEmployeeName())) {
					throw new CommonException(getMessage("name.alphabets"));

				}

				if (phoneBookVo.getEmployeeName().length() > 50) {
					throw new CommonException(getMessage("phonebook_empName_val"));

				}

			}

			if (field.equals(ControlNameConstants.PHONE_BOOK_DEPARTMENT)) {

				if (ValidationUtil.isNullOrBlank(phoneBookVo.getUserDepartmentId())) {
					throw new CommonException(getMessage("phonebook_dep_val"));

				}
			}
			if (field.equals(ControlNameConstants.PHONE_BOOK_LOCATION)) {
				if (ValidationUtil.isNullOrBlank(phoneBookVo.getUserLocationId())) {
					throw new CommonException(getMessage("phonebook_loc_val"));

				}
			}
			if (field.equals(ControlNameConstants.PHONE_BOOK_SUBLOCATION)) {
				if (ValidationUtil.isNullOrBlank(phoneBookVo.getSublocationId())) {
					throw new CommonException(getMessage("phonebook_subloc_val"));

				}
			}
			if (field.equals(ControlNameConstants.PHONE_BOOK_EMAIL_ID)) {
				if (ValidationUtil.isNullOrBlank(phoneBookVo.getEmailId())) {
					throw new CommonException(getMessage("phonebook_mail_req"));

				}
				if (ValidationUtil.isEmail(phoneBookVo.getEmailId())) {
					throw new CommonException(getMessage("phonebook_mail_val"));

				}

			}
		/*	if (field.equals(ControlNameConstants.PHONE_BOOK_EXTENSION_NUMBER)) {
				if (ValidationUtil.isNullOrBlank(phoneBookVo.getExtensionNumber())) {
					throw new CommonException(getMessage("phonebook_ext_req"));

				}

			}
			if (field.equals(ControlNameConstants.PHONE_BOOK_PHONE_NUMBER)) {

				if (ValidationUtil.isNullOrBlank(phoneBookVo.getPhoneNumber())) {
					throw new CommonException(getMessage("phonebook_ext_req"));

				}

			}
			if (field.equals(ControlNameConstants.PHONE_BOOK_MOBILE_NUMBER_P)) {

				if (ValidationUtil.isNullOrBlank(phoneBookVo.getMobileNumberP())) {
					throw new CommonException(getMessage("phonebook_per_req"));

				}

			}
			if (field.equals(ControlNameConstants.PHONE_BOOK_MOBILE_NUMBER_C)) {
				if (ValidationUtil.isNullOrBlank(phoneBookVo.getMobileNumberC())) {
					throw new CommonException(getMessage("phonebook_com_req"));

				}

			}
			if (field.equals(ControlNameConstants.PHONE_BOOK_SKYPE_ID)) {

				if (ValidationUtil.isNullOrBlank(phoneBookVo.getSkypeId())) {
					throw new CommonException(getMessage("phonebook_skype_req"));

				}

				if (phoneBookVo.getSkypeId().length() > 50) {
					throw new CommonException(getMessage("phonebook_skype_val"));

				}

			}*/

			if (field.equals(ControlNameConstants.PHONE_BOOK_PROFILE)) {
				// Image Validation

				if (ValidationUtil.isImage(uploadingFiles)) {
					throw new CommonException(getMessage("image_val"));

				}
			}

		}

	}

	protected String getMessage(String code) {
		return getMessage(code, new Object[] {});
	}

	protected String getMessage(String code, Object args[]) {
		return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
	}
}
