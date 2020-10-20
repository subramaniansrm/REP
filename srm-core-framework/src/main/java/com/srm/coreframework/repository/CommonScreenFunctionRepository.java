package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.srm.coreframework.entity.CommonScreenFunctionEntity;

public interface CommonScreenFunctionRepository extends JpaRepository<CommonScreenFunctionEntity, Integer>{

	
	
	@Query(value = " select s from CommonScreenFunctionEntity s where s.subScreenId=:subScreenId ")
	List<CommonScreenFunctionEntity> findScreenFunction(@Param("subScreenId") int subScreenId);

}
