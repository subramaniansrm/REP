package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.srm.coreframework.entity.EntityLicense;

public interface EntityLicenseRepository extends JpaRepository<EntityLicense, Integer> {

	
	@Query(value = "select c from EntityLicense c where c.deleteFlag='0' and c.id=:entityId order by c.id desc  ")
	public List<EntityLicense> getAll(@Param("entityId") Integer entityId);
	
	
	@Query(value = "select c from EntityLicense c where c.deleteFlag='0' order by c.id desc  ")
	public List<EntityLicense> getAllEntity();
	
}