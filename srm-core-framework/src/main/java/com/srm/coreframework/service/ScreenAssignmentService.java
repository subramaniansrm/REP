package com.srm.coreframework.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.config.UserConstants;
import com.srm.coreframework.dao.ScreenAssignmentDAO;
import com.srm.coreframework.entity.EntityLicense;
import com.srm.coreframework.entity.FieldAuthentication;
import com.srm.coreframework.entity.FunctionAuthentication;
import com.srm.coreframework.entity.ScreenAuthentication;
import com.srm.coreframework.entity.UserRole;
import com.srm.coreframework.repository.FieldAuthenticationRepository;
import com.srm.coreframework.repository.FunctionAuthenticationRepostiory;
import com.srm.coreframework.repository.ScreenAuthenticationRepository;
import com.srm.coreframework.repository.ScreenFieldRepositoy;
import com.srm.coreframework.repository.ScreenFunctionRepository;
import com.srm.coreframework.repository.ScreenRepository;
import com.srm.coreframework.repository.SubScreenRepository;
import com.srm.coreframework.repository.UserRepository;
import com.srm.coreframework.repository.UserRoleRepository;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.AuthenticationListComboVO;
import com.srm.coreframework.vo.FieldAuthenticationVO;
import com.srm.coreframework.vo.FunctionAuthenticationVO;
import com.srm.coreframework.vo.ScreenAuthenticationVO;
import com.srm.coreframework.vo.ScreenAuthorizationVO;
import com.srm.coreframework.vo.ScreenJsonVO;
import com.srm.coreframework.vo.UserMasterListDisplayVO;
import com.srm.coreframework.vo.UserRoleMasterListDisplayVo;

@Component
public class ScreenAssignmentService extends CommonService {

	
	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	ScreenAuthenticationRepository screenAuthenticationRepository;

	@Autowired
	FunctionAuthenticationRepostiory functionAuthenticationRepostiory;

	@Autowired
	FieldAuthenticationRepository fieldAuthenticationRepository;

	@Autowired
	UserConstants userConstants;

	@Autowired
	ScreenAssignmentDAO screenAssignmentDAO;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ScreenRepository screenRepository;

	@Autowired
	ScreenFieldRepositoy screenFieldRepositoy;

	@Autowired
	ScreenFunctionRepository screenFunctionRepository;

	@Autowired
	SubScreenRepository subScreenRepository;

	@Transactional
	public List<UserRoleMasterListDisplayVo> searchScreenAuthentication(
			ScreenAuthenticationVO screenAuthenticationMaster,AuthDetailsVo authDetailsVo) {

		return screenAssignmentDAO.searchScreenAuthentication(screenAuthenticationMaster,authDetailsVo);
	}

	@Transactional
	public ScreenAuthenticationVO saveScreenAssignment(ScreenAuthenticationVO screenAuthenticationMaster,AuthDetailsVo authDetailsVo)
			throws CommonException {

		if (null != screenAuthenticationMaster.getScreenAuthenticationMasterList()
				&& screenAuthenticationMaster.getScreenAuthenticationMasterList().size() > 0) {

			for (ScreenAuthenticationVO screenAuthenticationMaster2 : screenAuthenticationMaster
					.getScreenAuthenticationMasterList()) {
				screenAuthenticationMaster2.setRoleId(screenAuthenticationMaster.getRoleId());
				List<Integer> list = screenAssignmentDAO.checkScreenExists(screenAuthenticationMaster.getRoleId(),
						screenAuthenticationMaster2.getScreenId(), screenAuthenticationMaster2.getSubScreenId(),authDetailsVo);
				if (null != list && list.size() > 0) {
					for (Integer id : list) {
						// screenAuthenticationMaster2.setRoleId(screenAuthenticationMaster.getRoleId());
						if (null != id) {
							updateScreenAuthentication(id, screenAuthenticationMaster2,authDetailsVo);
						}
					}
				} else {
					// Save Screen Authentication
					saveScreenAuthentication(screenAuthenticationMaster2,authDetailsVo);
				}

			}

		}

		return screenAuthenticationMaster;
	}

