package com.srm.coreframework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Data
@Configuration
@PropertySource("classpath:/properties/message.properties")
public class UserMessages {

	@Value("${entityRole}")
	private String entityRole;
	
	@Value("${spring.resolver.modify.url}")
	private String resolverModify;

	@Value("${spring.approval.modify.url}")
	private String approvalModify;

	@Value("${spring.request.delete.url}")
	private String requestDeleteUrl;

	@Value("${spring.request.create.url}")
	private String requestCreateUrl;

	@Value("${spring.request.reopen.url}")
	private String requestReopenUrl;

	@Value("${spring.request.update.url}")
	private String requestUpdateUrl;

	@Value("${successMessage}")
	private String successMessage;

	@Value("${saveSuccessMessage}")
	private String saveSuccessMessage;

	@Value("${commonFlagSla}")
	private int commonFlagSla;

	@Value("${noRecordFound}")
	private String noRecordFound;

	@Value("${saveErroMessage}")
	private String saveErroMessage;

	@Value("${updateSuccessMessage}")
	private String updateSuccessMessage;

	@Value("${updateErrorMessage}")
	private String updateErrorMessage;

	@Value("${beanUtilPropertiesFailure}")
	private String beanUtilPropertiesFailure;

	@Value("${deleteSuccessMessage}")
	private String deleteSuccessMessage;

	@Value("${deleteErrorMessage}")
	private String deleteErrorMessage;

	@Value("${levelCheck}")
	private String levelCheck;

	@Value("${modifyFailure}")
	private String modifyFailure;

	@Value("${copyFailure}")
	private String copyFailure;

	@Value("${unUsedRecordOnlyBeDeleted}")
	private String unUsedRecordOnlyBeDeleted;

	@Value("${filePath}")
	private String filePath;

	@Value("${errorMessage}")
	private String errorMessage;

	@Value("${empty}")
	private String empty;

	@Value("${invalidEmail}")
	private String invalidEmail;

	@Value("${passwordChanged}")
	private String passwordChanged;

	@Value("${requestWorkflowNotAvailable}")
	private String requestWorkflowNotAvailable;

	@Value("${requestUpdateRestriction}")
	private String requestUpdateRestriction;

	@Value("${dbFailure}")
	private String dbFailure;

	@Value("${dataFailure}")
	private String dataFailure;

	@Value("${oldPassworManditory}")
	private String oldPassworManditory;

	@Value("${newPassworManditory}")
	private String newPassworManditory;

	@Value("${confirmPassworManditory}")
	private String confirmPassworManditory;

	@Value("${passwordEncription}")
	private String passwordEncription;

	@Value("${changePassword}")
	private String changePassword;

	@Value("${changePasswordFailure}")
	private String changePasswordFailure;

	@Value("${oldPasswordMismatch}")
	private String oldPasswordMismatch;

	@Value("${uniqueLoginId}")
	private String uniqueLoginId;

	@Value("${passwordManditory}")
	private String passwordManditory;

	@Value("${generatePasswordFailed}")
	private String generatePasswordFailed;

	@Value("${passwordChangedfailure}")
	private String passwordChangedfailure;

	@Value("${mailSendingfailed}")
	private String mailSendingfailed;

	@Value("${passwordNotSame}")
	private String passwordNotSame;

	@Value("${noResultFound}")
	private String noResultFound;

	@Value("${noUniqueFound}")
	private String noUniqueFound;

	@Value("${processValidation}")
	private String processValidation;

	@Value("${processValidationCompleted}")
	private String processValidationCompleted;

	@Value("${requestReOpen}")
	private String requestReOpen;

	@Value("${autoCodeGenerationFailure}")
	private String autoCodeGenerationFailure;

	@Value("${downloadPath}")
	private String downloadPath;

	@Value("${cannotsetinactive}")
	private String cannotsetinactive;

	@Value("${userandreportingtousercannotbesame}")
	private String userandreportingtousercannotbesame;

	// Authorization

	@Value("${noAuthorizationAvailableForThisUser}")
	private String noAuthorizationAvailableForThisUser;

	@Value("${noScreenAvailableForThisUser}")
	private String noScreenAvailableForThisUser;

	@Value("${duplicatemappingFound}")
	private String duplicatemappingFound;

	@Value("${userExistForSelectedRole}")
	private String userExistForSelectedRole;

	@Value("${selectSubScreen}")
	private String selectSubScreen;

	// Duplicate Record Validation

	@Value("${LocationAlreadyExists}")
	private String LocationAlreadyExists;

	@Value("${SubLocationAlreadyExists}")
	private String SubLocationAlreadyExists;

