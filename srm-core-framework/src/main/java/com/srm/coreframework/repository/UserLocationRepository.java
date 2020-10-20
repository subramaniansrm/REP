package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.srm.coreframework.entity.CityEntity;
import com.srm.coreframework.entity.CountryEntity;
import com.srm.coreframework.entity.StateEntity;
import com.srm.coreframework.entity.UserLocation;

public interface UserLocationRepository extends CrudRepository<UserLocation, Integer> {

	@Query(value = "select loc from UserLocation loc where loc.deleteFlag = '0' and  loc.entityLicenseEntity.id=:entityId order by loc.id desc ")
	public List<UserLocation> getLocation(@Param("entityId") Integer entityId);

	@Query(value = "select c from CityEntity c where c.cityId =:i  ")
	public CityEntity getCity(@Param("i") int i);

	@Query(value = "select s from StateEntity s where  s.stateId=:stateId ")
	public StateEntity getState(@Param("stateId")int stateId);

	@Query(value = "select country from CountryEntity country where  country.id =:countryId  ")
	public CountryEntity getCountry(@Param("countryId") int countryId);

	//public List<UserLocation> search(UserLocationVO userLocationMasterVo);

}
