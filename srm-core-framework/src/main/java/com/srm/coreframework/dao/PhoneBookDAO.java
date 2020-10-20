package com.srm.coreframework.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.PhoneBookVO;

@Repository
public class PhoneBookDAO extends CommonDAO{

	/**
	 * Method is used to get all the Phone Booking Details
	 * 
	 * @return phoneBook List<Object[]>
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getAll(AuthDetailsVo authDetailsVo) {

		String query = "SELECT phone.idrin_ma_phone_book_id , phone.rin_ma_phone_book_employee_id , phone.rin_ma_phone_book_employee_name ,"
				+ " phone.rin_ma_phone_book_department_id , dep.USER_DEPARTMENT_NAME, phone.rin_ma_phone_book_location_id , "
				+ " location.USER_LOCATION_NAME ,"
				+ " phone.rin_ma_phone_book_sublocation_id , sub.rin_ma_sublocation_name,"
				+ " phone.rin_ma_phone_book_mobile_number_c , phone.rin_ma_phone_book_mobile_number_p , "
				+ " phone.rin_ma_phone_book_pnone_number , "
				+ " phone.rin_ma_phone_book_extension_number , phone.rin_ma_phone_book_email_id ,"
				+ " phone.rin_ma_phone_book_skype_id , phone.rin_ma_phone_book_is_active , "
				+ " phone.rin_ma_phone_book_profile"
				+ " FROM rin_ma_phone_book phone LEFT JOIN "+getCommonDatabaseSchema()+".user_location location ON  phone.rin_ma_phone_book_location_id = location.USER_LOCATION_ID "
				+ " LEFT JOIN " + getCommonDatabaseSchema()+".user_department dep ON phone.rin_ma_phone_book_department_id = dep.USER_DEPARTMENT_ID "
				+ " LEFT JOIN " +getCommonDatabaseSchema()+".rin_ma_sublocation sub ON phone.rin_ma_phone_book_sublocation_id = sub.idrin_ma_sublocation_sublocationId"
				+ " WHERE phone.rin_ma_entity_id = '"+ authDetailsVo.getEntityId()+"' and phone.delete_flag = " + CommonConstant.FLAG_ZERO
				+ " order by  phone.idrin_ma_phone_book_id desc ";

		List<Object[]> phoneBook = (List<Object[]>) getEntityManager().createNativeQuery(query).getResultList();

		return phoneBook;
	}
	/**
	 * Method is used to Load the Phone Booking Details
	 * 
	 * @param id
	 *            Integer
	 * @return phoneBook List<Object[]>
	 */
	public Object[] load(Integer id,AuthDetailsVo authDetailsVo) {
		String query = "SELECT phone.idrin_ma_phone_book_id , phone.rin_ma_phone_book_employee_id , phone.rin_ma_phone_book_employee_name ,"
				+ " phone.rin_ma_phone_book_department_id , dep.USER_DEPARTMENT_NAME, phone.rin_ma_phone_book_location_id , location.USER_LOCATION_NAME ,"
				+ " phone.rin_ma_phone_book_sublocation_id , sub.rin_ma_sublocation_name,"
				+ " phone.rin_ma_phone_book_mobile_number_c , phone.rin_ma_phone_book_mobile_number_p , phone.rin_ma_phone_book_pnone_number , "
				+ " phone.rin_ma_phone_book_extension_number , phone.rin_ma_phone_book_email_id , phone.rin_ma_phone_book_skype_id , phone.rin_ma_phone_book_is_active, "
				+ " phone.rin_ma_phone_book_profile "
				+ " FROM "+getRtaDatabaseSchema()+".rin_ma_phone_book phone "
				+ " LEFT JOIN "+getCommonDatabaseSchema()+".user_location location ON  phone.rin_ma_phone_book_location_id = location.USER_LOCATION_ID "
				+ " LEFT JOIN "+getCommonDatabaseSchema()+".user_department dep ON phone.rin_ma_phone_book_department_id = dep.USER_DEPARTMENT_ID LEFT JOIN "
				+ getCommonDatabaseSchema()+ ".rin_ma_sublocation sub ON phone.rin_ma_phone_book_sublocation_id = sub.idrin_ma_sublocation_sublocationId "
				+ " WHERE phone.rin_ma_entity_id = '"+ authDetailsVo.getEntityId()+"' and phone.delete_flag = "
				+ CommonConstant.FLAG_ZERO + " AND phone.idrin_ma_phone_book_id = " + id
				+ " ORDER BY phone.idrin_ma_phone_book_id DESC";

		Object[] phoneBook = (Object[]) getEntityManager().createNativeQuery(query).getSingleResult();

		return phoneBook;


	}

