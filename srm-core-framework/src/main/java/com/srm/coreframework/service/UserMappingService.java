package com.srm.coreframework.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.srm.coreframework.auth.AuthUtil;
import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.controller.CommonController;
import com.srm.coreframework.dao.UserMappingDAO;
import com.srm.coreframework.entity.CommonStorageEntity;
import com.srm.coreframework.entity.EntityLicense;
import com.srm.coreframework.entity.SubLocation;
import com.srm.coreframework.entity.UserDepartment;
import com.srm.coreframework.entity.UserEntity;
import com.srm.coreframework.entity.UserLocation;
import com.srm.coreframework.entity.UserMappingEntity;
import com.srm.coreframework.entity.UserRole;
import com.srm.coreframework.repository.UserMappingRepository;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.UserMappingVO;

@Component
public class UserMappingService extends CommonController<UserMappingVO> {

	Logger logger = LoggerFactory.getLogger(UserMappingService.class);

	@Autowired
	private UserMappingRepository userMappingRepository;

	@Autowired
	private UserMappingDAO userMappingDAO;

	

	

	/**
	 * This Method is to get all the details.
	 * 
	 * @return userMappingVoList List<userMappingVo>
	 *
	 */

	@Transactional
	public List<UserMappingVO> getAll(AuthDetailsVo authDetailsVo) {
		try {
			// Integer entityId = AuthUtil.getEntityId();
			List<UserMappingVO> userMappingVoList = new ArrayList<UserMappingVO>();
			List<Object[]> userMappingEntityList = null;

			userMappingEntityList = userMappingDAO.getAll(authDetailsVo);

			if (userMappingEntityList != null && !userMappingEntityList.isEmpty()) {
				userMappingVoList = getAllList(userMappingEntityList);
			}
			return userMappingVoList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
	}

	/**
	 * method is to get all the detailsList.
	 * 
	 * @param userMappingEntityList
	 * @return userMappingVoList
	 * 
	 */
	public List<UserMappingVO> getAllList(List<Object[]> userMappingEntityList) {

		List<UserMappingVO> userMappingVoList = new ArrayList<UserMappingVO>();
		for (Object userMappingEntity : userMappingEntityList) {
			UserMappingVO userMappingVo = new UserMappingVO();

			if (0 != (int) ((Object[]) userMappingEntity)[0]) {
				userMappingVo.setUserMappingId((int) ((Object[]) userMappingEntity)[0]);
			}

			if (0 != (int) ((Object[]) userMappingEntity)[1]) {
				userMappingVo.setUserLocationId((int) ((Object[]) userMappingEntity)[1]);
			}

			if (null != (String) ((Object[]) userMappingEntity)[2]) {
				userMappingVo.setUserLocationName((String) ((Object[]) userMappingEntity)[2]);
			}

			if (0 != (int) ((Object[]) userMappingEntity)[3]) {

				userMappingVo.setSubLocationId((int) ((Object[]) userMappingEntity)[3]);
			}
			if (null != (String) ((Object[]) userMappingEntity)[4]) {

				userMappingVo.setSubLocationName((String) ((Object[]) userMappingEntity)[4]);
			}

			if (0 != (int) ((Object[]) userMappingEntity)[5]) {
				userMappingVo.setUserDepartmentId((int) ((Object[]) userMappingEntity)[5]);
			}

			if (null != (String) ((Object[]) userMappingEntity)[6]) {
				userMappingVo.setUserDepartmentName((String) ((Object[]) userMappingEntity)[6]);
			}

			if (0 != (int) ((Object[]) userMappingEntity)[7]) {
				userMappingVo.setUserRoleId((int) ((Object[]) userMappingEntity)[7]);
			}

			if (null != (String) ((Object[]) userMappingEntity)[8]) {
				userMappingVo.setUserRoleName((String) ((Object[]) userMappingEntity)[8]);
			}

			if (0 != (int) ((Object[]) userMappingEntity)[9]) {
				userMappingVo.setLevelId((int) ((Object[]) userMappingEntity)[9]);
			}
			if (null != (String) ((Object[]) userMappingEntity)[10]) {
				userMappingVo.setLevelName((String) ((Object[]) userMappingEntity)[10]);
			}

			if (0 != (int) ((Object[]) userMappingEntity)[11]) {
				userMappingVo.setReportingLocationId((int) ((Object[]) userMappingEntity)[11]);
			}

			if (0 != (int) ((Object[]) userMappingEntity)[12]) {
				userMappingVo.setReportingSubLocationId((int) ((Object[]) userMappingEntity)[12]);
			}

			if (0 != (int) ((Object[]) userMappingEntity)[13]) {
				userMappingVo.setReportingUserDepartment((int) ((Object[]) userMappingEntity)[13]);
			}
			if (0 != (int) ((Object[]) userMappingEntity)[14]) {
				userMappingVo.setReportingToUser((int) ((Object[]) userMappingEntity)[14]);
			}
			if (0 != (int) ((Object[]) userMappingEntity)[15]) {
				userMappingVo.setUserId((int) ((Object[]) userMappingEntity)[15]);
			}
			if (null != (String) ((Object[]) userMappingEntity)[16]) {

				userMappingVo.setUserName((String) ((Object[]) userMappingEntity)[16]);

			}
			if (null != (String) ((Object[]) userMappingEntity)[17]) {
				userMappingVo.setReportingLocationName((String) ((Object[]) userMappingEntity)[17]);
			}
			if (null != (String) ((Object[]) userMappingEntity)[18]) {
				userMappingVo.setReportingSubLocationName((String) ((Object[]) userMappingEntity)[18]);
			}
			if (null != (String) ((Object[]) userMappingEntity)[19]) {
				userMappingVo.setReportingDepartmentName((String) ((Object[]) userMappingEntity)[19]);
			}
			if (null != (String) ((Object[]) userMappingEntity)[20]) {
				userMappingVo.setReportingToUserName((String) ((Object[]) userMappingEntity)[20]);
			}

			userMappingVoList.add(userMappingVo);
		}
		return userMappingVoList;
	}

	/**
	 * Method used to Create user mapping.
	 * 
	 * @param userMappingVo
	 *            UserMappingVo
	 * 
	 */

	@Transactional
	public void create(UserMappingVO userMappingVo,AuthDetailsVo authDetailsVo) {
		try {

			UserMappingEntity userMappingEntity = new UserMappingEntity();

			UserLocation userLocationEntity = new UserLocation();
			userLocationEntity.setId(userMappingVo.getUserLocationId());
			userMappingEntity.setUserLocationEntity(userLocationEntity);

			SubLocation subLocationEntity = new SubLocation();
			subLocationEntity.setSublocationId(userMappingVo.getSubLocationId());
			subLocationEntity.setSubLocationName(userMappingVo.getSubLocationName());
			userMappingEntity.setSubLocationEntity(subLocationEntity);

			UserLocation reportingLocationEntity = new UserLocation();
			reportingLocationEntity.setId(userMappingVo.getReportingLocationId());
			reportingLocationEntity.setUserLocationName(userMappingVo.getReportingLocationName());
			userMappingEntity.setReportingLocationEntity(reportingLocationEntity);

			SubLocation reportingSubLocationEntity = new SubLocation();
			reportingSubLocationEntity.setSublocationId(userMappingVo.getReportingSubLocationId());
			reportingSubLocationEntity.setSubLocationName(userMappingVo.getReportingSubLocationName());
			userMappingEntity.setReportingSublocationEntity(reportingSubLocationEntity);

			/*
			 * SystemApplicationEntity systemApplicationEntity = new
			 * SystemApplicationEntity();
			 * systemApplicationEntity.setSysAppId(2);
			 * userMappingEntity.setSysAppEntity(systemApplicationEntity);
			 */
			UserEntity userEntity = new UserEntity();
			userEntity.setId(userMappingVo.getUserId());
			userMappingEntity.setUserEntity(userEntity);

			UserDepartment userDepartmentEntity = new UserDepartment();
			userDepartmentEntity.setId(userMappingVo.getUserDepartmentId());
			userMappingEntity.setUserDepartmentEntity(userDepartmentEntity);

			UserRole userRoleEntity = new UserRole();
			userRoleEntity.setId(userMappingVo.getUserRoleId());
			userMappingEntity.setUserRoleEntity(userRoleEntity);

			UserDepartment userDepartmentEntity1 = new UserDepartment();
			userDepartmentEntity1.setId(userMappingVo.getReportingUserDepartment());
			userMappingEntity.setReportingUserDepartmentEntity(userDepartmentEntity1);

			UserEntity userEntity1 = new UserEntity();
			userEntity1.setId(userMappingVo.getReportingToUser());
			userMappingEntity.setReportingToUser(userEntity1);

			CommonStorageEntity commonStorageEntity = new CommonStorageEntity();
			commonStorageEntity.setCommonId(userMappingVo.getLevelId());
			userMappingEntity.setLevel(commonStorageEntity);

			userMappingEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);

			userMappingEntity.setCreateDate(CommonConstant.getCalenderDate());

			userMappingEntity.setUpdateDate(CommonConstant.getCalenderDate());

			userMappingEntity.setCreateBy(authDetailsVo.getUserId());

			userMappingEntity.setUpdateBy(authDetailsVo.getUserId());

			EntityLicense entityLicenseEntity = new EntityLicense();
			entityLicenseEntity.setId(authDetailsVo.getEntityId());
			userMappingEntity.setEntityLicenseEntity(entityLicenseEntity);

			userMappingRepository.save(userMappingEntity);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}

	}

