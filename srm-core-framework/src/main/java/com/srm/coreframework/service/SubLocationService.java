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

import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.controller.CommonController;
import com.srm.coreframework.dao.SubLocationDAO;
import com.srm.coreframework.entity.EntityLicense;
import com.srm.coreframework.entity.SubLocation;
import com.srm.coreframework.repository.SubLocationRepository;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.SubLocationVO;


@Component
public class SubLocationService extends CommonController<SubLocationVO>{
	
	Logger logger = LoggerFactory.getLogger(SubLocationService.class);

	@Autowired
	private SubLocationRepository subLocationRepository;
	
	@Autowired
	private SubLocationDAO subLocationDao;

	

	/**
	 * Method is for get all details
	 * 
	 * @return subLocationVoList List<SubLocationVo>
	 * @throws BusinessException
	 */
	@Transactional
	public List<SubLocationVO> getAll(AuthDetailsVo authDetailsVo) throws CommonException {

		List<SubLocationVO> subLocationVoList = new ArrayList<SubLocationVO>();
		try {
			Integer entityId = authDetailsVo.getEntityId();
			SubLocationVO subLocationVo = null;

			List<Object[]> subLocationEntityList = subLocationRepository.getAll(entityId);

			for (Object[] object : subLocationEntityList) {

				subLocationVo = new SubLocationVO();

				if (null != (String) object[0]) {
					subLocationVo.setSublocationId((int) object[4]);
					subLocationVo.setSubLocationCode((String) object[0]);
				}
				if (null != (String) object[1]) {

					subLocationVo.setSubLocationName((String) object[1]);
				}
				if (null != (String) ((Object[]) object)[3]) {
					subLocationVo.setId((int) object[5]);
					subLocationVo.setUserLocationName((String) object[3]);
				}
				if ((int) object[2] == 1) {
					subLocationVo.setSubLocationIsActive(true);
				} else if ((int) object[2] == 0) {
					subLocationVo.setSubLocationIsActive(false);
				}
				if (subLocationVo.isSubLocationIsActive()) {
					subLocationVo.setStatus(CommonConstant.Active);
				} else {
					subLocationVo.setStatus(CommonConstant.InActive);
				}

				subLocationVoList.add(subLocationVo);
			}

			return subLocationVoList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}

	}

