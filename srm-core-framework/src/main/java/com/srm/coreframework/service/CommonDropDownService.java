package com.srm.coreframework.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.controller.CommonController;
import com.srm.coreframework.dao.CommonDropDownDAO;
import com.srm.coreframework.entity.CityEntity;
import com.srm.coreframework.entity.CountryEntity;
import com.srm.coreframework.entity.CurrencyEntity;
import com.srm.coreframework.entity.Division;
import com.srm.coreframework.entity.StateEntity;
import com.srm.coreframework.entity.UserDepartment;
import com.srm.coreframework.entity.UserEntity;
import com.srm.coreframework.entity.UserLocation;
import com.srm.coreframework.entity.UserType;
import com.srm.coreframework.repository.CurrencyRepostiory;
import com.srm.coreframework.repository.UserRepository;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.CityVO;
import com.srm.coreframework.vo.CountryVO;
import com.srm.coreframework.vo.DivisionMasterVO;
import com.srm.coreframework.vo.DropdownDepartmentVO;
import com.srm.coreframework.vo.DropdownLocationVO;
import com.srm.coreframework.vo.DropdownSubLocationVO;
import com.srm.coreframework.vo.DropdownUserMasterVO;
import com.srm.coreframework.vo.DropdownUserRoleVO;
import com.srm.coreframework.vo.ScreenJsonVO;
import com.srm.coreframework.vo.StateVO;
import com.srm.coreframework.vo.UserDepartmentVO;
import com.srm.coreframework.vo.UserMasterVO;
import com.srm.coreframework.vo.UserRoleTypeVO;
import com.srm.coreframework.vo.UserRoleVO;
@Component
public class CommonDropDownService extends CommonController {
	
	Logger logger = LoggerFactory.getLogger(CommonDropDownService.class);
	
	
	
	@Autowired
	CommonDropDownDAO commonDropDownDAO;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	CurrencyRepostiory currencyRepostiory;
	
