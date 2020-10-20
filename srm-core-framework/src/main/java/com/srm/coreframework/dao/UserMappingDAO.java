package com.srm.coreframework.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.UserMappingVO;

@Repository
public class UserMappingDAO extends CommonDAO{


	@SuppressWarnings("unchecked")
	public List<Object[]> getAllSearch(UserMappingVO userMappingVo,AuthDetailsVo authDetailsVo) {

		String query = " SELECT u.USER_MAPPING_ID,u.USER_LOCATION_ID,loc.USER_LOCATION_NAME as locname,u.USER_SUBLOCATION_ID,"
				+ " sub.rin_ma_sublocation_name as subname,"
				+ " u.USER_DEPARTMENT_ID,dep.USER_DEPARTMENT_NAME as deptname ,u.USER_ROLE_ID,ur.USER_ROLE_NAME,u.LEVEL,com.ITEM_VALUE,"
				+ " u.REPORTING_TO_LOCATION,u.REPORTING_TO_SUBLOCATION,u.REPORTING_DEPARTMENT,u.REPORTING_TO,u.USER_ID,"
				+ " CONCAT(us.FIRST_NAME,' ',us.LAST_NAME) as userName,rloc.USER_LOCATION_NAME as rlocname,"
				+ " rsub.rin_ma_sublocation_name as rsubname,udep.USER_DEPARTMENT_NAME as rdeptname,"
				+ " CONCAT(urt.FIRST_NAME,' ',urt.LAST_NAME) as reportingUserName FROM user_mapping u "
				+ " LEFT JOIN common_storage com ON com.COMMON_ID =  u.LEVEL "
				+ " LEFT JOIN rin_ma_sublocation sub ON sub.idrin_ma_sublocation_sublocationId = u.USER_SUBLOCATION_ID "
				+ " LEFT JOIN user_location loc ON loc.USER_LOCATION_ID = u.USER_LOCATION_ID "
				+ " LEFT JOIN user_department dep ON dep.USER_DEPARTMENT_ID = u.USER_DEPARTMENT_ID "
				+ " LEFT JOIN user_role ur ON ur.ROLE_ID = u.USER_ROLE_ID "
				+ " LEFT JOIN user_location rloc ON rloc.USER_LOCATION_ID = u.REPORTING_TO_LOCATION "
				+ " LEFT JOIN rin_ma_sublocation rsub ON rsub.idrin_ma_sublocation_sublocationId  = u.REPORTING_TO_SUBLOCATION "
				+ " LEFT JOIN user_department udep ON udep.USER_DEPARTMENT_ID = u.REPORTING_DEPARTMENT "
				+ " LEFT JOIN `user` us ON us.USER_ID = u.USER_ID "
				+ " LEFT JOIN `user` urt ON urt.USER_ID = u.REPORTING_TO " + " WHERE  u.rin_ma_entity_id = '"+ authDetailsVo.getEntityId()+"'"
						+ "and u.delete_flag = '0' ";// AND
																											// u.SYS_APP_ID
																											// =
																											// 2
																											// ";

		StringBuffer modifiedQuery = new StringBuffer(query);

		if (userMappingVo.getUserLocationName() != null && !userMappingVo.getUserLocationName().isEmpty()) {
			modifiedQuery.append(
					" and LOWER(loc.USER_LOCATION_NAME) LIKE LOWER('%" + userMappingVo.getUserLocationName() + "%')");
		}

		if (userMappingVo.getSubLocationName() != null && !userMappingVo.getSubLocationName().isEmpty()) {
			modifiedQuery.append(" and LOWER(sub.rin_ma_sublocation_name) LIKE LOWER('%"
					+ userMappingVo.getSubLocationName() + "%')");
		}

		if (userMappingVo.getReportingLocationName() != null && !userMappingVo.getReportingLocationName().isEmpty()) {
			modifiedQuery.append(" and LOWER(rloc.USER_LOCATION_NAME) LIKE LOWER('%"
					+ userMappingVo.getReportingLocationName() + "%')");
		}

		if (userMappingVo.getReportingSubLocationName() != null
				&& !userMappingVo.getReportingSubLocationName().isEmpty()) {
			modifiedQuery.append(" and LOWER(rsub.rin_ma_sublocation_name as rsubname) LIKE LOWER('%"
					+ userMappingVo.getReportingSubLocationName() + "%')");
		}

		if (userMappingVo.getLevelName() != null) {
			modifiedQuery.append(" and LOWER(com.ITEM_VALUE) LIKE LOWER('%" + userMappingVo.getLevelName() + "%')");
		}

		if (userMappingVo.getUserDepartmentName() != null && !userMappingVo.getUserDepartmentName().isEmpty()) {

			modifiedQuery.append(" and LOWER(dep.USER_DEPARTMENT_NAME) LIKE LOWER('%"
					+ userMappingVo.getUserDepartmentName() + "%')");
		}
		if (userMappingVo.getUserRoleName() != null && !userMappingVo.getUserRoleName().isEmpty()) {

			modifiedQuery
					.append(" and LOWER(ur.USER_ROLE_NAME) LIKE LOWER('%" + userMappingVo.getUserRoleName() + "%')");
		}
		if (userMappingVo.getUserName() != null && !userMappingVo.getUserName().isEmpty()) {

			modifiedQuery.append(" and LOWER(CONCAT(us.FIRST_NAME,' ',us.LAST_NAME)) LIKE LOWER('%"
					+ userMappingVo.getUserName() + "%')");
		}
		if (userMappingVo.getReportingDepartmentName() != null
				&& !userMappingVo.getReportingDepartmentName().isEmpty()) {

			modifiedQuery.append(" and LOWER(udep.USER_DEPARTMENT_NAME) LIKE LOWER('%"
					+ userMappingVo.getReportingDepartmentName() + "%')");
		}
		if (userMappingVo.getReportingToUserName() != null && !userMappingVo.getReportingToUserName().isEmpty()) {

			modifiedQuery.append(" and LOWER(CONCAT(urt.FIRST_NAME,' ',urt.LAST_NAME)) LIKE LOWER('%"
					+ userMappingVo.getReportingToUserName() + "%')");
		}

		modifiedQuery.append("ORDER BY u.USER_MAPPING_ID DESC");

		List<Object[]> userMappingEntityList = (List<Object[]>) getEntityManager()
				.createNativeQuery(modifiedQuery.toString()).getResultList();

		return userMappingEntityList;
	}

