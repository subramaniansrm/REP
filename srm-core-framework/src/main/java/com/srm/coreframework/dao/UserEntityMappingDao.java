package com.srm.coreframework.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.srm.coreframework.config.UserConstants;
import com.srm.coreframework.config.UserMessages;
import com.srm.coreframework.entity.EntityLicense;
import com.srm.coreframework.entity.UserEntity;
import com.srm.coreframework.entity.UserEntityMapping;
import com.srm.coreframework.entity.UserRole;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.UserRoleVO;

@Component
public class UserEntityMappingDao extends CommonDAO{
	

	@Autowired
	UserMessages userMessages;
	
	/**
	 * Method is used for Load the Entity
	 * 
	 * @return list List<EntityLicense>
	 */
	@SuppressWarnings("unchecked")
	public List<EntityLicense> getEntity(AuthDetailsVo authDetailsVo) {

		String query = "FROM EntityLicense c WHERE c.deleteFlag = " +CommonConstant.FLAG_ZERO
				+ " ORDER BY rin_ma_entity_name asc ";

		List<EntityLicense> list = (List<EntityLicense>) getEntityManager().createQuery(query).getResultList();

		return list;
	}
	
	
	
	/**
	 * Method is used for Load the Entity
	 * 
	 * @return list List<EntityLicense>
	 */
	@SuppressWarnings("unchecked")
	public List<UserEntityMapping> getUsersEntity(Integer id) {

		String query = "FROM UserEntityMapping c WHERE c.userId = " + id;

		List<UserEntityMapping> list = (List<UserEntityMapping>) getEntityManager().createQuery(query).getResultList();

		return list;
	}
	public void updateUserEntity(Integer userId, Integer entityId){
		String query = "update "+getCommonDatabaseSchema()+".user u set u.rin_ma_entity_id ='"+ entityId 
						+ "'  where u.USER_ID= '"+ userId+"'" ;
		getEntityManager().createNativeQuery(query).executeUpdate();
	}
	
/*	//Super Admin Entity - Null
    public void updateSuperAdminUserEntity(Integer userId){
		String query = "update "+getCommonDatabaseSchema()+".user u set u.rin_ma_entity_id = 'NULL'  where u.USER_ID= '"+ userId+"'" ;
		getEntityManager().createNativeQuery(query).executeUpdate();
	}*/
	
    public void updateUserEntity1(Integer userId, Integer entityId){
		String query = "update "+getCommonDatabaseSchema()+".user u set u.rin_ma_entity_id ='"+ entityId 
						+ "' where u.USER_ID= '"+ userId+"'" ;
		getEntityManager().createNativeQuery(query).executeUpdate();
	}
    
    
	public void updateUserEntityWithRole(Integer userId, Integer entityId,Integer defaultRoleId){
		String query = "update "+getCommonDatabaseSchema()+".user u set u.rin_ma_entity_id ='"+ entityId 
						+ "' , u.USER_ROLE_ID = " +  defaultRoleId + " where u.USER_ID= '"+ userId+"'" ;
		getEntityManager().createNativeQuery(query).executeUpdate();
	}
	
	public UserEntity getLoggedInUserRole(Integer userId){
		
		String query = " SELECT c.USER_ID,c.USER_ROLE_ID,r.USER_ROLE_NAME  FROM "+getCommonDatabaseSchema()+".user c "
				+ " LEFT JOIN "+getCommonDatabaseSchema()+".user_role r ON r.ROLE_ID = c.USER_ROLE_ID "
				+ "WHERE c.USER_ID = " + userId + " and c.delete_flag = "+ CommonConstant.FLAG_ZERO;
				
		Object[] object = (Object[]) getEntityManager().createNativeQuery(query).getSingleResult();
		UserEntity userEntity = null;
		if(null != object){
			userEntity = new UserEntity();
			if(null !=object[1] ){
				UserRole userRole = new UserRole(); 
				userRole.setId((Integer)object[1]);
				userRole.setUserRoleName((String)object[2]);
				userEntity.setUserRoleEntity(userRole);
			}
		}
		return userEntity;
	}
	
	@SuppressWarnings("unchecked")
	public UserRoleVO getSelectedEntityRole(Integer entityId, String roleName){
		
		UserRoleVO userRoleVO = null;
		String query = " select ROLE_ID,USER_ROLE_NAME FROM "+getCommonDatabaseSchema()+".user_role"
				+ " WHERE delete_flag = " + CommonConstant.FLAG_ZERO 
				//+ " and rin_ma_entity_id = " + entityId 
				//+ " and USER_ROLE_NAME LIKE '%"+roleName+ "%'";
				+ " and USER_ROLE_NAME = '"+ roleName+"' ";
		
		List<Object[]> userEntity = (List<Object[]>) getEntityManager().createNativeQuery(query).getResultList();
		
		if (null != userEntity && userEntity.size() == 1) {
			for(Object[] role :userEntity){
				userRoleVO = new UserRoleVO();
				userRoleVO.setId((Integer)role[0]);
			}
		
		} /*else {
			
			userRoleVO = new UserRoleVO();
			String defaultQuery = " select ROLE_ID,USER_ROLE_NAME FROM "+getCommonDatabaseSchema()+".user_role "
					+ " WHERE delete_flag = " + CommonConstant.FLAG_ZERO
					+ " and rin_ma_entity_id = " + entityId + " and USER_ROLE_NAME LIKE '%"
					+ userMessages.getEntityRole() + "%'";
			
			List<Object[]> defaultList = (List<Object[]>) getEntityManager().createNativeQuery(defaultQuery).getResultList();
			
			if (null != defaultList && defaultList.size() == 1) {
				for(Object[] role :defaultList){
					userRoleVO = new UserRoleVO();
					userRoleVO.setId((Integer)role[1]);
				}
			
			}
		}*/
		
		return userRoleVO;
		
	}
	
}