	/**
	 * Method is for Search Sub location
	 * 
	 * @param subLocationVoSearch
	 *            SubLocationVo
	 * @return subLocationVoList List<SubLocationVo>
	 * @throws BusinessException
	 */
	@Transactional
	public List<SubLocationVO> getAllSearch(SubLocationVO subLocationVoSearch,AuthDetailsVo authDetailsVo) throws CommonException {
		try {
			
			
			
			List<SubLocationVO> subLocationVoList = new ArrayList<SubLocationVO>();

			List<Object[]> subLocationEntityList = null;
			SubLocationVO subLocationVo = null;

			subLocationEntityList = subLocationDao.getAllSearch(subLocationVoSearch,authDetailsVo);

			for (Object[] object : subLocationEntityList) {

				subLocationVo = new SubLocationVO();

				if (null != (String) object[0]) {
					subLocationVo.setSublocationId((int) object[4]);
					subLocationVo.setSubLocationCode((String) object[0]);
				}
				if (null != (String) object[1]) {

					subLocationVo.setSubLocationName((String) object[1]);
				}
				if (null != (String) ((Object[]) object)[3]) {
					subLocationVo.setId((int) object[5]);
					subLocationVo.setUserLocationName((String) object[3]);
				}
				if ((int) object[2] == 1) {
					subLocationVo.setSubLocationIsActive(true);
				} else if ((int) object[2] == 0) {
					subLocationVo.setSubLocationIsActive(false);
				}
				if (subLocationVo.isSubLocationIsActive()) {
					subLocationVo.setStatus(CommonConstant.Active);
				} else {
					subLocationVo.setStatus(CommonConstant.InActive);
				}

				subLocationVoList.add(subLocationVo);
			}

			return subLocationVoList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
	}

	/**
	 * This method is used to delete values in subLocation.
	 * 
	 * 
	 * @param SubLocationVo
	 *            SubLocationVo
	 * 
	 */
	@Transactional
	public void deletesublocation(SubLocationVO subLocationVo,AuthDetailsVo authDetailsVo) throws CommonException {
		SubLocation subLocationEntity = new SubLocation();

		boolean subLocation = false;

		boolean subLocationDelete = false;

		List<String> codeList = new ArrayList<String>();

		for (Integer id : subLocationVo.getDeleteItem()) {

			subLocationDelete = findUserLocation(id,authDetailsVo);
			try {

				subLocationEntity = findId(id);

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
			if (subLocationDelete) {
				subLocation = true;
				codeList.add(subLocationEntity.getSubLocationName());
				continue;
			}

			subLocationEntity.setDeleteFlag(CommonConstant.FLAG_ONE);
			subLocationEntity.setCreateBy(authDetailsVo.getUserId());
			subLocationEntity.setCreateDate(CommonConstant.getCalenderDate());
			subLocationEntity.setCreateBy(authDetailsVo.getUserId());
			subLocationEntity.setUpdateDate(CommonConstant.getCalenderDate());
		}

		try {
			subLocationRepository.save(subLocationEntity);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
		if (subLocation) {
			throw new CommonException(
					CommonConstant.format(getMessage("unUsedRecordOnlyBeDeleted",authDetailsVo), codeList));

		}
	}

	private boolean findUserLocation(Integer id,AuthDetailsVo authDetailsVo) {
		boolean status = false;

		/*if (!status) {
			status = findRequestConfig(id);
		}*/
		if (!status) {
			status = findRequestConfigExecuter(id,authDetailsVo);
		}
		if (!status) {
			status = findRequestConfigSeq(id,authDetailsVo);
		}
		/*if (!status) {
			status = findRoomConfig(id);
		}
		if (!status) {
			status = findRoomBookingConfig(id);
		}
		if (!status) {
			status = findRoomBookingConfigSeq(id);
		}*/
		if (!status) {
			status = findRequest(id,authDetailsVo);
		}
		/*if (!status) {
			status = findRoomBooking(id);
		}
		if (!status) {
			status = findRoomBookingDetails(id);
		}
		if (!status) {
			status = findRoomBookingDetails(id);
		}*/
		if (!status) {
			status = findPhoneBook(id,authDetailsVo);
		}
		return status;

	}

	private boolean findPhoneBook(Integer id,AuthDetailsVo authDetailsVo) {
		int count = 0;
		try {
			count =  subLocationDao.findPhoneBook(id,authDetailsVo);
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

	

	private boolean findRequest(Integer id,AuthDetailsVo authDetailsVo) {
		int count = 0;
		try {
			count = subLocationDao.findRequest(id,authDetailsVo);
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

	

	
	private boolean findRequestConfigSeq(Integer id,AuthDetailsVo authDetailsVo) {
		int count = 0;
		try {
			count = subLocationDao.findRequestConfigSeq(id,authDetailsVo);
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

	private boolean findRequestConfig(Integer id,AuthDetailsVo authDetailsVo) {
		int count = 0;
		try {
			count = (int) (long) subLocationDao.findRequestConfig(id,authDetailsVo);
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
			count =  subLocationDao.findRequestConfigExecuter(id,authDetailsVo);
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

	private SubLocation findId(Integer id) {
		SubLocation subLocationEntity = (SubLocation) subLocationRepository.findOne(id);

		return subLocationEntity;
	}

	/**
	 * Method is to create sub location.
	 * 
	 * @param subLocationVo
	 *            SubLocationVo
	 */
	@Transactional
	public void create(SubLocationVO subLocationVo,AuthDetailsVo authDetailsVo) {

		SubLocation subLocationEntity = new SubLocation();
		
		
		String code = null;
		try {
			code = subLocationDao.findAutoGenericCode(CommonConstant.SubLocation,authDetailsVo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("autoCodeGenerationFailure",authDetailsVo));
		}
		 
		try {
			if (subLocationVo != null) {

				subLocationEntity.setSubLocationCode(code);
				subLocationEntity.setSubLocationName(subLocationVo.getSubLocationName());
				subLocationEntity.setId(subLocationVo.getId());
				subLocationEntity.setSubLocationIsActive(subLocationVo.isSubLocationIsActive());
				subLocationEntity.setCreateBy(authDetailsVo.getUserId());
				subLocationEntity.setCreateDate(CommonConstant.getCalenderDate());
				subLocationEntity.setUpdateBy(authDetailsVo.getUserId());
				subLocationEntity.setUpdateDate(CommonConstant.getCalenderDate());
				subLocationEntity = setCreateUserDetails(subLocationEntity,authDetailsVo);
				
				EntityLicense entityLicenseEntity =  new EntityLicense();
				entityLicenseEntity.setId(authDetailsVo.getEntityId());
				subLocationEntity.setEntityLicenseEntity(entityLicenseEntity);
				subLocationRepository.save(subLocationEntity);
				//System.out.println("Saved");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
	}
	private SubLocation setCreateUserDetails(SubLocation subLocationEntity,AuthDetailsVo authDetailsVo) {

		subLocationEntity.setCreateBy(authDetailsVo.getUserId());
		subLocationEntity.setCreateDate(CommonConstant.getCalenderDate());
		subLocationEntity.setUpdateBy(authDetailsVo.getUserId());
		subLocationEntity.setUpdateDate(CommonConstant.getCalenderDate());
		subLocationEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);
		return subLocationEntity;
	}
	/**
	 * Method is to update sub location.
	 * 
	 * @param subLocationVo
	 *            SubLocationVo
	 */
	@Transactional
	public void updateSublocation(SubLocationVO subLocationVo,AuthDetailsVo authDetailsVo) {
		SubLocation subLocationEntity = new SubLocation();
		boolean subLocation = false;
		if (subLocationVo.isSubLocationIsActive() == false) {

			subLocation = deleteSubLocation(subLocationVo.getSublocationId(),authDetailsVo);
		}
		if (subLocation) {
			throw new CommonException(getMessage("cannotsetinactive",authDetailsVo));
		}

		try {

			subLocationEntity = subLocationRepository.findOne(subLocationVo.getSublocationId());

			if (subLocationEntity != null) {

				subLocationEntity.setUpdateBy(authDetailsVo.getUserId());
				subLocationEntity.setUpdateDate(CommonConstant.getCalenderDate());

				BeanUtils.copyProperties(subLocationVo, subLocationEntity);
				
				EntityLicense entityLicenseEntity =  new EntityLicense();
				entityLicenseEntity.setId(authDetailsVo.getEntityId());
				subLocationEntity.setEntityLicenseEntity(entityLicenseEntity);

				subLocationRepository.save(subLocationEntity);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));

		}
	}

	private boolean deleteSubLocation(int sublocationId,AuthDetailsVo authDetailsVo) {
		boolean status = false;
		if (status == false) {
			status = findPhoneBook(sublocationId,authDetailsVo);
		}
		
		if (status == false) {
			status = findRequest(sublocationId,authDetailsVo);
		}
		
		if (status == false) {
			status = findRequestConfigSeq(sublocationId,authDetailsVo);
		}
		/*
		 * if (status == false) { status = findRequestConfig(sublocationId);
		 * 
		 * }
		 */
		return status;
	}

	/**
	 * Method is to view sub location
	 * 
	 * @param subLocationVo
	 *            SubLocationVo
	 * @return subLocation SubLocationVo
	 */
	@Transactional
	public SubLocationVO viewSublocation(SubLocationVO locationVo,AuthDetailsVo authDetailsVo) {
		try {
			Object[] object = subLocationDao.findBySubId(locationVo.getSublocationId(),authDetailsVo);

			SubLocationVO subLocation = new SubLocationVO();
			BeanUtils.copyProperties(locationVo, subLocation);

			
			

			if (null != (String) object[0]) {
				subLocation.setSublocationId((int) object[4]);
				subLocation.setSubLocationCode((String) object[0]);
			}
			if (null != (String) object[1]) {

				subLocation.setSubLocationName((String) object[1]);
			}
			if (null != (String) ((Object[]) object)[3]) {
				subLocation.setId((int) object[5]);
				subLocation.setUserLocationName((String) object[3]);
			}
			if ((int) object[2] == 1) {
				subLocation.setSubLocationIsActive(true);
			} else if ((int) object[2] == 0) {
				subLocation.setSubLocationIsActive(false);
			}

			return subLocation;
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
	 * Method is to get the Code.
	 * 
	 * @param subLocationVo
	 *            subLocationVo
	 * @return subLocationVo Boolean
	 */

	@Transactional
	public Boolean getCode(SubLocationVO subLocationVo,AuthDetailsVo authDetailsVo) {
		try {

			return subLocationDao.getCode(subLocationVo,authDetailsVo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
	}

	@Transactional
	public void duplicateSubLocation(SubLocationVO subLocationVo,AuthDetailsVo authDetailsVo)throws CommonException {

		try {
			int count = subLocationDao.duplicateSubLocation(subLocationVo,authDetailsVo);
			if (count > 0) {
				throw new CommonException(getMessage("subLocationAlreadyExists",authDetailsVo));
			}
		} catch (CommonException e) {
			logger.error(e.getMessage());
			throw new CommonException(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}

	}
	
}
