package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srm.coreframework.entity.UserAuditLogEntity;

@Repository
public interface UserAuditLogRepository extends JpaRepository<UserAuditLogEntity, Integer> {

	@Query(value = " select s from UserAuditLogEntity s where s.userId=:userId ")
	List<UserAuditLogEntity> getAuditListByUser(@Param("userId") Integer userId);

	
}