	/**
	 * Method is used to Get all search the phone booking details.
	 * 
	 * @param phoneBookVo
	 *            PhoneBookVo
	 * @param authDetailsVo 
	 * @return phoneBook List<Object[]>
	 */
	public List<Object[]> getAllSearch(PhoneBookVO phoneBookVo, AuthDetailsVo authDetailsVo) {

		String query = "SELECT phone.idrin_ma_phone_book_id , phone.rin_ma_phone_book_employee_id , phone.rin_ma_phone_book_employee_name ,"
				+ "phone.rin_ma_phone_book_department_id , dep.USER_DEPARTMENT_NAME, phone.rin_ma_phone_book_location_id , location.USER_LOCATION_NAME ,"
				+ "phone.rin_ma_phone_book_sublocation_id , sub.rin_ma_sublocation_name,"
				+ "phone.rin_ma_phone_book_mobile_number_c , phone.rin_ma_phone_book_mobile_number_p , phone.rin_ma_phone_book_pnone_number , "
				+ " phone.rin_ma_phone_book_extension_number , phone.rin_ma_phone_book_email_id , phone.rin_ma_phone_book_skype_id , phone.rin_ma_phone_book_is_active , "
				+ " phone.rin_ma_phone_book_profile FROM rin_ma_phone_book phone LEFT JOIN "+getCommonDatabaseSchema()+".user_location location ON  phone.rin_ma_phone_book_location_id = location.USER_LOCATION_ID "
				+ " LEFT JOIN "+getCommonDatabaseSchema()+".user_department dep ON phone.rin_ma_phone_book_department_id = dep.USER_DEPARTMENT_ID LEFT JOIN "
				+ " "+getCommonDatabaseSchema()+".rin_ma_sublocation sub ON phone.rin_ma_phone_book_sublocation_id = sub.idrin_ma_sublocation_sublocationId"
				+ " WHERE phone.rin_ma_entity_id = '"+ authDetailsVo.getEntityId()+"' and phone.delete_flag = " + CommonConstant.FLAG_ZERO;

		StringBuffer modifiedQuery = new StringBuffer(query);

		if (phoneBookVo.getPhoneBookId() != null)
			modifiedQuery.append(" and phone.idrin_ma_phone_book_id = " + phoneBookVo.getPhoneBookId());

		if (phoneBookVo.getEmployeeId() != null && !phoneBookVo.getEmployeeId().isEmpty())
			modifiedQuery.append(" and LOWER(phone.rin_ma_phone_book_employee_id) LIKE LOWER('%"
					+ phoneBookVo.getEmployeeId() + "%')");

		if (phoneBookVo.getEmployeeName() != null && !phoneBookVo.getEmployeeName().isEmpty())
			modifiedQuery.append(" and LOWER(phone.rin_ma_phone_book_employee_name) LIKE LOWER('%"
					+ phoneBookVo.getEmployeeName() + "%')");

		if (phoneBookVo.getUserDepartmentId() != null)
			modifiedQuery.append(" and phone.rin_ma_phone_book_department_id = " + phoneBookVo.getUserDepartmentId());

		if (phoneBookVo.getUserDepartment() != null && !phoneBookVo.getUserDepartment().isEmpty())
			modifiedQuery.append(
					" and LOWER(dep.USER_DEPARTMENT_NAME) LIKE LOWER('%" + phoneBookVo.getUserDepartment() + "%')");

		if (phoneBookVo.getUserLocationId() !=null)
			modifiedQuery.append(" and phone.rin_ma_phone_book_location_id = " + phoneBookVo.getUserLocationId());

		if (phoneBookVo.getLocation() != null && !phoneBookVo.getLocation().isEmpty())
			modifiedQuery.append(
					" and LOWER(location.USER_LOCATION_NAME) LIKE LOWER('%" + phoneBookVo.getLocation() + "%')");

		if (phoneBookVo.getSublocationId() !=null)
			modifiedQuery.append(" and phone.rin_ma_phone_book_sublocation_id = " + phoneBookVo.getSublocationId());

		if (phoneBookVo.getSubLocation() != null && !phoneBookVo.getSubLocation().isEmpty())
			modifiedQuery.append(
					" and LOWER(sub.rin_ma_sublocation_name) LIKE LOWER('%" + phoneBookVo.getSubLocation() + "%')");

		if (phoneBookVo.getMobileNumberC() != null && !phoneBookVo.getMobileNumberC().isEmpty())
			modifiedQuery.append(" and LOWER(phone.rin_ma_phone_book_mobile_number_c) LIKE LOWER('%"
					+ phoneBookVo.getMobileNumberC() + "%')");

		if (phoneBookVo.getMobileNumberP() != null && !phoneBookVo.getMobileNumberP().isEmpty())
			modifiedQuery.append(" and LOWER(phone.rin_ma_phone_book_mobile_number_p) LIKE LOWER('%"
					+ phoneBookVo.getMobileNumberP() + "%')");
		
		if (phoneBookVo.getPhoneNumber() != null && !phoneBookVo.getPhoneNumber().isEmpty())
			modifiedQuery.append(" and LOWER(phone.rin_ma_phone_book_pnone_number) LIKE LOWER('%"
					+ phoneBookVo.getPhoneNumber() + "%')");

		if (phoneBookVo.getExtensionNumber() != null && !phoneBookVo.getExtensionNumber().isEmpty())
			modifiedQuery.append(" and LOWER(phone.rin_ma_phone_book_extension_number) LIKE LOWER('%"
					+ phoneBookVo.getExtensionNumber() + "%')");

		if (phoneBookVo.getEmailId() != null && !phoneBookVo.getEmailId().isEmpty())
			modifiedQuery.append(
					" and LOWER(phone.rin_ma_phone_book_email_id) LIKE LOWER('%" + phoneBookVo.getEmailId() + "%')");

		if (phoneBookVo.getSkypeId() != null && !phoneBookVo.getSkypeId().isEmpty())
			modifiedQuery.append(
					" and LOWER(phone.rin_ma_phone_book_skype_id) LIKE LOWER('%" + phoneBookVo.getSkypeId() + "%')");

		if (phoneBookVo.getStatus() != null) {
			if (phoneBookVo.getStatus().equals(CommonConstant.Active)) {
				modifiedQuery.append(" and phone.rin_ma_phone_book_is_active =" + CommonConstant.ACTIVE);
			} else {
				modifiedQuery.append(" and phone.rin_ma_phone_book_is_active =" + CommonConstant.CONSTANT_ZERO);
			}
		}

		modifiedQuery.append(" ORDER BY phone.idrin_ma_phone_book_id DESC ");

		@SuppressWarnings("unchecked")

		List<Object[]> phoneBook = (List<Object[]>) getEntityManager().createNativeQuery(modifiedQuery.toString())
				.getResultList();

		return phoneBook;
	}
	