	@Value("${DepartmentAlreadyExists}")
	private String DepartmentAlreadyExists;

	@Value("${RoleAlreadyExists}")
	private String RoleAlreadyExists;

	@Value("${userMapping_user_check}")
	private String userMapping_user_check;
	

	/** Request **/

	@Value("${request_validation_requestId}")
	private String request_validation_requestId;

	@Value("${request_validation_requestCode}")
	private String request_validation_requestCode;

	@Value("${request_validation_requestTypeId}")
	private String request_validation_requestTypeId;

	@Value("${request_validation_requestSubTypeId}")
	private String request_validation_requestSubTypeId;

	@Value("${request_validation_locationId}")
	private String request_validation_locationId;

	@Value("${request_validation_departmentId}")
	private String request_validation_departmentId;

	@Value("${request_validation_sublocationId}")
	private String request_validation_sublocationId;

	@Value("${request_validation_requestDate}")
	private String request_validation_requestDate;

	@Value("${request_validation_requestPriority}")
	private String request_validation_requestPriority;

	@Value("${request_validation_requestSubject}")
	private String request_validation_requestSubject;

	@Value("${request_validation_requestFromDate}")
	private String request_validation_requestFromDate;

	@Value("${request_validation_requestToDate}")
	private String request_validation_requestToDate;

	@Value("${request_validation_requestIsCancel}")
	private String request_validation_requestIsCancel;

	@Value("${request_validation_requestMobNo}")
	private String request_validation_requestMobNo;

	@Value("${request_validation_requestExtension}")
	private String request_validation_requestExtension;

	@Value("${request_validation_requestExtensionSize}")
	private String request_validation_requestExtensionSize;

	@Value("${request_validation_requestExtensionSplChar}")
	private String request_validation_requestExtensionSplChar;

	@Value("${request_validation_currentStatusName}")
	private String request_validation_currentStatusName;

	@Value("${request_validation_requestFromAndToDate}")
	private String request_validation_requestFromAndToDate;

	@Value("${request_validation_deleteIdList}")
	private String request_validation_deleteIdList;

	@Value("${requestAuditListDeletionFailed}")
	private String requestAuditListDeletionFailed;

	@Value("${request_validation_requestCodeDidNotMatch}")
	private String request_validation_requestCodeDidNotMatch;

	@Value("${request_validation_requestDeletionErrorMessage}")
	private String request_validation_requestDeletionErrorMessage;

	@Value("${request_validation_moreThanOneWorkflow}")
	private String request_validation_moreThanOneWorkflow;

	@Value("${request_validation_executersNotAvailble}")
	private String request_validation_executersNotAvailble;

	@Value("${requestTypeNotAvailableForThisRequest}")
	private String requestTypeNotAvailableForThisRequest;

	@Value("${mailProblem}")
	private String mailProblem;

	@Value("${requestWorkFlowSequenceFailure}")
	private String requestWorkFlowSequenceFailure;

	@Value("${requestWorkFlowAuditListFailure}")
	private String requestWorkFlowAuditListFailure;

	@Value("${requestWorkFlowScreenFailure}")
	private String requestWorkFlowScreenFailure;

	@Value("${requestReSubmitListFailure}")
	private String requestReSubmitListFailure;

	@Value("${requestApprovalSequenceFailure}")
	private String requestApprovalSequenceFailure;

	@Value("${updateRemarksFailure}")
	private String updateRemarksFailure;

	/** Request **/

	@Value("${requestDetail_validation_requestDetailId}")
	private String requestDetail_validation_requestDetailId;

	@Value("${requestDetail_validation_requestScreenConfigId}")
	private String requestDetail_validation_requestScreenConfigId;

	@Value("${requestDetail_validation_requestScreenConfigDetailId}")
	private String requestDetail_validation_requestScreenConfigDetailId;

	@Value("${requestDetail_validation_requestDetailFieldType}")
	private String requestDetail_validation_requestDetailFieldType;

	@Value("${requestDetail_validation_requestDetailFieldValue}")
	private String requestDetail_validation_requestDetailFieldValue;

	@Value("${requestDetail_validation_requestDetailIsActive}")
	private String requestDetail_validation_requestDetailIsActive;

	@Value("${requestDetail_validation_requestDetailObjectListValue}")
	private String requestDetail_validation_requestDetailObjectListValue;

	@Value("${detailsNotAvailableForThisRequest}")
	private String detailsNotAvailableForThisRequest;

	/** Subrequest **/

	@Value("${oldRequestIdNotHold}")
	private String oldRequestIdNotHold;

	@Value("${checkTheSubrequestForThatRequest}")
	private String checkTheSubrequestForThatRequest;

