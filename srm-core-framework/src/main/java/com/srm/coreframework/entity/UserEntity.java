package com.srm.coreframework.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "user", schema = "common_rta_2_local")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Integer id;

	@Column(name = "USER_LOGIN_ID")
	private String userName;

	@Column(name = "USER_EMPLOYEE_ID")
	private String userEmployeeId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "MIDDLE_NAME")
	private String middleName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "CURRENT_ADDRESS")
	private String currentAddress;

	@JoinColumn(name = "USER_SUBLOCATION_ID", referencedColumnName = "idrin_ma_sublocation_sublocationId")
	@ManyToOne
	private SubLocation subLocationEntity;

	@JoinColumn(name = "USER_LOCATION_ID", referencedColumnName = "USER_LOCATION_ID")
	@ManyToOne
	private UserLocation userLocationEntity;

	@Column(name = "PERMANENT_ADDRESS")
	private String permanentAddress;

	@Column(name = "MOBILE")
	private String mobile;

	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;

	@Column(name = "EMAIL_ID")
	private String emailId;

	@Column(name = "SKYPE_ID")
	private String skypeId;

	@Column(name = "USER_PROFILE")
	private String userProfile;

	@JoinColumn(name = "USER_ROLE_ID", referencedColumnName = "ROLE_ID")
	@ManyToOne
	private UserRole userRoleEntity;

	@JoinColumn(name = "USER_DEPARTMENT_ID", referencedColumnName = "USER_DEPARTMENT_ID")
	@ManyToOne
	private UserDepartment userDepartmentEntity;

	@Column(name = "DIVISION_ID")
	private Integer divisionId;

	@Column(name = "THEME")
	private String theme;

	@Column(name = "PREFIX")
	private String prefix;

	@Column(name = "ACTIVE")
	private Character activeFlag;

	@Column(name = "APPLICATION_PROFILE_ID")
	private Integer applicationProfileId;

	@JoinColumn(name = "rin_ma_entity_id")
	@ManyToOne
	private EntityLicense entityLicenseEntity;

	@Column(name = "access_token")
	private String accessToken;
	
	@Column(name = "LANG_CODE")
	private String langCode;
	
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
	
	@JoinColumn(name = "DEFAULT_ROLE_ID", referencedColumnName = "ROLE_ID")
	@ManyToOne
	private UserRole defaultRole;
	
	@Column(name = "CHANGE_PASSWORD_DATE")
	private Date changePasswordDate;
	
	@Column(name = "FIRST_LOGIN")
	private Character firstLogin;
	
}
