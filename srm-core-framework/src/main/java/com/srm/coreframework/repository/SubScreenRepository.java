package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.srm.coreframework.entity.SubScreen;

public interface SubScreenRepository extends JpaRepository<SubScreen, Integer> {

	@Query(value = "select s.subScreenId,s.subScreenName  from SubScreen s where s.screenId =:screenId  and "
			+ " s.entityLicenseEntity.id=:entityId")
	List<Object> getSubScreenList(@Param("screenId") Integer screenId,@Param("entityId") Integer entityId);
	
	
	@Query(value = " select s from SubScreen s where s.screenId=:screenAuthId and s.entityLicenseEntity.id=:entityId ")
	List<SubScreen> getSubScreenEntity(@Param("screenAuthId")int screenAuthId,@Param("entityId") int entityId);


}
 