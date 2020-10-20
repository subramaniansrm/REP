package com.srm.coreframework.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.srm.coreframework.response.JSONResponse;


@Component
public class HttpAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		ObjectMapper mapper = new ObjectMapper();
		JSONResponse jsonResponse = new JSONResponse();
		jsonResponse.setResponseCode(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
		jsonResponse.setResponseMessage("Invalid token");
		response.getOutputStream().println(mapper.writeValueAsString(jsonResponse));
	}
}