	@Transactional
	public ScreenAuthenticationVO updateScreenAuthentication(Integer authenticationId,
			ScreenAuthenticationVO screenAuthenticationMaster,AuthDetailsVo authDetailsVo) {
		Integer entityId = authDetailsVo.getEntityId();

		if (null != authenticationId) {

			functionAuthenticationRepostiory.deleteFunction(authenticationId);
			fieldAuthenticationRepository.deleteField(authenticationId);

			// Delete Screen Fields Based on screenAuthenticationId
			screenAuthenticationRepository.deleteScreenAuthentication(authenticationId, entityId);
			
			/*ScreenAuthentication screenAuthentication = new ScreenAuthentication();
			BeanUtils.copyProperties(screenAuthenticationMaster, screenAuthentication);
			screenAuthenticationRepository.save(screenAuthentication);*/
			saveScreenAuthentication(screenAuthenticationMaster,authDetailsVo);

		}

		return screenAuthenticationMaster;

	}

	@Transactional
	public ScreenAuthenticationVO saveScreenAuthentication(ScreenAuthenticationVO screenAuthenticationMaster,AuthDetailsVo authDetailsVo)
			throws CommonException {

		Integer entityId = authDetailsVo.getEntityId();

		if (null != screenAuthenticationMaster.getRoleId()) {

			ScreenAuthentication screenAuthenticationEntity = new ScreenAuthentication();
			screenAuthenticationEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);
			screenAuthenticationEntity.setCreateBy(authDetailsVo.getUserId());
			screenAuthenticationEntity.setCreateDate(CommonConstant.getCalenderDate());
			screenAuthenticationEntity.setUpdateBy(authDetailsVo.getUserId());
			screenAuthenticationEntity.setUpdateDate(CommonConstant.getCalenderDate());

			/*
			 * SystemApplicationEntity systemApplicationEntity = new
			 * SystemApplicationEntity();
			 * systemApplicationEntity.setSysAppId(userConstants.getSysAppId());
			 * screenAuthenticationEntity.setSysAppEntity( systemApplicationEntity);
			 */

			UserRole userRoleEntity = new UserRole();
			userRoleEntity.setId(screenAuthenticationMaster.getRoleId());
			screenAuthenticationEntity.setUserRoleEntity(userRoleEntity);
			EntityLicense entityLicenseEntity = new EntityLicense();
			entityLicenseEntity.setId(authDetailsVo.getEntityId());

			screenAuthenticationEntity.setEntityLicenseEntity(entityLicenseEntity);
			if (null != screenAuthenticationMaster.getScreenId()) {

				screenAuthenticationEntity.setScreenId(screenAuthenticationMaster.getScreenId());
				// screenAuthenticationEntity.setScreenEntity(screenEntity);
			}
			if (null != screenAuthenticationMaster.getSubScreenId()) {

				screenAuthenticationEntity.setSubScreenId(screenAuthenticationMaster.getSubScreenId());
				// screenAuthenticationEntity.setSubScreenEntity(subScreenEntity);
			}

			if (null == screenAuthenticationMaster.getSubScreenId()) {
				throw new CommonException(getMessage("selectSubScreen",authDetailsVo));
			}

			ScreenAuthentication screenAuthenticationEntity2 = screenAuthenticationRepository
					.save(screenAuthenticationEntity);

			if (screenAuthenticationMaster.getFieldAuthenticationMasterList().isEmpty()
					&& screenAuthenticationMaster.getFunctionAuthenticationMasterList().isEmpty()) {

				if (userConstants.getStaticScreenIds().contains(screenAuthenticationMaster.getScreenId())) {
					// Save Screen Fields
					saveScreenFields(screenAuthenticationMaster,
							screenAuthenticationEntity2.getScreenAuthenticationId(),authDetailsVo );
					// Save Screen Functions
					saveScreenFunctions(screenAuthenticationMaster,
							screenAuthenticationEntity2.getScreenAuthenticationId(),authDetailsVo );

				} else {
					screenAuthenticationRepository.deleteScreenAuthentication(
							screenAuthenticationEntity2.getScreenAuthenticationId(), entityId);
				}
			} else if (null != screenAuthenticationEntity2
					&& null != screenAuthenticationEntity2.getScreenAuthenticationId()) {
				// Save Screen Fields
				saveScreenFields(screenAuthenticationMaster, screenAuthenticationEntity2.getScreenAuthenticationId(),authDetailsVo);
				// Save Screen Funcions
				saveScreenFunctions(screenAuthenticationMaster,
						screenAuthenticationEntity2.getScreenAuthenticationId(),authDetailsVo);
			}
		}

