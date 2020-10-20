package com.srm.coreframework.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.dao.UserLocationDAO;
import com.srm.coreframework.entity.CityEntity;
import com.srm.coreframework.entity.CountryEntity;
import com.srm.coreframework.entity.EntityLicense;
import com.srm.coreframework.entity.StateEntity;
import com.srm.coreframework.entity.UserLocation;
import com.srm.coreframework.repository.UserLocationRepository;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.UserLocationVO;

@Service
public class UserLocationService extends CommonService{

	Logger logger = LoggerFactory.getLogger(UserLocationService.class);

	

	@Autowired
	UserLocationRepository userLocationRepo;
	
	@Autowired
	UserLocationDAO userLocationDAO;

	
	@Transactional
	public List<UserLocationVO> getLocation(AuthDetailsVo authDetailsVo) throws CommonException, CommonException {

		List<UserLocationVO> listUserLocationMasterVo = new ArrayList<UserLocationVO>();
		List<UserLocation> listUserLocationEntity = null;
		try {
			Integer entityId = authDetailsVo.getEntityId();
			listUserLocationEntity = userLocationRepo.getLocation(entityId);
			if (listUserLocationEntity != null && listUserLocationEntity.size() > 0) {
				listUserLocationMasterVo = getAllList(listUserLocationEntity,authDetailsVo);
			}
			return listUserLocationMasterVo;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
	}
	
		@Transactional
	public List<UserLocationVO> getSearchList(List<Object[]> listUserLocationEntity, AuthDetailsVo authDetailsVo) {


		List<UserLocationVO> listUserLocationMasterVo = new ArrayList<UserLocationVO>();

		for (Object[] obj : listUserLocationEntity) {

			/*"SELECT c.id,c.userLocationName, c.userLocationDetails, c.zip, c.phone, " //4
			+ " c.fax,c.email,c.contactName,cty.cityName, s.stateName,con.country "//10
*/			
			UserLocationVO userLocationMasterVo = new UserLocationVO();

			if (null != obj[0]) {
				userLocationMasterVo.setId((Integer)obj[0]);
			}
			if (null != obj[1]) {
				userLocationMasterVo.setUserLocationName((String)obj[1]);
			}
			if (null != obj[2]) {
				userLocationMasterVo.setUserLocationDetails((String)obj[2]);
			}
			if (null != obj[3]) {
				userLocationMasterVo.setZip((String)obj[3]);
			}
			if (null != obj[4]) {
				userLocationMasterVo.setPhone((String)obj[4]);
			}
			if (null != obj[5]) {
				userLocationMasterVo.setFax((String)obj[5]);
			}
			if (null != obj[6]) {
				userLocationMasterVo.setEmail((String)obj[6]);
			}
			if (null != obj[7]) {
				userLocationMasterVo.setContactName((String)obj[7]);
			}
			if (null != obj[8]) {
				userLocationMasterVo.setCity((String)obj[8]);
			}
			if (null != obj[9]) {
				userLocationMasterVo.setState((String)obj[9]);
			}
			if (null != obj[10]) {
				userLocationMasterVo.setCountry((String)obj[10]);
			}
			
			listUserLocationMasterVo.add(userLocationMasterVo);
		}
		return listUserLocationMasterVo;

	}
	@Transactional
	public List<UserLocationVO> getAllList(List<UserLocation> listUserLocationEntity, AuthDetailsVo authDetailsVo) {

		Integer entityId = authDetailsVo.getEntityId();

		List<UserLocationVO> listUserLocationMasterVo = new ArrayList<UserLocationVO>();

		for (UserLocation userLocationEntity : listUserLocationEntity) {

			UserLocationVO userLocationMasterVo = new UserLocationVO();

			BeanUtils.copyProperties(userLocationEntity, userLocationMasterVo);

			if (null != userLocationEntity.getCityId() && userLocationEntity.getCityId() != 0) {

				CityEntity city = userLocationRepo.getCity(userLocationEntity.getCityId());
				userLocationMasterVo.setCityId(userLocationEntity.getCityId());
				if (null != city) {
					userLocationMasterVo.setCity(city.getCityName());
				}
			}

			if (null != userLocationEntity.getStateId() && userLocationEntity.getStateId() != 0) {
				StateEntity state = userLocationRepo.getState(userLocationEntity.getStateId());
				userLocationMasterVo.setStateId(userLocationEntity.getStateId());
				if (null != state) {
					userLocationMasterVo.setState(state.getStateName());
				}
			}

			if (null != userLocationEntity.getCountryId() && userLocationEntity.getCountryId() != 0) {

				CountryEntity country = userLocationRepo.getCountry(userLocationEntity.getCountryId());
				if (null != country) {
					userLocationMasterVo.setCountry(country.getCountry());
					userLocationMasterVo.setCountryId(country.getId());
				}
			}
			listUserLocationMasterVo.add(userLocationMasterVo);
		}
		return listUserLocationMasterVo;

	}

	@Transactional
	public void create(UserLocationVO userLocationVo,AuthDetailsVo authDetailsVo) {
		UserLocation userLocationEntity = new UserLocation();
		BeanUtils.copyProperties(userLocationVo, userLocationEntity);
		userLocationEntity.setActiveFlag(CommonConstant.FLAG_ONE);
		userLocationEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);

		if (authDetailsVo.getEntityId() != null) {
			EntityLicense entityLicenseEntity = new EntityLicense();
			entityLicenseEntity.setId(authDetailsVo.getEntityId());
			userLocationEntity.setEntityLicenseEntity(entityLicenseEntity);
			userLocationEntity.setCreateBy(authDetailsVo.getUserId());
			userLocationEntity.setCreateDate(CommonConstant.getCalenderDate());
			userLocationEntity.setUpdateBy(authDetailsVo.getUserId());
			userLocationEntity.setUpdateDate(CommonConstant.getCalenderDate());
		}

		userLocationRepo.save(userLocationEntity);

	}
	