	/**
	 * Method is used to get all the first search
	 * 
	 * @param phoneBookVo
	 * @return phoneBook
	 */
	public List<Object[]> getAllFirstSearch(PhoneBookVO phoneBookVo,AuthDetailsVo authDetailsVo) {

		String query = "SELECT phone.idrin_ma_phone_book_id , phone.rin_ma_phone_book_employee_id , phone.rin_ma_phone_book_employee_name ,"
				+ " phone.rin_ma_phone_book_department_id , dep.USER_DEPARTMENT_NAME, phone.rin_ma_phone_book_location_id , location.USER_LOCATION_NAME ,"
				+ " phone.rin_ma_phone_book_sublocation_id , sub.rin_ma_sublocation_name,"
				+ " phone.rin_ma_phone_book_mobile_number_c , phone.rin_ma_phone_book_mobile_number_p , phone.rin_ma_phone_book_pnone_number , "
				+ " phone.rin_ma_phone_book_extension_number , phone.rin_ma_phone_book_email_id , phone.rin_ma_phone_book_skype_id , phone.rin_ma_phone_book_is_active , "
				+ " phone.rin_ma_phone_book_profile "
				+ " FROM rin_ma_phone_book phone "
				+ " LEFT JOIN "+getCommonDatabaseSchema()+".user_location location ON  phone.rin_ma_phone_book_location_id = location.USER_LOCATION_ID "
				+ " LEFT JOIN "+getCommonDatabaseSchema()+".user_department dep ON phone.rin_ma_phone_book_department_id = dep.USER_DEPARTMENT_ID"
				+ " LEFT JOIN "+ getCommonDatabaseSchema()+".rin_ma_sublocation sub ON phone.rin_ma_phone_book_sublocation_id = sub.idrin_ma_sublocation_sublocationId WHERE phone.delete_flag = "
				+ CommonConstant.FLAG_ZERO + " AND phone.rin_ma_phone_book_is_active = " +CommonConstant.FLAG_ONE  
				+ " AND phone.rin_ma_entity_id = '"+ authDetailsVo.getEntityId()+"' ";

		StringBuffer modifiedQuery = new StringBuffer(query);

		if (phoneBookVo.getEmployeeName() != null && !phoneBookVo.getEmployeeName().isEmpty())
			modifiedQuery.append(" and LOWER(phone.rin_ma_phone_book_employee_name) LIKE LOWER('%"+ phoneBookVo.getEmployeeName()+"%')");

		modifiedQuery.append(" ORDER BY phone.idrin_ma_phone_book_id DESC ");

		@SuppressWarnings("unchecked")

		List<Object[]> phoneBook = (List<Object[]>) getEntityManager().createNativeQuery(modifiedQuery.toString())
				.getResultList();

		return phoneBook;
	}

}
