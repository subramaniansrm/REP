package com.srm.coreframework.auth;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.srm.coreframework.response.CustomUserDetails;
import com.srm.coreframework.security.OAuth2AuthenticationUser;

import lombok.Data;

@Data
public class AuthUserInfo {

	private Integer id;
	private String firstName;
	private String lastName;
	private Integer entityId;
	private Integer roleId;
	private String langCode ;
	
	CustomUserDetails customUserDetails=new CustomUserDetails();
	public AuthUserInfo() {
		/*CustomUserDetails customUserDetails=getUserDetails();
		if(customUserDetails!=null) {
			this.roleId=customUserDetails.getRoleId();
			this.entityId=customUserDetails.getEntityId();
			this.id=customUserDetails.getUserId();
			this.firstName=customUserDetails.getFirstName();
			this.lastName=customUserDetails.getLastName();
		}*/
	}

	
	
	public static CustomUserDetails getUserDetails() {
		CustomUserDetails customUserDetails=null;
		if ((!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken))) {
			OAuth2AuthenticationUser authentication = (OAuth2AuthenticationUser) SecurityContextHolder.getContext()
					.getAuthentication();
			if (authentication != null) {
				 customUserDetails = authentication.getCustomUserDetails();
				
			}
	}
		return customUserDetails;
	}
	

}
