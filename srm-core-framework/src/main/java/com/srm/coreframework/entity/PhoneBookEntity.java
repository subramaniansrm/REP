package com.srm.coreframework.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;


	@Data
	@Entity
	@Table(name = "rin_ma_phone_book", schema = "rta_2_local")
	public class PhoneBookEntity {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "idrin_ma_phone_book_id")
		private Integer phoneBookId;

		@Column(name = "rin_ma_phone_book_employee_id")
		private String employeeId;

		@Column(name = "rin_ma_phone_book_employee_name")
		private String employeeName;

		@Column(name = "rin_ma_phone_book_department_id")
		private Integer userDepartmentId;

		@Column(name = "rin_ma_phone_book_location_id")
		private Integer userLocationId;

		@Column(name = "rin_ma_phone_book_sublocation_id")
		private Integer sublocationId;

		@Column(name = "rin_ma_phone_book_mobile_number_c")
		private String mobileNumberC;

		@Column(name = "rin_ma_phone_book_extension_number")
		private String extensionNumber;

		@Column(name = "rin_ma_phone_book_email_id")
		private String emailId;

		@Column(name = "rin_ma_phone_book_mobile_number_p")
		private String mobileNumberP;

		@Column(name = "rin_ma_phone_book_pnone_number")
		private String phoneNumber;

		@Column(name = "rin_ma_phone_book_skype_id")
		private String skypeId;

		@Column(name = "rin_ma_phone_book_is_active")
		private Boolean phoneBookIsActive;
		
		
		@Column(name = "rin_ma_phone_book_profile")
		private String phoneBookProfile;


		@Column(name = "rin_ma_entity_id")
		private Integer entityLicenseId;
		
		@Column(name = "create_by")
		private Integer createBy;

		@Column(name = "create_date")
		@Temporal(TemporalType.TIMESTAMP)
		private Date createDate;

		@Column(name = "update_by")
		private Integer updateBy;

		@Column(name = "update_date")
		@Temporal(TemporalType.TIMESTAMP)
		private Date updateDate;

		@Column(name = "delete_flag")
		private Character deleteFlag;
	
}
