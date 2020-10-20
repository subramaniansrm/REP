package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srm.coreframework.entity.UserEntityMapping;


@Repository
public interface UserEntityMappingRepository extends JpaRepository<UserEntityMapping, Integer> {
	
	@Query(value = "SELECT entityId FROM UserEntityMapping " + " where userId = :userId ")
	public List<Integer> getUserEntity(@Param("userId") Integer userId);

	@Query(value = " FROM UserEntityMapping u " + " where u.userId = :userId and u.defaultId ='1' ")
	public UserEntityMapping getDefaultUserEntity(@Param("userId") Integer userId); 
	
	@Query(value = " SELECT entityId FROM UserEntityMapping u " + " where u.userId = :userId and u.defaultId ='1' ")
	public List<Integer>  getDefaultSingleUserEntity(@Param("userId") Integer userId);
}
