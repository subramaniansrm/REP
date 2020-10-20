package com.srm.coreframework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.srm.coreframework.entity.UserEntity;
import com.srm.coreframework.repository.UserRepository;
import com.srm.coreframework.response.CustomUserDetails;


@Service
@Transactional(readOnly = true)
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService  { 

	@Autowired
	private UserRepository userRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByuserName(username);

		if (user == null) {
			throw new UsernameNotFoundException(String.format("The username %s doesn't exist", username));
		}

		
		CustomUserDetails customUserDetails = new CustomUserDetails(user.getUserName());
		customUserDetails.setAuthentication(true);
		customUserDetails.setPassword(user.getPassword());
		if(user.getUserName()!=null) {
			customUserDetails.setUserId(user.getId());
		}
		return customUserDetails;
	}


}