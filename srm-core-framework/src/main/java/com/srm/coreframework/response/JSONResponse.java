package com.srm.coreframework.response;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(Include.NON_NULL)
@Data
public class JSONResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8734821040215766522L;
	private String responseCode;
	private String responseMessage;
	private Object authSuccesObject;
	private Object succesObject;

	
}
