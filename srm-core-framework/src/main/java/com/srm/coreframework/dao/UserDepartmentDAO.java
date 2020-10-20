package com.srm.coreframework.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.srm.coreframework.entity.UserDepartment;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.UserDepartmentVO;

@Repository
public class UserDepartmentDAO extends CommonDAO{
	
	@SuppressWarnings("unchecked")
	public List<UserDepartment> searchDepartment(UserDepartmentVO userDepartmentMasterVo,AuthDetailsVo authDetailsVo) {

		String query = "FROM UserDepartment c WHERE c.deleteFlag  = '" + CommonConstant.FLAG_ZERO +"'"
		+ " and c.entityLicenseEntity.id = " + authDetailsVo.getEntityId();

		if (userDepartmentMasterVo.getUserDepartmentName() != null
				&& !userDepartmentMasterVo.getUserDepartmentName().isEmpty())
			query = query + " AND LOWER(c.userDepartmentName) LIKE LOWER('%"
					+ userDepartmentMasterVo.getUserDepartmentName() + "%')";

		if (userDepartmentMasterVo.getUserLocation() != null) {
			query = query + " AND c.userLocationEntity.id = '" + userDepartmentMasterVo.getUserLocation() + "'";

		}

		if (userDepartmentMasterVo.getUserLocationName() != null
				&& !userDepartmentMasterVo.getUserLocationName().isEmpty()) {
			query = query + " AND LOWER(c.userLocationEntity.userLocationName) LIKE LOWER('%"
					+ userDepartmentMasterVo.getUserLocationName() + "%')";
		}
		
		if (userDepartmentMasterVo.getSublocationName() != null
				&& !userDepartmentMasterVo.getSublocationName().isEmpty()) {
			query = query + " AND LOWER(c.subLocationEntity.subLocationName) LIKE LOWER('%"
					+ userDepartmentMasterVo.getSublocationName() + "%')";
		}
		if (userDepartmentMasterVo.getDescription() != null && !userDepartmentMasterVo.getDescription().isEmpty()) {
			query = query + " AND LOWER(c.description) LIKE LOWER('%" + userDepartmentMasterVo.getDescription() + "%')";
		}

		List<UserDepartment> userDepartmentEntity = (List<UserDepartment>) getEntityManager()
				.createQuery(query).getResultList();
		return userDepartmentEntity;

	}

	public int duplicateDepartment(UserDepartmentVO userDepartmentMasterVo,AuthDetailsVo authDetailsVo) {
		int count = 0;

		String query = "SELECT COUNT(id) FROM UserDepartment"
				+ " where "
				+ " entityLicenseEntity.id = " + authDetailsVo.getEntityId() +" and  "
				+ " deleteFlag = " + CommonConstant.CONSTANT_ZERO
				+ " AND LOWER(userDepartmentName) = LOWER('" + userDepartmentMasterVo.getUserDepartmentName().trim()
				+ "') and userLocationEntity.id = " + userDepartmentMasterVo.getUserLocation()
				+ " AND subLocationEntity.sublocationId =" + userDepartmentMasterVo.getSublocationId();
		StringBuffer modifiedQuery = new StringBuffer(query);
		if (null != userDepartmentMasterVo.getId()) {
			modifiedQuery.append(" AND id != " + userDepartmentMasterVo.getId());
		}

		count = (int) (long) getEntityManager().createQuery(modifiedQuery.toString()).getSingleResult();

		return count;

	}
	
	
	public int findRequestWorkFlowSeqDep(Integer integer,AuthDetailsVo authDetailsVo) {
		int count = 0;
		String query = "SELECT COUNT(u.userDepartmentId) FROM RequestWorkFlowSeqEntity u where deleteFlag = "
				+ CommonConstant.CONSTANT_ZERO + " AND u.userDepartmentId = " + integer
				+ " AND  u.entityLicenseId = " + authDetailsVo.getEntityId();
		count = (int) (long) getEntityManager().createQuery(query).getSingleResult();

		return count;
	}

