package com.srm.coreframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.srm.coreframework.exception.CoreException;
import com.srm.coreframework.exception.DAOException;
import com.srm.coreframework.exception.ErrorMessageCode;
import com.srm.coreframework.exception.NoRecordFoundException;
import com.srm.coreframework.exception.ValidationErrorResponse;
import com.srm.coreframework.i18n.CoreMessageSource;

@Component
@ControllerAdvice
public class ExceptionControllerAdvice {

	@Autowired
	private CoreMessageSource messageSource;
	
	private static final Logger LOG = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

	@ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponse> exceptionHttpClientErrorException(HttpClientErrorException ex) {
           LOG.debug("An exception occurred with message: {}", ex);  
           LOG.error("An exception occurred with message: {}", ex);  
           ErrorResponse errorResponse = new ErrorResponse();
           errorResponse.setCode(String.valueOf(ex.getStatusCode()));
           errorResponse.setMessage(ex.getStatusText());
           return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorResponse> exceptionUsernameNotFoundHandler(UsernameNotFoundException ex) {
		LOG.debug("An exception occurred with message: {}", ex);  
		LOG.error("An exception occurred with message: {}", ex);  
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCode(ErrorMessageCode.USER_NOT_FOUND);
		errorResponse.setMessage(
				messageSource.getMessage(ErrorMessageCode.USER_NOT_FOUND));
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> exceptionHttpMessageNotReadableHandler(HttpMessageNotReadableException ex) {
		LOG.debug("An exception occurred with message: {}", ex);  
		LOG.error("An exception occurred with message: {}", ex);  
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCode(ErrorMessageCode.INVALID_REQUEST);
		errorResponse.setMessage(
				messageSource.getMessage(ErrorMessageCode.INVALID_REQUEST));
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorResponse> exceptionNoHandlerFoundHandler(NoHandlerFoundException ex) {
		LOG.debug("An exception occurred with message: {}", ex);  
		LOG.error("An exception occurred with message: {}", ex);  
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCode(ErrorMessageCode.INVALID_404);
		errorResponse.setMessage(
				messageSource.getMessage(ErrorMessageCode.INVALID_404));
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> exceptionAccessDeniedHandler(AccessDeniedException ex) {
		LOG.debug(ex.getMessage());
		LOG.error(ex.getMessage());
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCode(ErrorMessageCode.INVALID_403);
		errorResponse.setMessage(
				messageSource.getMessage(ErrorMessageCode.INVALID_403));
		return new ResponseEntity<>(errorResponse, HttpStatus.OK);
	}

	@ExceptionHandler(OAuth2Exception.class)
	public ResponseEntity<ErrorResponse> exceptionOAuth2Handler(OAuth2Exception ex) {
		LOG.debug("An exception occurred with message: {}", ex);  
		LOG.error("An exception occurred with message: {}", ex);  
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCode(ErrorMessageCode.INVALID_405);
		errorResponse.setMessage(
				messageSource.getMessage(ErrorMessageCode.INVALID_405));
		return new ResponseEntity<>(errorResponse, HttpStatus.OK);
	}

	@ExceptionHandler(NoRecordFoundException.class)
	public ResponseEntity<ErrorResponse> exceptionNoRecordHandler(NoRecordFoundException ex) {
		LOG.debug("An exception occurred with message: {}", ex);  
		LOG.error("An exception occurred with message: {}", ex);  
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCode(ex.getCode());
		errorResponse.setMessage(messageSource.getMessage(ex.getCode()));
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CoreException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(CoreException ex) {
		LOG.debug("An exception occurred with message: {}", ex);  
		LOG.error("An exception occurred with message: {}", ex);  
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCode(ex.getCode());
		if(ex.getData()!=null) {
			errorResponse.setMessage(ex.getData() +" " + messageSource.getMessage(ex.getCode()));
		}else {
			errorResponse.setMessage(messageSource.getMessage(ex.getCode()));
		}
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(DAOException.class)
	public ResponseEntity<ErrorResponse> exceptionDAOHandler(DAOException ex) {
		LOG.debug("An exception occurred with message: {}", ex);  
		LOG.error("An exception occurred with message: {}", ex);  
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCode(ex.getCode());
		errorResponse.setMessage(messageSource.getMessage(ex.getCode()));
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
		LOG.debug("An exception occurred with message: {}", ex);  
		LOG.error("An exception occurred with message: {}", ex);  
			ErrorResponse errorResponse = new ErrorResponse();
			if(ex.getMessage()!=null && ex.getMessage().contains("SQL Exception")) {
				errorResponse.setCode(ErrorMessageCode.DATA_ACCESS_PROBLEM);
				errorResponse.setMessage(messageSource.getMessage(ErrorMessageCode.DATA_ACCESS_PROBLEM));
			}else {
				errorResponse.setCode(ErrorMessageCode.INTERNAL_SERVER_ERROR);
				errorResponse.setMessage(messageSource.getMessage(ErrorMessageCode.INTERNAL_SERVER_ERROR));
			}
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorResponse> validationException(MethodArgumentNotValidException ex) {
		LOG.debug("An exception occurred with message: {}", ex);  
		LOG.error("An exception occurred with message: {}", ex);  
		ValidationErrorResponse errorResponse = new ValidationErrorResponse();
		FieldError fieldError = (FieldError) ex.getBindingResult().getAllErrors().get(0);
		errorResponse.setRejectedValue(fieldError.getRejectedValue().toString());
		errorResponse.setFieldName(fieldError.getField());
		errorResponse.setMessage(fieldError.getDefaultMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}