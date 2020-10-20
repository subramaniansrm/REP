package com.srm.coreframework.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.srm.coreframework.auth.AuthUtil;
import com.srm.coreframework.entity.UserLocation;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.UserLocationVO;

@Repository
public class UserLocationDAO extends CommonDAO{

	@Value("${commonDatabaseSchema}")
	private String commonDatabaseSchema;
	
	@Value("${rtaDatabaseSchema}")
	private String rtaDatabaseSchema;
	

	public int duplicateLocation(UserLocationVO userLocationVo,AuthDetailsVo authDetailsVo) {
		
		int count = 0;

		String query = "SELECT COUNT(id) FROM UserLocation" + "  where  deleteFlag = "
				+ CommonConstant.CONSTANT_ZERO;
		if (null != authDetailsVo.getEntityId()) {
			query = query + " and entityLicenseEntity.id = " + authDetailsVo.getEntityId();
		}
		if (null != userLocationVo.getUserLocationName().trim()) {
			query = query + " AND LOWER(userLocationName) = LOWER('" + userLocationVo.getUserLocationName().trim()
					+ "')";
		}
		StringBuffer modifiedQuery = new StringBuffer(query);
		if (null != userLocationVo.getId()) {
			modifiedQuery.append(" AND id != " + userLocationVo.getId());
		}

		count = (int) (long) getEntityManager().createQuery(modifiedQuery.toString()).getSingleResult();

		return count;

	}

	public int findUser(Integer id,AuthDetailsVo authDetailsVo) {
		int count = 0;

		String query = "SELECT COUNT(u.userLocationEntity.id) FROM UserEntity u  where deleteFlag = "
				+ CommonConstant.CONSTANT_ZERO + " AND u.userLocationEntity.id = " + id
				+ " and u.entityLicenseEntity.id = " + authDetailsVo.getEntityId();

		count = (int) (long) getEntityManager().createQuery(query).getSingleResult();

		return count;

	}

	/**
	 * Method is to find request configuration
	 * 
	 * @param id
	 * @return
	 */
	public int findUserRole(Integer id,AuthDetailsVo authDetailsVo) {
		int count = 0;

		String query = "SELECT COUNT(ur.userLocationEntity.id) FROM UserRole ur  where ur.deleteFlag = "
				+ CommonConstant.CONSTANT_ZERO + " AND ur.userLocationEntity.id = " + id
				+ " and ur.entityLicenseEntity.id = " + authDetailsVo.getEntityId();

		count = (int) (long) getEntityManager().createQuery(query).getSingleResult();

		return count;

	}

	/**
	 * Method is to find request configuration
	 * 
	 * @param id
	 * @return
	 */
	public int findRequestConfig(Integer id,AuthDetailsVo authDetailsVo) {
		int count = 0;

		String query = "SELECT COUNT(reqWorkFlowId) FROM RequestWorkFlowEntity  where deleteFlag = "
				+ CommonConstant.CONSTANT_ZERO + " AND workFlowLocationId = " + id + " and entityLicenseId.id = "
				+ authDetailsVo.getEntityId();

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
		//int count = 0;
		BigInteger  count = new BigInteger("0");
		String query = " SELECT COUNT(idrin_ma_req_workflow_executer_id) FROM "+ rtaDatabaseSchema +".rin_ma_req_workflow_executer WHERE " 
			      + " delete_flag = '"+CommonConstant.FLAG_ZERO+"' AND rin_ma_req_executer_location_id = '"+id+"' AND rin_ma_entity_id = '"+authDetailsVo.getEntityId()+"' ";
					
		count =  (BigInteger) getEntityManager().createNativeQuery(query).getSingleResult();
		
		return count.intValue();
	}

