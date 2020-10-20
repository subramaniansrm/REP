package com.srm.coreframework.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.UserMasterVO;

@Repository
public class UserMasterDAO extends CommonDAO{
	
	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Object[]> getAll(AuthDetailsVo authDetailsVo) {

		String query = " SELECT u.id,u.userName,u.userEmployeeId,u.firstName,u.middleName,"//4
				+ " u.lastName,u.password,u.emailId,u.mobile,u.phoneNumber,ul.userLocationName,"//10
				+ " sl.subLocationName,ud.userDepartmentName,ur.userRoleName ,u.langCode "//14
				+ " FROM UserEntity u , UserLocation ul,SubLocation sl, UserDepartment ud,"
				+ " UserRole ur where ul.id = u.userLocationEntity.id "
				+ "   and sl.sublocationId = u.subLocationEntity.sublocationId "
				+ "  and ud.id = u.userDepartmentEntity.id   and ur.id = u.userRoleEntity.id" + " and u.deleteFlag = '"
				+ CommonConstant.CONSTANT_ZERO + "' and u.entityLicenseEntity.id = " + authDetailsVo.getEntityId()
				/*+ " AND u.activeFlag = '" + CommonConstant.CONSTANT_ONE + "'" */
				+ " ORDER BY  u.id DESC ";
		try {
			//List<Object[]> list = (List<Object[]>) entityManager.createQuery(query).getResultList();
		Query	qry = entityManager.createQuery(query);
		List<Object[]> list = qry.getResultList();
			return list;
		} catch (Exception e) {

		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getAllSearch(UserMasterVO userMasterVo,AuthDetailsVo authDetailsVo) {

		String query = " SELECT u.id,u.userName,u.userEmployeeId,u.firstName,u.middleName,"
				+ " u.lastName,u.password,u.emailId,u.mobile,u.phoneNumber,ul.userLocationName,"
				+ " sl.subLocationName,ud.userDepartmentName,ur.userRoleName ,u.langCode "
				+ " FROM UserEntity u , UserLocation ul,SubLocation sl, UserDepartment ud,"
				+ " UserRole ur where ul.id = u.userLocationEntity.id "
				+ "   and sl.sublocationId = u.subLocationEntity.sublocationId "
				+ "  and ud.id = u.userDepartmentEntity.id   and ur.id = u.userRoleEntity.id" + " and u.deleteFlag = '"
				+ CommonConstant.CONSTANT_ZERO + "' and u.entityLicenseEntity.id = " + authDetailsVo.getEntityId();
				/*+ " AND u.activeFlag = '" + CommonConstant.CONSTANT_ONE + "'";*/

		if (userMasterVo.getUserEmployeeId() != null && !userMasterVo.getUserEmployeeId().isEmpty()) {
			query = query + " and LOWER(u.userEmployeeId) LIKE LOWER('%" + userMasterVo.getUserEmployeeId() + "%')";
		}
		if (userMasterVo.getFirstName() != null && !userMasterVo.getFirstName().isEmpty()) {
			query = query + " and LOWER(u.firstName) LIKE LOWER('%" + userMasterVo.getFirstName() + "%')";
		}
		if (userMasterVo.getUserLoginId() != null && !userMasterVo.getUserLoginId().isEmpty()) {
			query = query + " and u.userName =  '" + userMasterVo.getUserLoginId() + "'";
		}

		if (userMasterVo.getUserLocationName() != null) {
			query = query + " and LOWER(ul.userLocationName) LIKE LOWER('%" + userMasterVo.getUserLocationName()
					+ "%')";
		}

		if (userMasterVo.getSubLocationName() != null) {
			query = query + " and LOWER(sl.subLocationName) LIKE LOWER('%" + userMasterVo.getSubLocationName() + "%')";
		}

		if (userMasterVo.getMiddleName() != null && !userMasterVo.getMiddleName().isEmpty()) {
			query = query + " and LOWER(u.middleName) LIKE LOWER('%" + userMasterVo.getMiddleName() + "%')";
		}

		if (userMasterVo.getLastName() != null && !userMasterVo.getLastName().isEmpty()) {
			query = query + " and LOWER(u.lastName) LIKE LOWER('%" + userMasterVo.getLastName() + "%')";
		}

		if (userMasterVo.getUserDepartmentName() != null) {
			query = query + " and LOWER(ud.userDepartmentName) LIKE LOWER('%" + userMasterVo.getUserDepartmentName()
					+ "%')";
		}

		if (userMasterVo.getUserRoleName() != null) {
			query = query + " and LOWER(ur.userRoleName) LIKE LOWER('%" + userMasterVo.getUserRoleName() + "%')";
		}
		if (userMasterVo.getMobile() != null && !userMasterVo.getMobile().isEmpty()) {
			query = query + " and u.mobile LIKE '%" + userMasterVo.getMobile() + "%'";
		}
		if (userMasterVo.getPhoneNumber() != null && !userMasterVo.getPhoneNumber().isEmpty()) {
			query = query + " and u.phoneNumber LIKE '%" + userMasterVo.getPhoneNumber() + "%'";
		}
		if (userMasterVo.getEmailId() != null && !userMasterVo.getEmailId().isEmpty()) {
			query = query + " and LOWER(u.emailId) LIKE LOWER('%" + userMasterVo.getEmailId() + "%')";
		}

		query = query + " ORDER BY  u.id DESC";

		Query qry = entityManager.createQuery(query);

		List<Object[]> list = (List<Object[]>) qry.getResultList();

		return list;
	}

	public Object[] findByuserName(String loginId) {

		String query = "Select access_token,USER_LOGIN_ID FROM common_rta_2_local.user c WHERE "
				+ " c.ACTIVE = '1' AND c.delete_flag  = '0' " + " AND USER_LOGIN_ID = '" + loginId + "'";

		Object[] userEntity = null;

		try {
			//userEntity = (Object[]) entityManager.createNativeQuery(query).getSingleResult();
			
			Query qry = entityManager.createQuery(query);

			userEntity = (Object[]) qry.getSingleResult();

		} catch (NoResultException e) {

		}

		return userEntity;

	}
}
