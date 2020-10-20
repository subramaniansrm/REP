package com.srm.coreframework.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.dao.UserEntityMappingDao;
import com.srm.coreframework.entity.EntityLicense;
import com.srm.coreframework.entity.UserEntityMapping;
import com.srm.coreframework.repository.UserEntityMappingRepository;
import com.srm.coreframework.repository.UserRepository;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.EntityLicenseVO;
import com.srm.coreframework.vo.UserEntityMappingVo;

@Service
public class UserEntityMappingService extends CommonService{
	
	private static final Logger logger = LogManager.getLogger(UserEntityMappingService.class);

	@Autowired
	UserEntityMappingDao userEntityMappingDao;

	@Autowired
	UserEntityMappingRepository userEntityMappingRepository;
	
	@Autowired
	UserRepository userRepository;
	
	/**
	 * Method is used to Load the State List
	 * 
	 * @return stateMasterVoList List<StateMasterVo>
	 */
	@Transactional
	public List<EntityLicenseVO> getEntity(AuthDetailsVo authDetailsVo) {

		try {

			List<EntityLicenseVO> entityLicenseList = new ArrayList<EntityLicenseVO>();

			//Load All Entity
			List<EntityLicense> list = userEntityMappingDao.getEntity(authDetailsVo);
			
			//Load User Based Entity
			List<Integer> userEntity = userEntityMappingRepository.getUserEntity(authDetailsVo.getUserId());
			
			List<Integer>  id = userEntityMappingRepository.getDefaultSingleUserEntity(authDetailsVo.getUserId());
			
			EntityLicenseVO entityLicenseVO = null;
			for (EntityLicense entityLicense : list) {
				
				entityLicenseVO = new EntityLicenseVO();
				entityLicenseVO.setId(entityLicense.getId());
				entityLicenseVO.setEntityName(entityLicense.getEntityName());
				
				if (null != userEntity && userEntity.size() > 0) {
					if (null != entityLicenseVO.getId() && userEntity.contains(entityLicenseVO.getId())) {
						entityLicenseVO.setResult(true);
						if(id.contains(entityLicenseVO.getId())){
							entityLicenseVO.setDefaultValue(CommonConstant.FLAG_ONE);
						}
					} else {
						entityLicenseVO.setResult(false);

					}
				}
				
				if(null == entityLicenseVO.getDefaultValue()){
					entityLicenseVO.setDefaultValue(CommonConstant.FLAG_ZERO);
				}
				entityLicenseList.add(entityLicenseVO);
				
			}
			return entityLicenseList;
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
	}
	
	@Transactional
	public void saveEntity(UserEntityMappingVo userEntityMappingVo, AuthDetailsVo authDetailsVo) {

		if (null != userEntityMappingVo.getEntityLicenseVOList()
				&& !userEntityMappingVo.getEntityLicenseVOList().isEmpty()
				&& userEntityMappingVo.getEntityLicenseVOList().size() > 0) {
			
			//delete Existing
			deleteUserEntity(userEntityMappingVo.getUserId());
			UserEntityMapping userEntityMapping = null;
			for (EntityLicenseVO licenseVO : userEntityMappingVo.getEntityLicenseVOList()) {
				userEntityMapping = new UserEntityMapping();
				userEntityMapping.setUserId(licenseVO.getUserId());
				userEntityMapping.setEntityId(licenseVO.getEntityId());
				if (null != licenseVO.getDefaultValue()
						&& licenseVO.getDefaultValue().equals(CommonConstant.FLAG_ONE)) {
					userEntityMapping.setDefaultId(CommonConstant.CONSTANT_ONE);
					updateDefaultEntity(licenseVO);
				} else {
					userEntityMapping.setDefaultId(CommonConstant.CONSTANT_ZERO);
				}

				
				userEntityMappingRepository.save(userEntityMapping);
			}
		}

	}
	@Transactional
	public void updateDefaultEntity(EntityLicenseVO licenseVO ){
		
		userEntityMappingDao.updateUserEntity(licenseVO.getUserId(),licenseVO.getEntityId());
	}
	@Transactional
	public void deleteUserEntity(Integer userId) {

		List<UserEntityMapping> list = userEntityMappingDao.getUsersEntity(userId);
		for (UserEntityMapping userEntityMapping : list) {
			if (null != userEntityMapping.getCommonId()) {
				userEntityMappingRepository.delete(userEntityMapping.getCommonId());
			}

		}
	}
}
