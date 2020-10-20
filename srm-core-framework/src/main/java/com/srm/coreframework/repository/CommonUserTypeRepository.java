package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.srm.coreframework.entity.CommonCodeGenerationEntity;
import com.srm.coreframework.entity.CommonUserTypeEntity;

public interface CommonUserTypeRepository extends JpaRepository<CommonUserTypeEntity, Integer>{
	
	@Query(value=" select s from CommonUserTypeEntity s where s.activeFlag='1'")
	List<CommonUserTypeEntity> getAllCommonUserType();

}
