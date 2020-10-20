package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.srm.coreframework.entity.CommonScreenEntity;

public interface CommonScreenRepository extends JpaRepository<CommonScreenEntity, Integer> {

	
	@Query(value=" select s from CommonScreenEntity s where s.activeFlag='0'")
	List<CommonScreenEntity> getScreen();

}
