package com.srm.coreframework.dao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.entity.CodeGenerationEntity;
import com.srm.coreframework.entity.EntityLicense;
import com.srm.coreframework.entity.EntityLicenseDetails;
import com.srm.coreframework.entity.SubLocation;
import com.srm.coreframework.entity.UserDepartment;
import com.srm.coreframework.entity.UserEntity;
import com.srm.coreframework.entity.UserLocation;
import com.srm.coreframework.entity.UserRole;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.EntityLicenseVO;
import com.srm.coreframework.vo.MailParameterVO;
import com.srm.coreframework.vo.SystemConfigurationVo;
import com.srm.coreframework.vo.UserMasterVO;

@Repository
public class CommonDAO {
	
	@Value("${commonDatabaseSchema}")
	private String commonDatabaseSchema;
	
	@Value("${rtaDatabaseSchema}")
	private String rtaDatabaseSchema;
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	private MessageSource messageSource;
	

	/**
	 * @return the commonDatabaseSchema
	 */
	public String getCommonDatabaseSchema() {
		return commonDatabaseSchema;
	}


	/**
	 * @param commonDatabaseSchema the commonDatabaseSchema to set
	 */
	public void setCommonDatabaseSchema(String commonDatabaseSchema) {
		this.commonDatabaseSchema = commonDatabaseSchema;
	}


	/**
	 * @return the rtaDatabaseSchema
	 */
	public String getRtaDatabaseSchema() {
		return rtaDatabaseSchema;
	}


	/**
	 * @param rtaDatabaseSchema the rtaDatabaseSchema to set
	 */
	public void setRtaDatabaseSchema(String rtaDatabaseSchema) {
		this.rtaDatabaseSchema = rtaDatabaseSchema;
	}


	/**
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}


	/**
	 * @param entityManager the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	
	public Integer getScreenAuthenticationId(Integer roleId,Integer screenId,Integer subScreenId,AuthDetailsVo authDetailsVo) {
		Integer screenAuthId = null;
		/*String query = "SELECT s.screenAuthenticationId FROM ScreenAuthentication s where s.deleteFlag = '0' "
				+ " and s.userRoleEntity.id = "+roleId
				+ " and s.screenId = "+ screenId
				+ " and s.subScreenId = "+subScreenId
				+ " and s.entityLicenseEntity.id="+AuthUtil.getEntityId()+"" ;*/
		
		String query = " SELECT SCREEN_AUTHENTICATION_ID FROM "+commonDatabaseSchema+".screen_authentication "
				+ " WHERE delete_flag = '"+ CommonConstant.FLAG_ZERO +"' and  USER_ROLE_ID = "+ roleId
				+ " and SCREEN_ID = " + screenId
				+ " and SUB_SCREEN_ID = "+ subScreenId 
				+ " and rin_ma_entity_id = " +authDetailsVo.getEntityId();
		
