package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.srm.coreframework.entity.EntityLicenseDetails;


public interface EntityLicenseDetailsRepository extends JpaRepository<EntityLicenseDetails, Integer> {
	
	
	@Query(value = "select c from EntityLicenseDetails c where c.deleteFlag='0' and c.entityId =:entityId  ")
	public List<EntityLicenseDetails> getAll(@Param("entityId") Integer entityId);
	
	@Query(value = "select c from EntityLicenseDetails c where c.deleteFlag='0' and c.entityId =:entityId  ")
	public EntityLicenseDetails entityDetails(@Param("entityId") Integer entityId);
	
}