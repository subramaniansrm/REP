package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.srm.coreframework.entity.FunctionAuthentication;

public interface FunctionAuthenticationRepostiory extends JpaRepository<FunctionAuthentication, Integer> {
	
	@Modifying
	@Query(value="DELETE FROM FunctionAuthentication where screenAuthenticationId=:screenAuthenticationId ")
	public void deleteFunction(@Param("screenAuthenticationId")Integer screenAuthenticationId);

	
	
	@Query(value=" select s.screenFunctionId from FunctionAuthentication s where s.screenAuthenticationId=:screenAuthenticationId and s.deleteFlag = '0'"
			+ " and s.entityLicenseEntity.id=:entityId")
	public List<Integer> screenFunctionBasedRole(@Param("screenAuthenticationId") Integer screenAuthenticationId, @Param("entityId") Integer entityId);

}

