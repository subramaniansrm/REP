package com.srm.coreframework.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.srm.coreframework.config.UserMessages;
import com.srm.coreframework.entity.EntityLicense;
import com.srm.coreframework.entity.UserEntity;
import com.srm.coreframework.entity.UserMappingEntity;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.EntityLicenseVO;

@Component
public class EntityDao extends CommonDAO{
	
	@Autowired
	UserMessages userMessages;
			
	@SuppressWarnings("unchecked")
	public List<Object[]> searchEntity(EntityLicenseVO entityLicenseVO, AuthDetailsVo authDetailsVo) {

		
		String query = "  select lic.idrin_ma_entity_id, lic.`rin_ma_entity_name` , "
				+ " lic.`entity_location` , lic.`entity_sublocation` ,pln.`PLAN_NAME` , lic.entity_status"
				+ " from `rin_ma_entity_license` lic "
				+ " left join `rin_ma_entity_license_details` det ON lic.`idrin_ma_entity_id` = det.`rin_ma_entity_id` "
				+ " LEFT JOIN `entity_planning` pln ON pln.PLAN_ID = det.entity_plan "
				+ " where lic.`idrin_ma_entity_id`  != 0  ";					

		if (null != entityLicenseVO.getLocation()) {
			query = query + " AND lic.entity_location LIKE LOWER('%" + entityLicenseVO.getLocation() + "%')";
		}

		if (null != entityLicenseVO.getSubLocation()) {
			query = query + " AND lic.entity_sublocation LIKE LOWER('%" + entityLicenseVO.getSubLocation() + "%')";
		}

		if (null != entityLicenseVO.getEntityName()) {
			query = query + " AND lic.rin_ma_entity_name LIKE LOWER('%" + entityLicenseVO.getEntityName() + "%')";
		}

		if (null != entityLicenseVO.getStatusValue()) {
			query = query + " AND lic.entity_status LIKE LOWER('%" + entityLicenseVO.getStatusValue() + "%')";
		}
		
		if (null != entityLicenseVO.getPlanName()) {
			query = query + " AND pln.PLAN_NAME LIKE LOWER('%" + entityLicenseVO.getPlanName() + "%')";
		}

		query = query + " ORDER BY lic.idrin_ma_entity_id desc";

		List<Object[]> list = (List<Object[]>) getEntityManager().createNativeQuery(query).getResultList();

		return list;

	}
	
	 
	public List<Object[]> getAllEntityList(AuthDetailsVo authDetailsVo) {
		String query = "  select lic.idrin_ma_entity_id, lic.`rin_ma_entity_name` , "
				+ " lic.`entity_location` , lic.`entity_sublocation` ,pln.`PLAN_NAME` , lic.entity_status"
				+ " from `rin_ma_entity_license` lic "
				+ " left join `rin_ma_entity_license_details` det ON lic.`idrin_ma_entity_id` = det.`rin_ma_entity_id` "
				+ " LEFT JOIN `entity_planning` pln ON pln.PLAN_ID = det.entity_plan "
				+ " where lic.`idrin_ma_entity_id`  != 0 order by  lic.idrin_ma_entity_id desc ";

		@SuppressWarnings("unchecked")
		List<Object[]> list = (List<Object[]>) getEntityManager().createNativeQuery(query).getResultList();

		return list;
	}

	public List<UserEntity> getUserList(int entityID) {

		List<UserEntity> userEntityList = new ArrayList<UserEntity>();
		String query = " SELECT u FROM UserEntity u" + " WHERE u.entityLicenseEntity.id = " + entityID
				+ " AND u.deleteFlag = '" + CommonConstant.FLAG_ZERO + "'  ";

		userEntityList = (List<UserEntity>) getEntityManager().createQuery(query).getResultList();

		return userEntityList;
	}

}
