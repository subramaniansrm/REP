package com.srm.coreframework.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srm.coreframework.entity.UserEntity;

/**
 * 
 * 
 * User Profile name
 * 
 * @author raathikaabm
 *
 */

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Serializable> {
	UserEntity findByuserName(String USER_LOGIN_ID);

	@Query(value = "SELECT COUNT(userRoleEntity.id) FROM UserEntity " + " where entityLicenseEntity.id = :entityId"
			+ " and deleteFlag = '0' AND userRoleEntity.id =:role ")
	public Integer deleteUser(@Param("entityId") Integer entityId, @Param("role") Integer role);

	@Query(value = " SELECT u.id,u.userName,u.firstName,u.lastName from UserEntity u where u.deleteFlag='0' and"
			+ " u.activeFlag='1' and u.entityLicenseEntity.id =:entityId and u.userRoleEntity.id=:roleId")
	List<Object> getRoleBasedUserList(@Param("roleId") Integer roleId, @Param("entityId") Integer entityId);

	@Query(value = " select u.userProfile,u.id,u.firstName,u.lastName from UserEntity u where u.deleteFlag='0' and u.entityLicenseEntity.id=:entityId and u.id=:userId")
	List<Object> loginProfile(@Param("entityId") Integer entityId, @Param("userId") Integer userId);

	@Query(value = " select  u.id,u.userEmployeeId,u.firstName,u.middleName,"
			+ "u.userName,u.skypeId,u.emailId,u.mobile, u.currentAddress,"
			+ "u.permanentAddress, u.userLocationEntity.id,u.userLocationEntity.userLocationName,"
			+ "u.subLocationEntity.sublocationId,u.subLocationEntity.subLocationName,"
			+ "u.userDepartmentEntity.id,u.userDepartmentEntity.userDepartmentName," + "u.userProfile,"

			+ "u.id," + "u.lastName,u.password," + "" + ""
			+ "u.userRoleEntity.id,u.userRoleEntity.userRoleName,u.divisionId," + " u.phoneNumber," + "u.userProfile"
			+ " from UserEntity u where u.deleteFlag='0' and u.entityLicenseEntity.id=:entityId and u.id=:userId")
	List<Object> userProfile(@Param("entityId") Integer entityId, @Param("userId") Integer userId);
	
	
	
	
	@Query(value = " select u.userEmployeeId,u.firstName,u.middleName,"
			+ "u.lastName,u.mobile,u.currentAddress,u.permanentAddress,u.emailId,u.skypeId,"
			+ " u.userLocationEntity.id,u.subLocationEntity.sublocationId,u.userDepartmentEntity.id,"
			+ "u.id,u.lastName,u.password ,"
			+ "u.userRoleEntity.id,u.userRoleEntity.userRoleName,u.divisionId,u.phoneNumber,u.userProfile"
			+ " from UserEntity u where u.deleteFlag='0' and u.id=:userId")
	List<Object> userProfileUpdate( @Param("userId") Integer userId);
	
	
	

	@Query(value = " select u.id,u.firstName,u.lastName,u.accessToken from UserEntity u where u.deleteFlag='0'  and u.id=:userId")
	List<Object> logOut( @Param("userId") Integer userId);

	@Query(value = "  from UserEntity u where u.deleteFlag='0'  and u.userName=:userId")
	UserEntity getUserId(@Param("userId") String userName);
	
	@Query(value = "  from UserEntity u where u.deleteFlag='0'  and u.id=:userId")
	UserEntity getUserName(@Param("userId") Integer userId);
	
	@Query(value="update UserEntity u set u.accessToken= null where u.id=:userId and u.deleteFlag='0'")
	void deleteToken(@Param("userId") Integer userId);

	
	@Query(value="update UserEntity u set u.entityLicenseEntity.id = :entityId where u.id=:userId and u.deleteFlag='0'")
	void updateUserEntity(@Param("userId") Integer userId,@Param("entityId") Integer entityId);
	
	@Query(value = "  from UserEntity u where u.deleteFlag='0'  and u.entityLicenseEntity.id=:userId")
	UserEntity getByEntityId(@Param("userId") Integer userId);
	
}
