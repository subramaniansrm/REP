package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srm.coreframework.entity.ScreenFunction;

@Repository
public interface ScreenFunctionRepository extends JpaRepository<ScreenFunction, Integer> {
	
	@Query(value="select s.screenFunctionId,s.functionName from ScreenFunction s where s.subScreenId=:subScreenId and s.activeFlag='0' and s.entityLicenseEntity.id=:entityId")
	List<Object> getFunctionList(@Param("subScreenId") Integer subScreenId,@Param("entityId") Integer entityId);

	@Query(value="select s from ScreenFunction s where s.subScreenId=:subScreenAuthId and s.entityLicenseEntity.id=:entityId")
	List<ScreenFunction> getSubScreenFunctionEntity(@Param("subScreenAuthId") int subScreenAuthId,@Param("entityId") int entityId);
	
}

