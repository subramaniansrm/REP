package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.srm.coreframework.entity.Screen;

public interface ScreenRepository extends JpaRepository<Screen, Integer>{

	
	@Query(value = "select s.screenId,s.screenName from Screen s where s.entityLicenseEntity.id =:entityId and s.activeFlag ='0'")
	List<Object> getScreenList(@Param("entityId") Integer entityId);
	

	
	@Query(value = "select s from Screen s where s.entityLicenseEntity.id =:entityId ")
	List<Screen> getScreenEntity(@Param("entityId") int entityId);


}