	@Value("${needToCloseTheSubRequest}")
	private String needToCloseTheSubRequest;

	/** Widget **/
	@Value("${widget_validation_widgetId}")
	private String widget_validation_widgetId;

	@Value("${widget_validation_widgetCode}")
	private String widget_validation_widgetCode;

	@Value("${widget_validation_widgetIndex}")
	private String widget_validation_widgetIndex;

	@Value("${widget_validation_widgetTitle}")
	private String widget_validation_widgetTitle;

	@Value("${widget_validation_widgetIcon}")
	private String widget_validation_widgetIcon;

	@Value("${widget_validation_widgetSequence}")
	private String widget_validation_widgetSequence;

	@Value("${widget_validation_widgetIsActive}")
	private String widget_validation_widgetIsActive;

	@Value("${widget_code_available}")
	private String widget_code_available;

	@Value("${widget_code_unchange}")
	private String widget_code_unchange;

	@Value("${widget_record_not_available}")
	private String widget_record_not_available;

	@Value("${widget_validation_deleteIdList}")
	private String widget_validation_deleteIdList;

	@Value("${widget_date_not_available}")
	private String widget_date_not_available;

	/** Widget **/
	@Value("${widgetDetail_validation_widgetDetailId}")
	private String widgetDetail_validation_widgetDetailId;

	@Value("${widgetDetail_validation_heading}")
	private String widgetDetail_validation_heading;

	@Value("${widgetDetail_validation_headingIndex}")
	private String widgetDetail_validation_headingIndex;

	@Value("${widgetDetail_validation_picIsReq}")
	private String widgetDetail_validation_picIsReq;

	@Value("${widgetDetail_validation_picPath}")
	private String widgetDetail_validation_picPath;

	@Value("${widgetDetail_validation_descript}")
	private String widgetDetail_validation_descript;

	@Value("${widgetDetail_validation_attIsReq}")
	private String widgetDetail_validation_attIsReq;

	@Value("${widgetDetail_validation_attPath}")
	private String widgetDetail_validation_attPath;

	@Value("${widgetDetail_validation_morePath}")
	private String widgetDetail_validation_morePath;

	@Value("${widgetDetail_validation_externUrl}")
	private String widgetDetail_validation_externUrl;

	@Value("${widgetDetail_validation_isActive}")
	private String widgetDetail_validation_isActive;

	@Value("${widgetDetail_validation_announceDate}")
	private String widgetDetail_validation_announceDate;

	@Value("${widgetDetail_validation_validFrom}")
	private String widgetDetail_validation_validFrom;

	@Value("${widgetDetail_validation_validTo}")
	private String widgetDetail_validation_validTo;

	@Value("${widgetDetail_record_not_available}")
	private String widgetDetail_record_not_available;

	@Value("${widgetDetail_validFrom}")
	private String widgetDetail_validFrom;

	@Value("${widgetDetail_validTo}")
	private String widgetDetail_validTo;

	@Value("${widgetDetail_validFromAndTo}")
	private String widgetDetail_validFromAndTo;

	@Value("${widgetDetail_validFromList}")
	private String widgetDetail_validFromList;

	@Value("${widgetDetail_validToList}")
	private String widgetDetail_validToList;

	@Value("${widgetDetail_validFromAndToList}")
	private String widgetDetail_validFromAndToList;

	@Value("${widgetDetail_validFromAndTo_invalid}")
	private String widgetDetail_validFromAndTo_invalid;

	@Value("${widget_announcement_date_should_be_available_in_between}")
	private String widget_announcement_date_should_be_available_in_between;

	@Value("${autoWidgetIndexFailure}")
	private String autoWidgetIndexFailure;

	@Value("${widgetDetailPicPath}")
	private String widgetDetailPicPath;

	// Request Type

	@Value("${requestType_record_available_in_request}")
	private String requestType_record_available_in_request;

	@Value("${requestType_record_available_in_screen_config}")
	private String requestType_record_available_in_screen_config;

	@Value("${requestType_record_available_in_workflow}")
	private String requestType_record_available_in_workflow;

	@Value("${requestType_record_available_in_requestSubType}")
	private String requestType_record_available_in_requestSubType;

	// Request Type

	@Value("${requestSubType_record_available_in_request}")
	private String requestSubType_record_available_in_request;

	@Value("${requestSubType_record_available_in_screen_config}")
	private String requestSubType_record_available_in_screen_config;

	@Value("${requestSubType_record_available_in_workflow}")
	private String requestSubType_record_available_in_workflow;

	
	// Emergency Contact

	@Value("${emergencyContactNameEmpty}")
	private String emergencyContactNameEmpty;