		Query qry = null;
		try {
			qry = getEntityManager().createNativeQuery(query);

		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();

		}
		screenAuthId= (Integer) qry.getSingleResult();
		return screenAuthId;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]>getScreenField(Integer screenAuthId){
		List<Object[]> fieldList =null;
		/*String fieldQuery = "SELECT s.fieldId, s.fieldName FROM FieldAuthentication f ,ScreenField s "
				+ " where f.screenFieldId = s.fieldId" + " and f.deleteFlag = '0' and f.screenAuthenticationId = "
				+ screenAuthId + "  ORDER BY s.sequence ASC ";
		Query fieldQry = getEntityManager().createQuery(fieldQuery);*/
		
		String fieldQuery = " SELECT  s.FIELD_ID, s.FIELD_NAME FROM "+commonDatabaseSchema+".field_validiation f "
							+ " LEFT JOIN  "+commonDatabaseSchema+".screen_field s ON f.FIELD_ID = s.FIELD_ID "
							+ " WHERE f.delete_flag = '" + CommonConstant.FLAG_ZERO + "' and f.SCREEN_AUTHENTICATION_ID = "
							+ screenAuthId + " ORDER BY s.SEQUENCE ";
		Query fieldQry = getEntityManager().createNativeQuery(fieldQuery);
		fieldList = (List<Object[]>) fieldQry.getResultList();
		 
		return fieldList;
		
	}
	
	

	@SuppressWarnings("unchecked")
	public List<Object[]>getScreenFunction(Integer screenAuthId){
		List<Object[]> functionList =null;
		/*String functionQuery = "SELECT sf.screenFunctionId, sf.functionName FROM FunctionAuthentication fn "
				+ " ,ScreenFunction sf where fn.screenFunctionId = sf.screenFunctionId "
				+ "  and fn.deleteFlag = '0'" + " and fn.screenAuthenticationId = " + screenAuthId;
		Query fieldQry = getEntityManager().createQuery(functionQuery);
		functionList = (List<Object[]>) fieldQry.getResultList();*/
		
		String functionQuery = " SELECT  sf.SCREEN_FUNCTION_ID, sf.FUNCTION_NAME FROM "+commonDatabaseSchema+".function_authentication fn "
							+ " LEFT JOIN "+commonDatabaseSchema+".screen_function sf ON fn.SCREEN_FUNCTION_ID = sf.SCREEN_FUNCTION_ID "
							+ "  WHERE fn.delete_flag = '0'" + " and fn.SCREEN_AUTHENTICATION_ID = " + screenAuthId;
		
		Query fieldQry = getEntityManager().createNativeQuery(functionQuery);
		functionList = (List<Object[]>) fieldQry.getResultList();
		return functionList;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getScreenAuhentication(AuthDetailsVo authDetailsVo){
		List<Object[]> screenAuthenticationEntities=null;
		/*String query = "SELECT a.SCREEN_ID,SCREEN_NAME,SCREEN_TYPE_FLAG,SCREEN_URL,SCREEN_ICON FROM "+commonDatabaseSchema+".screen_authentication a "
				+ " JOIN " + commonDatabaseSchema +".screen s ON a.SCREEN_ID=s.SCREEN_ID WHERE a.delete_flag=0 AND " + " a.USER_ROLE_ID = "
				+ authDetailsVo.getRoleId() + " AND a.rin_ma_entity_id = "
				+ authDetailsVo.getEntityId() + " " + " ORDER BY  a.SCREEN_ID DESC";*/
		
		String query =null;
		if("en".equalsIgnoreCase(authDetailsVo.getLangCode())){
			
			 query = "SELECT a.SCREEN_ID,SCREEN_NAME,SCREEN_TYPE_FLAG,SCREEN_URL,SCREEN_ICON FROM "+commonDatabaseSchema+".screen_authentication a "
					+ " JOIN " + commonDatabaseSchema +".screen s ON a.SCREEN_ID=s.SCREEN_ID WHERE a.delete_flag=0 AND " + " a.USER_ROLE_ID = "
					+ authDetailsVo.getRoleId() + " AND a.rin_ma_entity_id = "
					+ authDetailsVo.getEntityId() + " and s.rin_ma_entity_id = " + authDetailsVo.getEntityId() + " ORDER BY  a.SCREEN_ID DESC";
			 
		}else{
			 query = "SELECT a.SCREEN_ID,SCREEN_NAME_JP,SCREEN_TYPE_FLAG,SCREEN_URL,SCREEN_ICON FROM "+commonDatabaseSchema+".screen_authentication a "
						+ " JOIN " + commonDatabaseSchema +".screen s ON a.SCREEN_ID=s.SCREEN_ID WHERE a.delete_flag=0 AND " + " a.USER_ROLE_ID = "
						+ authDetailsVo.getRoleId() + " AND a.rin_ma_entity_id = "
						+ authDetailsVo.getEntityId() + " and s.rin_ma_entity_id = " + authDetailsVo.getEntityId()  + " ORDER BY  a.SCREEN_ID DESC";
		}
		
		Query screenQry = getEntityManager().createNativeQuery(query);
		 screenAuthenticationEntities = (List<Object[]>) screenQry.getResultList();
		return screenAuthenticationEntities;

	}
	
	/**
	 * Method to get auto code generation prefix, counter amd starting number
	 * 
	 * @param comboName
	 * @return codeGenerationEntity
	 */
	public CodeGenerationEntity generateCode(String comboName) {

		String query = "SELECT * FROM "+commonDatabaseSchema+".`code_generation` p LEFT JOIN "+ rtaDatabaseSchema
				+ ".`combo_details` c ON c.COMBO_ID = p.CODE_NAME" + " WHERE p.delete_flag = '"
				+ CommonConstant.FLAG_ZERO + "' and c.COMBO_VALUE = '" + comboName + "'";
		Query qry = getEntityManager().createNativeQuery(query, CodeGenerationEntity.class);

		CodeGenerationEntity list = (CodeGenerationEntity) qry.getSingleResult();

		CodeGenerationEntity codeGenerationEntity = null;

		if (null != list) {
			codeGenerationEntity = new CodeGenerationEntity();
			codeGenerationEntity = list;
		}

		return codeGenerationEntity;
	}
	
	
	/**
	 * update starting number after auto code generated
	 * 
	 * @param codeGenerationId
	 * @param startingNumber
	 */
	public void updateCodeStartNumber(int codeGenerationId, int startingNumber) {

		String query1 = " update CodeGenerationEntity set startingNumber =" + startingNumber + " where deleteFlag =   "
				+ CommonConstant.CONSTANT_ZERO + " and codeGenerationId = " + codeGenerationId;
		getEntityManager().createQuery(query1).executeUpdate();

	}

	/**
	 * This method is used to autogenerate the widgetCode by executing the
	 * query.
	 * 
	 * 
	 * @return String widgetCode
	 */
	@SuppressWarnings("unchecked")
	public String findAutoGenericCode(String comboName,AuthDetailsVo authDetailsVo) throws CommonException{

		String requestCode;

		try {
		 
		String query = "SELECT p.* FROM "+getCommonDatabaseSchema()+".`code_generation` p LEFT JOIN " + getCommonDatabaseSchema()
				+ ".`combo_details` c ON c.COMBO_ID = p.CODE_NAME" + " WHERE p.delete_flag = '"
				+ CommonConstant.FLAG_ZERO + "' and c.COMBO_VALUE = '" + comboName + "'"
				+ " and p.rin_ma_entity_id = " + authDetailsVo.getEntityId();
		
		Query qry = getEntityManager().createNativeQuery(query, CodeGenerationEntity.class);

		CodeGenerationEntity codeGenerationEntity = (CodeGenerationEntity) qry.getSingleResult();

		if(null == codeGenerationEntity){
			throw new CommonException("code does not exists");
		}
		int startingNumber = codeGenerationEntity.getStartingNumber() + CommonConstant.CONSTANT_ONE;

			if (null != authDetailsVo.getLangCode() && authDetailsVo.getLangCode().equals(CommonConstant.en)) {
				requestCode = codeGenerationEntity.getPrefix()
						+ String.format("%0" + codeGenerationEntity.getCounter() + "d", startingNumber);
			} else if (null != authDetailsVo.getLangCode() && authDetailsVo.getLangCode().equals(CommonConstant.jp)) {
				requestCode = codeGenerationEntity.getPrefixJp()
						+ String.format("%0" + codeGenerationEntity.getCounter() + "d", startingNumber);
			} else {
				requestCode = codeGenerationEntity.getPrefix()
						+ String.format("%0" + codeGenerationEntity.getCounter() + "d", startingNumber);
			}
		
		
	/*	String query1 = " update CodeGenerationEntity set startingNumber =" + startingNumber + " where deleteFlag =   "
				+ CommonConstant.CONSTANT_ZERO + " and codeGenerationId = "
				+ codeGenerationEntity.getCodeGenerationId();
		getEntityManager().createQuery(query1).executeUpdate();*/
		
		String query1 = " update " +getCommonDatabaseSchema()+".`code_generation` set STARTING_NUMBER =" + startingNumber + " where delete_flag =   "
				+ CommonConstant.CONSTANT_ZERO + " and CODE_GENERATION_ID = "
				+ codeGenerationEntity.getCodeGenerationId()+" and rin_ma_entity_id = " +authDetailsVo.getEntityId();
		getEntityManager().createNativeQuery(query1).executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();		 
			throw new CommonException("autoCodeGenerationFailure");
		}
		
		return requestCode;

	}

	
	public EntityLicenseDetails getUserCount(AuthDetailsVo authDetailsVo) {

		String query = " Select e from EntityLicenseDetails e " + " where e.licenseType = "
				+ CommonConstant.CONSTANT_ONE + " and e.deleteFlag = " + CommonConstant.CONSTANT_ZERO
				+ " and e.entityId = " + authDetailsVo.getEntityId();

		EntityLicenseDetails entityLicenseDetailsEntity = (EntityLicenseDetails) getEntityManager()
				.createQuery(query).getSingleResult();

		return entityLicenseDetailsEntity;
	}

	/*@SuppressWarnings("unchecked")
	public EntityLicenseDetails getTransactionCount(int entityId) {

		String query = " Select e from EntityLicenseDetails e " + " where e.licenseType = "
				+ CommonConstant.CONSTANT_TWO + " and e.deleteFlag = " + CommonConstant.CONSTANT_ZERO
				+ " and e.entityId = " + entityId;
				EntityLicenseDetails entityLicenseDetailsEntity = (EntityLicenseDetails) getEntityManager()
				.createQuery(query).getSingleResult();

		return entityLicenseDetailsEntity;
		
		
		String query = " SELECT * FROM "+commonDatabaseSchema+".rin_ma_entity_license_details"
				+ " WHERE rin_ma_entity_license_type = " + CommonConstant.CONSTANT_TWO + " and delete_flag = "
				+ CommonConstant.CONSTANT_ZERO + " and rin_ma_entity_id = "+ entityId ;

		Query qry = getEntityManager().createNativeQuery(query, EntityLicenseDetails.class);
		EntityLicenseDetails list = (EntityLicenseDetails) qry.getSingleResult();

		EntityLicenseDetails entityLicenseDetails = null;
		if(null!=list){
				entityLicenseDetails = new EntityLicenseDetails();
				entityLicenseDetails = list;
		}
		return entityLicenseDetails;
		
		
	}*/

	/*@SuppressWarnings("unchecked")
	public List<Object> getTotalUserCount(int entityId) {

		String query = "SELECT u.USER_ID FROM "+commonDatabaseSchema+".user u "
				+ " JOIN  "+commonDatabaseSchema+".rin_ma_entity_license el ON el.idrin_ma_entity_id = u.rin_ma_entity_id "
				+ " JOIN  "+commonDatabaseSchema+".rin_ma_entity_license_details eld ON eld.rin_ma_entity_id = el.idrin_ma_entity_id "
				+ " WHERE eld.rin_ma_entity_license_type = " + CommonConstant.CONSTANT_ONE + " AND u.ACTIVE = '"
				+ CommonConstant.CONSTANT_ONE + "'" + " AND u.delete_flag = '" + CommonConstant.CONSTANT_ZERO + "'"
				+ " AND u.rin_ma_entity_id = " + entityId;

		List<Object> userEntity = (List<Object>) getEntityManager().createNativeQuery(query).getResultList();

		return userEntity;
	}*/

	@SuppressWarnings("unchecked")
	public List<Object> getTotalRequestCount(int entityId) {

		String query = " SELECT e.idrin_tr_request_id FROM "+rtaDatabaseSchema+".rin_tr_request e "
				+ " JOIN  "+commonDatabaseSchema+".rin_ma_entity_license el "
				+ " ON el.idrin_ma_entity_id = e.rin_ma_entity_id"
				+ " JOIN  "+commonDatabaseSchema+".rin_ma_entity_license_details eld "
				+ " ON eld.rin_ma_entity_id = el.idrin_ma_entity_id"
				+ " WHERE e.rin_ma_entity_id = " + entityId 
				+ " AND eld.rin_ma_entity_license_type = "+ CommonConstant.CONSTANT_TWO 
				+ " AND e.rin_tr_request_is_cancel = '" + CommonConstant.CONSTANT_ZERO
				+ "'" + " AND e.delete_flag = '" + CommonConstant.CONSTANT_ZERO + "'";

		List<Object> request = (List<Object>) getEntityManager().createNativeQuery(query).getResultList();

		return request;
	}

	/*public void updateEntityUser(String count) {

		String query1 = " update "+commonDatabaseSchema+".rin_ma_entity_license_details e set e.rin_ma_entity_used_license = '" + count
				+ "' where e.delete_flag =   " + CommonConstant.CONSTANT_ZERO + " and e.rin_ma_entity_license_type = "
				+ CommonConstant.CONSTANT_ONE;

		getEntityManager().createNativeQuery(query1).executeUpdate();

	}

	public void getUpdateEntityRequest(String count) {

		String query1 = " update "+commonDatabaseSchema+".rin_ma_entity_license_details e set e.rin_ma_entity_used_license = '" + count
				+ "' where e.delete_flag =   " + CommonConstant.CONSTANT_ZERO + " and e.rin_ma_entity_license_type = "
				+ CommonConstant.CONSTANT_TWO;

		getEntityManager().createNativeQuery(query1).executeUpdate();

	}*/
	
	
/*	public void updateMailLog(MailParameterVO mailParameterVo,AuthDetailsVo authDetailsVo) {

		String query = "Select m.mailLogId from MailParameterEntity m Where m.requestId = "
				+ mailParameterVo.getRequestId() + " and m.userId = " + mailParameterVo.getUserId()
				+ " and m.message = '" + mailParameterVo.getMessage() + "'" + " ORDER BY mail_log_id DESC"
				;

		int id = 0;
		try {
			id = (int) getEntityManager().createQuery(query).setMaxResults(1).getSingleResult();
		} catch (Exception e) {
			e.getMessage();

		}

		if (id != 0) {
			String query1 = " Update MaillogEntity  " + " set notificationFlag = " + CommonConstant.CONSTANT_ONE;
			if (null !=  authDetailsVo.getUserId()) {
				query1 = query1 + " ,createUserId = " + authDetailsVo.getUserId();
			} else {
				if (0 != mailParameterVo.getCreateBy()) {
					query1 = query1 + " ,createUserId = " + mailParameterVo.getCreateBy();
				}
			}
			query1 = query1 + " ,lastUpdateDatetime = ' " + CommonConstant.getCurrentDateTimeAsString() + " ' "
					+ " where maillogNumber =" + id;

			getEntityManager().createQuery(query1).executeUpdate();

		}
	}*/

/*	public void updateMailLogResubmit(int id) {

		String query1 = " Update MailParameterEntity  " + " set deleteFlag = '" + CommonConstant.CONSTANT_ONE
				+ " ' where requestId =" + id;

		getEntityManager().createQuery(query1).executeUpdate();

	}*/

/*	@SuppressWarnings("unchecked")
	public void updateMailLogCancel(MailParameterVO mailParameterVo,AuthDetailsVo authDetailsVo) {

		String query = "Select m.mailLogId from MailParameterEntity m Where m.requestId = "
				+ mailParameterVo.getRequestId();

		List<Integer> id = (List<Integer>)getEntityManager().createQuery(query).getResultList();

		for (int i : id) {
			if (i != 0) {
				String query1 = " Update MaillogEntity  " + " set notificationFlag = " + CommonConstant.CONSTANT_ONE
						+ " ,createUserId = " + authDetailsVo.getUserId() + " ,lastUpdateDatetime = ' "
						+ CommonConstant.getCurrentDateTimeAsString() + " ' " + " where maillogNumber =" + i;

				getEntityManager().createQuery(query1).executeUpdate();

			}
		}
	}*/

	public String getMediaType1(String filename) {
		String arr[] = filename.split("\\.");
		String type = arr[arr.length - CommonConstant.CONSTANT_ONE];
		return type;
	}
		
	public UserEntity getUser(String userName) {

		String query = "SELECT e FROM UserEntity e where e.activeFlag = " + CommonConstant.FLAG_ONE
				+ " and  e.userName = '" + userName + "' and e.deleteFlag = " + CommonConstant.FLAG_ZERO;

		UserEntity userList = (UserEntity) getEntityManager().createQuery(query).getSingleResult();

		return userList;
	}
	
	public UserEntity getAcessTokenUser(String accessToken) {

		UserEntity userEntity = null;
		
		try{
		String query = "SELECT e.USER_ID,e.FIRST_NAME,e.LAST_NAME ,e.USER_ROLE_ID,e.rin_ma_entity_id,e.LANG_CODE,e.access_token "
				+ " ,e.USER_DEPARTMENT_ID,e.USER_LOCATION_ID , e.USER_SUBLOCATION_ID "
				+ " FROM "+getCommonDatabaseSchema() +".`user` e where e.ACTIVE = " + CommonConstant.FLAG_ONE
				+ " and  e.access_token = '" + accessToken + "' and e.delete_flag = " + CommonConstant.FLAG_ZERO;

		Object[] obj = (Object[]) getEntityManager().createNativeQuery(query).getSingleResult();
	
		if(null != obj ){
			userEntity=new UserEntity();
			if(null!=(Integer)((obj)[0])){
				userEntity.setId((Integer)((obj)[0]));
			}
			if(null!=(String)((obj)[1])){
				userEntity.setFirstName((String)((obj)[1]));
			}
			if(null!=(String)((obj)[2])){
				userEntity.setLastName((String)((obj)[2]));
			}
			if(null!=(Integer)((obj)[3])){
				UserRole userRole=new UserRole();
				userRole.setId((Integer)((obj)[3]));
				userEntity.setUserRoleEntity(userRole);
			}
			if (null != (Integer) ((obj)[4])) {
				EntityLicense entityLicense= new EntityLicense();
				entityLicense.setId((Integer) ((obj)[4]));
				userEntity.setEntityLicenseEntity(entityLicense);
			}
			if (null != (String) ((obj)[5])) {
				userEntity.setLangCode((String) ((obj)[5]));
			}
			if(null != (Integer) ((obj)[7])){
				UserDepartment userDepartmentEntity = new UserDepartment();
				userDepartmentEntity.setId((Integer) ((obj)[7]));
				userEntity.setUserDepartmentEntity(userDepartmentEntity);
			}
			if(null != (Integer) ((obj)[8])){
				UserLocation userLocation = new UserLocation();
				userLocation.setId((Integer) ((obj)[8]));
				userEntity.setUserLocationEntity(userLocation);
			}
			if(null != (Integer) ((obj)[9])){
				SubLocation subLocation = new SubLocation(); 
				subLocation.setId((Integer) ((obj)[9]));
				userEntity.setSubLocationEntity(subLocation);
			}
		}
		}catch(EmptyResultDataAccessException e){
			e.printStackTrace();
		}
		
		return userEntity;
	}
	
	/**Method to get accessToken from UI headers
	 * @param request
	 * @return String
	 */
	public String getHeaderAccessToken(HttpServletRequest request) {

		String accessToken = request.getHeader("Authorization");

		String access = accessToken.substring(0, 1).toUpperCase() + accessToken.substring(1);
	
		access = access.replaceFirst("Bearer ", "");
		
		return access;
	}
	
	
	public AuthDetailsVo tokenValidate(String accessToken) {
		
		UserEntity userEntity= getAcessTokenUser(accessToken);
		AuthDetailsVo authUserInfo=null;
		
		if(null!=userEntity){
			authUserInfo= new AuthDetailsVo();
			
			if(null!=userEntity.getId()){
				authUserInfo.setUserId(userEntity.getId());
			}
			if(null!=userEntity.getFirstName()){
				authUserInfo.setFirstName(userEntity.getFirstName());
			}
			if(null!=userEntity.getLastName()){
				authUserInfo.setLastName(userEntity.getLastName());
			}
			if(null!=userEntity.getEntityLicenseEntity().getId()){
				authUserInfo.setEntityId(userEntity.getEntityLicenseEntity().getId());
			}
			if(null!=userEntity.getUserRoleEntity().getId()){
				authUserInfo.setRoleId(userEntity.getUserRoleEntity().getId());
			}
			if(null!=userEntity.getLangCode()){
				authUserInfo.setLangCode(userEntity.getLangCode());
			}
			if(null!=userEntity.getFirstName()){
				authUserInfo.setUserName(userEntity.getFirstName());
			}
			if(null!=userEntity.getUserDepartmentEntity().getId()){
				authUserInfo.setDepartmentId(userEntity.getUserDepartmentEntity().getId());
			}
			if(null!=userEntity.getUserLocationEntity().getId()){
				authUserInfo.setLocationId(userEntity.getUserLocationEntity().getId());
			}
			if(null!=userEntity.getSubLocationEntity().getId()){
				authUserInfo.setSubLocationId(userEntity.getSubLocationEntity().getId());

			}
		}
		return authUserInfo;
		
	}
	
	
	public byte[] imageLoading(String fileName) throws IOException {
		BufferedImage originalImage;
		byte[] imageInByte;
		originalImage = ImageIO.read(new File(fileName));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(originalImage, getMediaType1(fileName) , baos);
		baos.flush();
		imageInByte = baos.toByteArray();
		return imageInByte;
	}
	
	public byte[] defaultImage(String fileName) throws IOException {
		BufferedImage originalImage;
		byte[] imageInByte;
		originalImage = ImageIO.read(new File(fileName));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(originalImage, getMediaType1(fileName) , baos);
		baos.flush();
		imageInByte = baos.toByteArray();
		return imageInByte;
	}
	
	@SuppressWarnings("unchecked")
	public List<EntityLicenseDetails> getDetail(int entityId) {

		String query = " SELECT c.* FROM " + commonDatabaseSchema + ".rin_ma_entity_license_details c "
				+ " LEFT JOIN "+getCommonDatabaseSchema()+".rin_ma_entity_license l ON l.idrin_ma_entity_id = c. rin_ma_entity_id "
				+ " WHERE c.delete_flag = " + CommonConstant.CONSTANT_ZERO + " and c.rin_ma_entity_id = " + entityId;
				//+ " and l.entity_status ='1' and ";

		Query qry = getEntityManager().createNativeQuery(query, EntityLicenseDetails.class);
		List<EntityLicenseDetails> list = (List<EntityLicenseDetails>) qry.getResultList();

		return list;

	}

	@SuppressWarnings("unchecked")
	public List<EntityLicenseDetails> getDetaillicenseDateValidation(AuthDetailsVo authDetailsVo) {

		String query = null;
		if(authDetailsVo.getUserId().equals(1)){
			 query = " SELECT c.* FROM " + commonDatabaseSchema + ".rin_ma_entity_license_details c "
					+ " LEFT JOIN "+getCommonDatabaseSchema()+".rin_ma_entity_license l ON l.idrin_ma_entity_id = c. rin_ma_entity_id "
					+ " WHERE c.delete_flag = " + CommonConstant.CONSTANT_ZERO + " and c.rin_ma_entity_id = " + authDetailsVo.getEntityId();
					
		}else{
			query = " SELECT c.* FROM " + commonDatabaseSchema + ".rin_ma_entity_license_details c "
					+ " LEFT JOIN "+getCommonDatabaseSchema()+".rin_ma_entity_license l ON l.idrin_ma_entity_id = c. rin_ma_entity_id "
					+ " WHERE c.delete_flag = " + CommonConstant.CONSTANT_ZERO + " and c.rin_ma_entity_id = " + authDetailsVo.getEntityId()
					+ " and l.entity_status ='1'  ";
		}

		Query qry = getEntityManager().createNativeQuery(query, EntityLicenseDetails.class);
		List<EntityLicenseDetails> list = (List<EntityLicenseDetails>) qry.getResultList();

		return list;

	}
	public EntityLicenseDetails deleteEntityRequest(int entityId) {

		String query = "SELECT * FROM "+getCommonDatabaseSchema()+".rin_ma_entity_license_details" + " WHERE rin_ma_entity_id =" + entityId
				+ " AND rin_ma_entity_used_transaction_license IS NOT NULL" + " AND delete_flag = 0";

		Query qry = getEntityManager().createNativeQuery(query, EntityLicenseDetails.class);
		EntityLicenseDetails list = (EntityLicenseDetails) qry.getSingleResult();

		return list;

	}
	
	
	public EntityLicenseDetails addEntityUser(int entityId) {

		String query = "SELECT * FROM " +getCommonDatabaseSchema()+".rin_ma_entity_license_details" + " WHERE rin_ma_entity_id =" + entityId
				+ " AND rin_ma_entity_used_user_license IS NOT NULL" + " AND delete_flag = 0";

		Query qry = getEntityManager().createNativeQuery(query, EntityLicenseDetails.class);
		EntityLicenseDetails list = (EntityLicenseDetails) qry.getSingleResult();

		return list;

	}
	
	public void getTransactionCountUpdate(String count,EntityLicenseDetails entityLicenseDetails) {

		String query1 = " update " + commonDatabaseSchema
				+ ".rin_ma_entity_license_details e set e.rin_ma_entity_used_transaction_license = '" + count
				+ "' where e.delete_flag =   " + CommonConstant.CONSTANT_ZERO 
				+ " and e.idrin_ma_entity_details_id = "
				+ entityLicenseDetails.getId();

		getEntityManager().createNativeQuery(query1).executeUpdate();

	}
	
	
	public void getUserCountUpdate(String count,EntityLicenseDetails entityLicenseDetails) {

		String query1 = " update " + commonDatabaseSchema
				+ ".rin_ma_entity_license_details e set e.rin_ma_entity_used_user_license = '" + count
				+ "' where e.delete_flag =   " + CommonConstant.CONSTANT_ZERO 
				+ " and e.idrin_ma_entity_details_id = "
				+ entityLicenseDetails.getId();

		getEntityManager().createNativeQuery(query1).executeUpdate();

	}
	
	public Date getHolidaySla(AuthDetailsVo authDetailsVo,Date requestDate,Integer slaMinutes){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date holidayDate = null;
		Date targetTime = requestDate; //now
		
		targetTime = DateUtils.addMinutes(targetTime, slaMinutes);
		
		Object[] object = null;
		
		String holiday = " select h.HOLIDAY_ID,h.HOLIDAY_DATE  FROM "+getRtaDatabaseSchema()+".`holiday` h "
				+ "	LEFT JOIN "+getRtaDatabaseSchema()+".`holiday_detail` hd ON hd.HOLIDAY_ID = h.HOLIDAY_ID " 
				+ " WHERE h.delete_flag = "+ CommonConstant.FLAG_ZERO+" and h.rin_ma_entity_id ="+authDetailsVo.getEntityId()
				+ " and hd.ACTIVE_FLAG = "+ CommonConstant.FLAG_ONE+ " and hd.delete_flag = "+CommonConstant.FLAG_ZERO
				+ " and hd.rin_ma_entity_id = "+ authDetailsVo.getEntityId();
		
		if(null != targetTime){
			holiday = holiday	+ " and h.HOLIDAY_DATE ='"+(formatter.format(targetTime))+"'";
		}
		
		String withLocationQuery = holiday + " and hd.LOCATION_ID = " + authDetailsVo.getLocationId()
				+ " and hd.SUB_LOCATION_ID = "+authDetailsVo.getSubLocationId()+ " GROUP BY h.HOLIDAY_ID ";
		
		try{
		object = (Object[])getEntityManager().createNativeQuery(withLocationQuery).getSingleResult();
		}catch(Exception e){
			
		}
		if (null == object) {
			String withoutLocationQuery = holiday + " and hd.LOCATION_ID = " + CommonConstant.CONSTANT_ZERO
					+ " and hd.SUB_LOCATION_ID = " + CommonConstant.CONSTANT_ZERO + " GROUP BY h.HOLIDAY_ID ";
			try{	object = (Object[])getEntityManager().createNativeQuery(withoutLocationQuery).getSingleResult();
			
			}catch(Exception e){
				
			}
		}
		//for (Object[] object : objectList) {
			if (null != object) {
				if (null != object[1]) {
					holidayDate = (Date) object[1];
					holidayDate = DateUtils.addMinutes(holidayDate, slaMinutes);
				}

			}
		//}
		return holidayDate;
	}
	
	public void fileSizeValidation(MultipartFile filePath) throws CommonException {

		if (filePath.getSize() > CommonConstant.TWO_MB) {
			throw new CommonException("fileSizeLimitMessage");
		}
	}
	
	public EntityLicenseVO getEntityDetails(Integer entityId){
		
		EntityLicenseVO entityLicenseVO = new EntityLicenseVO();
		String query = " SELECT  idrin_ma_entity_id, entity_password_length, entity_password_special_char, "//2
				+ " entity_password_numeric , entity_password_alphanumeric_caps, entity_password_check_count "//5
				+ " , entity_password_expiry_days , email_flag" //6
				+ " FROM " + commonDatabaseSchema + ".rin_ma_entity_license "
				+ " WHERE delete_flag = " + CommonConstant.CONSTANT_ZERO + " and idrin_ma_entity_id = " + entityId;
		Object[] obj = (Object[])getEntityManager().createNativeQuery(query).getSingleResult();
		
		if(null != obj){
			
			if(null != obj[0]){
				entityLicenseVO.setId((Integer)obj[0]);
			}
			if(null != obj[1]){
				entityLicenseVO.setPasswordLength((Integer)obj[1]);
			}
			if(null != obj[2]){
				entityLicenseVO.setPasswordSpecialChar((String)obj[2]);
			}
			if(null != obj[3]){
				entityLicenseVO.setPasswordNumeric((String)obj[3]);
			}
			if(null != obj[4]){
				entityLicenseVO.setPasswordAlphanumericCaps((String)obj[4]);
			}
			if(null != obj[5]){
				entityLicenseVO.setPasswordCheckCount((Integer)obj[5]);
			}
			if(null != obj[6]){
				entityLicenseVO.setExpiryDays((Integer)obj[6]);
			}
			if(null != obj[7]){
				entityLicenseVO.setEmailFlag((Integer)obj[7]);
			}
		}
		return entityLicenseVO;
	}
	
	public UserMasterVO getUserDetails(AuthDetailsVo authDetailsVo){
		UserMasterVO userMasterVO = new UserMasterVO();
		
		String query = " SELECT USER_ID,CHANGE_PASSWORD_DATE,FIRST_LOGIN "
				+ " FROM " + getCommonDatabaseSchema()+".user WHERE USER_ID = "+authDetailsVo.getUserId()
						+ " and rin_ma_entity_id = "+authDetailsVo.getEntityId()+ " and delete_flag ="+CommonConstant.FLAG_ZERO;
		Object[] obj = (Object[])getEntityManager().createNativeQuery(query).getSingleResult();
		if(null != obj){
			if(null != obj[0]){
				userMasterVO.setId((Integer)obj[0]);
			}
			if(null != obj[1]){
				userMasterVO.setChangePasswordDate((Date)obj[1]);
			}
			if(null != obj[2]){
				userMasterVO.setFirstLogin((Character)obj[2]);
			}
		}
		return userMasterVO;
		
	}
	
	
	
	@Transactional
	public String getRequestCode(Integer requestId,Integer entityId) {

		String requestCode = null;
		String query = " SELECT r.idrin_tr_request_id,r.rin_tr_request_code,cs.rin_ma_current_status_name " + " FROM " + getRtaDatabaseSchema()
				+ ".rin_tr_request r "
				+ " LEFT JOIN "+getRtaDatabaseSchema()+".rin_ma_current_status cs ON cs.idrin_ma_current_status_id = r.current_status_id"
				+ " WHERE r.idrin_tr_request_id= " + requestId
				+ " and r.rin_ma_entity_id ="+entityId;

		Object[] obj = (Object[]) getEntityManager().createNativeQuery(query).getSingleResult();

		if (null != obj) {
			if (null != (String) obj[1]) {
				requestCode = (String) obj[1];
			}
			
		}
		return requestCode;

	}
	
	@Transactional
	public String getRequestStatus(Integer requestId,Integer entityId) {

		String requestCode = null;
		String query = " SELECT r.idrin_tr_request_id,r.rin_tr_request_code,cs.rin_ma_current_status_code " + " FROM " + getRtaDatabaseSchema()
				+ ".rin_tr_request r "
				+ " LEFT JOIN "+getRtaDatabaseSchema()+".rin_ma_current_status cs ON cs.idrin_ma_current_status_id = r.current_status_id"
				+ " WHERE r.idrin_tr_request_id= " + requestId+ " and r.rin_ma_entity_id ="+entityId;;

		Object[] obj = (Object[]) getEntityManager().createNativeQuery(query).getSingleResult();

		if (null != obj) {
			if (null != (String) obj[2]) {
				if(((String) obj[2]).equals("COM")){
					requestCode = "Completed";
				}else if(((String) obj[2]).equals("ESC")){
					requestCode = "Escalated";
				}else if(((String) obj[2]).equals("APP")){
					requestCode = "Approved";
				}else if(((String) obj[2]).equals("REJ")){
					requestCode = "Rejected";
				}else if(((String) obj[2]).equals("RS")){
					requestCode = "ReSubmitted";
				}else if(((String) obj[2]).equals("IP")){
					requestCode = "In-progressed";
				}else if(((String) obj[2]).equals("RA")){
					requestCode ="Re-Assigned";
				}else if(((String) obj[2]).equals("RO")){
					requestCode = "Reopened";
				}else if(((String) obj[2]).equals("CLO")){
					requestCode = "Closed";
				}else if(((String) obj[2]).equals("CAN")){
					requestCode = "Cancelled";
				}
				
			}
			
		}
		return requestCode;

	}	
	
	public UserMasterVO getEmailAddress(Integer userId, AuthDetailsVo authDetailsVo) throws CommonException {

		UserMasterVO userMasterVO = new UserMasterVO();
		String firstName = "";
		String lastName = "";
		
		try {
			String query = " SELECT EMAIL_ID ,FIRST_NAME ,LAST_NAME , USER_ID , LANG_CODE, USER_LOCATION_ID,USER_SUBLOCATION_ID" + " FROM " + getCommonDatabaseSchema() + ".user "
					+ "WHERE ";

			if (userId != 0) {
				query = query + "USER_ID = " + userId;
			} else {
				query = query + "USER_ID = " + authDetailsVo.getUserId();
			}

			query = query	+ " and rin_ma_entity_id = " + authDetailsVo.getEntityId()
					+ " and delete_flag =" + CommonConstant.FLAG_ZERO;
			
			
			Object[] obj = (Object[]) getEntityManager().createNativeQuery(query).getSingleResult();
			if (null != obj) {
				if (null != (String) obj[0]) {

					userMasterVO.setEmailId((String) obj[0]);
				}
				
				if (null != (String) obj[1]) {
				
					firstName = (String) obj[1];
				}
				
				if (null != (String) obj[2]) {

					lastName = (String) obj[2];
				}
				
				userMasterVO.setUserName(firstName + " " + lastName);
				
				userMasterVO.setId((Integer)obj[3]);
				
				if (null != (String) obj[4]) {

					userMasterVO.setLangCode((String) obj[4]);
				}
				
				if (null != (Integer) obj[5]) {

					userMasterVO.setUserLocation((Integer)obj[5]);
				}
				
				if (null != (Integer) obj[6]) {

					userMasterVO.setSubLocation((Integer)obj[6]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return userMasterVO;
	}

	
	public List<SystemConfigurationVo> getSystemConfigurationDetails(AuthDetailsVo authDetailsVo) {

	
		List<SystemConfigurationVo> SytemConfigurationList = new ArrayList<SystemConfigurationVo>();

		String query = " SELECT entity_id ,code ,configuration , configuration_detail" + " FROM "
				+ getCommonDatabaseSchema() + ".system_configuration WHERE " 
				+ "   entity_id = " + authDetailsVo.getEntityId() + " and active ="
				+ CommonConstant.CONSTANT_ONE;
		List<Object[]> objList = new ArrayList<>();
		objList = (List<Object[]>) getEntityManager().createNativeQuery(query).getResultList();			
		
		for (Object[] object : objList) {

			SystemConfigurationVo SytemConfigurationVo = new SystemConfigurationVo();

			if (null != (Integer) ((Object[]) object)[0]) {

				SytemConfigurationVo.setEntityId((Integer) object[0]);
			}

			if (null != (String) ((Object[]) object)[1]) {
				SytemConfigurationVo.setCode((String) object[1]);
			}

			if (null != (String) ((Object[]) object)[2]) {
				SytemConfigurationVo.setConfiguration((String) object[2]);
			}

			if (null != (String) ((Object[]) object)[3]) {

				SytemConfigurationVo.setConfigurationDetail((String) object[3]);
			}
			SytemConfigurationList.add(SytemConfigurationVo);
		}
					 	 
		return SytemConfigurationList;

	}
	
	public String getMessage(String code, AuthDetailsVo authDetailsVo) {

		 return getMessageStr(code , new Object[] {} , authDetailsVo);
		
		/*String langCode = null;
		if (null != authDetailsVo && null != authDetailsVo.getLangCode()) {
			langCode = authDetailsVo.getLangCode();
		}

		Locale locale = null;
		if (CommonConstant.LANG_CODE_JP.equalsIgnoreCase(langCode)) {
			locale = Locale.JAPANESE;
		} else if (CommonConstant.LANG_CODE_EN.equalsIgnoreCase(langCode)) {
			locale = Locale.ENGLISH;
		} else {
			locale = Locale.ENGLISH;
		}
		return messageSource.getMessage(code, args, locale);*/
	}
	
	
	public String getMessageStr(String code, Object args[], AuthDetailsVo authDetailsVo) {
		 	
		String langCode = null;
		if (null != authDetailsVo && null != authDetailsVo.getLangCode()) {
			langCode = authDetailsVo.getLangCode();
		}

		Locale locale = null;
		if (CommonConstant.LANG_CODE_JP.equalsIgnoreCase(langCode)) {
			locale = Locale.JAPANESE;
		} else if (CommonConstant.LANG_CODE_EN.equalsIgnoreCase(langCode)) {
			locale = Locale.ENGLISH;
		} else {
			locale = Locale.ENGLISH;
		}
		return messageSource.getMessage(code, args, locale);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getEntityLogList(int entityId) {

		List<Object[]> list = new ArrayList<Object[]>();
		try {

			String query = "SELECT ENTITY_ID , USED_TRANSACTION_COUNT , USED_USER_COUNT FROM " + getCommonDatabaseSchema()+".entity_license_log WHERE ENTITY_ID =  "+entityId
					+ " order by ENTITY_LICENSE_LOG_ID desc ";

			list = (List<Object[]>) getEntityManager().createNativeQuery(query).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