	/**
	 * Method used to update user mapping.
	 * 
	 * @param userMappingVo
	 *            UserMappingVo
	 * 
	 */

	@Transactional
	public void update(UserMappingVO userMappingVo,AuthDetailsVo authDetailsVo) {
		try {

			UserMappingEntity userMappingEntity = new UserMappingEntity();

			userMappingEntity = findId(userMappingVo.getUserMappingId(),authDetailsVo);

			userMappingEntity.setUserMappingId(userMappingVo.getUserMappingId());

			UserLocation userLocationEntity = new UserLocation();
			userLocationEntity.setId(userMappingVo.getUserLocationId());
			userMappingEntity.setUserLocationEntity(userLocationEntity);

			/*
			 * SystemApplicationEntity systemApplicationEntity = new
			 * SystemApplicationEntity();
			 * systemApplicationEntity.setSysAppId(2);
			 * userMappingEntity.setSysAppEntity(systemApplicationEntity);
			 */

			UserEntity userEntity = new UserEntity();
			userEntity.setId(userMappingVo.getUserId());
			userMappingEntity.setUserEntity(userEntity);

			UserDepartment userDepartmentEntity = new UserDepartment();
			userDepartmentEntity.setId(userMappingVo.getUserDepartmentId());
			userMappingEntity.setUserDepartmentEntity(userDepartmentEntity);

			UserRole userRoleEntity = new UserRole();
			userRoleEntity.setId(userMappingVo.getUserRoleId());
			userMappingEntity.setUserRoleEntity(userRoleEntity);

			UserDepartment userDepartmentEntity1 = new UserDepartment();
			userDepartmentEntity1.setId(userMappingVo.getReportingUserDepartment());
			userMappingEntity.setReportingUserDepartmentEntity(userDepartmentEntity1);

			SubLocation subLocationEntity = new SubLocation();
			subLocationEntity.setSublocationId(userMappingVo.getSubLocationId());
			subLocationEntity.setSubLocationName(userMappingVo.getSubLocationName());
			userMappingEntity.setSubLocationEntity(subLocationEntity);

			UserLocation reportingLocationEntity = new UserLocation();
			reportingLocationEntity.setId(userMappingVo.getReportingLocationId());
			reportingLocationEntity.setUserLocationName(userMappingVo.getReportingLocationName());
			userMappingEntity.setReportingLocationEntity(reportingLocationEntity);

			SubLocation reportingSubLocationEntity = new SubLocation();
			reportingSubLocationEntity.setSublocationId(userMappingVo.getReportingSubLocationId());
			reportingSubLocationEntity.setSubLocationName(userMappingVo.getReportingSubLocationName());
			userMappingEntity.setReportingSublocationEntity(reportingSubLocationEntity);

			UserEntity userEntity1 = new UserEntity();
			userEntity1.setId(userMappingVo.getReportingToUser());
			userMappingEntity.setReportingToUser(userEntity1);

			userMappingEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);

			CommonStorageEntity commonStorageEntity = new CommonStorageEntity();
			commonStorageEntity.setCommonId(userMappingVo.getLevelId());
			userMappingEntity.setLevel(commonStorageEntity);

			userMappingEntity.setUpdateDate(CommonConstant.getCalenderDate());

			userMappingEntity.setUpdateBy(authDetailsVo.getUserId());

			EntityLicense entityLicenseEntity = new EntityLicense();
			entityLicenseEntity.setId(authDetailsVo.getEntityId());
			userMappingEntity.setEntityLicenseEntity(entityLicenseEntity);

			userMappingRepository.save(userMappingEntity);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}

	}

	/**
	 * Method used to find id in user mapping.
	 * 
	 * @param userMappingVo
	 *            UserMappingVo
	 * 
	 */
	@Transactional
	private UserMappingEntity findId(Integer id,AuthDetailsVo authDetailsVo) {

		try {
			UserMappingEntity userMappingEntity = new UserMappingEntity() ;
			userMappingEntity = userMappingRepository.findOne(id);
			return userMappingEntity;
		} catch (NoResultException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("noResultFound",authDetailsVo));
		} catch (NonUniqueResultException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("noRecordFound",authDetailsVo));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}

	}

	/**
	 * Method used to load user mapping.
	 * 
	 * @param userMappingVo
	 *            UserMappingVo
	 * 
	 */

	@Transactional
	public UserMappingVO load(UserMappingVO userMapping,AuthDetailsVo authDetailsVo) {

		try {
			UserMappingEntity userMappingEntity = new UserMappingEntity();

			userMappingEntity = findId(userMapping.getUserMappingId(),authDetailsVo);

			UserMappingVO usermappingVo = new UserMappingVO();

			BeanUtils.copyProperties(userMapping, usermappingVo);

			if (null != userMappingEntity.getUserMappingId()) {
				usermappingVo.setUserMappingId(userMappingEntity.getUserMappingId());
			}

			if (null != userMappingEntity.getUserLocationEntity().getId()) {
				usermappingVo.setUserLocationId(userMappingEntity.getUserLocationEntity().getId());
			}

			if (null != userMappingEntity.getUserRoleEntity().getId()) {
				usermappingVo.setUserRoleId(userMappingEntity.getUserRoleEntity().getId());
			}

			if (null != userMappingEntity.getUserDepartmentEntity().getId()) {
				usermappingVo.setUserDepartmentId(userMappingEntity.getUserDepartmentEntity().getId());
			}

			if (null != userMappingEntity.getLevel()) {
				usermappingVo.setLevelId(userMappingEntity.getLevel().getCommonId());
			}
			if (null != userMappingEntity.getLevel()) {
				usermappingVo.setLevelName(userMappingEntity.getLevel().getItemValue());
			}
			if (null != userMappingEntity.getReportingUserDepartmentEntity()) {
				usermappingVo.setReportingUserDepartment(userMappingEntity.getReportingUserDepartmentEntity().getId());
			}
			if (null != userMappingEntity.getReportingUserDepartmentEntity()) {
				usermappingVo.setReportingDepartmentName(
						userMappingEntity.getReportingUserDepartmentEntity().getUserDepartmentName());
			}

			if (null != userMappingEntity.getReportingToUser()) {
				usermappingVo.setReportingToUser(userMappingEntity.getReportingToUser().getId());
			}
			if (null != userMappingEntity.getUserRoleEntity()
					&& null != userMappingEntity.getUserRoleEntity().getUserRoleName()) {
				usermappingVo.setUserRoleName(userMappingEntity.getUserRoleEntity().getUserRoleName());
			}

			if (null != userMappingEntity.getUserDepartmentEntity()
					&& null != userMappingEntity.getUserDepartmentEntity().getUserDepartmentName()) {
				usermappingVo
						.setUserDepartmentName(userMappingEntity.getUserDepartmentEntity().getUserDepartmentName());
			}

			usermappingVo.setUserLocationId(userMappingEntity.getUserLocationEntity().getId());
			if (null != userMappingEntity.getUserLocationEntity()
					&& null != userMappingEntity.getUserLocationEntity().getUserLocationName()) {
				usermappingVo.setUserLocationName(userMappingEntity.getUserLocationEntity().getUserLocationName());
			}

			usermappingVo.setSubLocationId(userMappingEntity.getSubLocationEntity().getSublocationId());

			if (null != userMappingEntity.getSubLocationEntity()
					&& null != userMappingEntity.getSubLocationEntity().getSubLocationName()) {
				usermappingVo.setSubLocationName(userMappingEntity.getSubLocationEntity().getSubLocationName());
			}
			usermappingVo.setReportingLocationId(userMappingEntity.getReportingLocationEntity().getId());

			usermappingVo.setReportingLocationId(userMappingEntity.getReportingLocationEntity().getId());
			if (null != userMappingEntity.getReportingLocationEntity()
					&& null != userMappingEntity.getReportingLocationEntity().getUserLocationName()) {
				usermappingVo
						.setReportingLocationName(userMappingEntity.getReportingLocationEntity().getUserLocationName());
			}

			usermappingVo
					.setReportingSubLocationId(userMappingEntity.getReportingSublocationEntity().getSublocationId());

			if (null != userMappingEntity.getReportingSublocationEntity()
					&& null != userMappingEntity.getReportingSublocationEntity().getSubLocationName()) {
				usermappingVo.setReportingSubLocationName(
						userMappingEntity.getReportingSublocationEntity().getSubLocationName());
			}

			if (null != userMappingEntity.getReportingToUser()) {
				usermappingVo.setReportingToUserName(userMappingEntity.getReportingToUser().getFirstName());
			}

			if (null != userMappingEntity.getUserEntity()) {
				usermappingVo.setUserName(userMappingEntity.getUserEntity().getFirstName());
			}
			if (null != userMappingEntity.getUserEntity()) {
				usermappingVo.setUserId(userMappingEntity.getUserEntity().getId());
			}
			return usermappingVo;
		} catch (NoResultException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("noResultFound",authDetailsVo));
		} catch (NonUniqueResultException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("noRecordFound",authDetailsVo));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
	}

	/**
	 * Method used to delete user mapping.
	 * 
	 * @param userMappingVo
	 *            UserMappingVo
	 * 
	 */
	@Transactional
	public void delete(UserMappingVO userMappingVo,AuthDetailsVo authDetailsVo) {
		try {
			for (Integer id : userMappingVo.getUserMappingList()) {

				UserMappingEntity userMappingEntity = new UserMappingEntity();

				userMappingEntity = userMappingRepository.findOne(id);

				userMappingEntity.setDeleteFlag(CommonConstant.FLAG_ONE);

				userMappingRepository.save(userMappingEntity);
			}
		} catch (NoResultException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("noResultFound",authDetailsVo));
		} catch (NonUniqueResultException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("noRecordFound",authDetailsVo));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}

	}

	/**
	 * Method used to search user mapping.
	 * 
	 * @param userMappingVo
	 *            UserMappingVo
	 * 
	 */
	@Transactional
	public List<UserMappingVO> getAllSearch(UserMappingVO userMappingVo,AuthDetailsVo authDetailsVo) {

		try {
			List<UserMappingVO> userMappingVoList = new ArrayList<UserMappingVO>();

			List<Object[]> userMappingEntityList = userMappingDAO.getAllSearch(userMappingVo,authDetailsVo);
			userMappingVoList = getAllList(userMappingEntityList);

			return userMappingVoList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
	}

	@Transactional
	public int UserMapping(UserMappingVO userMappingVo,AuthDetailsVo authDetailsVo) {

		try {
			int count = userMappingDAO.UserMapping(userMappingVo);
			if (count > 0) {
				throw new CommonException(getMessage("duplicatemappingFound",authDetailsVo));
			}
		} catch (CommonException e) {
			logger.error(e.getMessage());
			throw new CommonException(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}

		return 0;

	}

	

	@Transactional
	public void findDuplicate(UserMappingVO userMappingVo,AuthDetailsVo authDetailsVo) {
		int count = userMappingDAO.findDuplicate(userMappingVo);
		
		if (count > 0) {
			throw new CommonException(getMessage("userMapping_user_check",authDetailsVo));
		}
	}
}
