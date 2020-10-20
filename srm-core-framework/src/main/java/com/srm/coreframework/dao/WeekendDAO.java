package com.srm.coreframework.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.srm.coreframework.entity.DaysEntity;
import com.srm.coreframework.entity.WeekendDetailsEntity;
import com.srm.coreframework.entity.WeekendEntity;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.WeekendVO;
 

@Repository
public class WeekendDAO extends CommonDAO{
	 
	org.slf4j.Logger logger = LoggerFactory.getLogger(WeekendDAO.class);
	
	@Value("${commonDatabaseSchema}")
	private String commonDatabaseSchema;
	
	@SuppressWarnings("unchecked")
	public List<Object[]> search(WeekendVO weekendVO, AuthDetailsVo authDetailsVo) {

		List<Object[]> weekendList = new ArrayList<Object[]>();
				
		String query = "";
		try{
			if(authDetailsVo.getLangCode().equals("en")){
			
			 query = "  SELECT wk.idrin_ma_weekend_id , loc.`USER_LOCATION_NAME` , subloc.`rin_ma_sublocation_name` , GROUP_CONCAT(d.`rin_ma_day_name_en`) , wk.`rinma_active`  " 
						+ ", wk.`rinma_location_id` , det.`rin_ma_sublocation_id` " 
						+ " FROM weekend wk "
						+ " LEFT JOIN weekend_details det ON wk.idrin_ma_weekend_id = det.rin_ma_weekend_id "
						+ " LEFT JOIN days d ON  d.rin_ma_day_number = det.rin_ma_weekend_day "
						+ " LEFT JOIN `user_location` loc ON loc.`USER_LOCATION_ID` = wk.`rinma_location_id` "
						+ " LEFT JOIN `rin_ma_sublocation` subloc ON subloc.`idrin_ma_sublocation_sublocationId` = det.`rin_ma_sublocation_id` "
						+ " WHERE idrin_ma_weekend_id != 0 AND wk.`delete_flag` = '0'"
						+ " AND wk.`rin_ma_entity_id` = " + authDetailsVo.getEntityId() ;
						//+ " AND wk.`rinma_active` = '1' "
						
		}else{
			  query = "  SELECT wk.idrin_ma_weekend_id , loc.`USER_LOCATION_NAME` , subloc.`rin_ma_sublocation_name` , GROUP_CONCAT(d.`rin_ma_day_name_jp`) , wk.`rinma_active` " 
					+ ", wk.`rinma_location_id` , det.`rin_ma_sublocation_id` " 
					+ " FROM weekend wk "
					+ " LEFT JOIN weekend_details det ON wk.idrin_ma_weekend_id = det.rin_ma_weekend_id "
					+ " LEFT JOIN days d ON  d.rin_ma_day_number = det.rin_ma_weekend_day "
					+ " LEFT JOIN `user_location` loc ON loc.`USER_LOCATION_ID` = wk.`rinma_location_id` "
					+ " LEFT JOIN `rin_ma_sublocation` subloc ON subloc.`idrin_ma_sublocation_sublocationId` = det.`rin_ma_sublocation_id` "
					+ " WHERE idrin_ma_weekend_id != 0 AND wk.`delete_flag` = '0'"
					+ " AND wk.`rin_ma_entity_id` = " + authDetailsVo.getEntityId() ;
					//+ " AND wk.`rinma_active` = '1' "					 		
		}	
							
		if (weekendVO.getLocation() != null) {
			query = query + " and LOWER(loc.USER_LOCATION_NAME) LIKE LOWER('%" + weekendVO.getLocation()
					+ "%')";
		}

		if (weekendVO.getSubLocation() != null) {
			query = query + " and LOWER(subloc.rin_ma_sublocation_name) LIKE LOWER('%" + weekendVO.getSubLocation()
					+ "%')";
		}
		
			if (weekendVO.getStatus() != null) {

				if (weekendVO.getStatus().equals(CommonConstant.Active)) {
					query = query + " and LOWER(wk.rinma_active) LIKE LOWER('%" + CommonConstant.ACTIVE + "%')";
				} else {
					query = query + " and LOWER(uwk.rinma_active) LIKE LOWER('%" + CommonConstant.CONSTANT_ZERO + "%')";
				}
			}				
					 	
		query = query + " GROUP BY wk.`rinma_location_id` , det.`rin_ma_sublocation_id` "
				+ " ORDER BY wk.`idrin_ma_weekend_id` DESC";

		weekendList = (List<Object[]>) getEntityManager().createNativeQuery(query).getResultList();

		}catch (Exception e) {
			logger.error(e.getMessage());			
		}
		
		return weekendList;
	}
	
	public void updateWeekend(WeekendVO weekendVO, AuthDetailsVo authDetailsVo) {
		
		try{
		String query1 = " update WeekendDetailsEntity set deleteFlag = '"+CommonConstant.FLAG_ONE+"' " 
				+ " where weekendId =   " + weekendVO.getWeekendId()
				+ " and entityId = " + authDetailsVo.getEntityId();
		getEntityManager().createQuery(query1).executeUpdate();
		
		}catch (Exception e) {
			logger.error(e.getMessage());			
		}
	}
		
