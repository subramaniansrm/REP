package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.srm.coreframework.entity.CommonSubScreenEntity;

public interface CommonSubScreenRepository extends JpaRepository<CommonSubScreenEntity, Integer>{

	
	
	@Query(value = " select s from CommonSubScreenEntity s where s.activeFlag ='0' and s.screenId=:screenId")
	List<CommonSubScreenEntity> findSubScreen(@Param("screenId") int screenId);
	
	

}
