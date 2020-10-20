package com.srm.coreframework.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srm.coreframework.entity.UserEntity;

@Repository
public interface UserMasterRepository extends CrudRepository<UserEntity, Serializable> {
	
	
	UserEntity findByuserName(String userLoginId);
	
	@Query(value = "SELECT COUNT(userRoleEntity.id) FROM UserEntity " + " where entityLicenseEntity.id = :entityId"
			+ " and deleteFlag = '0' AND userRoleEntity.id =:role ")
	public Integer deleteUser(@Param("entityId") Integer entityId, @Param("role") Integer role);

	@Query(value = "FROM UserEntity c WHERE c.entityLicenseEntity.id = :entityId"
			+ " AND c.activeFlag = '1' AND c.deleteFlag  = '0' " + " AND userName = :loginId ")
	public List<UserEntity> uniqueUserLoginId(@Param("entityId") Integer entityId, @Param("loginId") String loginId);

	@Query(value = "FROM UserEntity c WHERE c.entityLicenseEntity.id = :entityId"
			+ " AND c.activeFlag = '1' AND c.deleteFlag  = '0' " + " AND userEmployeeId = :loginId ")
	public List<UserEntity> uniqueUserEmployeeId(@Param("entityId") Integer entityId, @Param("loginId") String loginId);
	
	@Query(value = "SELECT COUNT(c.id) FROM UserEntity c WHERE c.entityLicenseEntity.id = :entityId"
			+ " AND c.activeFlag = '1' AND c.deleteFlag  = '0'" + " AND c.emailId = :emailId ")
	public Integer getEmailUnique(@Param("entityId") Integer entityId, @Param("emailId") String emailId);

	@Query(value = "SELECT COUNT(c.id) FROM UserEntity c WHERE c.entityLicenseEntity.id = :entityId"
			+ " AND c.activeFlag = 1 AND c.deleteFlag  = '0' " + " AND c.userEmployeeId = :empId ")
	public Integer getEmployeeIdUnique(@Param("entityId") Integer entityId, @Param("empId") String empId);

}