	@SuppressWarnings("unchecked")
	public List<WeekendDetailsEntity> getWeekendDetailByWeekendId(WeekendVO weekendVO, AuthDetailsVo authDetailsVo) {

		List<WeekendDetailsEntity> list = new ArrayList<WeekendDetailsEntity>();

		try {
			String query = " select c from WeekendDetailsEntity c where c.deleteFlag='0'" + " and c.entityId= "
					+ authDetailsVo.getEntityId() + " and c.weekendId = " + weekendVO.getWeekendId()
					+ " order by c.weekendDetailId desc ";

			list = (List<WeekendDetailsEntity>) getEntityManager().createQuery(query).getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getAll(AuthDetailsVo authDetailsVo) {

		List<Object[]> weekendList = new ArrayList<Object[]>();

		try {
			String query = "";
			if(authDetailsVo.getLangCode().equals("en")){
			  query = "  SELECT wk.idrin_ma_weekend_id , loc.USER_LOCATION_NAME , subloc.rin_ma_sublocation_name , GROUP_CONCAT(d.rin_ma_day_name_en) , wk.rinma_active  " 
							+ ", wk.rinma_location_id , det.rin_ma_sublocation_id " 
							+ " FROM weekend wk "
							+ " LEFT JOIN weekend_details det ON wk.idrin_ma_weekend_id = det.rin_ma_weekend_id "
							+ " LEFT JOIN days d ON  d.rin_ma_day_number = det.rin_ma_weekend_day "
							+ " LEFT JOIN `user_location` loc ON loc.`USER_LOCATION_ID` = wk.`rinma_location_id` "
							+ " LEFT JOIN `rin_ma_sublocation` subloc ON subloc.`idrin_ma_sublocation_sublocationId` = det.`rin_ma_sublocation_id` "
							+ " WHERE idrin_ma_weekend_id != 0 AND wk.delete_flag = '0' AND det.delete_flag = '0' "
							+ " AND wk.`rin_ma_entity_id` = " + authDetailsVo.getEntityId()
							//+ " AND wk.`rinma_active` = '1' "
							+ " GROUP BY wk.rinma_location_id , det.rin_ma_sublocation_id "
							+ " ORDER BY wk.idrin_ma_weekend_id DESC";	
			}else{
				  query = "  SELECT wk.idrin_ma_weekend_id , loc.USER_LOCATION_NAME , subloc.rin_ma_sublocation_name , GROUP_CONCAT(d.rin_ma_day_name_jp) , wk.rinma_active " 
						+ ", wk.rinma_location_id , det.rin_ma_sublocation_id " 
						+ " FROM weekend wk "
						+ " LEFT JOIN weekend_details det ON wk.idrin_ma_weekend_id = det.rin_ma_weekend_id "
						+ " LEFT JOIN days d ON  d.rin_ma_day_number = det.rin_ma_weekend_day "
						+ " LEFT JOIN `user_location` loc ON loc.`USER_LOCATION_ID` = wk.`rinma_location_id` "
						+ " LEFT JOIN `rin_ma_sublocation` subloc ON subloc.`idrin_ma_sublocation_sublocationId` = det.`rin_ma_sublocation_id` "
						+ " WHERE idrin_ma_weekend_id != 0 AND wk.delete_flag = '0' AND det.delete_flag = '0' "
						+ " AND wk.`rin_ma_entity_id` = " + authDetailsVo.getEntityId()
						//+ " AND wk.`rinma_active` = '1' "
						+ " GROUP BY wk.rinma_location_id , det.rin_ma_sublocation_id "
						+ " ORDER BY wk.idrin_ma_weekend_id DESC";				
			}
								
			weekendList = (List<Object[]>) getEntityManager().createNativeQuery(query).getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return weekendList;
	}
	
	@SuppressWarnings("unchecked")
	public WeekendEntity weekendView(AuthDetailsVo authDetailsVo, WeekendVO weekendVO) {

		WeekendEntity weekendEntity = new WeekendEntity();

		try {
			String query = " select c from WeekendEntity c where c.deleteFlag='0'";

			if (null != authDetailsVo.getEntityId()) {
				query = query + " and c.entityId= " + authDetailsVo.getEntityId();
			}

			if (null != weekendVO.getWeekendId()) {
				query = query + " and c.weekendId= " + weekendVO.getWeekendId();
			}

			weekendEntity = (WeekendEntity) getEntityManager().createQuery(query).getSingleResult();

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return weekendEntity;
	}
		
	@SuppressWarnings("unchecked")
	public List<DaysEntity> listDays(AuthDetailsVo authDetailsVo) {

		List<DaysEntity> dayEntityList = new ArrayList<DaysEntity>();

		try {
			String query = " select c from DaysEntity c where c.deleteFlag='0'";

			dayEntityList = (List<DaysEntity>) getEntityManager().createQuery(query).getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return dayEntityList;
	}
		
	@SuppressWarnings("unchecked")
	public List<Object[]> duplicateValidation(AuthDetailsVo authDetailsVo, WeekendVO weekendVO) {

		List<Object[]> weekendList = new ArrayList<Object[]>();

		try {
			String query = " select wk.idrin_ma_weekend_id from  "+getCommonDatabaseSchema()+".weekend wk "
					+ " left join "+getCommonDatabaseSchema()+".weekend_details det ON wk.idrin_ma_weekend_id = det.rin_ma_weekend_id "
					+ " where  wk.rinma_location_id = " + weekendVO.getLocationId() + "  and det.rin_ma_sublocation_id = "
					+ weekendVO.getSubLocationId();

			if (null != authDetailsVo.getEntityId()) {
				query = query + " and wk.rin_ma_entity_id= " + authDetailsVo.getEntityId();
			}

			weekendList = (List<Object[]>) getEntityManager().createNativeQuery(query).getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return weekendList;
	}
	
 

}