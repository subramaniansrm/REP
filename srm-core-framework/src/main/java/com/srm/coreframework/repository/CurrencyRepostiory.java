package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.srm.coreframework.entity.CurrencyEntity;

public interface CurrencyRepostiory extends JpaRepository<CurrencyEntity, Integer> {

	@Query(value = "select c from CurrencyEntity c ")
	public List<CurrencyEntity> currencyList();
	
}
