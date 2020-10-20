package com.srm.coreframework.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.srm.coreframework.entity.UserEntity;
import com.srm.coreframework.response.CustomUserDetails;
import com.srm.coreframework.security.OAuth2AuthenticationUser;
import com.srm.coreframework.service.CommonService;

public class AuthUtil {
	
	/*@Autowired
	private  CommonService commonService;
	
	public static AuthUserInfo getAuthUserInfo() {
		return TokenManager.getAuthUserInfo
		
	*//**
	 * Static Method used to get user details.
	 * 
	 * @
	 * return AuthUserInfo
	 *//*
	public   AuthUserInfo getAuthUserInfo(String accessToken) {
		
		UserEntity userEntity= commonService.getAcessTokenUser(accessToken);
		AuthUserInfo authUserInfo=null;
		CustomUserDetails customUserDetails=new CustomUserDetails();
		
		if(null!=userEntity){
			authUserInfo= new AuthUserInfo();
			
			if(null!=userEntity.getId()){
				//authUserInfo.setId(userEntity.getId());
				customUserDetails.setUserId(userEntity.getId());
			}
			if(null!=userEntity.getFirstName()){
				//authUserInfo.setFirstName(userEntity.getFirstName());
				customUserDetails.setFirstName(userEntity.getFirstName());
			}
			if(null!=userEntity.getLastName()){
				//authUserInfo.setLastName(userEntity.getLastName());
				customUserDetails.setLastName(userEntity.getLastName());
			}
			if(null!=userEntity.getEntityLicenseEntity().getId()){
				//authUserInfo.setEntityId(userEntity.getEntityLicenseEntity().getId());
				customUserDetails.setEntityId(userEntity.getEntityLicenseEntity().getId());
			}
			if(null!=userEntity.getUserRoleEntity().getId()){
				//authUserInfo.setRoleId(userEntity.getUserRoleEntity().getId());
				customUserDetails.setRoleId(userEntity.getUserRoleEntity().getId());
			}
			if(null!=userEntity.getLangCode()){
				//authUserInfo.setLangCode(userEntity.getLangCode());
				customUserDetails.setLangCode(userEntity.getLangCode());
			}
			authUserInfo.setCustomUserDetails(customUserDetails);
		}
		return authUserInfo;
	}

	*//**
	 * Static Method used to get the user Id.
	 * 
	 * @return Integer
	 *//*
	public  Integer getUserId(String accessToken) {
		AuthUserInfo authUserInfo = getAuthUserInfo(accessToken);
		if (authUserInfo != null) {
			return authUserInfo.getCustomUserDetails().getUserId();
		}else {
			if ((!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken))) {
				OAuth2AuthenticationUser authentication = (OAuth2AuthenticationUser) SecurityContextHolder.getContext()
						.getAuthentication();
				if (authentication != null) {
					CustomUserDetails  customUserDetails = authentication.getCustomUserDetails();
					if(customUserDetails!=null){
						return customUserDetails.getUserId();
					}
					
				}
		}
		}
		return null;
	}
	
	public  String getLangCode(String accessToken) {
		AuthUserInfo authUserInfo = getAuthUserInfo(accessToken);
		if (authUserInfo != null) {
			return authUserInfo.getCustomUserDetails().getLangCode();
		}else {
			if ((!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken))) {
				OAuth2AuthenticationUser authentication = (OAuth2AuthenticationUser) SecurityContextHolder.getContext()
						.getAuthentication();
				if (authentication != null) {
					CustomUserDetails  customUserDetails = authentication.getCustomUserDetails();
					if(customUserDetails!=null){
						return customUserDetails.getLangCode();
					}
					
				}
		}
		}
		return null;
	}

	*//**
	 * Static Method used to get the entity Id.
	 * 
	 * @return Integer
	 *//*
	public  Integer getEntityId(String accessToken) {
		AuthUserInfo authUserInfo = getAuthUserInfo(accessToken);
		if (authUserInfo != null) {
			return authUserInfo.getCustomUserDetails().getEntityId();
		}
		else {if ((!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken))) {
			OAuth2AuthenticationUser authentication = (OAuth2AuthenticationUser) SecurityContextHolder.getContext()
					.getAuthentication();
			if (authentication != null) {
				CustomUserDetails  customUserDetails = authentication.getCustomUserDetails();
				if(customUserDetails!=null){
					return customUserDetails.getEntityId();
				}
				
			}
	}
	}
		return null; 
		}
	
	
	
	*//**
	 * Static Method used to get the entity Id.
	 * 
	 * @return Integer
	 *//*
	public  Integer getRoleId(String accessToken) {
		AuthUserInfo authUserInfo = getAuthUserInfo(accessToken);
		if (authUserInfo != null) {
			return authUserInfo.getCustomUserDetails().getRoleId();
		}else {
			if ((!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken))) {
				OAuth2AuthenticationUser authentication = (OAuth2AuthenticationUser) SecurityContextHolder.getContext()
						.getAuthentication();
				if (authentication != null) {
					CustomUserDetails  customUserDetails = authentication.getCustomUserDetails();
					if(customUserDetails!=null){
						return customUserDetails.getRoleId();
					}
					
				}
		}
		}
		return null; 
		}
	
	
	
	*//**
	 * Static Method used to get the user name.
	 * 
	 * @return String 
	 *//*
	public String getUserName(String accessToken) {
		AuthUserInfo authUserInfo = getAuthUserInfo(accessToken);
		if (authUserInfo != null) {
			return authUserInfo.getCustomUserDetails().getFirstName() + " "
					+ authUserInfo.getCustomUserDetails().getLastName();
		} else {
			if ((!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken))) {
				OAuth2AuthenticationUser authentication = (OAuth2AuthenticationUser) SecurityContextHolder.getContext()
						.getAuthentication();
				if (authentication != null) {
					CustomUserDetails customUserDetails = authentication.getCustomUserDetails();
					if (customUserDetails != null) {
						return customUserDetails.getUsername();
					}

				}
			}
		}
		return null;
	}*/
	
	}

