package com.srm.coreframework.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.controller.CommonController;
import com.srm.coreframework.dao.UserDepartmentDAO;
import com.srm.coreframework.entity.EntityLicense;
import com.srm.coreframework.entity.SubLocation;
import com.srm.coreframework.entity.UserDepartment;
import com.srm.coreframework.entity.UserLocation;
import com.srm.coreframework.repository.UserDepartmentRepository;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.UserDepartmentVO;

@Service
public class UserDepartmentService extends CommonController<UserDepartmentVO> {

	Logger logger = LoggerFactory.getLogger(UserDepartmentService.class);

	@Autowired
	UserDepartmentRepository userDepartmentRepo;

	@Autowired
	UserDepartmentDAO userDepartmentDAO;

	

	@Transactional
	public List<UserDepartmentVO> loadDepartment(AuthDetailsVo authDetailsVo) {
		List<UserDepartment> userDepartmentEntity = new ArrayList<UserDepartment>();
		try {
			Integer entityId = authDetailsVo.getEntityId();
			userDepartmentEntity = userDepartmentRepo.loadDepartment(entityId);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dbFailure",authDetailsVo));
		}
		try {
			List<UserDepartmentVO> userDepartmentMasterVo = getAllList(userDepartmentEntity,authDetailsVo);

			return userDepartmentMasterVo;

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}

	}