	@Transactional
	public void update(UserLocationVO userLocationMasterVo,AuthDetailsVo authDetailsVo) throws CommonException {

		UserLocation userLocationEntity = new UserLocation();
		userLocationEntity = userLocationRepo.findOne(userLocationMasterVo.getId());
		BeanUtils.copyProperties(userLocationMasterVo, userLocationEntity);
		userLocationEntity.setActiveFlag(CommonConstant.FLAG_ONE);
		userLocationEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);
		userLocationEntity.setUpdateBy(authDetailsVo.getUserId());
		userLocationEntity.setUpdateDate(CommonConstant.getCalenderDate());
		userLocationRepo.save(userLocationEntity);

	}

	@Transactional
	public void delete(UserLocationVO userLocationMasterVo,AuthDetailsVo authDetailsVo) throws CommonException, CommonException {
		UserLocation userLocationEntity = new UserLocation();
		
		boolean location = false;
		boolean locationDelete = false;

		List<String> codeList = new ArrayList<String>();

		if (null != userLocationMasterVo.getDeleteItem()) {
			for (Integer id : userLocationMasterVo.getDeleteItem()) {

				locationDelete = findUserLocation(id,authDetailsVo);
				try {

					userLocationEntity = userLocationRepo.findOne(id);

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
				if (locationDelete) {
					location = true;
					codeList.add(userLocationEntity.getUserLocationName());
					continue;
				}

				userLocationEntity.setDeleteFlag(CommonConstant.FLAG_ONE);
				userLocationEntity.setCreateBy(userLocationEntity.getCreateBy());
				// userLocationEntity.setCreatedDate(userLocationEntity.getc);
				userLocationEntity.setUpdateBy(authDetailsVo.getUserId());
				// userLocationEntity.setUpdatedDate(CommonConstant.getCalenderDate());
			}

		}
		try {
			userLocationRepo.save(userLocationEntity);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
		if (location) {
			throw new CommonException(CommonConstant.format(getMessage("unUsedRecordOnlyBeDeleted",authDetailsVo), codeList)
					);

		}
	}

	@Transactional
	public UserLocationVO view(UserLocationVO userLocation,AuthDetailsVo authDetailsVo) {

		UserLocationVO userLocationMasterVo = new UserLocationVO();
		UserLocation userLocationEntity = new UserLocation();
try{
		userLocationEntity = userLocationRepo.findOne(userLocation.getId());
		BeanUtils.copyProperties(userLocationEntity, userLocationMasterVo);
	} catch (Exception e) {
		e.printStackTrace();
		logger.error(e.getMessage());
		throw new CommonException(getMessage("dbFailure",authDetailsVo));
	}
		return userLocationMasterVo;

	}

	@Transactional
	public List<UserLocationVO> search(UserLocationVO userLocationMasterVo,AuthDetailsVo authDetailsVo)
			throws CommonException, CommonException {

		List<UserLocationVO> userLocationMasterVoList = new ArrayList<UserLocationVO>();

		List<Object[]> userLocationList = null;

		try {

			userLocationList = userLocationDAO.search(userLocationMasterVo,authDetailsVo);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dbFailure",authDetailsVo));
		}

		userLocationMasterVoList = getSearchList(userLocationList,authDetailsVo);

		return userLocationMasterVoList;

	}
	
	@Transactional
	public void duplicateLocation(UserLocationVO userLocationVo, AuthDetailsVo authDetailsVo) throws CommonException {

		try {
			int count = userLocationDAO.duplicateLocation(userLocationVo,authDetailsVo);
			if (count > 0) {
				throw new CommonException(getMessage("locationAlreadyExists",authDetailsVo));
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
	public boolean findUserLocation(int id,AuthDetailsVo authDetailsVo) throws CommonException, CommonException {
		boolean status = false;
		if (!status) {
			status = finduser(id,authDetailsVo);
		}
		if (!status) {
			status = finduserRole(id,authDetailsVo);
		}
		if (!status) {
			status = findRequestConfigExecuter(id,authDetailsVo);
		}
		if (!status) {
			status = findRequestConfigSeq(id,authDetailsVo);
		}
		if (!status) {
			status = findRequest(id,authDetailsVo);
		}
		if (!status) {
			status = findPhoneBook(id,authDetailsVo);
		}
		if (!status) {
			status = findUserMapping(id,authDetailsVo);
		}
		if (!status) {
			status = findSubLocation(id,authDetailsVo);
		}
		if (!status) {
			status = findDepartment(id,authDetailsVo);
		}
		return status;

	}


	private boolean finduser(Integer id,AuthDetailsVo authDetailsVo) throws CommonException {
		int count = 0;
		try {
			count = (int) (long) userLocationDAO.findUser(id,authDetailsVo);
			if (count > 0) {
				return true;
			}
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
	 * @throws CommonException 
	 */
	private boolean findRequestConfigExecuter(Integer id,AuthDetailsVo authDetailsVo) throws CommonException {
		int count = 0;
		try {
			count = (int) (long) userLocationDAO.findRequestConfigExecuter(id,authDetailsVo);
			if (count > 0) {
				return true;
			}
		} catch (Exception exe) {
			exe.printStackTrace();
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
		return false;
	}

	/**
	 * Method is to find user role
	 * 
	 * @param id
	 * @return
	 * @throws CommonException 
	 */
	private boolean finduserRole(Integer id,AuthDetailsVo authDetailsVo) throws CommonException {
		int count = 0;
		try {
			count = (int) (long) userLocationDAO.findUserRole(id,authDetailsVo);
			if (count > 0) {
				return true;
			}
		} catch (Exception exe) {
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
		return false;
	}

	/**
	 * Method is to find request configuration sequence
	 * 
	 * @param id
	 * @return
	 * @throws CommonException 
	 */
	private boolean findRequestConfigSeq(Integer id,AuthDetailsVo authDetailsVo) throws CommonException {
		int count = 0;
		try {
			count = userLocationDAO.findRequestConfigSeq(id,authDetailsVo);
			if (count > 0) {
				return true;
			}
		} catch (Exception exe) {
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
		return false;
	}

	/**
	 * Method is to find request
	 * 
	 * @param id
	 * @return
	 * @throws CommonException 
	 */
	private boolean findRequest(Integer id, AuthDetailsVo authDetailsVo) throws CommonException {
		int count = 0;
		try {
			count = userLocationDAO.findRequest(id,authDetailsVo);
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
	 * Method is to find phone book
	 * 
	 * @param id
	 * @return
	 * @throws CommonException 
	 */
	private boolean findPhoneBook(Integer id,AuthDetailsVo authDetailsVo) throws CommonException {
		int count = 0;
		try {
			count =  userLocationDAO.findPhoneBook(id,authDetailsVo);
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
	 * Method is to find user mapping
	 * 
	 * @param id
	 * @return
	 * @throws CommonException 
	 */
	private boolean findUserMapping(Integer id,AuthDetailsVo authDetailsVo) throws CommonException {
		int count = 0;
		try {
			count = userLocationDAO.findUserMapping(id,authDetailsVo);
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
	 * Method is to find sub location
	 * 
	 * @param id
	 * @return
	 * @throws CommonException 
	 */
	private boolean findSubLocation(Integer id,AuthDetailsVo authDetailsVo) throws CommonException {
		int count = 0;
		try {
			count = userLocationDAO.findSubLocation(id,authDetailsVo);
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
	 * Method is to find department
	 * 
	 * @param id
	 * @return
	 * @throws CommonException 
	 */
	private boolean findDepartment(Integer id,AuthDetailsVo authDetailsVo) throws CommonException {
		int count = 0;
		try {
			count = (int) (long) userLocationDAO.findDepartment(id,authDetailsVo);
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

	
}