	public int UserMapping(UserMappingVO userMappingVo) {
		String query = "SELECT COUNT(u.userMappingId) FROM UserMappingEntity u WHERE" + " u.userLocationEntity.id = "
				+ userMappingVo.getUserLocationId() + " AND u.userDepartmentEntity.id = "
				+ userMappingVo.getUserDepartmentId() + "  AND u.userRoleEntity.id = " + userMappingVo.getUserRoleId()
				+ " AND u.level.commonId = " + userMappingVo.getLevelId() + " AND u.reportingUserDepartmentEntity.id = "
				+ userMappingVo.getReportingUserDepartment() + " AND u.reportingToUser.id = "
				+ userMappingVo.getReportingToUser() + " AND u.userEntity.id = " + userMappingVo.getUserId()
				+ " AND u.deleteFlag  = '" + CommonConstant.FLAG_ZERO + "'";

		StringBuffer modifiedQuery = new StringBuffer(query);
		if (null != userMappingVo.getUserMappingId() && 0 != userMappingVo.getUserMappingId()) {
			modifiedQuery.append(" AND u.userMappingId != " + userMappingVo.getUserMappingId());
		}

		int count = (int) (long) getEntityManager().createQuery(modifiedQuery.toString()).getSingleResult();
		return count;

	}
 
