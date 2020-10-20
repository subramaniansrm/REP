package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.srm.coreframework.entity.CommonScreenFieldEntity;

public interface CommonScreenFieldRepository extends JpaRepository<CommonScreenFieldEntity, Integer> {

	@Query(value = " select s from CommonScreenFieldEntity s where s.subScreenId=:subScreenId and s.activeFlag = '0'")
	List<CommonScreenFieldEntity> findScreenField(@Param("subScreenId") int subScreenId);
	
	

}
