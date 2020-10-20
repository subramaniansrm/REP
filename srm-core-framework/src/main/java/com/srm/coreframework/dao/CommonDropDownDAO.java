package com.srm.coreframework.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.srm.coreframework.entity.CityEntity;
import com.srm.coreframework.entity.CountryEntity;
import com.srm.coreframework.entity.Division;
import com.srm.coreframework.entity.StateEntity;
import com.srm.coreframework.entity.UserDepartment;
import com.srm.coreframework.entity.UserEntity;
import com.srm.coreframework.entity.UserLocation;
import com.srm.coreframework.entity.UserType;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.ScreenJsonVO;
import com.srm.coreframework.vo.UserDepartmentVO;
import com.srm.coreframework.vo.UserMasterVO;
import com.srm.coreframework.vo.UserRoleTypeVO;
import com.srm.coreframework.vo.UserRoleVO;

@Repository
public class CommonDropDownDAO extends CommonDAO {

	/**
	 * Method is used for Load the location
	 * 
	 * @return list List<UserLocationEntity>
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getAllLocation(ScreenJsonVO screenJson,AuthDetailsVo authDetailsVo) {

		try {
			String query = "SELECT c.id,c.userLocationName FROM UserLocation c WHERE  c.entityLicenseEntity.id = "
					+ authDetailsVo.getEntityId() + " AND c.activeFlag = '" + CommonConstant.CONSTANT_ONE
					+ "' AND c.deleteFlag  = '" + CommonConstant.FLAG_ZERO + "' ORDER BY id DESC ";

			List<Object[]> list = (List<Object[]>) getEntityManager().createQuery(query).getResultList();

			return list;
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * Method is used for Load the Division
	 * 
	 * @return list List<DivisionEntity>
	 */
	@SuppressWarnings("unchecked")
	public List<Division> getAllDivision(AuthDetailsVo authDetailsVo) {

		String query = "FROM Division c WHERE   c.entityLicenseEntity.id = " + authDetailsVo.getEntityId()
				+ " AND c.deleteFlag  = '" + CommonConstant.FLAG_ZERO + "' ORDER BY id DESC ";

		try {
			List<Division> list = (List<Division>) getEntityManager().createQuery(query).getResultList();
			return list;

		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();

		}

		return null;
	}

	/**
	 * Method is used for Load the Department
	 * 
	 * @return list List<UserDepartmentEntity>
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getAllDepartment(UserDepartmentVO departmentVo,AuthDetailsVo authDetailsVo) {

		List<Object[]> list = null;

		String query = "select c.id,c.userDepartmentName,c.subLocationEntity.subLocationName FROM UserDepartment c WHERE  c.entityLicenseEntity.id = "
				+ authDetailsVo.getEntityId() + " AND c.deleteFlag  = '" + CommonConstant.FLAG_ZERO + "'";

		if (departmentVo.getUserLocation() != null && departmentVo.getUserLocation() != null) {

			query = query + " and c.userLocationEntity.id = " + departmentVo.getUserLocation() + "";
		}
		if (departmentVo.getSublocationId() != null) {

			query = query + " and c.subLocationEntity.sublocationId = " + departmentVo.getSublocationId() + "";
		}

		query = query + " ORDER BY c.id DESC ";

		try {
			list = (List<Object[]>) getEntityManager().createQuery(query).getResultList();
			return list;

		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Method is used for Load the role
	 * 
	 * @return list List<UserRoleEntity>
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getAllRole(UserRoleVO userRoleVo,AuthDetailsVo authDetailsVo) {

		String query = "SELECT c.id,c.userRoleName FROM UserRole c WHERE  c.entityLicenseEntity.id = "
				+ authDetailsVo.getEntityId() + " AND c.deleteFlag  = '" + CommonConstant.FLAG_ZERO + "'";

		if (userRoleVo.getUserLocation() != null && userRoleVo.getUserLocation() != null) {

			query = query + " and c.userLocationEntity.id = " + userRoleVo.getUserLocation();
		}

		if (userRoleVo.getSublocationId() != null) {

			query = query + " and c.subLocationEntity.sublocationId = " + userRoleVo.getSublocationId();
		}

		if (userRoleVo.getUserDepartment() != null && userRoleVo.getUserDepartment() != null) {

			query = query + " and c.userDepartmentEntity.id = " + userRoleVo.getUserDepartment();
		}

		query = query + " ORDER BY c.id DESC ";

		List<Object[]> list = (List<Object[]>) getEntityManager().createQuery(query).getResultList();

		return list;
	}

	/**
	 * Method to return user list
	 * 
	 * @param userMasterVo
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getAllUser(UserMasterVO userMasterVo,AuthDetailsVo authDetailsVo) {

		String query = "select USER_ID,concat(FIRST_NAME,' ',LAST_NAME )FROM user c WHERE " + "  c.ACTIVE = '"
				+ CommonConstant.CONSTANT_ONE + "' AND c.delete_flag  = '" + CommonConstant.FLAG_ZERO
				+ "' and c.rin_ma_entity_id = " + authDetailsVo.getEntityId();

		if (userMasterVo.getUserLocation() != null && userMasterVo.getUserLocation() != 0) {

			query = query + " and c.USER_LOCATION_ID = " + userMasterVo.getUserLocation();
		}
		if (userMasterVo.getSubLocation() != null && userMasterVo.getSubLocation() != 0) {

			query = query + " and c.USER_SUBLOCATION_ID = " + userMasterVo.getSubLocation();
		}else if (userMasterVo.getSublocationId() != null &&  userMasterVo.getSublocationId() != 0){
			query = query + " and c.USER_SUBLOCATION_ID = " + userMasterVo.getSublocationId();
		}
		if (userMasterVo.getUserDepartment() != null && userMasterVo.getUserDepartment() != 0) {

			query = query + " and c.USER_DEPARTMENT_ID = " + userMasterVo.getUserDepartment();
		}
		if (userMasterVo.getUserRole() != null && userMasterVo.getUserRole() != 0) {

			query = query + " and c.USER_ROLE_ID= " + userMasterVo.getUserRole();
		}

		query = query + " ORDER BY USER_ID DESC ";

		List<Object> list = (List<Object>) getEntityManager().createNativeQuery(query).getResultList();

		return list;
	}

	/**
	 * Method is used for Load Sub location list
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getAllSublocatList(int id,AuthDetailsVo authDetailsVo) {
		String query = "SELECT idrin_ma_sublocation_sublocationId,rin_ma_sublocation_name "
				+ " FROM "+getCommonDatabaseSchema()+".rin_ma_sublocation " + " WHERE delete_flag = 0 "
				+ " AND rin_ma_sublocation_subLocationIsActive = 1 " + " and rin_ma_entity_id ="
				+ authDetailsVo.getEntityId();

		if (id != 0) {

			query = query + " AND rin_ma_sublocation_locationId =" + id;
		}

		query = query + " ORDER BY idrin_ma_sublocation_sublocationId DESC";

		List<Object[]> subLocationEntityList = (List<Object[]>) getEntityManager().createNativeQuery(query)
				.getResultList();
		return subLocationEntityList;

	}
	/**
	 * Method is used for Load the Country.
	 * 
	 * @return list List<CountryEntity>
	 */
	@SuppressWarnings("unchecked")
	public List<CountryEntity> getAllCountry(AuthDetailsVo authDetailsVo) {

		String query = "FROM CountryEntity c WHERE c.deleteFlag  = '" + CommonConstant.FLAG_ZERO + "' ORDER BY c.id DESC ";

		List<CountryEntity> list = (List<CountryEntity>) getEntityManager().createQuery(query).getResultList();

		return list;
	}
	@SuppressWarnings("unchecked")
	public List<UserEntity> getUserDep(UserMasterVO userMasterVo,AuthDetailsVo authDetailsVo) {
		String query = "FROM UserEntity c WHERE c.entityLicenseEntity.id = " + authDetailsVo.getEntityId()
				+ "AND c.userDepartmentEntity.id = " + userMasterVo.getUserDepartment() + " AND c.activeFlag = '"
				+ CommonConstant.CONSTANT_ONE + "' AND c.deleteFlag  = '" + CommonConstant.FLAG_ZERO
				+ "' ORDER BY id DESC ";
		List<UserEntity> list = (List<UserEntity>) getEntityManager().createQuery(query).getResultList();
		return list;
	}
	/**
	 * method to load userLocation
	 * 
	 * @return UserRoleMaster
	 */

