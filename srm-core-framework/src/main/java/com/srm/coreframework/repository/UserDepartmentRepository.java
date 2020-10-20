package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srm.coreframework.entity.UserDepartment;

@Repository
public interface UserDepartmentRepository extends CrudRepository<UserDepartment, Integer> {
	
	@Query(value = "select u from UserDepartment u where u.deleteFlag ='0' and u.entityLicenseEntity.id=:entityId order by u.id desc ")
	public List<UserDepartment> loadDepartment(@Param("entityId") Integer entityId);
	
	
}
