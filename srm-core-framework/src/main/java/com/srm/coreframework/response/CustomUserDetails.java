package com.srm.coreframework.response;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	Collection<? extends GrantedAuthority> list = null;
	String userLoginId = null;
	String password = null;
	String firstName = null;
	String lastName = null;
	String mobileNumber = null;
	/**
	 * @return the langCode
	 */
	public String getLangCode() {
		return langCode;
	}

	/**
	 * @param langCode the langCode to set
	 */
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	String email=null;
	String langCode ;
	int userId ;
	Boolean superAdmin;
	int entityId;
	int roleId;
	
	
	/**
	 * @return the entityId
	 */
	public int getEntityId() {
		return entityId;
	}

	/**
	 * @param entityId the entityId to set
	 */
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	/**
	 * @return the superAdmin
	 */
	public Boolean getSuperAdmin() {
		return superAdmin;
	}

	/**
	 * @param superAdmin the superAdmin to set
	 */
	public void setSuperAdmin(Boolean superAdmin) {
		this.superAdmin = superAdmin;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	boolean status = false;

	public CustomUserDetails() {
		list = new ArrayList<GrantedAuthority>();
	}
	
	public CustomUserDetails(String userLoginId)
	{
		this.userLoginId = userLoginId;
	}

	public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.list;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> roles) {
		this.list = roles;
	}

	public void setAuthentication(boolean status) {
		this.status = status;
	}
	

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String pass) {
		this.password = pass;
	}

	

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * @return the list
	 */
	public Collection<? extends GrantedAuthority> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(Collection<? extends GrantedAuthority> list) {
		this.list = list;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String getUsername() {
		return this.userLoginId;
	}

	/**
	 * @return the roleId
	 */
	public int getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}


}
