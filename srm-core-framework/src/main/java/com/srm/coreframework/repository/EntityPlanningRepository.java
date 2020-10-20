package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srm.coreframework.entity.EntityPlanning;

@Repository
public interface EntityPlanningRepository extends JpaRepository<EntityPlanning, Integer> {

	@Query(value = "select c from EntityPlanning c where c.activeFlag='1' and c.deleteFlag ='0' ")
	public List<EntityPlanning> getEntityPlanningList();
	
	@Query(value = "select c from EntityPlanning c where c.deleteFlag ='0' ORDER BY c.planId desc ")
	public List<EntityPlanning> list();
	
	@Query(value = "select c from EntityPlanning c where c.planId =:planId ")
	public EntityPlanning getEntityPlanningDetails(@Param("planId") Integer planId);
	
	
}
