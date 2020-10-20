package com.srm.coreframework.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.srm.coreframework.entity.SubLocation;
import com.srm.coreframework.entity.UserEntity;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.SubLocationVO;

@Repository
public class SubLocationDAO extends CommonDAO{

	@Value("${commonDatabaseSchema}")
	private String commonDatabaseSchema;
	
	@Value("${rtaDatabaseSchema}")
	private String rtaDatabaseSchema;
		
	@SuppressWarnings("unchecked")
	public List<Object[]> getAllSearch(SubLocationVO subLocationVo,AuthDetailsVo authDetailsVo) {

		String query = "SELECT sub.rin_ma_sublocation_code,sub.rin_ma_sublocation_name,sub.rin_ma_sublocation_subLocationIsActive,"
				+ " loca.USER_LOCATION_NAME , sub.idrin_ma_sublocation_sublocationId,sub.rin_ma_sublocation_locationId"
				+ " FROM "+getCommonDatabaseSchema()+".rin_ma_sublocation sub JOIN "+getCommonDatabaseSchema()+".user_location loca "
				+ " ON loca.USER_LOCATION_ID = sub.rin_ma_sublocation_locationId "
			 	+ " WHERE sub.rin_ma_entity_id = '"+ authDetailsVo.getEntityId()+"'"
				+ " and loca.delete_flag = '" + CommonConstant.CONSTANT_ZERO + "'"
				+ " AND sub.delete_flag = " + CommonConstant.CONSTANT_ZERO;
		StringBuffer modifyQuery = new StringBuffer(query);

		if (null != subLocationVo.getSublocationId() && subLocationVo.getSublocationId() != 0)
			modifyQuery.append(" AND sub.idrin_ma_sublocation_sublocationId = " + subLocationVo.getSublocationId());

		if (subLocationVo.getUserLocationName() != null && !subLocationVo.getUserLocationName().isEmpty())
			modifyQuery.append(
					" AND LOWER(loca.user_location_name) LIKE LOWER('%" + subLocationVo.getUserLocationName() + "%')");

		if (subLocationVo.getSubLocationName() != null && !subLocationVo.getSubLocationName().isEmpty())
			modifyQuery.append(" AND LOWER(sub.rin_ma_sublocation_name) LIKE LOWER('%"
					+ subLocationVo.getSubLocationName() + "%')");

		if (subLocationVo.getSubLocationCode() != null && !subLocationVo.getSubLocationCode().isEmpty())
			modifyQuery.append(" AND LOWER(sub.rin_ma_sublocation_code) LIKE LOWER('%"
					+ subLocationVo.getSubLocationCode() + "%')");

		if (subLocationVo.getStatus() != null) {
			if (subLocationVo.getStatus().equals(CommonConstant.Active)) {
				modifyQuery.append(" and sub.rin_ma_sublocation_subLocationIsActive =" + CommonConstant.ACTIVE);
			} else {
				modifyQuery.append(" and sub.rin_ma_sublocation_subLocationIsActive =" + CommonConstant.CONSTANT_ZERO);
			}
		}

		modifyQuery.append(" ORDER BY sub.idrin_ma_sublocation_sublocationId DESC ");

		List<Object[]> list_SubLocationEntity = (List<Object[]>) getEntityManager().createNativeQuery(modifyQuery.toString())
				.getResultList();

		return list_SubLocationEntity;

	}
	@SuppressWarnings("unchecked")
	public Boolean getCode(SubLocationVO subLocationVo,AuthDetailsVo authDetailsVo) {

		Boolean check = true;

		String query = "FROM SubLocationEntity where deleteFlag = " + CommonConstant.CONSTANT_ZERO
				+ " AND subLocationCode = '" + subLocationVo.getSubLocationCode() + "' "
				+ " and entityLicenseEntity.id =  '"+ authDetailsVo.getEntityId()+"'";

		List<SubLocation> subLocationEntity = new ArrayList<SubLocation>();
		subLocationEntity = (List<SubLocation>) getEntityManager().createQuery(query).getResultList();

		if (subLocationEntity != null && subLocationEntity.size() > 0) {
			check = false;
		}

		return check;
	}

	public int findUser(int sublocationid) {
		int count = 0;

		String query = "SELECT COUNT(subLocationEntity.sublocationId) FROM UserEntity "
				+ " where entityLicenseEntity.id = :entityId "  
				+ " and deleteFlag = '0' AND subLocationEntity.sublocationId = " + sublocationid;

		count = (int) (long) getEntityManager().createQuery(query).getSingleResult();

		return count;

	}

	public int findRequestConfig(int sublocationid,AuthDetailsVo authDetailsVo) {
		int count = 0;

		String query = "SELECT COUNT(reqWorkFlowId) FROM RequestWorkFlowEntity "
				+ " where entityLicenseId = '"+ authDetailsVo.getEntityId()+"' and deleteFlag = "
				+ CommonConstant.CONSTANT_ZERO + " AND workFlowSublocationId = " + sublocationid;

		count = (int) (long) getEntityManager().createQuery(query).getSingleResult();

		return count;

	}
	
	/**
	 * Method is to find request configuration executer
	 * 
	 * @param id
	 * @return
	 */
	public int findRequestConfigExecuter(Integer id,AuthDetailsVo authDetailsVo) {
		BigInteger  count = new BigInteger("0");
	
		String query = " SELECT COUNT(idrin_ma_req_workflow_executer_id) FROM "+ rtaDatabaseSchema +".rin_ma_req_workflow_executer "
				+ " WHERE rin_ma_entity_id = '"+ authDetailsVo.getEntityId()+"' "
				 + " AND delete_Flag = '" + CommonConstant.FLAG_ZERO + "' AND rin_ma_req_executer_sublocation_id = '"+id+"'";
		

		count = (BigInteger) getEntityManager().createNativeQuery(query).getSingleResult();

		return count.intValue();	
	}
 
