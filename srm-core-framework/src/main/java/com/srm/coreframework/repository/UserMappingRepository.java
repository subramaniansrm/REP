package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.srm.coreframework.entity.UserDepartment;
import com.srm.coreframework.entity.UserMappingEntity;

public interface UserMappingRepository  extends JpaRepository<UserMappingEntity, Integer>  {
	
	
	@Query(value = "SELECT COUNT(userRoleEntity.id) FROM UserMappingEntity  where entityLicenseEntity.id =:entityId "
			+ " and deleteFlag = '0' AND userRoleEntity.id =:role")
	public Integer deleteUserMapping(@Param("entityId") Integer entityId, @Param("role") Integer role);
	
	/*@Query(value = " SELECT u.USER_MAPPING_ID,u.USER_LOCATION_ID,loc.USER_LOCATION_NAME as locname,u.USER_SUBLOCATION_ID,"
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
			+ " LEFT JOIN `user` urt ON urt.USER_ID = u.REPORTING_TO "
			+ " WHERE u.rin_ma_entity_id = :entityId and "
			+ " u.DELETE_FLAG = '0'  ORDER BY u.USER_MAPPING_ID DESC",nativeQuery = true)
	public List<Object[]> getAll(@Param("entityId") Integer entityId);*/

}
