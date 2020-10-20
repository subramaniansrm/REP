package com.srm.coreframework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srm.coreframework.entity.UserType;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Integer> {

	@Query(value=" select s from UserType s where s.typeOfUser='Admin' and s.entityLicenseEntity.id =:entityId"  )
	public UserType getAdminRoleType(@Param("entityId") Integer entityId);
	
	
}
