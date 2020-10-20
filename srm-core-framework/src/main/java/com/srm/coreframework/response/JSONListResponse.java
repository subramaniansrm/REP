package com.srm.coreframework.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
@JsonInclude(Include.NON_EMPTY)
@Data
public class JSONListResponse<T> {

	private int status;
	private Boolean isUserLocked;
	private String errorMessage = null;
	private List<?> result = new ArrayList<>();

	public JSONListResponse(List<?> result, int status) {
		this.result = result;
		this.status = status;
	}


}
