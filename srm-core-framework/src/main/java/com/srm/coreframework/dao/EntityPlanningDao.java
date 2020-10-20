package com.srm.coreframework.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.entity.EntityPlanning;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.EntityPlanningVo;

@Repository
public class EntityPlanningDao extends CommonDAO{

	@SuppressWarnings("unchecked")
	public List<EntityPlanning> search(EntityPlanningVo entityPlanningVo,AuthDetailsVo authDetailsVo) {

		String query = " SELECT ur FROM EntityPlanning ur"
			+ " WHERE ur.deleteFlag = '0' "; 
				
		if (entityPlanningVo.getPlanName() != null && !entityPlanningVo.getPlanName().isEmpty()) {
			query = query + " and LOWER(ur.planName) LIKE LOWER('%" + entityPlanningVo.getPlanName() + "%')";
		}
		if (null != entityPlanningVo.getDuration()) {
			query = query + " and ur.duration = "+ entityPlanningVo.getDuration();
		}
		
		if (null != entityPlanningVo.getUserCount()) {
			query = query + " and ur.userCount = "+ entityPlanningVo.getUserCount();
		}
		if (null != entityPlanningVo.getTransactionCount()) {
			query = query + " and ur.transactionCount = "+ entityPlanningVo.getTransactionCount();
		}
		if (null != entityPlanningVo.getAmount()) {
			query = query + " and ur.amount = "+ entityPlanningVo.getAmount();
		}
		if (null != entityPlanningVo.getAmount()) {
			query = query + " and ur.offerAmount = "+ entityPlanningVo.getOfferAmount();
		}		
	/*	if (entityPlanningVo.isActiveFlag()) {
			query = query + " and ur.activeFlag = '1' ";
		}else if(!entityPlanningVo.isActiveFlag()){
			query = query + " and ur.activeFlag = '0'";
		}*/
		query = query + " ORDER BY ur.planId desc";

		List<EntityPlanning> list = (List<EntityPlanning>) getEntityManager().createQuery(query).getResultList();

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<EntityPlanning> getPlanList() {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		 Date date = new Date();  
		 String currentDate = "";
		 currentDate =  (formatter.format(date)); 		
		
		String query = " select c from EntityPlanning c where "
				//+ " c.fromDate <= "+ currentDate
				+ " c.toDate < '"+ currentDate +"'"
				+ " and c.activeFlag='1'"
				+ " and c.deleteFlag ='0' "; 											

		List<EntityPlanning> list = (List<EntityPlanning>) getEntityManager().createQuery(query).getResultList();

		return list;
	}		  	
	
	public void updatePlanStatusForExpiry(int planId) {
		try {

			String query = "update " + getCommonDatabaseSchema() + ".entity_planning u "
					+ " set u.ACTIVE =" + '0'
					+ " , u.UPDATED_DATE = '"+ CommonConstant.getCurrentDateTimeAsString()+"' "
					+ " where u.PLAN_ID= '" + planId + "'";
			getEntityManager().createNativeQuery(query).executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
