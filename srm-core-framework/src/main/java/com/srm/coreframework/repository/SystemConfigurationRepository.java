package com.srm.coreframework.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.srm.coreframework.entity.SystemConfigurationEntity;

public interface SystemConfigurationRepository extends JpaRepository<SystemConfigurationEntity, Integer> {
	
	 
}