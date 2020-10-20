package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srm.coreframework.auth.AuthUtil;
import com.srm.coreframework.entity.SubLocation;
import com.srm.coreframework.entity.UserDepartment;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.UserDepartmentVO;



public interface SubLocationRepository extends JpaRepository<SubLocation, Integer>{
	
	@Query(value = "SELECT sub.rin_ma_sublocation_code,sub.rin_ma_sublocation_name,sub.rin_ma_sublocation_subLocationIsActive, loca.USER_LOCATION_NAME , sub.idrin_ma_sublocation_sublocationId,sub.rin_ma_sublocation_locationId "
			+ " FROM common_rta_2_local.rin_ma_sublocation sub JOIN common_rta_2_local.user_location loca "
			+ " ON loca.USER_LOCATION_ID = sub.rin_ma_sublocation_locationId"
			+ " WHERE sub.rin_ma_entity_id = :entityId"
			+ " and loca.delete_flag = '0' AND sub.delete_flag = '0'" 
			+ " ORDER BY sub.idrin_ma_sublocation_sublocationId DESC ",nativeQuery = true)
	public List<Object[]> getAll(@Param("entityId") Integer entityId);
	
	
	
	
	@Query(value = "SELECT sub.rin_ma_sublocation_code,sub.rin_ma_sublocation_name,sub.rin_ma_sublocation_subLocationIsActive,"
			+ " loca.USER_LOCATION_NAME , sub.idrin_ma_sublocation_sublocationId,sub.rin_ma_sublocation_locationId "
			+ " FROM common_rta_2_local.rin_ma_sublocation sub JOIN common_rta_2_local.user_location loca "
			+ " ON loca.USER_LOCATION_ID = sub.rin_ma_sublocation_locationId "
			+ " WHERE sub.rin_ma_entity_id = :entityId "
			+ " AND loca.delete_flag =  '0'   AND sub.delete_flag =  '0'"
			+ " AND sub.idrin_ma_sublocation_sublocationId =  :sublocationId ",nativeQuery = true)
	public Object[] view(@Param("entityId") Integer entityId,@Param("sublocationId") Integer sublocationId);

}