	/**
	 * Method is used to Load the Division List
	 * 
	 * @return userDivisionMasterVo
	 */
	@Transactional
	public List<DivisionMasterVO> getAllDivision(AuthDetailsVo authDetailsVo) {

		try {

			List<DivisionMasterVO> userDivisionMasterVo = new ArrayList<DivisionMasterVO>();

			List<Division> divisionList = commonDropDownDAO.getAllDivision(authDetailsVo);

			for (Division divisionEntity : divisionList) {
				DivisionMasterVO divisionMasterVo = new DivisionMasterVO();
				divisionMasterVo.setId(divisionEntity.getId());
				divisionMasterVo.setDivision(divisionEntity.getDivision());
				userDivisionMasterVo.add(divisionMasterVo);
			}

			return userDivisionMasterVo;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
	}
	
	@Transactional
	public List<DropdownLocationVO> getAllLocation( ScreenJsonVO screenJson,AuthDetailsVo authDetailsVo) {
		try {
			List<DropdownLocationVO> dropdownLocationVOList = new ArrayList<DropdownLocationVO>();

			List<Object[]> locationList = commonDropDownDAO.getAllLocation(screenJson,authDetailsVo);

			for (Object[] userLocationEntity : locationList) {
				DropdownLocationVO dropdownLocationVO = new DropdownLocationVO();

				if (null != ((Object[]) userLocationEntity)[0]) {
					dropdownLocationVO.setId((int) ((Object[]) userLocationEntity)[0]);
				}
				if (null != (String) ((Object[]) userLocationEntity)[1]) {
					dropdownLocationVO.setUserLocationName((String) ((Object[]) userLocationEntity)[1]);
				}
				dropdownLocationVOList.add(dropdownLocationVO);
			}

			return dropdownLocationVOList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
	}
	
	/**
	 * Method is used to Load the Department List
	 * 
	 * @return userDepartmentMasterVo
	 */
	@Transactional
	public List<DropdownDepartmentVO> getAllDepartment(UserDepartmentVO departmentVo,AuthDetailsVo authDetailsVo) {

		try {

			List<DropdownDepartmentVO> dropdownDepartmentVOList = new ArrayList<DropdownDepartmentVO>();

			List<Object[]> departmentList = commonDropDownDAO.getAllDepartment(departmentVo,authDetailsVo);

			for (Object[] userDepartmentEntity : departmentList) {
				DropdownDepartmentVO dropdownDepartmentVO = new DropdownDepartmentVO();
				if (null != (Integer) ((Object[]) userDepartmentEntity)[0]) {
					dropdownDepartmentVO.setId((Integer) ((Object[]) userDepartmentEntity)[0]);
				}
				if (null != (String) ((Object[]) userDepartmentEntity)[1]) {
					dropdownDepartmentVO.setUserDepartmentName((String) ((Object[]) userDepartmentEntity)[1]);
				}
				if (null != (String) ((Object[]) userDepartmentEntity)[2]) {
					dropdownDepartmentVO.setToolTipName(dropdownDepartmentVO.getUserDepartmentName()
							.concat(" - " + (String) ((Object[]) userDepartmentEntity)[2]));
				} else {
					dropdownDepartmentVO.setToolTipName(dropdownDepartmentVO.getUserDepartmentName());
				}
				dropdownDepartmentVOList.add(dropdownDepartmentVO);
			}
			return dropdownDepartmentVOList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
	}
	/**
	 * Method is used to Load the Role List
	 * 
	 * @return userRoleMasterVo
	 */
	@Transactional
	public List<DropdownUserRoleVO> getAllRole(UserRoleVO userRoleVo,AuthDetailsVo authDetailsVo) {

		try {

			List<DropdownUserRoleVO> dropdownUserRoleVOList = new ArrayList<DropdownUserRoleVO>();

			List<Object[]> roleList = commonDropDownDAO.getAllRole(userRoleVo,authDetailsVo);

			for (Object[] userRoleEntity : roleList) {

				DropdownUserRoleVO dropdownUserRoleVO = new DropdownUserRoleVO();

				if (0 != (int) ((Object[]) userRoleEntity)[0]) {
					dropdownUserRoleVO.setId((int) ((Object[]) userRoleEntity)[0]);
				}
				if (null != (String) ((Object[]) userRoleEntity)[1]) {
					dropdownUserRoleVO.setUserRoleName((String) ((Object[]) userRoleEntity)[1]);
				}

				dropdownUserRoleVOList.add(dropdownUserRoleVO);
			}
			return dropdownUserRoleVOList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
	}
	/**
	 * Method to get User list
	 * 
	 * @return userMasterVoList
	 */
	@Transactional
	public List<DropdownUserMasterVO> getAllUser(UserMasterVO userMasterVo,AuthDetailsVo authDetailsVo) {
		try {
			List<DropdownUserMasterVO> userMasterVoList = new ArrayList<DropdownUserMasterVO>();

			List<Object> userEntityList = commonDropDownDAO.getAllUser(userMasterVo,authDetailsVo);

			for (Object userEntity : userEntityList) {
				DropdownUserMasterVO userMaster = new DropdownUserMasterVO();
				if (null != (Integer) ((Object[]) userEntity)[0]) {
					userMaster.setId((int) ((Object[]) userEntity)[0]);
				}
				if (null != (String) ((Object[]) userEntity)[1]) {
					userMaster.setFirstName((String) ((Object[]) userEntity)[1]);
				}
				userMasterVoList.add(userMaster);
			}
			return userMasterVoList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
	}
	/**
	 * Method is used to Load the Sub Location List
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public List<DropdownSubLocationVO> getAllsublocationList(int id,AuthDetailsVo authDetailsVo) {

		try {

			List<DropdownSubLocationVO> dropdownSubLocationVOList = new ArrayList<DropdownSubLocationVO>();

			List<Object[]> listSubLocationEntity = commonDropDownDAO.getAllSublocatList(id,authDetailsVo);

			for (Object[] subLocationEntityEntity : listSubLocationEntity) {

				DropdownSubLocationVO dropdownSubLocationVO = new DropdownSubLocationVO();
				if (null != (Integer) ((Object[]) subLocationEntityEntity)[0]) {
					dropdownSubLocationVO.setSublocationId((Integer) ((Object[]) subLocationEntityEntity)[0]);
				}
				if (null != (String) ((Object[]) subLocationEntityEntity)[1]) {
					dropdownSubLocationVO.setSubLocationName((String) ((Object[]) subLocationEntityEntity)[1]);
				}
				dropdownSubLocationVOList.add(dropdownSubLocationVO);
			}

			return dropdownSubLocationVOList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
	}
	/**
	 * Method is used to Load the Country List
	 * 
	 * @return CountryMasterVoList List<CountryMasterVo>
	 */
	@Transactional
	public List<CountryVO> getAllCountry(AuthDetailsVo authDetailsVo) {

		try {

			List<CountryVO> CountryMasterVoList = new ArrayList<CountryVO>();

			List<CountryEntity> countryEntityList = commonDropDownDAO.getAllCountry(authDetailsVo);

			for (CountryEntity countryEntity : countryEntityList) {
				CountryVO countryMasterVo = new CountryVO();

				countryMasterVo.setCountryId(countryEntity.getId());
				countryMasterVo.setCountryName(countryEntity.getCountry());
				CountryMasterVoList.add(countryMasterVo);
			}
			return CountryMasterVoList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}

	}
	@Transactional
	public List<DropdownUserMasterVO> getUserDep(UserMasterVO userMasterVo,AuthDetailsVo authDetailsVo) {
		try {
			List<DropdownUserMasterVO> userMasterVoList = new ArrayList<DropdownUserMasterVO>();
			List<UserEntity> userEntityList = null;

			userEntityList = commonDropDownDAO.getUserDep(userMasterVo,authDetailsVo);

			for (UserEntity userEntity : userEntityList) {
				DropdownUserMasterVO userMaster = new DropdownUserMasterVO();
				userMaster.setId(userEntity.getId());
				if (userEntity.getLastName() != null) {
					userMaster.setFirstName(userEntity.getFirstName().concat(" " + userEntity.getLastName()));
				} else {
					userMaster.setFirstName(userEntity.getFirstName());
				}

				userMasterVoList.add(userMaster);
			}
			return userMasterVoList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
	}
	
	/**
	 * Method is used to Load the Location List
	 * 
	 * @return UserRoleMaster
	 */

	@Transactional
	public List<DropdownLocationVO> getLoadUserLocationDetails(AuthDetailsVo authDetailsVo) {
		try {
			List<UserLocation> userLocation = commonDropDownDAO.getLoadUserLocationDetails(authDetailsVo);

			List<DropdownLocationVO> locationVoList = new ArrayList<DropdownLocationVO>();

			for (UserLocation userLocationEntity : userLocation) {

				DropdownLocationVO dropdownLocationVo = new DropdownLocationVO();

				dropdownLocationVo.setId(userLocationEntity.getId());
				dropdownLocationVo.setUserLocationName(userLocationEntity.getUserLocationName());
				locationVoList.add(dropdownLocationVo);

			}
			return locationVoList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
	}
	
	public List<UserRoleTypeVO> getRoleType(UserRoleTypeVO userRoleTypeVo,AuthDetailsVo authDetailsVo) {
		try {
			List<UserRoleTypeVO> userRoleTypeVoList = new ArrayList<UserRoleTypeVO>();
			List<UserType> userTypeEntityList = null;

			userTypeEntityList = commonDropDownDAO.getRoleType(userRoleTypeVo,authDetailsVo);

			for (UserType userTypeEntity : userTypeEntityList) {
				UserRoleTypeVO userRoleType = new UserRoleTypeVO();
				userRoleType.setUserTypeId(userTypeEntity.getUserTypeId());
				userRoleType.setTypeOfUser(userTypeEntity.getTypeOfUser());
				userRoleTypeVoList.add(userRoleType);
			}
			return userRoleTypeVoList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
	}
	/**
	 * Method is used to Load the Department List
	 * 
	 * @return UserRoleMaster
	 */
	@Transactional
	public List<DropdownDepartmentVO> getLoadUserDepartmentDetails(AuthDetailsVo authDetailsVo) {
		try {
			List<DropdownDepartmentVO> dropdownDepartmentVo = new ArrayList<DropdownDepartmentVO>();

			List<UserDepartment> userDepartmentEntityList = commonDropDownDAO.getLoadUserDepartmentDetails(authDetailsVo);

			for (UserDepartment userDepartmentEntity : userDepartmentEntityList) {

				DropdownDepartmentVO dropdownLocationVo = new DropdownDepartmentVO();

				dropdownLocationVo.setId(userDepartmentEntity.getId());
				dropdownLocationVo.setUserDepartmentName(userDepartmentEntity.getUserDepartmentName());

				dropdownDepartmentVo.add(dropdownLocationVo);

			}
			return dropdownDepartmentVo;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
	}
	
	/**
	 * Method is used to Load the City List
	 * 
	 * @return cityMasterVoList List<CityMasterVo>
	 */
	@Transactional
	public List<CityVO> getAllCity(AuthDetailsVo authDetailsVo) {

		try {

			List<CityVO> cityMasterVoList = new ArrayList<CityVO>();

			List<CityEntity> cityEntityList = commonDropDownDAO.getAllCity(authDetailsVo);

			for (CityEntity cityEntity : cityEntityList) {
				CityVO cityMasterVo = new CityVO();
				cityMasterVo.setCityId(cityEntity.getCityId());
				cityMasterVo.setCityName(cityEntity.getCityName());
				cityMasterVoList.add(cityMasterVo);
			}
			return cityMasterVoList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}

	}
	
	/**
	 * Method is used to Load the State List
	 * 
	 * @return stateMasterVoList List<StateMasterVo>
	 */
	@Transactional
	public List<StateVO> getAllState(AuthDetailsVo authDetailsVo) {

		try {

			List<StateVO> stateMasterVoList = new ArrayList<StateVO>();

			List<StateEntity> stateEntityList = commonDropDownDAO.getAllState(authDetailsVo);

			for (StateEntity stateEntity : stateEntityList) {
				StateVO stateMasterVo = new StateVO();
				stateMasterVo.setStateId(stateEntity.getStateId());
				stateMasterVo.setStateName(stateEntity.getStateName());
				stateMasterVoList.add(stateMasterVo);
			}
			return stateMasterVoList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("dataFailure",authDetailsVo));
		}
	}
	
	/**
	 * Method is used to Load the State List
	 * 
	 * @return stateMasterVoList List<StateMasterVo>
	 */
	@Transactional
	public List<CurrencyEntity> currencyLoad() {

		return	currencyRepostiory.currencyList();
	}
	
	
}