		return screenAuthenticationMaster;
	}

	@Transactional
	public ScreenAuthenticationVO saveScreenFunctions(ScreenAuthenticationVO screenAuthenticationMaster,
			Integer screenAuthenticationId,AuthDetailsVo authDetailsVo) {

		if (null != screenAuthenticationId && null != screenAuthenticationMaster.getFunctionAuthenticationMasterList()
				&& screenAuthenticationMaster.getFunctionAuthenticationMasterList().size() > 0) {
			FunctionAuthentication functionAuthenticationEntity = null;
			for (FunctionAuthenticationVO functionAuthenticationMaster : screenAuthenticationMaster
					.getFunctionAuthenticationMasterList()) {

				if (null != functionAuthenticationMaster.getScreenFunctionId()) {
					functionAuthenticationEntity = new FunctionAuthentication();

					functionAuthenticationEntity.setCreateBy(authDetailsVo.getUserId());
					functionAuthenticationEntity.setCreateDate(CommonConstant.getCalenderDate());
					functionAuthenticationEntity.setUpdateBy(authDetailsVo.getUserId());
					functionAuthenticationEntity.setUpdateDate(CommonConstant.getCalenderDate());
					functionAuthenticationEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);

					/*
					 * SystemApplicationEntity systemApplicationEntity = new
					 * SystemApplicationEntity(); systemApplicationEntity.setSysAppId(userConstants.
					 * getSysAppId()); functionAuthenticationEntity.setSysAppEntity(
					 * systemApplicationEntity);
					 */

					// ScreenAuthenticationEntity screenAuthenticationEntity =
					// new ScreenAuthenticationEntity();
					functionAuthenticationEntity.setScreenAuthenticationId(screenAuthenticationId);
					// functionAuthenticationEntity.setScreenAuthenticationEntity(screenAuthenticationEntity);

					if (null != functionAuthenticationMaster.getScreenFunctionId()) {

						functionAuthenticationEntity
								.setScreenFunctionId(functionAuthenticationMaster.getScreenFunctionId());
						// functionAuthenticationEntity.setScreenFunctionEntity(screenFunctionEntity);
					}
					EntityLicense entityLicenseEntity = new EntityLicense();
					entityLicenseEntity.setId(authDetailsVo.getEntityId());

					functionAuthenticationEntity.setEntityLicenseEntity(entityLicenseEntity);

					functionAuthenticationRepostiory.save(functionAuthenticationEntity);
				}
			}
		}
		return screenAuthenticationMaster;
	}

	@Transactional
	public ScreenAuthenticationVO saveScreenFields(ScreenAuthenticationVO screenAuthenticationMaster,
			Integer screenAuthenticationId,AuthDetailsVo authDetailsVo) {

		if (null != screenAuthenticationId && null != screenAuthenticationMaster.getFieldAuthenticationMasterList()
				&& screenAuthenticationMaster.getFieldAuthenticationMasterList().size() > 0) {
			FieldAuthentication fieldAuthenticationEntity = null;
			for (FieldAuthenticationVO fieldAuthenticationMaster : screenAuthenticationMaster
					.getFieldAuthenticationMasterList()) {

				fieldAuthenticationEntity = new FieldAuthentication();
				fieldAuthenticationEntity.setDeleteFlag(CommonConstant.FLAG_ZERO);
				fieldAuthenticationEntity.setCreateBy(authDetailsVo.getUserId());
				fieldAuthenticationEntity.setCreateDate(CommonConstant.getCalenderDate());
				fieldAuthenticationEntity.setUpdateBy(authDetailsVo.getUserId());
				fieldAuthenticationEntity.setUpdateDate(CommonConstant.getCalenderDate());

				/*
				 * SystemApplicationEntity systemApplicationEntity = new
				 * SystemApplicationEntity();
				 * systemApplicationEntity.setSysAppId(userConstants.getSysAppId ());
				 * fieldAuthenticationEntity.setSysAppEntity( systemApplicationEntity);
				 */

				ScreenAuthentication screenAuthenticationEntity = new ScreenAuthentication();
				fieldAuthenticationEntity.setScreenAuthenticationId(screenAuthenticationId);
				// fieldAuthenticationEntity.setScreenAuthenticationEntity(screenAuthenticationEntity);

				fieldAuthenticationEntity.setScreenFieldId(fieldAuthenticationMaster.getFieldId());
				// fieldAuthenticationEntity.setScreenFieldEntity(screenFieldEntity);

				/*
				 * if(null != fieldAuthenticationMaster.getBaseFilter() &&
				 * fieldAuthenticationMaster.getBaseFilter().equals(true)){
				 * fieldAuthenticationEntity.setBaseFilter(CommonConstant. STRING_Y); }else{
				 * fieldAuthenticationEntity.setBaseFilter(CommonConstant. STRING_N); }
				 */

				EntityLicense entityLicenseEntity = new EntityLicense();
				entityLicenseEntity.setId(authDetailsVo.getEntityId());

				fieldAuthenticationEntity.setEntityLicenseEntity(entityLicenseEntity);
				fieldAuthenticationRepository.save(fieldAuthenticationEntity);
			}
		}
		return screenAuthenticationMaster;
	}

	@Transactional
	public ScreenAuthenticationVO deleteScreenAssignment(ScreenAuthenticationVO screenAuthenticationMaster,AuthDetailsVo authDetailsVo)
			throws CommonException {

		Integer entityId = authDetailsVo.getEntityId();

		// Get screenAuthenticationId Based on Role

		List<Integer> screenAuthenticationIdList = screenAuthenticationRepository
				.getAuthenticationId(screenAuthenticationMaster.getRoleId(), entityId);

		List<Integer> userList = screenAuthenticationRepository
				.getRoleBasedUsers(screenAuthenticationMaster.getRoleId(), entityId);

		if (null != userList && userList.size() > 0) {
			throw new CommonException(getMessage("userExistForSelectedRole",authDetailsVo));
		}
		for (Integer screenAuthenticationId : screenAuthenticationIdList) {

			// Delete Screen Functions Based on screenAuthenticationId
			functionAuthenticationRepostiory.deleteFunction(screenAuthenticationId);

			// Delete Screen Fields Based on screenAuthenticationId
			fieldAuthenticationRepository.deleteField(screenAuthenticationId);

			// Delete Screen Fields Based on screenAuthenticationId
			screenAuthenticationRepository.deleteScreenAuthentication(screenAuthenticationId, entityId);
		}

		return screenAuthenticationMaster;
	}

	@Transactional
	public ScreenAuthenticationVO deleteAuthenticationBeforeUpdating(
			ScreenAuthenticationVO screenAuthenticationMaster,AuthDetailsVo authDetailsVo) {

		Integer entityId = authDetailsVo.getEntityId();

		// Get screenAuthenticationId Based on Role
		List<Integer> screenAuthenticationIdList = screenAuthenticationRepository
				.getAuthenticationId(screenAuthenticationMaster.getRoleId(), entityId);

		for (Integer screenAuthenticationId : screenAuthenticationIdList) {

			// Delete Screen Functions Based on screenAuthenticationId
			functionAuthenticationRepostiory.deleteFunction(screenAuthenticationId);

			// Delete Screen Fields Based on screenAuthenticationId
			fieldAuthenticationRepository.deleteField(screenAuthenticationId);

			// Delete Screen Fields Based on screenAuthenticationId
			screenAuthenticationRepository.deleteScreenAuthentication(screenAuthenticationId, entityId);
		}

		return screenAuthenticationMaster;
	}

	@Transactional
	public List<UserRoleMasterListDisplayVo> getRoleList(AuthDetailsVo authDetailsVo) {

		Integer entityId = authDetailsVo.getEntityId();
		// Get Role List
		List<Object> result = userRoleRepository.getRoleList(entityId);
		List<UserRoleMasterListDisplayVo> userRoleMasterListDisplayVoList = new ArrayList<UserRoleMasterListDisplayVo>();
		UserRoleMasterListDisplayVo userRoleMasterListDisplayVo = null;
		@SuppressWarnings("rawtypes")
		Iterator itr = result.iterator();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();

			userRoleMasterListDisplayVo = new UserRoleMasterListDisplayVo();

			if (null != obj[0]) {
				userRoleMasterListDisplayVo.setId(Integer.parseInt(String.valueOf(obj[0])));
			}
			if (null != obj[1]) {
				userRoleMasterListDisplayVo.setUserRoleName(String.valueOf(obj[1]));
			}
			userRoleMasterListDisplayVoList.add(userRoleMasterListDisplayVo);
		}
		return userRoleMasterListDisplayVoList;
	}

	@Transactional
	public ScreenAuthenticationVO load(ScreenAuthenticationVO screenAuthenticationMaster,AuthDetailsVo authDetailsVo) {

		// Load Users and Role Name For Role Id
		loadUserAndRoleName(screenAuthenticationMaster,authDetailsVo);

		// Load Screen List based on Role
		loadRoleBasedScreenList(screenAuthenticationMaster,authDetailsVo);

		return screenAuthenticationMaster;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public List<AuthenticationListComboVO> loadRoleBasedSubScreenList(
			ScreenAuthenticationVO screenAuthenticationMaster,AuthDetailsVo authDetailsVo) {

		Integer entityId = authDetailsVo.getEntityId();

		// Load Sub Screen List based on Screen
		List<Object> result = subScreenRepository.getSubScreenList(screenAuthenticationMaster.getScreenId(), entityId);

		// Load Sub Screen List based on Role and Screen
		List<Integer> subScreenList = screenAuthenticationRepository.authenticationRoleForScreen(
				screenAuthenticationMaster.getRoleId(), screenAuthenticationMaster.getScreenId(), entityId);

		AuthenticationListComboVO authenticationListCombo = null;
		List<AuthenticationListComboVO> authenticationListComboList = new ArrayList<AuthenticationListComboVO>();
		Iterator itr = result.iterator();
		while (itr.hasNext()) {
			authenticationListCombo = new AuthenticationListComboVO();
			Object[] obj = (Object[]) itr.next();
			authenticationListCombo.setId(Integer.parseInt(String.valueOf(obj[0])));
			authenticationListCombo.setName(String.valueOf(obj[1]));
			if (null != subScreenList && subScreenList.size() > 0) {
				if (null != authenticationListCombo.getId()
						&& subScreenList.contains(authenticationListCombo.getId())) {
					authenticationListCombo.setResult(true);
				} else {
					authenticationListCombo.setResult(false);
				}
			}
			if (null == authenticationListCombo.getResult()) {
				authenticationListCombo.setResult(false);
			}
			authenticationListComboList.add(authenticationListCombo);
		}

		return authenticationListComboList;
	}

	@Transactional
	public Map<Integer, String> loadRoleCombo(AuthDetailsVo authDetailsVo) {
		Integer entityId = authDetailsVo.getEntityId();
		return userRoleRepository.loadRoleCombo(entityId);
	}

	@SuppressWarnings("rawtypes")
	public List<AuthenticationListComboVO> loadRoleBasedFunctionList(
			ScreenAuthenticationVO screenAuthenticationMaster,AuthDetailsVo authDetailsVo) {

		Integer entityId = authDetailsVo.getEntityId();

		// Load Function List Based on Sub Screen
		List<Object> result = screenFunctionRepository.getFunctionList(screenAuthenticationMaster.getSubScreenId(),
				entityId);

		// Get Screen Authentication Id
		List<Integer> screenAuthenticationIdList = screenAuthenticationRepository.screenSubScreenBasedRole(
				screenAuthenticationMaster.getRoleId(), screenAuthenticationMaster.getScreenId(),
				screenAuthenticationMaster.getSubScreenId(), entityId);
		List<AuthenticationListComboVO> authenticationListComboList = new ArrayList<AuthenticationListComboVO>();
		List<Integer> functionList = new ArrayList<Integer>();
		for (Integer screenAuthenticationId : screenAuthenticationIdList) {

			screenAuthenticationMaster.setScreenAuthenticationId(screenAuthenticationId);
			functionList = functionAuthenticationRepostiory.screenFunctionBasedRole(screenAuthenticationId, entityId);
		}

		// Load Screen Functions

		AuthenticationListComboVO authenticationListCombo = null;
		Iterator itr = result.iterator();
		while (itr.hasNext()) {
			authenticationListCombo = new AuthenticationListComboVO();
			Object[] obj = (Object[]) itr.next();

			authenticationListCombo.setId(Integer.parseInt(String.valueOf(obj[0])));
			authenticationListCombo.setName(String.valueOf(obj[1]));
			if (null != functionList && functionList.size() > 0) {
				if (null != authenticationListCombo.getId() && functionList.contains(authenticationListCombo.getId())) {
					authenticationListCombo.setResult(true);
				} else {
					authenticationListCombo.setResult(false);
				}
			}
			if (null == authenticationListCombo.getResult()) {
				authenticationListCombo.setResult(false);
			}
			authenticationListComboList.add(authenticationListCombo);
		}
		return authenticationListComboList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public List<AuthenticationListComboVO> loadRoleBasedScreenFieldsList(
			ScreenAuthenticationVO screenAuthenticationMaster,AuthDetailsVo authDetailsVo) {

		Integer entityId = authDetailsVo.getEntityId();

		// Load Field List Based on Sub Screen
		List<Object> result = screenFieldRepositoy.getScreenFieldList(screenAuthenticationMaster.getSubScreenId(),
				entityId);
		List<AuthenticationListComboVO> authenticationListComboList = new ArrayList<AuthenticationListComboVO>();
		// Get Screen Authentication Id
		List<Integer> screenAuthenticationIdList = screenAuthenticationRepository.screenSubScreenBasedRole(
				screenAuthenticationMaster.getRoleId(), screenAuthenticationMaster.getScreenId(),
				screenAuthenticationMaster.getSubScreenId(), entityId);
		List<Integer> fieldList = new ArrayList<Integer>();

		for (Integer screenAuthenticationId : screenAuthenticationIdList) {

			screenAuthenticationMaster.setScreenAuthenticationId(screenAuthenticationId);

			// Get Fields of Authentication Id
			if (null != screenAuthenticationId) {
				fieldList = fieldAuthenticationRepository.getAuthenticationBasedFields(screenAuthenticationId,
						entityId);
			}
		}
		AuthenticationListComboVO authenticationListCombo = null;

		Iterator itr = result.iterator();
		while (itr.hasNext()) {
			authenticationListCombo = new AuthenticationListComboVO();
			Object[] obj = (Object[]) itr.next();

			authenticationListCombo.setId(Integer.parseInt(String.valueOf(obj[0])));
			authenticationListCombo.setName(String.valueOf(obj[1]));
			if (null != fieldList && fieldList.size() > 0) {
				if (null != authenticationListCombo.getId() && fieldList.contains(authenticationListCombo.getId())) {
					authenticationListCombo.setResult(true);
				} else {
					authenticationListCombo.setResult(false);
				}
			}
			if (null == authenticationListCombo.getResult()) {
				authenticationListCombo.setResult(false);
			}

			/*
			 * // Get Base filter From Field Id if (null != authenticationListCombo.getId()
			 * && null != screenAuthenticationMaster.getScreenAuthenticationId()) {
			 * List<String> baseFilter =
			 * screenAssignmentDao.getAuthenticationBasedFieldsWithBaseFilter(
			 * screenAuthenticationMaster.getScreenAuthenticationId(),
			 * authenticationListCombo.getId()); if (null != baseFilter && baseFilter.size()
			 * > 0) { for (String filter : baseFilter) { if (null != filter &&
			 * filter.equals(CommonConstant.STRING_Y)) {
			 * authenticationListCombo.setBaseResult(true); } else {
			 * authenticationListCombo.setBaseResult(false); } } }
			 * 
			 * } if (null == authenticationListCombo.getBaseResult()) {
			 * authenticationListCombo.setBaseResult(false); }
			 */
			authenticationListComboList.add(authenticationListCombo);
		}

		return authenticationListComboList;
	}

	@SuppressWarnings("rawtypes")
	public ScreenAuthenticationVO loadRoleBasedScreenList(ScreenAuthenticationVO screenAuthenticationMaster,AuthDetailsVo authDetailsVo) {
		Integer entityId = authDetailsVo.getEntityId();

		// Load All Screen List
		List<Object> result = screenRepository.getScreenList(entityId);
		// Load Screen List Based on Role
		/*List<Integer> screenList = screenAuthenticationRepository
				.authenticationRole(screenAuthenticationMaster.getRoleId(), entityId);*/
		List<Integer> screenList = screenAssignmentDAO
				.authenticationRole(screenAuthenticationMaster.getRoleId(), entityId);
		AuthenticationListComboVO authenticationListCombo = null;
		List<AuthenticationListComboVO> authenticationListComboList = new ArrayList<AuthenticationListComboVO>();
		Iterator itr = result.iterator();
		while (itr.hasNext()) {
			authenticationListCombo = new AuthenticationListComboVO();
			Object[] obj = (Object[]) itr.next();

			authenticationListCombo.setId(Integer.parseInt(String.valueOf(obj[0])));
			authenticationListCombo.setName(String.valueOf(obj[1]));
			if (null != screenList && screenList.size() > 0) {
				if (null != authenticationListCombo.getId() && screenList.contains(authenticationListCombo.getId())) {
					authenticationListCombo.setResult(true);
				} else {
					authenticationListCombo.setResult(false);
				}
			}
			if (null == authenticationListCombo.getResult()) {
				authenticationListCombo.setResult(false);
			}
			authenticationListComboList.add(authenticationListCombo);
		}
		screenAuthenticationMaster.setScreenComboList(authenticationListComboList);
		return screenAuthenticationMaster;
	}

	@SuppressWarnings("rawtypes")
	public ScreenAuthenticationVO loadUserAndRoleName(ScreenAuthenticationVO screenAuthenticationMaster,AuthDetailsVo authDetailsVo) {
		// Load Users Based on Role
		if (null != screenAuthenticationMaster.getRoleId()) {

			Integer entityId = authDetailsVo.getEntityId();

			List<Object> result = userRepository.getRoleBasedUserList(screenAuthenticationMaster.getRoleId(), entityId);
			UserMasterListDisplayVO userMasterListDisplayVo = null;
			Iterator itr = result.iterator();
			String userList = null;
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();

				userMasterListDisplayVo = new UserMasterListDisplayVO();

				if (null != obj[0]) {
					userMasterListDisplayVo.setId(Integer.parseInt(String.valueOf(obj[0])));
				}
				if (null != obj[1]) {
					userMasterListDisplayVo.setUserLoginId(String.valueOf(obj[1]));
				}
				if (null != obj[2]) {
					userMasterListDisplayVo.setFirstName(String.valueOf(obj[2]));
				}
				if (null != obj[3]) {
					userMasterListDisplayVo.setLastName(String.valueOf(obj[3]));
				}
				if (null != userMasterListDisplayVo.getFirstName()) {
					if (null != userList) {
						userList = userList + " , " + userMasterListDisplayVo.getFirstName();
					} else {
						userList = userMasterListDisplayVo.getFirstName();
					}
				}

			}
			screenAuthenticationMaster.setUserList(userList);
			UserRole userRoleEntity = userRoleRepository.findOne(screenAuthenticationMaster.getRoleId());
			if (null != userRoleEntity && null != userRoleEntity.getUserRoleName()) {
				screenAuthenticationMaster.setRoleName(userRoleEntity.getUserRoleName());
			}
		}
		return screenAuthenticationMaster;
	}

	public ScreenAuthenticationVO getScreenFields(ScreenJsonVO screenJson,AuthDetailsVo authDetailsVo) {
		ScreenAuthenticationVO screenAuthenticationMaster = new ScreenAuthenticationVO();

		ScreenAuthorizationVO screenAuthorizationMasterVo = getScreenAuthorization(screenJson,authDetailsVo);

		screenAuthenticationMaster = getScreenAuhentication(authDetailsVo);

		if (null != screenAuthorizationMasterVo) {

			// Get the Fields List
			screenAuthenticationMaster
					.setScreenFieldDisplayVoList(screenAuthorizationMasterVo.getScreenFieldDisplayVoList());

			// Get the Functions & Side Tab List
			screenAuthenticationMaster
					.setScreenFunctionDisplayList(screenAuthorizationMasterVo.getScreenFunctionDisplayList());

		} else {
			throw new CommonException(getMessage("noAuthorizationAvailableForThisUser",authDetailsVo));

		}

		screenAuthenticationMaster.setUserName(authDetailsVo.getUserName());

		return screenAuthenticationMaster;
	}

}