package com.srm.coreframework.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.srm.coreframework.entity.CommonStorageEntity;

public interface CommonStorageRepository extends JpaRepository<CommonStorageEntity, Integer> {
	
	

}