	public int findRequestWorkFlowDep(Integer integer,AuthDetailsVo authDetailsVo) {
		int count = 0;
		String query = "SELECT COUNT(u.workFlowDepartmentId) FROM RequestWorkFlowEntity u  where deleteFlag = "
				+ CommonConstant.CONSTANT_ZERO + " AND u.workFlowDepartmentId = " + integer
				+ " AND  u.entityLicenseId = " + authDetailsVo.getEntityId();;
		count = (int) (long) getEntityManager().createQuery(query).getSingleResult();

		return count;
	}
	
	/**
	 * Method is to find request configuration executer
	 * 
	 * @param id
	 * @return
	 */
	public int findRequestConfigExecuter(Integer id,AuthDetailsVo authDetailsVo) {
		int count = 0;

		String query = "SELECT COUNT(reqWorkFlowExecuterId) FROM RequestWorkFlowExecuterEntity  where deleteFlag = "
				+ CommonConstant.CONSTANT_ZERO + " AND executerDepartmentId = " + id 
				+ " and entityLicenseId = " + authDetailsVo.getEntityId() ;
		count = (int) (long) getEntityManager().createQuery(query).getSingleResult();

		return count;

	}

	public int findRequestDep(Integer integer,AuthDetailsVo authDetailsVo) {
		int count = 0;
		String query = "SELECT COUNT(u.departmentId) FROM RequestEntity u  where deleteFlag = "
				+ CommonConstant.CONSTANT_ZERO + " AND u.departmentId = " + integer+ " "
				+ "and u.entityLicenseId = " + authDetailsVo.getEntityId() ;
		count = (int) (long) getEntityManager().createQuery(query).getSingleResult();

		return count;
	}

	public int findUserRole(Integer integer,AuthDetailsVo authDetailsVo) {
		int count = 0;
		String query = "SELECT COUNT(u.userDepartmentEntity.id) FROM UserRole u  where deleteFlag = "
				+ CommonConstant.CONSTANT_ZERO + " AND u.userDepartmentEntity.id = " + integer
				+ "AND u.entityLicenseEntity.id =" + authDetailsVo.getEntityId() ;
		count = (int) (long) getEntityManager().createQuery(query).getSingleResult();

		return count;
	}

	public int finduser(Integer integer,AuthDetailsVo authDetailsVo) {
		int count = 0;
		String query = "SELECT COUNT(u.userDepartmentEntity.id) FROM UserEntity u  where deleteFlag = "
				+ CommonConstant.CONSTANT_ZERO + " AND u.userDepartmentEntity.id = " + integer
				+ " AND u.entityLicenseEntity.id =" + authDetailsVo.getEntityId() ;
		count = (int) (long) getEntityManager().createQuery(query).getSingleResult();

		return count;
	}


	public int findPhoneBookDep(Integer integer, AuthDetailsVo authDetailsVo) {
		int count = 0;
		String query = "SELECT COUNT(u.userDepartmentId) FROM PhoneBookEntity u  where deleteFlag = "
				+ CommonConstant.CONSTANT_ZERO + " AND u.userDepartmentId = " + integer +" and u.entityLicenseId = '"+ authDetailsVo.getEntityId()+"'";
		count = (int) (long) getEntityManager().createQuery(query).getSingleResult();

		return count;
	}

	/**
	 * Method used to delete department
	 * 
	 * @param integer
	 * @return
	 */
	public int findUserMappingDep(Integer integer,AuthDetailsVo authDetailsVo) {
		int count = 0;
		String query = "SELECT COUNT(u.reportingUserDepartmentEntity.id) FROM UserMappingEntity u  where deleteFlag = "
				+ CommonConstant.CONSTANT_ZERO + " AND u.reportingUserDepartmentEntity.id = " + integer 
				+ " AND  u.entityLicenseEntity.id = " + authDetailsVo.getEntityId();
		count = (int) (long) getEntityManager().createQuery(query).getSingleResult();

		return count;
	}
}
