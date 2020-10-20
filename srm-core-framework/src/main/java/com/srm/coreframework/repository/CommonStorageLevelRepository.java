package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.srm.coreframework.entity.CommnStorageLevelEntity;



public interface CommonStorageLevelRepository extends JpaRepository<CommnStorageLevelEntity, Integer> {

	
	
	@Query(value=" select s from CommnStorageLevelEntity s where s.activeFlag='1'")
	List<CommnStorageLevelEntity> getAlllevel();

}