	@SuppressWarnings("unchecked")
	public List<Object[]> getAll(AuthDetailsVo authDetailsVo) {

		List<Object[]> userMappingEntityList = null;

		try {
			String query = " SELECT u.USER_MAPPING_ID,u.USER_LOCATION_ID,loc.USER_LOCATION_NAME as locname,u.USER_SUBLOCATION_ID,"
					+ " sub.rin_ma_sublocation_name as subname,"
					+ " u.USER_DEPARTMENT_ID,dep.USER_DEPARTMENT_NAME as deptname ,u.USER_ROLE_ID,ur.USER_ROLE_NAME,u.LEVEL,com.ITEM_VALUE,"
					+ " u.REPORTING_TO_LOCATION,u.REPORTING_TO_SUBLOCATION,u.REPORTING_DEPARTMENT,u.REPORTING_TO,u.USER_ID,"
					+ " CONCAT(us.FIRST_NAME,' ',us.LAST_NAME) as userName,rloc.USER_LOCATION_NAME as rlocname,"
					+ " rsub.rin_ma_sublocation_name as rsubname,udep.USER_DEPARTMENT_NAME as rdeptname,"
					+ " CONCAT(urt.FIRST_NAME,' ',urt.LAST_NAME) as reportingUserName FROM common_rta_2_local.user_mapping u "
					+ " LEFT JOIN "+getCommonDatabaseSchema()+".common_storage com ON com.COMMON_ID =  u.LEVEL "
					+ " LEFT JOIN "+getCommonDatabaseSchema()+".rin_ma_sublocation sub ON sub.idrin_ma_sublocation_sublocationId = u.USER_SUBLOCATION_ID "
					+ " LEFT JOIN "+getCommonDatabaseSchema()+".user_location loc ON loc.USER_LOCATION_ID = u.USER_LOCATION_ID "
					+ " LEFT JOIN "+getCommonDatabaseSchema()+".user_department dep ON dep.USER_DEPARTMENT_ID = u.USER_DEPARTMENT_ID "
					+ " LEFT JOIN "+getCommonDatabaseSchema()+".user_role ur ON ur.ROLE_ID = u.USER_ROLE_ID "
					+ " LEFT JOIN "+getCommonDatabaseSchema()+".user_location rloc ON rloc.USER_LOCATION_ID = u.REPORTING_TO_LOCATION "
					+ " LEFT JOIN "+getCommonDatabaseSchema()+".rin_ma_sublocation rsub ON rsub.idrin_ma_sublocation_sublocationId  = u.REPORTING_TO_SUBLOCATION "
					+ " LEFT JOIN "+getCommonDatabaseSchema()+".user_department udep ON udep.USER_DEPARTMENT_ID = u.REPORTING_DEPARTMENT "
					+ " LEFT JOIN "+getCommonDatabaseSchema()+".`user` us ON us.USER_ID = u.USER_ID "
					+ " LEFT JOIN "+getCommonDatabaseSchema()+".`user` urt ON urt.USER_ID = u.REPORTING_TO " + " WHERE u.rin_ma_entity_id = '"
					+ authDetailsVo.getEntityId() + "' and " + " u.delete_flag = '0'  ORDER BY u.USER_MAPPING_ID DESC";

			userMappingEntityList = (List<Object[]>) getEntityManager().createNativeQuery(query).getResultList();
			return userMappingEntityList;
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
		return userMappingEntityList;
	}

	public int findDuplicate(UserMappingVO userMappingVo) {
		int count = 0;

		String query = "SELECT COUNT(userMappingId) FROM UserMappingEntity where  userEntity.id = "
				+ userMappingVo.getUserId() + " " + "  and deleteFlag ='0' ";

		StringBuffer modifiedQuery = new StringBuffer(query);
		
		if (null != userMappingVo.getUserMappingId() && 0 != userMappingVo.getUserMappingId() ) {
			modifiedQuery.append(" AND userMappingId != " + userMappingVo.getUserMappingId());
		}

		count = (int) (long) getEntityManager().createQuery(modifiedQuery.toString()).getSingleResult();

		return count;

	}
}