	/**
	 * Method is to find request configuration sequence
	 * 
	 * @param id
	 * @return
	 */
	public int findRequestConfigSeq(Integer id,AuthDetailsVo authDetailsVo) {
		BigInteger  count = new BigInteger("0");
	
		String query =  " SELECT COUNT(idrin_ma_req_workflow_seq_id) FROM "+ rtaDatabaseSchema +".rin_ma_req_workflow_seq "  
		+ " WHERE delete_Flag = '"+CommonConstant.FLAG_ZERO+"' AND rin_ma_req_workflow_seq_location_id = '"+id+"' AND rin_ma_entity_id = '"+authDetailsVo.getEntityId()+"' ";
		
		count = (BigInteger) getEntityManager().createNativeQuery(query).getSingleResult();

		return count.intValue();
	}

	public int findRequest(int id,AuthDetailsVo authDetailsVo) {
		BigInteger  count = new BigInteger("0");

		String query = "SELECT COUNT(idrin_tr_request_id) FROM "+ rtaDatabaseSchema +".rin_tr_request  WHERE delete_Flag =  '"+CommonConstant.FLAG_ZERO+"'  "
				      + " AND rin_tr_request_user_location_id = '"+id +"' AND rin_ma_entity_id = '"+authDetailsVo.getEntityId()+"'" ;
		
		count = (BigInteger) getEntityManager().createNativeQuery(query).getSingleResult();

		return count.intValue();
	}

	public int findPhoneBook(int id,AuthDetailsVo authDetailsVo) {
		BigInteger  count = new BigInteger("0");
		
 		String query = " SELECT COUNT(idrin_ma_phone_book_id) FROM "+ rtaDatabaseSchema +".rin_ma_phone_book  WHERE delete_Flag = '"+CommonConstant.FLAG_ZERO+"'  "
	                  + " AND rin_ma_phone_book_location_id = '"+id+"' AND rin_ma_entity_id = '"+authDetailsVo.getEntityId()+"'" ;
		
 		count = (BigInteger) getEntityManager().createNativeQuery(query).getSingleResult();

		return count.intValue();

	}

	/**
	 * Method is to find user mapping
	 * 
	 * @param id
	 * @return
	 */
	public int findUserMapping(int id,AuthDetailsVo authDetailsVo) {
		BigInteger  count = new BigInteger("0");

		String query = " SELECT COUNT(USER_MAPPING_ID) FROM "+ commonDatabaseSchema +".user_mapping c  WHERE c.delete_Flag = '"+CommonConstant.FLAG_ZERO+"' "
				+ " AND c.USER_LOCATION_ID = '"+id+"' "
				+ " AND c.rin_ma_entity_id = '"+authDetailsVo.getEntityId()+"'";
		
		count = (BigInteger) getEntityManager().createNativeQuery(query).getSingleResult();

		return count.intValue();

	}

	/**
	 * Method is to find sub location
	 * 
	 * @param id
	 * @return
	 */
	public int findSubLocation(int id,AuthDetailsVo authDetailsVo) {
		BigInteger  count = new BigInteger("0");
		
		String query = " SELECT COUNT(idrin_ma_sublocation_sublocationId) FROM "+ commonDatabaseSchema +".rin_ma_sublocation c WHERE c.delete_Flag = '"+CommonConstant.FLAG_ZERO+"' "
					+ " AND c.rin_ma_sublocation_locationId = " + id + " AND c.rin_ma_entity_id = '"+ authDetailsVo.getEntityId()+"'" ;

		count = (BigInteger) getEntityManager().createNativeQuery(query).getSingleResult();

		return count.intValue();

	}

	/**
	 * Method is to find department
	 * 
	 * @param id
	 * @return
	 */
	public int findDepartment(int id,AuthDetailsVo authDetailsVo) {
		int count = 0;

		String query = "SELECT COUNT(id) FROM UserDepartment c  where c.deleteFlag = "
				+ CommonConstant.CONSTANT_ZERO + " AND c.userLocationEntity.id = " + id
				+ " and c.entityLicenseEntity.id = " + authDetailsVo.getEntityId();

		count = (int) (long) getEntityManager().createQuery(query).getSingleResult();

		return count;

	}