	/**
	 * Method to get set User Department Details
	 * 
	 * @param userDepartmentEntity
	 *            List<UserDepartmentEntity>
	 * @return userDepartmentMasterVo List<UserDepartmentMasterVo>
	 */
	@Transactional
	private List<UserDepartmentVO> getAllList(List<UserDepartment> userDepartmentEntity,AuthDetailsVo authDetailsVo) {

		try {

			List<UserDepartmentVO> userDepartmentMasterVo = new ArrayList<UserDepartmentVO>();

			for (UserDepartment departmentEntity : userDepartmentEntity) {

				UserDepartmentVO departmentMasterVo = new UserDepartmentVO();

				if (0 != departmentEntity.getId()) {
					departmentMasterVo.setId(departmentEntity.getId());
				}
				if (null != departmentEntity.getUserDepartmentName()) {
					departmentMasterVo.setUserDepartmentName(departmentEntity.getUserDepartmentName());
				}
				if (0 != departmentEntity.getUserLocationEntity().getId()) {
					departmentMasterVo.setUserLocation(departmentEntity.getUserLocationEntity().getId());
				}
				if (null != departmentEntity.getUserLocationEntity().getUserLocationName()) {
					departmentMasterVo
							.setUserLocationName(departmentEntity.getUserLocationEntity().getUserLocationName());
				}
				if (0 != departmentEntity.getSubLocationEntity().getSublocationId()) {
					departmentMasterVo.setSublocationId(departmentEntity.getSubLocationEntity().getSublocationId());
				}
				if (null != departmentEntity.getSubLocationEntity().getSubLocationName()) {
					departmentMasterVo.setSublocationName(departmentEntity.getSubLocationEntity().getSubLocationName());
				}
				if (null != departmentEntity.getDescription()) {
					departmentMasterVo.setDescription(departmentEntity.getDescription());
				}

				// departmentMasterVo.setSystemApplication(departmentEntity.getSystemApplicationEntity().getSysAppId());
				if (0 != departmentEntity.getEntityLicenseEntity().getId()) {
					departmentMasterVo.setEntityLicenseId(departmentEntity.getEntityLicenseEntity().getId());
				}

				userDepartmentMasterVo.add(departmentMasterVo);

			}

			return userDepartmentMasterVo;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
	}

	@Transactional
	public List<UserDepartmentVO> searchDepartment(UserDepartmentVO userDepartmentMasterVo,AuthDetailsVo authDetailsVo) {
		List<UserDepartment> userDepartmentEntity = new ArrayList<UserDepartment>();
		try {

			userDepartmentEntity = userDepartmentDAO.searchDepartment(userDepartmentMasterVo,authDetailsVo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dbFailure",authDetailsVo));
		}
		List<UserDepartmentVO> departmentMasterVo = new ArrayList<UserDepartmentVO>();
		try {
			departmentMasterVo = getAllList(userDepartmentEntity,authDetailsVo);

			return departmentMasterVo;

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}

	}

	@Transactional
	public void addDepartment(UserDepartmentVO userDepartmentMasterVo,AuthDetailsVo authDetailsVo) {

		UserDepartment userDepartmentEntity = new UserDepartment();
		try {

			userDepartmentEntity.setCreateBy(authDetailsVo.getUserId());
			userDepartmentEntity.setUpdateBy(authDetailsVo.getUserId());
			userDepartmentEntity.setCreateDate(CommonConstant.getCalenderDate());
			userDepartmentEntity.setUpdateDate(CommonConstant.getCalenderDate());
			userDepartmentEntity.setUserDepartmentName(userDepartmentMasterVo.getUserDepartmentName());
			UserLocation userLocationEntity = new UserLocation();
			userLocationEntity.setId(userDepartmentMasterVo.getUserLocation());
			userDepartmentEntity.setUserLocationEntity(userLocationEntity);

			SubLocation subLocationEntity = new SubLocation();
			subLocationEntity.setSublocationId(userDepartmentMasterVo.getSublocationId());
			userDepartmentEntity.setSubLocationEntity(subLocationEntity);

			userDepartmentEntity.setDescription(userDepartmentMasterVo.getDescription());
			userDepartmentEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);
			EntityLicense entityLicenseEntity = new EntityLicense();
			entityLicenseEntity.setId(authDetailsVo.getEntityId());

			userDepartmentEntity.setEntityLicenseEntity(entityLicenseEntity);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
		try {
			userDepartmentRepo.save(userDepartmentEntity);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dbFailure",authDetailsVo));
		}

	}

	@Transactional
	public void duplicateDepartment(UserDepartmentVO userDepartmentMasterVo,AuthDetailsVo authDetailsVo) {

		try {
			int count = userDepartmentDAO.duplicateDepartment(userDepartmentMasterVo,authDetailsVo);
			if (count > 0) {
				throw new CommonException(getMessage("departmentAlreadyExists",authDetailsVo));
			}
		} catch (CommonException e) {
			logger.error(e.getMessage());
			throw new CommonException(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}

	}

	/**
	 * Method used to update department
	 * 
	 * @param userDepartmentMasterVo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */

	@Transactional
	public void updateDepartment(UserDepartmentVO userDepartmentMasterVo,AuthDetailsVo authDetailsVo) {
		UserDepartment userDepartmentEntity = new UserDepartment();

		try {

			userDepartmentEntity = userDepartmentRepo.findOne(userDepartmentMasterVo.getId());
			userDepartmentEntity.setId(userDepartmentMasterVo.getId());
			userDepartmentEntity.setCreateBy(authDetailsVo.getUserId());
			userDepartmentEntity.setUpdateBy(authDetailsVo.getUserId());
			userDepartmentEntity.setCreateDate(CommonConstant.getCalenderDate());
			userDepartmentEntity.setUpdateDate(CommonConstant.getCalenderDate());
			userDepartmentEntity.setUserDepartmentName(userDepartmentMasterVo.getUserDepartmentName());
			userDepartmentEntity.setDescription(userDepartmentMasterVo.getDescription());
			UserLocation userLocationEntity = new UserLocation();
			userLocationEntity.setId(userDepartmentMasterVo.getUserLocation());
			userDepartmentEntity.setUserLocationEntity(userLocationEntity);

			SubLocation subLocationEntity = new SubLocation();
			subLocationEntity.setSublocationId(userDepartmentMasterVo.getSublocationId());
			userDepartmentEntity.setSubLocationEntity(subLocationEntity);

			EntityLicense entityLicenseEntity = new EntityLicense();
			entityLicenseEntity.setId(authDetailsVo.getEntityId());

			userDepartmentEntity.setEntityLicenseEntity(entityLicenseEntity);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
		try {
			userDepartmentEntity = userDepartmentRepo.save(userDepartmentEntity);

		} catch (NoResultException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("noResultFound",authDetailsVo));
		} catch (NonUniqueResultException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("noRecordFound",authDetailsVo));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dbFailure",authDetailsVo));
		}
	}

	/**
	 * Method used to delete department
	 * 
	 * 
	 * @param userDepartmentMasterVo
	 * @return
	 */

	@Transactional
	public void deleteDepartment(UserDepartmentVO userDepartmentMasterVo,AuthDetailsVo authDetailsVo) {

		// User
		boolean user = false;
		@SuppressWarnings("unused")
		boolean userDep = false;

		List<String> codeList = new ArrayList<String>();
		try {
			Integer[] deleteItems = userDepartmentMasterVo.getDeleteItem();
			for (Integer i = 0; i < deleteItems.length; i++) {
				user = deleteuser(deleteItems[i]);
				UserDepartment userDepartmentEntity = userDepartmentRepo.findOne(deleteItems[i]);

				// User
				if (user) {
					userDep = true;
					codeList.add(userDepartmentEntity.getUserDepartmentName());
					continue;
				}

				userDepartmentEntity.setCreateBy(authDetailsVo.getUserId());
				userDepartmentEntity.setUpdateBy(authDetailsVo.getUserId());
				userDepartmentEntity.setCreateDate(CommonConstant.getCalenderDate());
				userDepartmentEntity.setUpdateDate(CommonConstant.getCalenderDate());
				userDepartmentEntity.setDeleteFlag(CommonConstant.FLAG_ONE);
				userDepartmentEntity = userDepartmentRepo.save(userDepartmentEntity);
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
		if (user) {
			throw new CommonException(
					CommonConstant.format(getMessage("unUsedRecordOnlyBeDeleted",authDetailsVo), codeList));

		}

	}

	private boolean deleteuser(Integer integer) {
		boolean status = false;
		/*
		 * if (status == false) { status = finduser(integer); } if (status ==
		 * false) { status = finduserRole(integer); } if (status == false) {
		 * status = findRequest(integer); } if (!status) { status =
		 * findRequestConfigExecuter(integer); } if (status == false) { status =
		 * findRequestWorkFlowSeq(integer); } if (status == false) { status =
		 * findUserMappingDep(integer); } if (status == false) { status =
		 * findPhoneBookDep(integer); }
		 */

		return status;
	}

	/**
	 * Method used to find count in phone book
	 * 
	 * @param integer
	 * @return
	 */

	private boolean findPhoneBookDep(Integer integer, AuthDetailsVo authDetailsVo) {
		int count = 0;
		try {
			count = (int) (long) userDepartmentDAO.findPhoneBookDep(integer,authDetailsVo);
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

	/**
	 * Method used to find User Mapping Department
	 * 
	 * @param integer
	 * @return
	 */
	private boolean findUserMappingDep(Integer integer,AuthDetailsVo authDetailsVo) {
		int count = 0;
		try {
			count = (int) (long) userDepartmentDAO.findUserMappingDep(integer,authDetailsVo);
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

	/**
	 * Method used to find Request WorkFLow Seq
	 * 
	 * @param integer
	 * @return
	 */

	private boolean findRequestWorkFlowSeq(Integer integer,AuthDetailsVo authDetailsVo) {
		int count = 0;
		try {
			count = (int) (long) userDepartmentDAO.findRequestWorkFlowSeqDep(integer,authDetailsVo);
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

	/**
	 * Method used for Request WorkFLow
	 * 
	 * @param integer
	 * @return
	 */

	private boolean findRequestConfig(Integer integer,AuthDetailsVo authDetailsVo) {
		int count = 0;
		try {
			count = (int) (long) userDepartmentDAO.findRequestWorkFlowDep(integer,authDetailsVo);
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

	/**
	 * Method is to find request configuration executer
	 * 
	 * @param id
	 * @return
	 */
	private boolean findRequestConfigExecuter(Integer id,AuthDetailsVo authDetailsVo) {
		int count = 0;
		try {
			count = (int) (long) userDepartmentDAO.findRequestConfigExecuter(id,authDetailsVo);
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

	/**
	 * Method used for Request
	 * 
	 * @param integer
	 * @return
	 */

	private boolean findRequest(Integer integer,AuthDetailsVo authDetailsVo) {
		int count = 0;
		try {
			count = (int) (long) userDepartmentDAO.findRequestDep(integer,authDetailsVo);
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

	/**
	 * Method used to find User Role
	 * 
	 * @param integer
	 * @return
	 */
	private boolean finduserRole(Integer integer,AuthDetailsVo authDetailsVo) {
		int count = 0;
		try {
			count = (int) (long) userDepartmentDAO.findUserRole(integer,authDetailsVo);
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

	/**
	 * Method used to find User
	 * 
	 * @param integer
	 * @return
	 */
	private boolean finduser(Integer integer, AuthDetailsVo authDetailsVo) {
		int count = 0;
		try {
			count = (int) (long) userDepartmentDAO.finduser(integer,authDetailsVo);
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



	/**
	 * Method used to view Particular record
	 * 
	 * @param userDepartmentMasterVo
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */

	@Transactional
	public UserDepartmentVO viewDepartment(UserDepartmentVO userDepartmentMasterVo, AuthDetailsVo authDetailsVo)
			throws IllegalAccessException, InvocationTargetException {
		UserDepartment userDepartmentEntity = new UserDepartment();

		try {

			userDepartmentEntity = userDepartmentRepo.findOne(userDepartmentMasterVo.getId());
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

		UserDepartmentVO departmentMasterVo = new UserDepartmentVO();
		BeanUtils.copyProperties(departmentMasterVo,userDepartmentMasterVo);
		try {
			departmentMasterVo.setId(userDepartmentEntity.getId());
			departmentMasterVo.setUserDepartmentName(userDepartmentEntity.getUserDepartmentName());
			departmentMasterVo.setUserLocation(userDepartmentEntity.getUserLocationEntity().getId());
			departmentMasterVo.setUserLocationName(userDepartmentEntity.getUserLocationEntity().getUserLocationName());
			departmentMasterVo.setSublocationName(userDepartmentEntity.getSubLocationEntity().getSubLocationName());
			departmentMasterVo.setDescription(userDepartmentEntity.getDescription());
			departmentMasterVo.setSublocationId(userDepartmentEntity.getSubLocationEntity().getSublocationId());
			departmentMasterVo.setSublocationName(userDepartmentEntity.getSubLocationEntity().getSubLocationName());
			departmentMasterVo.setEntityLicenseId(userDepartmentEntity.getEntityLicenseEntity().getId());

			return departmentMasterVo;

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}

	}

}
