package com.srm.coreframework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.srm.coreframework.entity.ScreenAuthentication;
import com.srm.coreframework.vo.ScreenAuthenticationVO;


public interface ScreenAuthenticationRepository extends JpaRepository<ScreenAuthentication, Integer>  {
	
	@Query(value="SELECT s.screenAuthenticationId FROM ScreenAuthentication s where s.userRoleEntity.id =:roleId and s.screenId=:screenId and s.subScreenId=:subScreenId and s.entityLicenseEntity.id=:entityId")
	public List<Integer> checkScreenExists(@Param("roleId")Integer roleId, @Param("screenId")Integer screenId, @Param("subScreenId")Integer subScreenId,@Param("entityId")Integer entityId);
	
	
	@Modifying
	@Query(value="DELETE FROM ScreenAuthentication where screenAuthenticationId=:screenAuthenticationId and deleteFlag='0' and entityLicenseEntity.id=:entityId ")
	public void deleteScreenAuthentication(@Param("screenAuthenticationId")Integer screenAuthenticationId,@Param("entityId")Integer entityId);
	
	@Query(value="SELECT s.screenAuthenticationId FROM ScreenAuthentication s where s.userRoleEntity.id =:roleId and s.entityLicenseEntity.id=:entityId and s.deleteFlag='0'")
	public List<Integer> getAuthenticationId(@Param("roleId")Integer roleId,@Param("entityId")Integer entityId);
	
	@Query(value="SELECT u.id FROM UserEntity u where u.userRoleEntity.id =:roleId and u.entityLicenseEntity.id=:entityId and u.deleteFlag='0'")
	public List<Integer> getRoleBasedUsers(@Param("roleId")Integer roleId,@Param("entityId")Integer entityId);
	
	@Query(value="SELECT r.id ,r.userRoleName FROM UserRole r WHERE r.deleteFlag = '0' and r.entityLicenseEntity.id =:entityId")
	public List<Object>getRoleList(@Param("entityId")Integer entityId);
	
	@Query(value="SELECT u.id ,u.firstName,u.lastName FROM UserEntity u WHERE u.deleteFlag = '0' and u.entityLicenseEntity.id =:entityId and u.userRoleEntity.id=:roleId and activeFlag='1'")
	public List<Object>getRoleBasedUserList(@Param("roleId")Integer roleId,@Param("entityId")Integer entityId);
	
	@Query(value="SELECT r.screenId ,r.screenName FROM Screen r WHERE r.activeFlag = '1' and r.entityLicenseEntity.id =:entityId")
	public List<Object>getScreenList(@Param("entityId")Integer entityId);

	@Query(value="Select s.screenId from ScreenAuthentication s where s.userRoleEntity.id=:roleId and s.deleteFlag='0'and s.entityLicenseEntity.id=:entityId ")
	public List<Integer> authenticationRole(@Param("entityId") Integer entityId,@Param("roleId")Integer roleId);


	@Query(value="Select s.screenAuthenticationId from ScreenAuthentication s where s.userRoleEntity.id=:roleId and s.deleteFlag='0'and s.entityLicenseEntity.id=:entityId "
			+ " and s.screenId=:screenId and s.subScreenId=:subScreenId ")
	public List<Integer> screenSubScreenBasedRole(@Param("roleId") Integer roleId, @Param("screenId") Integer screenId, @Param("subScreenId") Integer subScreenId,
			 @Param("entityId")	Integer entityId);

	@Query(value=" select s.subScreenId from ScreenAuthentication s where s.userRoleEntity.id =:roleId and s.deleteFlag='0'"
			+ " and s.entityLicenseEntity.id=:entityId  and s.screenId=:screenId")
	public List<Integer> authenticationRoleForScreen(@Param("roleId") Integer roleId,@Param("screenId") Integer screenId,@Param("entityId")  Integer entityId);


	

}





