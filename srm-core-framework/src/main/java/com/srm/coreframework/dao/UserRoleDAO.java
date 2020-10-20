package com.srm.coreframework.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.srm.coreframework.entity.UserRole;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.UserRoleVO;

@Repository
public class UserRoleDAO extends CommonDAO{
	
	public int duplicateRole(UserRoleVO userRoleMaster,AuthDetailsVo authDetailsVo) {
		int count = 0;

		String query = "SELECT COUNT(id) FROM UserRole " + " where "
				+ "entityLicenseEntity.id = "+ authDetailsVo.getEntityId() + " AND "
				+ " deleteFlag = " + CommonConstant.CONSTANT_ZERO
				+ " AND LOWER(userRoleName) = LOWER('" + userRoleMaster.getUserRoleName().trim()
				+ "') and userDepartmentEntity.id = " + userRoleMaster.getUserDepartment();
		StringBuffer modifiedQuery = new StringBuffer(query);
		if (null != userRoleMaster.getId()) {
			modifiedQuery.append(" AND id != " + userRoleMaster.getId());
		}

		count = (int) (long) getEntityManager().createQuery(modifiedQuery.toString()).getSingleResult();

		return count;

	}
	
	
	@SuppressWarnings("unchecked")
	public List<UserRole> search(UserRoleVO searchUserRoleMaster,AuthDetailsVo authDetailsVo) {

		String query = " SELECT ur FROM UserRole ur"
			+ " WHERE ur.deleteFlag = '0' "
			+ " AND ur.entityLicenseEntity.id = " + authDetailsVo.getEntityId(); 
				
				

		if (searchUserRoleMaster.getUserRoleName() != null && !searchUserRoleMaster.getUserRoleName().isEmpty()) {
			query = query + " and LOWER(ur.userRoleName) LIKE LOWER('%" + searchUserRoleMaster.getUserRoleName() + "%')";
		}
		if (searchUserRoleMaster.getUserDepartmentName() != null) {
			query = query + " and LOWER(ur.userDepartmentEntity.userDepartmentName) LIKE LOWER('%"
					+ searchUserRoleMaster.getUserDepartmentName() + "%')";
		}
		if (searchUserRoleMaster.getUserLocationName() != null) {
			query = query + " and LOWER(ur.userLocationEntity.userLocationName) LIKE LOWER('%"
					+ searchUserRoleMaster.getUserLocationName() + "%')";
		}
		
		
		if (searchUserRoleMaster.getSublocationName() != null) {
			query = query + " and LOWER(ur.subLocationEntity.subLocationName) LIKE LOWER('%"
					+ searchUserRoleMaster.getSublocationName() + "%')";
		}

		
		if (searchUserRoleMaster.getRoleTypeName() != null) {
			query = query + " and LOWER(ur.userTypeEntity.typeOfUser) LIKE LOWER('%"
					+ searchUserRoleMaster.getRoleTypeName() + "%')";
		}

		
		if (searchUserRoleMaster.getDescription() != null && !searchUserRoleMaster.getDescription().isEmpty()) {
			query = query + " and LOWER(ur.description) LIKE LOWER('%" + searchUserRoleMaster.getDescription() + "%')";
		}
		query = query + " ORDER BY ur.id desc";

		List<UserRole> list = (List<UserRole>) getEntityManager().createQuery(query).getResultList();

		return list;
	}
	
	
}
