package com.srm.coreframework.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srm.coreframework.dao.UserEntityMappingDao;
import com.srm.coreframework.entity.UserEntity;
import com.srm.coreframework.entity.UserEntityMapping;
import com.srm.coreframework.repository.UserEntityMappingRepository;
import com.srm.coreframework.repository.UserRepository;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.UserMasterVO;

@Service
public class LogOutService {

	Logger logger = LoggerFactory.getLogger(LogOutService.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserEntityMappingDao userEntityMappingDao;
	
	@Autowired
	UserEntityMappingRepository userEntityMappingRepository;
	
	@Transactional
	public void logout(UserMasterVO userMasterVO) {
		UserEntity user = null;
		try{
		 user = userRepository.findOne(userMasterVO.getId());
		 if(user!=null){
				user.setAccessToken(null);
			}
			userRepository.save(user);
		}catch(Exception e){
			e.getMessage();
			e.printStackTrace();
		}		
	}
	@Transactional
	public void updateSuperAdminUserEntity(UserMasterVO userMasterVO) {
		UserEntity user = null;
		 user = userRepository.findOne(userMasterVO.getId());
		if(user != null ){
			
			if(user.getUserRoleEntity().getId() == 1){
				//userEntityMappingDao.updateSuperAdminUserEntity(userMasterVO.getId());
				UserEntityMapping userEntityMapping = userEntityMappingRepository
						.getDefaultUserEntity(userMasterVO.getId());

						
				if (null != userEntityMapping) {

					userEntityMappingDao.updateUserEntity1(userMasterVO.getId(), userEntityMapping.getEntityId());
					
					
				}
				
			}
			
		}			
	}
	
	@Transactional
	public void updateUserEntity(AuthDetailsVo authDetailsVo) {

		UserEntityMapping userEntityMapping = userEntityMappingRepository
				.getDefaultUserEntity(authDetailsVo.getUserId());

		UserEntity user = userRepository.findOne(authDetailsVo.getUserId());
				
		if (null != userEntityMapping && null != user.getDefaultRole().getId()) {

			userEntityMappingDao.updateUserEntityWithRole(authDetailsVo.getUserId(), userEntityMapping.getEntityId() , user.getDefaultRole().getId());
		}

	}
}
