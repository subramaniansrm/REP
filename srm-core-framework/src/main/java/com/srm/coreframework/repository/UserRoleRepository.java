package com.srm.coreframework.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.srm.coreframework.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

	@Query(value = " SELECT ur FROM UserRole ur"
			+ " WHERE ur.deleteFlag = '0' "
			+ " and ur.entityLicenseEntity.id =:entityId " + " ORDER BY ur.id desc")
	public List<UserRole> getUserRoleList(@Param("entityId") Integer entityId);

	
	@Query(value =" select r.id,r.userRoleName from UserRole r where r.deleteFlag='0' and r.entityLicenseEntity.id=:entityId ")
	public Map<Integer, String> loadRoleCombo(@Param("entityId") Integer entityId);


	@Query(value=" select u.id,u.userRoleName from UserRole u where u.deleteFlag='0' and u.entityLicenseEntity.id=:entityId")
	public List<Object> getRoleList(@Param("entityId") Integer entityId);

	
	@Query(value=" select u from UserRole u where u.deleteFlag='0' and u.id=:roleId")
	public UserRole getRole(@Param("roleId") Integer roleId);
		
}
