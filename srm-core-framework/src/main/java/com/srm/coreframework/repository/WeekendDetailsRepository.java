package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.srm.coreframework.entity.WeekendDetailsEntity;

public interface WeekendDetailsRepository extends JpaRepository<WeekendDetailsEntity, Integer> {

	/* @Query(value = "select c from WeekendDetailsEntity c where c.deleteFlag='0' and c.entityId=:entity and c.rin_ma_weekend_id =:weekendId order by c.id desc  ")
		public List<WeekendDetailsEntity> getWeekendDetailByWeekendId(@Param("entity") Integer entity , @Param("weekendId") Integer weekendId);
		 */
}