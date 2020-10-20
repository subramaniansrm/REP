package com.srm.coreframework.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.controller.CommonController;
import com.srm.coreframework.dao.UserRoleDAO;
import com.srm.coreframework.entity.EntityLicense;
import com.srm.coreframework.entity.SubLocation;
import com.srm.coreframework.entity.UserDepartment;
import com.srm.coreframework.entity.UserLocation;
import com.srm.coreframework.entity.UserRole;
import com.srm.coreframework.entity.UserType;
import com.srm.coreframework.repository.UserMappingRepository;
import com.srm.coreframework.repository.UserRepository;
import com.srm.coreframework.repository.UserRoleRepository;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.UserRoleVO;

@Component
public class UserRoleService extends CommonController<UserRoleVO>{
	
	org.slf4j.Logger logger = LoggerFactory.getLogger(UserRoleService.class);
	
	@Autowired
	UserMappingRepository userMappingRepo;
	
	@Autowired
	UserRepository userRepos;

	@Autowired
	private UserRoleRepository userRoleRepo;
	
	@Autowired
	private UserRoleDAO userRoleDAO;	

	
	
	
	@Transactional
	public List<UserRoleVO> getUserRoleList(AuthDetailsVo authDetailsVo) {
		List<UserRoleVO> userRoleList = new ArrayList<UserRoleVO>();

		UserRoleVO userRoleMaster = null;
		
		Integer entityId =authDetailsVo.getEntityId();
		
		try {
			List<UserRole> list = userRoleRepo.getUserRoleList(entityId);

			for (UserRole userRole : list) {

				userRoleMaster = new UserRoleVO();
				
				if(null != userRole.getId() ){
				userRoleMaster.setId(userRole.getId());
				}
				if(null !=  userRole.getUserRoleName()){
				userRoleMaster.setUserRoleName(userRole.getUserRoleName());
				}
				if(null != userRole.getUserTypeEntity().getTypeOfUser()){				
				userRoleMaster.setRoleTypeName(userRole.getUserTypeEntity().getTypeOfUser());
				}
				if(null != userRole.getDescription()){		
				userRoleMaster.setDescription(userRole.getDescription());
				}
				if(null != userRole.getUserLocationEntity().getUserLocationName()){
				userRoleMaster.setUserLocationName(userRole.getUserLocationEntity().getUserLocationName());
				}
				if(null != userRole.getSubLocationEntity().getSubLocationName()){				
				userRoleMaster.setSublocationName(userRole.getSubLocationEntity().getSubLocationName());
				}
				if(null != userRole.getUserDepartmentEntity().getUserDepartmentName()){
				userRoleMaster.setUserDepartmentName(userRole.getUserDepartmentEntity().getUserDepartmentName());
				}
			
				userRoleList.add(userRoleMaster);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
		return userRoleList;
	}
	
	@Transactional
	public void duplicateRole(UserRoleVO userRoleMaster,AuthDetailsVo authDetailsVo) {

		try {
			int count = userRoleDAO.duplicateRole(userRoleMaster,authDetailsVo);
			if (count > 0) {
				throw new CommonException(getMessage("roleAlreadyExists",authDetailsVo));
			}
		} catch (CommonException e) {
			logger.error(e.getMessage());
			throw new CommonException(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}

	}

	
	@Transactional
	public void saveUserRole(UserRoleVO saveUserRoleMaster,AuthDetailsVo authDetailsVo) throws IllegalAccessException, InvocationTargetException {
		UserRoleVO userRoleMaster = new UserRoleVO();
		try {

			BeanUtils.copyProperties(userRoleMaster,saveUserRoleMaster);
		} catch (CommonException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("beanUtilPropertiesFailure",authDetailsVo));
		}
		try {
			if (null != saveUserRoleMaster) {
				UserRole saveUserRoleEntity = new UserRole();

				userRoleMaster.setDeleteFlag(CommonConstant.FLAG_ZERO);
				userRoleMaster.setCreatedBy(authDetailsVo.getUserId());
				userRoleMaster.setCreatedDate(CommonConstant.getCalenderDate());
				userRoleMaster.setUpdatedBy(authDetailsVo.getUserId());
				userRoleMaster.setUpdatedDate(CommonConstant.getCalenderDate());
				try {
					BeanUtils.copyProperties(saveUserRoleEntity,userRoleMaster);
				} catch (CommonException e) {
					logger.error(e.getMessage());
					throw new CommonException(getMessage("beanUtilPropertiesFailure",authDetailsVo));
				}
				if (null != saveUserRoleMaster.getUserLocation()
						&& !saveUserRoleMaster.getUserLocation().equals(CommonConstant.BLANK)) {
					UserLocation locationEntity = new UserLocation();
					locationEntity.setId(saveUserRoleMaster.getUserLocation());
					saveUserRoleEntity.setUserLocationEntity(locationEntity);
				}

				SubLocation subLocation = new SubLocation();
				subLocation.setSublocationId(saveUserRoleMaster.getSublocationId());
				saveUserRoleEntity.setSubLocationEntity(subLocation);

				UserType userTypeEntity = new UserType();
				userTypeEntity.setUserTypeId(saveUserRoleMaster.getRoleType());
				saveUserRoleEntity.setUserTypeEntity(userTypeEntity);

				if (null != saveUserRoleMaster.getUserDepartment()
						&& !saveUserRoleMaster.getUserDepartment().equals(CommonConstant.BLANK)) {
					UserDepartment departmentEntity = new UserDepartment();
					departmentEntity.setId(saveUserRoleMaster.getUserDepartment());
					saveUserRoleEntity.setUserDepartmentEntity(departmentEntity);
				}
				saveUserRoleEntity.setCreateBy(authDetailsVo.getUserId());
				saveUserRoleEntity.setCreateDate(CommonConstant.getCalenderDate());
				saveUserRoleEntity.setUpdateBy(authDetailsVo.getUserId());
				saveUserRoleEntity.setUpdateDate(CommonConstant.getCalenderDate());
			
				EntityLicense entityLicenseEntity = new EntityLicense();
				entityLicenseEntity.setId(authDetailsVo.getEntityId());
				
				saveUserRoleEntity.setEntityLicenseEntity(entityLicenseEntity);

				userRoleRepo.save(saveUserRoleEntity);

			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}

	}
	
	
	@Transactional
	public void updateUserRole(UserRoleVO updateUserRoleMaster,AuthDetailsVo authDetailsVo) throws IllegalAccessException, InvocationTargetException {
		UserRoleVO userRoleMaster = new UserRoleVO();
		try {

			BeanUtils.copyProperties(userRoleMaster,updateUserRoleMaster);
		} catch (CommonException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("beanUtilPropertiesFailure",authDetailsVo));
		}
		try {
			if (null != updateUserRoleMaster.getId()) {

				UserRole updateUserRoleEntity = userRoleRepo.findOne(updateUserRoleMaster.getId());

				if (null != updateUserRoleEntity) {

					userRoleMaster.setDeleteFlag(CommonConstant.FLAG_ZERO);
					userRoleMaster.setCreatedBy(updateUserRoleEntity.getCreateBy());
					userRoleMaster.setCreatedDate(updateUserRoleEntity.getCreateDate());
					userRoleMaster.setUpdatedBy(authDetailsVo.getUserId());
					userRoleMaster.setUpdatedDate(CommonConstant.getCalenderDate());
					BeanUtils.copyProperties(updateUserRoleEntity,userRoleMaster);

					if (null != updateUserRoleMaster.getGfiLocationFlag() && String
							.valueOf(updateUserRoleMaster.getGfiLocationFlag()).equals(CommonConstant.STRING_ONE)) {
						updateUserRoleEntity.setGfiLocationFlag(CommonConstant.FLAG_ONE);
					} else {
						updateUserRoleEntity.setGfiLocationFlag(CommonConstant.FLAG_ZERO);
					}

					if (null != updateUserRoleMaster.getUserLocation()
							&& !updateUserRoleMaster.getUserLocation().equals(CommonConstant.BLANK)) {
						UserLocation locationEntity = new UserLocation();
						locationEntity.setId(updateUserRoleMaster.getUserLocation());
						updateUserRoleEntity.setUserLocationEntity(locationEntity);
					}
					if (null != updateUserRoleMaster.getUserDepartment()
							&& !updateUserRoleMaster.getUserDepartment().equals(CommonConstant.BLANK)) {
						UserDepartment departmentEntity = new UserDepartment();
						departmentEntity.setId(updateUserRoleMaster.getUserDepartment());
						updateUserRoleEntity.setUserDepartmentEntity(departmentEntity);
					}

					SubLocation subLocationEntity = new SubLocation();
					subLocationEntity.setSublocationId(updateUserRoleMaster.getSublocationId());
					updateUserRoleEntity.setSubLocationEntity(subLocationEntity);
					
					UserType userTypeEntity = new UserType();
					userTypeEntity.setUserTypeId(updateUserRoleMaster.getRoleType());
					updateUserRoleEntity.setUserTypeEntity(userTypeEntity);

				
					EntityLicense entityLicenseEntity = new EntityLicense();
					entityLicenseEntity.setId(authDetailsVo.getEntityId());
					updateUserRoleEntity.setEntityLicenseEntity(entityLicenseEntity);
					
					updateUserRoleEntity.setUpdateBy(authDetailsVo.getUserId());
					updateUserRoleEntity.setUpdateDate(CommonConstant.getCalenderDate());
					
					userRoleRepo.save(updateUserRoleEntity);

				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}

	}
	
	@Transactional
	public void deleteUserRole(UserRoleVO deleteUserRoleMaster,AuthDetailsVo authDetailsVo) {

		boolean userRole = false;

		List<String> codeList = new ArrayList<String>();

		try {
			Integer[] deleteItems = deleteUserRoleMaster.getDeleteItem();
			for (int i = 0; i < deleteItems.length; i++) {

				boolean Type = false;

				Type = deleteCode(deleteItems[i],authDetailsVo);

				UserRole userRoleEntity = userRoleRepo.findOne(deleteItems[i]);

				if (Type) {
					userRole = true;
					codeList.add(userRoleEntity.getUserRoleName());
					continue;
				}

				userRoleEntity.setDeleteFlag(CommonConstant.FLAG_ONE);
				userRoleEntity.setCreateBy(userRoleEntity.getCreateBy());
				userRoleEntity.setCreateDate(userRoleEntity.getCreateDate());
				userRoleEntity.setUpdateBy(authDetailsVo.getUserId());
				userRoleEntity.setUpdateDate(CommonConstant.getCalenderDate());
				userRoleRepo.save(userRoleEntity);
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
		if (userRole) {
			throw new CommonException(
					CommonConstant.format(getMessage("unUsedRecordOnlyBeDeleted",authDetailsVo), codeList));

		}

	}

	
	@Transactional
	public boolean deleteCode(int role,AuthDetailsVo authDetailsVo) throws CommonException {
		boolean status = false;
		if (!status) {
			status = deleteUser(role,authDetailsVo);
		}
		if (!status) {
			status = deleteUserMapping(role,authDetailsVo);
		}
		/*if (!status) {
			status = deleteRequestConfigurationExecuter(role);
		}
		if (!status) {
			status = deleteRequestConfigurationSequence(role);
		}*/
		
		return status;
	}

	
	@Transactional
	private boolean deleteUser(int role,AuthDetailsVo authDetailsVo) throws CommonException {
		Integer count = 0;
		Integer entityId = authDetailsVo.getEntityId();

		try {
			count = (int) (long) userRepos.deleteUser(entityId,role);
			if (count > 0) {
				return true;
			}
		} catch (CommonException exe) {
			throw new CommonException(exe.getMessage());
		} catch (Exception exe) {

			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
		return false;

	}
	
	
	@Transactional
	private boolean deleteUserMapping(int role,AuthDetailsVo authDetailsVo) throws CommonException {
		Integer count = 0;
		Integer entityId = authDetailsVo.getEntityId();

		try {
			count = (int) (long) userMappingRepo.deleteUserMapping(entityId,role);
			if (count > 0) {
				return true;
			}
		} catch (CommonException exe) {
			throw new CommonException(exe.getMessage());
		} catch (Exception exe) {

			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
		return false;

	}
	
	@Transactional
	private boolean deleteRequestConfigurationExecuter(int role,AuthDetailsVo authDetailsVo) throws CommonException {
		Integer count = 0;
		Integer entityId = authDetailsVo.getEntityId();

		try {
			//count = (int) (long) userRoleRepo.deleteRequestConfigurationExecuter(entityId,role);
			if (count > 0) {
				return true;
			}
		} catch (CommonException exe) {
			throw new CommonException(exe.getMessage());
		} catch (Exception exe) {

			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
		return false;

	}
	
	@Transactional
	private boolean deleteRequestConfigurationSequence(int role,AuthDetailsVo authDetailsVo) throws CommonException {
		Integer count = 0;
		Integer entityId = authDetailsVo.getEntityId();

		try {
			//count = (int) (long) userRoleRepo.deleteRequestConfigurationSequence(entityId,role);
			if (count > 0) {
				return true;
			}
		} catch (CommonException exe) {
			throw new CommonException(exe.getMessage());
		} catch (Exception exe) {

			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
		return false;

	}
	
	
	@Transactional
	public List<UserRoleVO> search(UserRoleVO userMaster,AuthDetailsVo authDetailsVo) {

		List<UserRoleVO> userRoleList = new ArrayList<UserRoleVO>();

		UserRoleVO userRoleMaster = null;
		

		try {
			List<UserRole> list =  userRoleDAO.search(userMaster,authDetailsVo);

			for (UserRole userRole : list) {

				userRoleMaster = new UserRoleVO();
				
				if(0 != userRole.getId()){
				userRoleMaster.setId(userRole.getId());
				}
				if(null !=  userRole.getUserRoleName()){
				userRoleMaster.setUserRoleName(userRole.getUserRoleName());
				}
				if(null != userRole.getUserTypeEntity().getTypeOfUser()){				
				userRoleMaster.setRoleTypeName(userRole.getUserTypeEntity().getTypeOfUser());
				}
				if(null != userRole.getDescription()){		
				userRoleMaster.setDescription(userRole.getDescription());
				}
				if(null != userRole.getUserLocationEntity().getUserLocationName()){
				userRoleMaster.setUserLocationName(userRole.getUserLocationEntity().getUserLocationName());
				}
				if(null != userRole.getSubLocationEntity().getSubLocationName()){				
				userRoleMaster.setSublocationName(userRole.getSubLocationEntity().getSubLocationName());
				}
				if(null != userRole.getUserDepartmentEntity().getUserDepartmentName()){
				userRoleMaster.setUserDepartmentName(userRole.getUserDepartmentEntity().getUserDepartmentName());
				}
			
				userRoleList.add(userRoleMaster);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
		
		return userRoleList;

	}
	
	@Transactional
	public UserRoleVO viewUserRole(UserRoleVO userRoleVo,AuthDetailsVo authDetailsVo) throws IllegalAccessException, InvocationTargetException {

		UserRoleVO userRoleMstr = new UserRoleVO();

		UserRole userRoleEntity = null;

		try {
			userRoleEntity = userRoleRepo.findOne(userRoleVo.getId());
		} catch (NoResultException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("noResultFound",authDetailsVo));
		} catch (NonUniqueResultException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("noUniqueFound",authDetailsVo));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
		try {
			BeanUtils.copyProperties(userRoleMstr,userRoleVo);
		} catch (CommonException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("beanUtilPropertiesFailure",authDetailsVo));
		}
		try {
			BeanUtils.copyProperties(userRoleMstr,userRoleEntity);
		} catch (CommonException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("beanUtilPropertiesFailure",authDetailsVo));
		}
		if (null != userRoleEntity.getGfiLocationFlag()
				&& String.valueOf(userRoleEntity.getGfiLocationFlag()).equals(CommonConstant.STRING_ONE)) {
			userRoleMstr.setGfiLocationFlag(CommonConstant.FLAG_ONE);
		} else {
			userRoleMstr.setGfiLocationFlag(CommonConstant.FLAG_ZERO);
		}

		if (null != userRoleEntity.getUserDepartmentEntity()) {

			userRoleMstr.setUserDepartment(userRoleEntity.getUserDepartmentEntity().getId());
			userRoleMstr.setUserDepartmentName(userRoleEntity.getUserDepartmentEntity().getUserDepartmentName());

		}
		if (null != userRoleEntity.getUserLocationEntity()) {
			userRoleMstr.setUserLocation(userRoleEntity.getUserLocationEntity().getId());
			userRoleMstr.setUserLocationName(userRoleEntity.getUserLocationEntity().getUserLocationName());

		}
		userRoleMstr.setSublocationId(userRoleEntity.getSubLocationEntity().getSublocationId());
		userRoleMstr.setSublocationName(userRoleEntity.getSubLocationEntity().getSubLocationName());
		
		userRoleMstr.setRoleType(userRoleEntity.getUserTypeEntity().getUserTypeId());
		userRoleMstr.setRoleTypeName(userRoleEntity.getUserTypeEntity().getTypeOfUser());
		

		return userRoleMstr;

	}
	
	

}