	@Value("${emergencyContactNameLength}")
	private String emergencyContactNameLength;

	@Value("${emergencyContactPath}")
	private String emergencyContactPath;

	// Request Workflow

	@Value("${createWorkflowErrorMessages}")
	private String createWorkflowErrorMessages;

	// External Link

	@Value("${externalLink_url_validation}")
	private String externalLink_url_validation;

	@Value("${externalLink_name_validation}")
	private String externalLink_name_validation;

	@Value("${externalLink_name_length_validation}")
	private String externalLink_name_length_validation;

	@Value("${externalLink_url_validation}")
	private String externalLink_url_length_validation;

	@Value("${externalLink_displaySeq_validation}")
	private String externalLink_displaySeq_validation;
	
	@Value("${workFlowCode}")
	private String workFlowCode;

	@Value("${resolveUpdate}")
	private String resolveUpdate;

	@Value("${requestCreate}")
	private String requestCreate;
	
	// PhoneBook
	@Value("${phonebook_empId_req}")
	private String phonebook_empId_req;

	@Value("${phonebook_empId_val}")
	private String phonebook_empId_val;

	@Value("${phonebook_empName_req}")
	private String phonebook_empName_req;

	@Value("${phonebook_empName_val}")
	private String phonebook_empName_val;

	@Value("${phonebook_dep_val}")
	private String phonebook_dep_val;

	@Value("${phonebook_loc_val}")
	private String phonebook_loc_val;

	@Value("${phonebook_subloc_val}")
	private String phonebook_subloc_val;

	@Value("${phonebook_mail_val}")
	private String phonebook_mail_val;

	@Value("${phonebook_mail_req}")
	private String phonebook_mail_req;

	@Value("${phonebook_ext_req}")
	private String phonebook_ext_req;

	@Value("${phonebook_ext_val}")
	private String phonebook_ext_val;

	@Value("${phonebook_per_req}")
	private String phonebook_per_req;

	@Value("${phonebook_com_req}")
	private String phonebook_com_req;

	@Value("${phonebook_skype_req}")
	private String phonebook_skype_req;

	@Value("${phonebook_skype_val}")
	private String phonebook_skype_val;

	// Department

	@Value("${department_departmentName_required}")
	private String department_departmentName_required;

	@Value("${department_departmentName_lengthVal}")
	private String department_departmentName_lengthVal;

	@Value("${department_locationId_required}")
	private String department_locationId_required;

	// Duplicate Entry

	@Value("${requestType_name_dup}")
	private String requestType_name_dup;

	@Value("${requestSubType_name_dup}")
	private String requestSubType_name_dup;

	@Value("${amenity_name_dup}")
	private String amenity_name_dup;
	
	/*@Value("${holdRequestMessage}")
	private String holdRequestMessage;
	
	@Value("${forwardRequestValidation}")
	private String forwardRequestValidation;
		
	@Value("${sameForwardRequestMsg}")
	private String sameForwardRequestMsg;*/
		
	/*public String getForwardRequestValidation() {
		return forwardRequestValidation;
	}

	public void setForwardRequestValidation(String forwardRequestValidation) {
		this.forwardRequestValidation = forwardRequestValidation;
	}*/
	
	
	public String getDepartment_departmentName_required() {
		return department_departmentName_required;
	}

	public void setDepartment_departmentName_required(String department_departmentName_required) {
		this.department_departmentName_required = department_departmentName_required;
	}

	public String getDepartment_departmentName_lengthVal() {
		return department_departmentName_lengthVal;
	}

	public void setDepartment_departmentName_lengthVal(String department_departmentName_lengthVal) {
		this.department_departmentName_lengthVal = department_departmentName_lengthVal;
	}

	public String getDepartment_locationId_required() {
		return department_locationId_required;
	}

	public void setDepartment_locationId_required(String department_locationId_required) {
		this.department_locationId_required = department_locationId_required;
	}

	public String getDepartment_description_required() {
		return department_description_required;
	}

	public void setDepartment_description_required(String department_description_required) {
		this.department_description_required = department_description_required;
	}

	public String getDepartment_description_lengthVal() {
		return department_description_lengthVal;
	}

	public void setDepartment_description_lengthVal(String department_description_lengthVal) {
		this.department_description_lengthVal = department_description_lengthVal;
	}

	@Value("${department_description_required}")
	private String department_description_required;

	@Value("${department_description_lengthVal}")
	private String department_description_lengthVal;

	@Value("${widgetIndexDuplicate}")
	private String widgetIndexDuplicate;
	
	@Value("${logout}")
	private String logout;

	// Image path
	@Value("${image_val}")
	private String image_val;

	

}
