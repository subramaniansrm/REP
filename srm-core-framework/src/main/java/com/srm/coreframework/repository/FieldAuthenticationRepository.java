package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.srm.coreframework.entity.FieldAuthentication;

public interface FieldAuthenticationRepository extends JpaRepository<FieldAuthentication, Integer> {
	
	@Modifying
	@Query(value="DELETE FROM FieldAuthentication where screenAuthenticationId=:screenAuthenticationId ")
	public void deleteField(@Param("screenAuthenticationId") Integer screenAuthenticationId);
	
	@Query(value=" select s.screenFieldId from FieldAuthentication s where s.screenAuthenticationId =:screenAuthenticationId "
			+ " and s.deleteFlag='0' and s.entityLicenseEntity.id =:entityId ")
	public List<Integer> getAuthenticationBasedFields(@Param("screenAuthenticationId") Integer screenAuthenticationId,@Param("entityId")  Integer entityId);

}

