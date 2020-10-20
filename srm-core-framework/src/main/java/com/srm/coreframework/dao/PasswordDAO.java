package com.srm.coreframework.dao;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Repository;

import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.constants.CodeSecurity;
import com.srm.coreframework.entity.UserEntity;
import com.srm.coreframework.entity.UserType;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.EntityLicenseVO;
import com.srm.coreframework.vo.LoginForm;
import com.srm.coreframework.vo.SubLocationVO;
import com.srm.coreframework.vo.UserDepartmentVO;
import com.srm.coreframework.vo.UserLocationVO;
import com.srm.coreframework.vo.UserMasterVO;
import com.srm.coreframework.vo.UserRoleVO;

@Repository
public class PasswordDAO extends CommonDAO {

	/**
	 * This method is to check valid login.
	 * 
	 * @param userName
	 *            UserMaster
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws Exception
	 */
	public UserMasterVO getLogin(String userName) throws IllegalAccessException, InvocationTargetException {

		@SuppressWarnings("unchecked")
		List<UserEntity> userList = (List<UserEntity>) getEntityManager()
				.createQuery("SELECT e FROM UserEntity e where e.activeFlag = " + CommonConstant.FLAG_ONE
						+ " and  e.userName = :uname ")
				.setParameter("uname", userName).getResultList();
		if (userList != null && userList.size() > 0) {
			UserMasterVO userMstr = new UserMasterVO();
			BeanUtils.copyProperties(userMstr, userList.get(0));

			String dycryptedPassword = null;
			try {
				dycryptedPassword = CodeSecurity.decrypt(userList.get(0).getPassword());
			} catch (Exception e) {
				e.printStackTrace();
			}
			userMstr.setPassword(dycryptedPassword.toString());

			if (null != userList.get(0).getUserRoleEntity().getId()
					&& userList.get(0).getUserRoleEntity().getId() != 0) {
				UserRoleVO roleMstr = new UserRoleVO();
				roleMstr.setId(userList.get(0).getUserRoleEntity().getId());
				roleMstr.setRoleType(userList.get(0).getUserRoleEntity().getUserTypeEntity().getUserTypeId());

				if (null != roleMstr.getRoleType()) {
					UserType userTypeEntity = getEntityManager().find(UserType.class, roleMstr.getRoleType());
					userMstr.setUrl(userTypeEntity.getUrl());
				}

				userMstr.setUserRoleMaster(roleMstr);
			}
			if (null != userList.get(0).getUserLocationEntity().getId()
					&& userList.get(0).getUserLocationEntity().getId() != 0) {
				UserLocationVO locationMstr = new UserLocationVO();
				locationMstr.setId(userList.get(0).getUserLocationEntity().getId());
				userMstr.setUserLocationMaster(locationMstr);
			}
			if (userList.get(0).getSubLocationEntity().getSublocationId() != 0) {
				SubLocationVO subLocationVo = new SubLocationVO();
				subLocationVo.setSublocationId(userList.get(0).getSubLocationEntity().getSublocationId());
				userMstr.setSubLocationMaster(subLocationVo);
			}
			if (null != userList.get(0).getUserDepartmentEntity().getId()
					&& userList.get(0).getUserDepartmentEntity().getId() != 0) {
				UserDepartmentVO departmentMstr = new UserDepartmentVO();
				departmentMstr.setId(userList.get(0).getUserDepartmentEntity().getId());
				userMstr.setUserDepartmentMaster(departmentMstr);
			}

			if (null != userList.get(0).getEntityLicenseEntity()
					&& userList.get(0).getEntityLicenseEntity().getId() != 0) {
				EntityLicenseVO entityMstr = new EntityLicenseVO();
				entityMstr.setId(userList.get(0).getEntityLicenseEntity().getId());
				userMstr.setEntityLicenseMstr(entityMstr);
			}

			return userMstr;
		}
		return null;
	}

	/**
	 * This method is to used to check old password.
	 * 
	 * @param changePasswordRequest
	 * @return LoginForm
	 */
	public Object[] getOldPassword(LoginForm changePasswordRequest) {

		String query = "Select e.PASSWORD,e.USER_ID from " + getCommonDatabaseSchema() +".user e where e.USER_ID = '"
				+ changePasswordRequest.getUserId() + "' ";

		Object[] userList = (Object[]) getEntityManager().createNativeQuery(query).getSingleResult();

		return userList;

	}

	/**
	 * This method is to used for change password.
	 * 
	 * @param changePasswordRequest
	 * @return LoginForm
	 */
	@Transactional
	public boolean changePassword(LoginForm passwordRequest) throws CommonException {

		try {
			String query = "UPDATE " + getCommonDatabaseSchema() + ".user  set PASSWORD = '"
					+ passwordRequest.getNewPassword() + "' ,FIRST_LOGIN = '1' , CHANGE_PASSWORD_DATE = '"
					+ CommonConstant.getCurrentDateTimeAsString() + "'where USER_ID = " + passwordRequest.getUserId();

			int updateRow = getEntityManager().createNativeQuery(query).executeUpdate();

			/*
			 * int updateRow = getEntityManager().createQuery(
			 * "UPDATE UserEntity set password =:newPassword where  id=:userId  "
			 * ) .setParameter("userId", passwordRequest.getUserId())
			 * .setParameter("newPassword",
			 * passwordRequest.getNewPassword()).executeUpdate();
			 */

			if (updateRow > 0) {
				return true;

			} else {

				return false;
			}

		} catch (Exception e) {

			throw new CommonException("Data Failure");
		}
	}

	/**
	 * This method is to used for forgot password process.
	 * 
	 * @param forgotPasswordRequest
	 * @return LoginForm
	 * @throws Exception
	 */
	@Transactional
	public Object[] forgotPassword(LoginForm forgotPasswordRequest) throws CommonException, Exception {

		String query = "Select e.USER_ID,e.EMAIL_ID from " + getCommonDatabaseSchema() +".user e where e.USER_LOGIN_ID = '"
				+ forgotPasswordRequest.getUserLoginId() + "' ";

		Object[] userList = (Object[]) getEntityManager().createNativeQuery(query).getSingleResult();

		return userList;
	}

	public int forgotPassword(String loginId) throws CommonException, Exception {

		String query = "Select Count(e.USER_LOGIN_ID) from " + getCommonDatabaseSchema() +".user e where e.USER_LOGIN_ID = '" + loginId + "' ";

		BigInteger i = (BigInteger)  getEntityManager().createNativeQuery(query).getSingleResult();

		int count = i.intValue();
		
		return count;
	}

}