	public int findRequestConfigSeq(int sublocationid,AuthDetailsVo authDetailsVo) {
		BigInteger  count = new BigInteger("0");
		
		String query = "SELECT COUNT(idrin_ma_req_workflow_seq_id) FROM "+ rtaDatabaseSchema +".rin_ma_req_workflow_seq "  
				 + " WHERE rin_ma_entity_id = '"+ authDetailsVo.getEntityId()+"' "
				 + " and delete_Flag = " + CommonConstant.FLAG_ZERO + " AND rin_ma_req_workflow_seq_sublocation_id = " + sublocationid;

		count = (BigInteger) getEntityManager().createNativeQuery(query).getSingleResult();

		return count.intValue();
	}

	

	public int findRequest(int sublocationid,AuthDetailsVo authDetailsVo) {
		BigInteger  count = new BigInteger("0");
		
		String query = "SELECT COUNT(idrin_tr_request_id) FROM  "+ rtaDatabaseSchema +".rin_tr_request "
				 + " WHERE rin_ma_entity_id = '"+ authDetailsVo.getEntityId()+"' "
				 + " AND  delete_Flag = " + CommonConstant.FLAG_ZERO +" "
				 + " AND rin_tr_request_sublocation_id = " + sublocationid ;
								
		count = (BigInteger) getEntityManager().createNativeQuery(query).getSingleResult();

		return count.intValue();

	}
	

	public int findPhoneBook(int sublocationid,AuthDetailsVo authDetailsVo) {
		BigInteger  count = new BigInteger("0");

		String query = " SELECT COUNT(idrin_ma_phone_book_id) FROM "+ rtaDatabaseSchema +".rin_ma_phone_book "
				 + " WHERE rin_ma_entity_id = '"+ authDetailsVo.getEntityId()+"' "
				 + "AND delete_Flag = '" + CommonConstant.FLAG_ZERO + "' AND rin_ma_phone_book_sublocation_id = " + sublocationid;
		
		count = (BigInteger) getEntityManager().createNativeQuery(query).getSingleResult();

		return count.intValue();

	}

	
	public int duplicateSubLocation(SubLocationVO subLocationVo,AuthDetailsVo authDetailsVo) {
		int count = 0;

		String query = "SELECT COUNT(sublocationId) FROM SubLocation  "
				+ " where entityLicenseEntity.id = '"+ authDetailsVo.getEntityId()+"'"
				+ " AND deleteFlag = " + CommonConstant.CONSTANT_ZERO
				+ " AND LOWER(subLocationName) = LOWER('"+subLocationVo.getSubLocationName().trim()+"') and id = "+subLocationVo.getId();
		StringBuffer modifiedQuery = new StringBuffer(query);
		if (null != subLocationVo.getSublocationId()) {
			modifiedQuery.append(" AND sublocationId != " + subLocationVo.getSublocationId());
		}

		count = (int) (long) getEntityManager().createQuery(modifiedQuery.toString()).getSingleResult();

		return count;

	}
	public Object[] findBySubId(int sublocationId,AuthDetailsVo authDetailsVo) {

		String query = "SELECT sub.rin_ma_sublocation_code,sub.rin_ma_sublocation_name,sub.rin_ma_sublocation_subLocationIsActive,"
				+ " loca.USER_LOCATION_NAME , sub.idrin_ma_sublocation_sublocationId,sub.rin_ma_sublocation_locationId "
				+ " FROM "+getCommonDatabaseSchema()+".rin_ma_sublocation sub JOIN "+getCommonDatabaseSchema()+".user_location loca "
				+ " ON loca.USER_LOCATION_ID = sub.rin_ma_sublocation_locationId "
				+ " WHERE sub.rin_ma_entity_id = '"+ authDetailsVo.getEntityId()+"' "
				+ " AND loca.delete_flag = '" + CommonConstant.CONSTANT_ZERO + "' AND sub.delete_flag = " + CommonConstant.CONSTANT_ZERO
				+ " AND sub.idrin_ma_sublocation_sublocationId = " + sublocationId;
				

		Object[] subLocationEntity = (Object[]) getEntityManager().createNativeQuery(query).getSingleResult();

		return subLocationEntity;
	}
	
	@SuppressWarnings("unchecked")
	public boolean getUniqueLogin(String userName) {

		boolean result = false;
		
		String query = " FROM UserEntity WHERE deleteFlag = " + CommonConstant.FLAG_ZERO + " and LOWER(userName) = '"
				+ userName.toLowerCase() +"'";
		
		List<UserEntity> userEntity =(List<UserEntity>) getEntityManager().createQuery(query).getResultList();
		if(null != userEntity && !userEntity.isEmpty()){
			result = true;
		}else{
			result = false;
		}
		return result;

	}
	
	@SuppressWarnings("unchecked")
	public UserEntity getSuperAdminInfo() {

		String query = "  FROM UserEntity ur" + " WHERE ur.deleteFlag = '0' " + " AND ur.userRoleEntity.id = " + 1
				+ " AND ur.activeFlag = '1'";

		List<UserEntity> list = (List<UserEntity>) getEntityManager().createQuery(query).getResultList();
		UserEntity userEntity = new UserEntity();

		if (list.size() > 0) {
			userEntity = list.get(0);
		}

		return userEntity;

	}

}