	@SuppressWarnings("unchecked")
	public List<Object[]> search(UserLocationVO userLocationMasterVo,AuthDetailsVo authDetailsVo) {

		String query = "SELECT c.id,c.userLocationName, c.userLocationDetails, c.zip, c.phone, " //4
				+ " c.fax,c.email,c.contactName,cty.cityName, s.stateName,con.country "//10
				+ " FROM UserLocation c "
				+ "  , StateEntity s, CountryEntity con,  CityEntity cty "
				+ "	WHERE  s.stateId = c.stateId "
				+ "   and  con.id = c.countryId "
				+ "   and cty.cityId = c.cityId "
				+ " and c.entityLicenseEntity.id = " + authDetailsVo.getEntityId()
				+ " AND c.activeFlag = '" + CommonConstant.CONSTANT_ONE + "' AND c.deleteFlag  = '"
				+ CommonConstant.FLAG_ZERO + "'";

		StringBuffer modifiedQuery = new StringBuffer(query);

		if (userLocationMasterVo.getUserLocationDetails() != null
				&& !userLocationMasterVo.getUserLocationDetails().isEmpty()) {
			modifiedQuery.append(" and LOWER(c.userLocationDetails) LIKE LOWER('%"
					+ userLocationMasterVo.getUserLocationDetails() + "%')");
		}

		if (userLocationMasterVo.getUserLocationName() != null
				&& !userLocationMasterVo.getUserLocationName().isEmpty()) {
			modifiedQuery.append(" and LOWER(c.userLocationName) LIKE LOWER('%"
					+ userLocationMasterVo.getUserLocationName() + "%')");
		}

		if (userLocationMasterVo.getZip() != null && !userLocationMasterVo.getZip().isEmpty()) {
			modifiedQuery.append(" and c.zip LIKE '%" + userLocationMasterVo.getZip() + "%'");
		}

		if (userLocationMasterVo.getPhone() != null && !userLocationMasterVo.getPhone().isEmpty()) {
			modifiedQuery.append(" and c.phone LIKE '%" + userLocationMasterVo.getPhone() + "%'");
		}

		if (userLocationMasterVo.getFax() != null && !userLocationMasterVo.getFax().isEmpty()) {
			modifiedQuery.append(" and c.fax LIKE '%" + userLocationMasterVo.getFax() + "%'");
		}
		if (userLocationMasterVo.getEmail() != null && !userLocationMasterVo.getEmail().isEmpty()) {
			modifiedQuery.append(" and LOWER(c.email) LIKE LOWER('%" + userLocationMasterVo.getEmail() + "%')");
		}
		if (userLocationMasterVo.getContactName() != null && !userLocationMasterVo.getContactName().isEmpty()) {
			modifiedQuery
					.append(" and LOWER(c.contactName) LIKE LOWER('%" + userLocationMasterVo.getContactName() + "%')");
		}

		if (userLocationMasterVo.getCity() != null) {
			modifiedQuery.append(" and LOWER(cty.cityName) LIKE LOWER('%" + userLocationMasterVo.getCity() + "%')");
		}
		if (userLocationMasterVo.getCountry() != null) {
			modifiedQuery.append(" and LOWER(con.country) LIKE LOWER('%" + userLocationMasterVo.getCountry() + "%')");
		}
		if (userLocationMasterVo.getState() != null) {
			modifiedQuery.append(" and LOWER(s.stateName) LIKE LOWER('%"+ userLocationMasterVo.getState() + "%')");
		}

		modifiedQuery.append(" ORDER BY c.id DESC");

		List<Object[]> userLocationList = (List<Object[]>) getEntityManager().createQuery(modifiedQuery.toString())
				.getResultList();

		return userLocationList;
	}
}
