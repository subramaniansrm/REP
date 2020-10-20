package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srm.coreframework.entity.EntityLicenseLogEntity;

@Repository
public interface EntityLicenseLogRepository extends JpaRepository<EntityLicenseLogEntity, Integer> {

	@Query(value = "select c from EntityLicenseLogEntity c where  c.entityId =:entityId order by c.entityLicenseLogId desc ")
	public List<EntityLicenseLogEntity> getAll(@Param("entityId") Integer entityId);
	
}
