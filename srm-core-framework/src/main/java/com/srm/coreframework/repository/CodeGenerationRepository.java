package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.srm.coreframework.entity.CodeGenerationEntity;

public interface CodeGenerationRepository extends JpaRepository<CodeGenerationEntity, Integer> {


	

}
