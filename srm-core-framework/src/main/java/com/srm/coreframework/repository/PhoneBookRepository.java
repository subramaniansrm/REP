package com.srm.coreframework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.srm.coreframework.entity.PhoneBookEntity;

public interface PhoneBookRepository extends JpaRepository<PhoneBookEntity, Integer> {
	
	@Query(value = "select r from PhoneBookEntity r where r.deleteFlag ='0' and r.entityLicenseId=:entityId and r.phoneBookId = :id")
	public PhoneBookEntity attachmentDownload(@Param("entityId") Integer entityId,@Param("id") Integer id);

}
