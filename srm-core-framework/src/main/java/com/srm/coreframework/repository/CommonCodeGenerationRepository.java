package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.srm.coreframework.entity.CodeGenerationEntity;
import com.srm.coreframework.entity.CommonCodeGenerationEntity;
import com.srm.coreframework.entity.CommonScreenEntity;

public interface CommonCodeGenerationRepository  extends JpaRepository<CommonCodeGenerationEntity, Integer>  {

	@Query(value=" select s from CommonCodeGenerationEntity s where s.activeFlag='1'")
	List<CommonCodeGenerationEntity> getAllCode();


}
