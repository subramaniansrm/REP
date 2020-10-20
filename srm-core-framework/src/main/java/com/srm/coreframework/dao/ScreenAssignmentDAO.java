package com.srm.coreframework.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.srm.coreframework.entity.UserRole;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.ScreenAuthenticationVO;
import com.srm.coreframework.vo.UserRoleMasterListDisplayVo;

@Repository
public class ScreenAssignmentDAO  extends CommonDAO{


	@SuppressWarnings("unchecked")
	public List<UserRoleMasterListDisplayVo> searchScreenAuthentication(
			ScreenAuthenticationVO screenAuthenticationMaster,AuthDetailsVo authDetailsVo) {

		List<UserRoleMasterListDisplayVo> userRoleMasterListDisplayVoList = new ArrayList<UserRoleMasterListDisplayVo>();

		String query = " select c FROM UserRole c where c.deleteFlag= '" + CommonConstant.FLAG_ZERO + "'"
				+ " and c.entityLicenseEntity.id = " + authDetailsVo.getEntityId();

		if (screenAuthenticationMaster.getRoleId() != null) {
			query = query + " and c.id = '" + screenAuthenticationMaster.getRoleId() + "'";
		}
		if (screenAuthenticationMaster.getRoleName() != null) {
			query = query + " and LOWER(c.userRoleName) LIKE LOWER('%" + screenAuthenticationMaster.getRoleName()
					+ "%')";
		}

		
			List<UserRole> screenAuthenticationList = (List<UserRole>) getEntityManager().createQuery(query).getResultList();

			UserRoleMasterListDisplayVo screenAuthenticationMasterMap = null;
			@SuppressWarnings("unused")
			ScreenAuthenticationVO screenAuthenticationVO = null;

			for (UserRole screenAuthenticationEntity : screenAuthenticationList) {

				screenAuthenticationMasterMap = new UserRoleMasterListDisplayVo();
				screenAuthenticationVO = new ScreenAuthenticationVO();
				BeanUtils.copyProperties(screenAuthenticationEntity, screenAuthenticationMasterMap);

				userRoleMasterListDisplayVoList.add(screenAuthenticationMasterMap);
			}
			return userRoleMasterListDisplayVoList;
		

	}

	@SuppressWarnings("unchecked")
	public List<Integer> checkScreenExists(Integer roleId, Integer screenId, Integer subScreenId,AuthDetailsVo authDetailsVo) {

		String query = " SELECT s.SCREEN_AUTHENTICATION_ID FROM "+getCommonDatabaseSchema()+".`screen_authentication` s WHERE "
				+ " s.USER_ROLE_ID = " + roleId + " and  s.delete_flag = " + CommonConstant.FLAG_ZERO
				+ " and  s.SCREEN_ID = " + screenId + " and s.SUB_SCREEN_ID = " + subScreenId
				+ " and s.rin_ma_entity_id = '" + authDetailsVo.getEntityId() + "' ";
		List<Integer> result = (List<Integer>) getEntityManager().createNativeQuery(query).getResultList();
		return result;

	}
	
	@SuppressWarnings("unchecked")
	@Query(value="Select s.screenId from ScreenAuthentication s where s.userRoleEntity.id=:roleId and s.deleteFlag='0'and s.entityLicenseEntity.id=:entityId ")
	public List<Integer> authenticationRole (Integer roleId ,Integer entityId){
		
		String query = " SELECT s.SCREEN_ID FROM "+getCommonDatabaseSchema()+".`screen_authentication` s WHERE "
				+ " s.USER_ROLE_ID = " + roleId + " and  s.delete_flag = " + CommonConstant.FLAG_ZERO
				+ " and s.rin_ma_entity_id = '" + entityId + "' ";
		List<Integer> result = (List<Integer>) getEntityManager().createNativeQuery(query).getResultList();
		return result;
		
	}

}