	@SuppressWarnings("unchecked")
	public List<UserLocation> getLoadUserLocationDetails(AuthDetailsVo authDetailsVo) {

		String query = "FROM UserLocation c WHERE c.entityLicenseEntity.id = " + authDetailsVo.getEntityId()
				+ " AND c.activeFlag = '" + CommonConstant.CONSTANT_ONE + "' AND c.deleteFlag  = '"
				+ CommonConstant.FLAG_ZERO + "' ORDER BY id DESC ";

		List<UserLocation> userLocationEntityList = (List<UserLocation>) getEntityManager().createQuery(query).getResultList();

		return userLocationEntityList;
	}
	public List<UserType> getRoleType(UserRoleTypeVO userRoleTypeVo,AuthDetailsVo authDetailsVo) {
		String query = "FROM UserType c WHERE c.entityLicenseEntity.id = " + authDetailsVo.getEntityId()
				+ " ORDER BY c.userTypeId DESC ";
		@SuppressWarnings("unchecked")
		List<UserType> list = (List<UserType>) getEntityManager().createQuery(query).getResultList();
		return list;
	}
	/**
	 * method to load userDepartment
	 * 
	 * @return UserRoleMaster
	 */
	@SuppressWarnings("unchecked")
	public List<UserDepartment> getLoadUserDepartmentDetails(AuthDetailsVo authDetailsVo) {

		String query = "FROM UserDepartment c WHERE c.entityLicenseEntity.id = " + authDetailsVo.getEntityId()
				+ " AND c.deleteFlag  = '" + CommonConstant.FLAG_ZERO + "' ORDER BY id DESC ";

		List<UserDepartment> userDepartmentEntityList = (List<UserDepartment>) getEntityManager().createQuery(query)
				.getResultList();

		return userDepartmentEntityList;
	}
	
	/**
	 * Method is used for Load the City.
	 * 
	 * @return list List<CityEntity>
	 */
	@SuppressWarnings("unchecked")
	public List<CityEntity> getAllCity(AuthDetailsVo authDetailsVo) {

		String query = "FROM CityEntity  ORDER BY  id DESC ";

		List<CityEntity> list = (List<CityEntity>) getEntityManager().createQuery(query).getResultList();

		return list;
	}
	/**
	 * Method is used for Load the State
	 * 
	 * @return list List<StateEntity>
	 */
	@SuppressWarnings("unchecked")
	public List<StateEntity> getAllState(AuthDetailsVo authDetailsVo) {

		String query = "FROM StateEntity c  ORDER BY id DESC ";

		List<StateEntity> list = (List<StateEntity>) getEntityManager().createQuery(query).getResultList();

		return list;
	}
	
	
	
}
