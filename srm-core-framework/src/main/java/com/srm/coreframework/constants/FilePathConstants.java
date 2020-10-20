package com.srm.coreframework.constants;

/**
 * @author vigneshS
 *
 */
public class FilePathConstants {

	
	public static final String FROM_USERlIST_CHAT = "/api/master/userList/chat";
	public static final String SAVE_CHAT = "/api/master/userList/saveChat";
	public static final String UPDATE_CHAT = "/api/master/userList/loadChat";
	public static final String LOAD_USER_CHAT = "/api/master/userList/chat";
	public static final String DELETE_CHAT = "/api/master/userList/deleteChat";
	public static final String LOAD_DEPARTMENT_LOCATION = "/api/master/userList/location";

	/* LOGIN AND COMMON MENU'S */
	public static final String GET_MYMESSAGE_LIST = "/api/master/mymessages/list";
	public static final String GET_MYMESSAGE_POPUP = "/api/master/mymessages/view";
	public static final String MYMESSAGE_SAVE = "/api/master/mymessages/save";
	public static final String GET_FAQ = "/api/master/faq/view";
	public static final String GET_HELP = "/sr/help/view";
	public static final String RTA_LOGIN = "/sr/login";
	public static final String RTA_AUTOLOGIN = "/api/autologin";
	public static final String RTA_LOGOUT = "/api/logout";
	public static final String RTA_CHANGE_PASSWORD = "/api/common/changePassword";
	public static final String RTA_FORGOT_PASSWORD = "/common/forgotPassword";
	public static final String CHAT_ACTIVE_USERS_LIST = "/api/transaction/activeUser/list";
	
//Rest template password
	public static final String RT_RTA_CHANGE_PASSWORD = "/api/common/RT/changePassword";
	public static final String RT_RTA_FORGOT_PASSWORD = "/common/RT/forgotPassword";
	public static final String RT_RTA_FORGOT_PASS_USER_CHECK = "/common/forgotPassword/validuser";
	
	/* AUTHENTICATION */
	public static final String AUTHENTICATION_LIST = "/sr/auth/getAll";
	public static final String REST_TEMPLATE_AUTHENTICATION_LIST = "/sr/restTemplate/auth/getAll";
	public static final String AUTHENTICATION_SEARCH = "/sr/auth/search";
	public static final String REST_TEMPLATE_AUTHENTICATION_SEARCH = "/sr/restTemplate/auth/search";
	public static final String AUTHENTICATION_ADD = "/sr/auth/add";
	public static final String REST_TEMPLATE_AUTHENTICATION_ADD = "/sr/restTemplate/auth/add";
	public static final String AUTHENTICATION_CREATE = "/sr/auth/create";
	public static final String REST_TEMPLATE_AUTHENTICATION_CREATE = "/sr/restTemplate/auth/create";
	// public static final String AUTHENTICATION_LOAD =
	// "/sr/authentication/load";
	// public static final String AUTHENTICATION_UPDATE =
	// "/sr/authentication/update";
	public static final String AUTHENTICATION_DELETE = "/sr/auth/delete";
	public static final String REST_TEMPLATE_AUTHENTICATION_DELETE = "/sr/auth/restTemplate/delete";
	public static final String AUTHENTICATION_SUB_SCREEN = "/sr/auth/subscreen";
	public static final String REST_TEMPLATE_AUTHENTICATION_SUB_SCREEN = "/sr/restTemplate/auth/subscreen";
	public static final String AUTHENTICATION_FIELD = "/sr/auth/field";
	public static final String REST_TEMPLATE_AUTHENTICATION_FIELD = "/sr/restTemplate/auth/field";
	public static final String AUTHENTICATION_FUNCTION = "/sr/auth/function";
	public static final String REST_TEMPLATE_AUTHENTICATION_FUNCTION = "/sr/restTemplate/auth/function";


	/* User */
	public static final String USER_LOAD = "/sr/user/userLoad";
	public static final String USER_ADD = "/sr/user/add";
	public static final String USER_CREATE = "/sr/user/userAdd";
	public static final String USER_UPDATE = "/sr/user/update";
	public static final String USER_DELETE = "/sr/user/userDelete";
	public static final String USER_VIEW = "/sr/user/userView";
	public static final String USER_SEARCH = "/sr/user/userSearch";
	public static final String USER_ACCESS_TOKEN = "/sr/user/accessToken";
	
	//rest template user
		public static final String USER_SAVE = "/sr/user/userSave";
		public static final String USER_AUTH_ADD = "/sr/user/auth/add";
		public static final String USER_LIST = "/sr/user/userList";
		public static final String USER_MODIFY = "/sr/user/update1";
		public static final String USER_DELETE1 = "/sr/user/userDelete1";
		public static final String USER_VIEW1 = "/sr/user/userView1";
		public static final String USER_SEARCH1 = "/sr/user/userSearch1";
	/* Drop down */
	public static final String LOCATION_LOAD = "/sr/user/locationLoad";
	public static final String LOCATION_LOAD_MOBILE = "/sr/location/getAll";
	public static final String REST_TEMPLATE_LOCATION_LOAD = "/sr/dropdown/location";
	public static final String DEPARTMENT_LOAD = "/sr/user/departmentLoad";
	public static final String REST_TEMPLATE_DEPARTMENT_LOAD = "/sr/dropdown/deparment";
	public static final String ROLE_LOAD = "/sr/user/roleLoad";
	public static final String REST_TEMPLATE_ROLE_LOAD = "/sr/dropdown/roleLoad";
	public static final String DIVISIONS_LOAD = "/sr/user/divisionsLoad";
	public static final String REST_TEMPLATE_DIVISIONS_LOAD = "/sr/dropdown/divisionsLoad";
	public static final String REQUEST_TYPE_DROPDOWN = "/sr/RT/dropdown";
	public static final String RESOLVER_REASSIGN = "/sr/Resolver/reassignUser";

	/* DROPDOWN */
	public static final String SCREEN_LOAD = "/sr/screen/dropdown";
	public static final String REST_TEMPLATE_COUNTRY_LOAD = "/sr/restTemplate/country/dropdown";
	public static final String COUNTRY_LOAD = "/sr/country/dropdown";
	public static final String STATE_LOAD = "/sr/state/dropdown";
	public static final String REST_TEMPLATE_STATE_LOAD = "/sr/restTemplate/state/dropdown";
	public static final String CURRENCY_LOAD = "/sr/currency/dropdown";
	public static final String REST_TEMPLATE_CURRENCY_LOAD = "/sr/restTemplate/currency/dropdown";
	
	public static final String REST_TEMPLATE_ENTITY_LIST = "/sr/restTemplate/entity/list";
	public static final String ENTITY_LIST = "/sr/entity/list";
	public static final String REST_TEMPLATE_ENTITY = "/sr/restTemplate/entity/load";
	public static final String ENTITY_DROP_DOWN = "/sr/entity/load";
	public static final String ENTITY_SAVE = "/sr/entity/saveEntity";
	public static final String REST_TEMPLATE_ENTITY_SAVE = "/sr/restTemplate/entity/saveEntity";
	public static final String USER_ENTITY_DELETE = "/sr/entity/delete";
	public static final String REST_TEMPLATE_USER_ENTITY_DELETE = "/sr/restTemplate/entity/delete";
	public static final String CITY_LOAD = "/sr/city/dropdown";
	public static final String REST_TEMPLATE_CITY_LOAD = "/sr/restTemplate/city/dropdown";
	public static final String USER_ROLE_LOAD_USER_DEPARTMENT = "/sr/userrole/loadUserDepartment";
	public static final String REST_TEMPLATE_USER_ROLE_LOAD_USER_DEPARTMENT = "/sr/restTemplate/userrole/loadUserDepartment";
	public static final String USER_ROLE_LOAD_USER_LOCATION = "/sr/userrole/loadUserLocation";
	public static final String REST_TEMPLATE_USER_ROLE_LOAD_USER_LOCATION = "/sr/restTemplate/userrole/loadUserLocation";
	public static final String SUBLOCATION_LOAD = "/sr/sub/dropdown";
	public static final String SUBLOCATION_LOAD_MOBILE = "/sr/sublocation/getAll";
	public static final String REST_TEMPLATE_SUBLOCATION_LOAD = "/sr/dropdown/subLocation";
	public static final String USER_DROPDOWN = "/sr/user/dropdown";
	public static final String REST_TEMPLATE_USER_DROPDOWN = "/sr/dropdown/user";
	public static final String USER_LEVEL = "/sr/userlevel/dropdown";
	public static final String REST_TEMPLATE_USER_DEP = "/sr/restTemplate/user/dep";
	public static final String LANGUAGE_DROPDOWN = "/sr/language/dropdown";
	
	/* SUB LOCATION */
	public static final String SUBLOCATION_LIST = "/sr/sub/getAll";
	public static final String SUBLOCATION_SEARCH = "/sr/sub/search";
	public static final String SUBLOCATION_CREATE = "/sr/sub/create";
	public static final String SUBLOCATION_UPDATE = "/sr/sub/update";
	public static final String SUBLOCATION_DELETE = "/sr/sub/delete";
	public static final String SUBLOCATION_VIEW = "/sr/sub/load";
	public static final String SUBLOCATION_ADD = "/sr/sub/add";

	/* Rest template SUB LOCATION */
	public static final String SUBLOCATION_LIST_RT = "/sr/sub/RT/getAll";
	public static final String SUBLOCATION_SEARCH_RT = "/sr/sub/RT/search";
	public static final String SUBLOCATION_CREATE_RT = "/sr/sub/RT/create";
	public static final String SUBLOCATION_UPDATE_RT = "/sr/sub/RT/update";
	public static final String SUBLOCATION_DELETE_RT = "/sr/sub/RT/delete";
	public static final String SUBLOCATION_VIEW_RT = "/sr/sub/RT/load";
	public static final String SUBLOCATION_ADD_RT = "/sr/sub/RT/add";

	
	/* REQUEST SUBTYPE */
	public static final String REQUEST_SUBTYPE_CREATE = "/sr/RST/create";
	public static final String REQUEST_SUBTYPE_UPDATE = "/sr/RST/update";
	public static final String REQUEST_SUBTYPE_LIST = "/sr/RST/getAll";
	public static final String REQUEST_SUBTYPE_SEARCH = "/sr/RST/search";
	public static final String REQUEST_SUBTYPE_DELETE = "/sr/RST/delete";
	public static final String REQUEST_SUBTYPE_VIEW = "/sr/RST/load";
	public static final String REQUEST_SUBTYPE_DROPDOWN = "/sr/RST/dropdown";

	/* REQUEST TYPE */
	public static final String REQUEST_TYPE_LIST = "sr/RT/getAll";

	public static final String REQUEST_TYPE_CREATE = "sr/RT/create";
	public static final String REQUEST_TYPE_UPDATE = "sr/RT/update";
	public static final String REQUEST_TYPE_DELETE = "sr/RT/delete";
	public static final String REQUEST_TYPE_LOAD = "sr/RT/load";
	public static final String REQUEST_TYPE_SEARCH = "sr/RT/search";

	/* USER_ROLE */
	public static final String GET_USER_ROLE_LIST = "/sr/userrole/getAll";
	public static final String REST_TEMPLATE_GET_USER_ROLE_LIST = "/sr/restTemplate/userrole/getAll";
	public static final String GET_USER_ROLE_ADD = "/sr/userrole/add";
	public static final String REST_TEMPLATE_GET_USER_ROLE_ADD = "/sr/restTemplate/userrole/add";
	public static final String SAVE_USER_ROLE = "/sr/userrole/create";
	public static final String REST_TEMPLATE_SAVE_USER_ROLE = "/sr/restTemplate/userrole/create";
	public static final String UPDATE_USER_ROLE = "/sr/userrole/update";
	public static final String REST_TEMPLATE_UPDATE_USER_ROLE = "/sr/restTemplate/userrole/update";
	public static final String DELETE_USER_ROLE = "/sr/userrole/delete";
	public static final String REST_TEMPLATE_DELETE_USER_ROLE = "/sr/restTemplate/userrole/delete";
	public static final String SEARCH_USER_ROLE = "/sr/userrole/search";
	public static final String REST_TEMPLATE_SEARCH_USER_ROLE = "/sr/restTemplate/userrole/search";
	public static final String VIEW_USER_ROLE = "/sr/userrole/load";
	public static final String REST_TEMPLATE_VIEW_USER_ROLE = "/sr/restTemplate/userrole/load";
	public static final String COPY_USER_ROLE = "/sr/userrole/copy";
	public static final String REST_TEMPLATE_COPY_USER_ROLE = "/sr/restTemplate/userrole/copy";

	/* DEPARTMENT */
	public static final String LOAD_DEPARTMENT = "/sr/depart/getAll";
	public static final String  REST_TEMPLATE_LOAD_DEPARTMENT = "/sr/restTemplate/depart/getAll";
	public static final String ADD_DEPARTMENT = "/sr/depart/create";
	public static final String REST_TEMPLATE_ADD_DEPARTMENT = "/sr/restTemplate/depart/create";
	public static final String UPDATE_DEPARTMENT = "/sr/depart/update";
	public static final String REST_TEMPLATE_UPDATE_DEPARTMENT = "/sr/restTemplate/depart/update";
	public static final String DELETE_DEPARTMENT = "/sr/depart/delete";
	public static final String REST_TEMPLATE_DELETE_DEPARTMENT = "/sr/restTemplate/depart/delete";
	public static final String SEARCH_DEPARTMENT = "/sr/depart/search";
	public static final String REST_TEMPLATE_SEARCH_DEPARTMENT = "/sr/restTemplate/depart/search";
	public static final String VIEW_DEPARTMENT = "/sr/depart/load";
	public static final String REST_TEMPLATE_VIEW_DEPARTMENT = "/sr/restTemplate/depart/load";
	public static final String DEPARTMENT_AUTH_ADD = "/sr/depart/loadAdd";
	public static final String REST_TEMPLATE_DEPARTMENT_AUTH_ADD = "/sr/restTemplate/depart/loadAdd";

	/* LOCATION */
	public static final String LOCATION_LIST = "/sr/loc/getAll";
	public static final String LOCATION_SEARCH = "/sr/loc/search";
	public static final String LOCATION_CREATE = "/sr/loc/create";
	public static final String LOCATION_UPDATE = "/sr/loc/update";
	public static final String LOCATION_DELETE = "/sr/loc/delete";
	public static final String LOCATION_VIEW = "/sr/loc/load";
	
	/* Rest Template LOCATION */
	public static final String LOCATION_LIST_RT = "/sr/loc/RT/getAll";
	public static final String LOCATION_SEARCH_RT = "/sr/loc/RT/search";
	public static final String LOCATION_CREATE_RT = "/sr/loc/RT/create";
	public static final String LOCATION_UPDATE_RT = "/sr/loc/RT/update";
	public static final String LOCATION_DELETE_RT = "/sr/loc/RT/delete";
	public static final String LOCATION_VIEW_RT = "/sr/loc/RT/load";


	// External Link
	public static final String EXTERNALLINK_GETALL = "/sr/externalLink/getAll";
	public static final String EXTERNALLINK_LOAD = "/sr/externalLink/load";
	public static final String EXTERNALLINK_SEARCH = "/sr/externalLink/search";
	public static final String EXTERNALLINK_DELETE = "/sr/externalLink/delete";
	public static final String EXTERNALLINK_CREATE = "/sr/externalLink/create";
	public static final String EXTERNALLINK_UPDATE = "/sr/externalLink/update";
	public static final String EXTERNALLINK_DOWNLOAD = "/sr/externalLink/download";
	public static final String EXTERNALLINK_ADD = "/sr/externalLink/add";

	// W = Widget
	public static final String WIDGET_LIST = "/sr/Wid/getAll";

	public static final String WIDGET_SEARCH = "/sr/Wid/search";

	public static final String WIDGET_ADD = "/sr/Wid/add";

	public static final String WIDGET_CREATE = "/sr/Wid/create";

	public static final String WIDGET_LOAD = "/sr/Wid/load";

	public static final String WIDGET_UPDATE = "/sr/Wid/update";

	public static final String WIDGET_DELETE = "/sr/Wid/delete";

	public static final String WIDGET_IMAGE = "/sr/Wid/image";

	public static final String WIDGET_DATE = "/sr/Wid/date";

	public static final String WIDGET_DOWNLOAD = "/sr/Wid/download";

	public static final String WIDGET_PIC_DOWNLOAD = "/sr/Wid/picdownload";

	public static final String WIDGET_ATT_DOWNLOAD = "/sr/Wid/attdownload";

	public static final String WIDGET_LOAD_DETAIL = "/sr/Wid/loaddetail";

	public static final String FILE_NAME_FORMAT_DATE = "yyyyMMddHHmmss";

	public static final String LIST_CHAT = "/sr/user/chatList";

	/* FAQ */
	public static final String FAQ_LIST = "/sr/faq/view";

	/* Request */

	public static final String REQUEST_LIST = "/sr/Req/getAll";
	
	public static final String REQUEST_COUNT = "/sr/Req/getCount";

	public static final String REQUEST_SEARCH = "/sr/Req/search";

	public static final String REQUEST_ADD = "/sr/Req/add";

	public static final String REQUEST_CREATE = "/sr/Req/create";
	
	public static final String REQUEST_MOBILE_CREATE = "/sr/ReqMobile/create";

	public static final String REQUEST_LOAD = "/sr/Req/load";
	
	public static final String RESOLVER_REQUEST_LOAD = "/sr/resolver/load";

	public static final String REQUEST_UPDATE = "/sr/Req/update";

	public static final String REQUEST_DELETE = "/sr/Req/delete";

	public static final String REQUEST_SCREEN_LIST = "/sr/Req/screenlist";

	public static final String REQUEST_VO = "/sr/Req/vo";

	public static final String REQUEST_RESTRICTION = "/sr/Req/restrict";

	public static final String REQUEST_RE_OPEN = "/sr/Req/reopen";

	public static final String REQUEST_WORKFLOW_STATUS = "/sr/Req/workflow";

	public static final String REQUEST_ATTACHMENT_DOWNLOAD = "/sr/Req/attachmentDownload";

	// RS = RequestScreen
	public static final String REQUESTSCREEN_LIST = "/sr/RSC/getAll";

	public static final String REQUESTSCREEN_SEARCH = "/sr/RSC/search";

	public static final String REQUESTSCREEN_ADD = "/sr/RSC/add";

	public static final String REQUESTSCREEN_CREATE = "/sr/RSC/create";

	public static final String REQUESTSCREEN_LOAD = "/sr/RSC/load";

	public static final String REQUESTSCREEN_UPDATE = "/sr/RSC/update";

	public static final String REQUESTSCREEN_DELETE = "/sr/RSC/delete";

	public static final String REQUESTSCREEN_COPY = "/sr/RSC/copy";

	// RWF = request work flow
	public static final String REQUEST_WORK_FLOW_LIST = "/sr/RWF/getAll";

	public static final String REQUEST_WORK_FLOW_SEARCH = "/sr/RWF/search";

	public static final String REQUEST_WORK_FLOW_ADD = "/sr/RWF/add";

	public static final String REQUEST_WORK_FLOW_CREATE = "/sr/RWF/create";

	public static final String REQUEST_WORK_FLOW_LOAD = "/sr/RWF/load";

	public static final String REQUEST_WORK_FLOW_UPDATE = "/sr/RWF/update";

	public static final String REQUEST_WORK_FLOW_DELETE = "/sr/RWF/delete";

	public static final String REQUEST_WORK_FLOW_COPY = "/sr/RWF/copy";

	public static final String REQUEST_WORK_FLOW_MODIFY_DELETE = "/sr/RWF/modifyDelete";

	public static final String REQUEST_WORK_FLOW_DETAIL_VALIDATION = "/sr/RWF/detailValidation";

	// Request Resolver
	public static final String LOAD_RESOLVER = "/sr/resolver/loadResolver";
	public static final String UPDATE_RESOLVER = "/sr/resolver/updateResolver";
	public static final String VIEW_RESOLVER = "/sr/resolver/viewResolver";
	public static final String VIEW_ALL_RESOLVER = "/sr/resolver/viewAllResolver";
	public static final String RESOLVER_SEARCH = "/sr/resolver/search";
	public static final String RESOLVER_REQLOAD = "/sr/resolver/reqLoad";
	public static final String STATUS_CHECK_RESOLVER = "/sr/resolver/holdstatus";
	public static final String STATUS_CHECK_RESOLVER_RESOLVER = "/sr/requestor/holdstatus";
	public static final String MOBILE_STATUS_CHECK_RESOLVER_RESOLVER = "/sr/mob/requestor/holdstatus";
	public static final String MOBILE_STATUS_CHECK_RESOLVER = "/sr/mob/resolver/holdstatus";
	
	
	// Request Approval
	public static final String APPROVAL_LIST = "/sr/approval/getAll";
	public static final String APPROVAL_LOAD = "/sr/approval/load";
	public static final String APPROVAL_MODIFY = "/sr/approval/modify";
	public static final String APPROVAL_LOADALL = "/sr/approval/loadAll";
	public static final String APPROVAL_GETALLSEARCH = "/sr/approval/getAllSearch";
	public static final String APPROVAL_REQLOAD = "/sr/approval/reqLoad";

	

	// summary
	public static final String SUMMARY_MYREQUEST_GETALL = "/sr/summary/myRequestGetall";

	public static final String SUMMARY_MYREQUEST_DASHBOARD_COUNT = "/sr/summary/myRequestDashboardCount";

	public static final String SUMMARY_MYREQUEST_VIEW = "/sr/summary/myRequestView";

	public static final String SUMMARY_MYREQUEST_GETALL_SEARCH = "/sr/summary/myRequestGetallSearch";

	public static final String SUMMARY_APPROVAL_GETALL = "/sr/summary/approvalGetall";

	public static final String SUMMARY_APPROVAL_DASHBOARD_COUNT = "/sr/summary/approvalDashboardCount";

	public static final String SUMMARY_OTHERS_GETALL = "/sr/summary/othersGetall";

	public static final String SUMMARY_OTHERS_DASHBOARD_COUNT = "/sr/summary/othersDashboardCount";

	public static final String SUMMARY_OTHERS_VIEW = "/sr/summary/othersView";

	public static final String SUMMARY_OTHERS_GETALL_SEARCH = "/sr/summary/othersGetallSearch";

	public static final String SUMMARY_RESOLVER_GETALL = "/sr/summary/resolverCount";

	public static final String SUMMARY_RESOLVER_HISTORY = "/sr/summary/resolverHistory";

	public static final String SUMMARY_RESOLVER_NUMBER = "/sr/summary/resolverNumber";

	public static final String SUMMARY_RESOLVER_SEARCH = "/sr/summary/resolverSearch";

	// db=dashboard

	public static final String APPLICATION_DASHBOARD = "/sr/dashboard/getAll";

	public static final String MYREQUEST_DASHBOARD = "/sr/dashboard/myReqdashboard/getAll";

	public static final String OTHERAPPLICATION_DASHBOARD = "/sr/dashboard/subdashboard/getAll";

	public static final String EXTERNALLINK_DASHBOARD = "/sr/dashboard/exLinkdashboard/getAll";

	public static final String REQUESTSTATUS_DASHBOARD = "/sr/dashboard/exLinkdashboard/getRequestStatus";

	public static final String SLAINSIGHTS_DASHBOARD = "/sr/slaDashboard/getAll";
	public static final String MORE_APPLICATION_DASHBOARD = "/sr/dashboard/MoreApplication/getAll";

	public static final String USER_EXECUTER = "/sr/user/userExecuter";

	// PhoneBook
	public static final String PHONE_BOOKING_LOAD = "/sr/phoneBooking/getAll";
	public static final String PHONE_BOOKING_VIEW = "/sr/phoneBooking/view";
	public static final String PHONE_BOOKING_SEARCH = "/sr/phoneBooking/search";
	public static final String PHONE_BOOKING_FIRST_SEARCH = "/sr/phoneBooking/firstSearch";
	public static final String PHONEBOOK_CREATE = "/sr/phoneBook/create";
	public static final String PHONEBOOK_UPDATE = "/sr/phoneBook/update";
	public static final String PHONEBOOK_DELETE = "/sr/phoneBook/delete";
	public static final String PHONEBOOK_PROFILECREATE = "/sr/phoneBook/profileCreate";
	public static final String PHONEBOOK_PROFILEUPDATE = "/sr/phoneBook/profileUpdate";
	public static final String PHONEBOOK_PROFILEDOWNLOAD = "/sr/phoneBook/profileDownload";
	public static final String PHONEBOOK_LOADADD = "/sr/phoneBook/loadAdd";

	public static final String DASHBOARD_REQUEST_CS_LIST = "/sr/DR/cr/getAll";

	// Mail

	public static final String MAIL_PENDING_LIST = "/sr/mail/pendingList";

	public static final String ESCALATION_MAIL_PENDING_LIST = "/sr/mail/escalationPendingList";

	public static final String GENERATE_MAIL = "/sr/mail/generate";

	public static final String ESCALATION_PENDING_LIST = "/sr/escalation/pendingList";

	public static final String AWAITING_APPROVAL_LIST = "/sr/awaiting/approvalList";

	public static final String AWAITING_APPROVAL_LIST_COUNT = "/sr/awaiting/approvalListCount";

	public static final String AWAITING_RESOLVER_LIST = "/sr/awaiting/resolverList";

	public static final String AWAITING_RESOLVER_LIST_COUNT = "/sr/awaiting/resolverListCount";

	public static final String ESCALATION_RESOLVER_LIST = "/sr/awaiting/resolverList";

	/* FlashNews */
	public static final String FLASH_NEWS_LOAD = "/sr/flashNews/getAll";
	public static final String FLASH_NEWS_LOAD_DASHBOARD = "/sr/flashNews/getAllDashboard";
	public static final String FLASH_NEWS_CREATE = "/sr/flashNews/create";
	public static final String FLASH_NEWS_UPDATE = "/sr/flashNews/update";
	public static final String FLASH_NEWS_DELETE = "/sr/flashNews/delete";
	public static final String FLASH_NEWS_VIEW = "/sr/flashNews/view";
	public static final String FLASH_NEWS_SEARCH = "/sr/flashNews/getAllSearch";
	public static final String FLASHNEWS_ADD = "/sr/flashNews/add";
	public static final String FLASH_NEWS_GETALL = "/sr/flashNews/getAllList";

	/* Notification */
	public static final String NOTIFICATION_LIST = "/sr/notificationList";
	public static final String DELETE_NOTIFICATION = "/sr/deleteNotification";
	public static final String RT_NOTIFICATION_LIST = "/sr/email/notificationList";
	public static final String RT_DELETE_NOTIFICATION = "/sr/email/deleteNotification";

	// user mapping

	public static final String USER_MAPPING_LIST = "/sr/um/getAll";
	public static final String USER_MAPPING_CREATE = "/sr/um/create";
	public static final String USER_MAPPING_UPDATE = "/sr/um/update";
	public static final String USER_MAPPING_LOAD = "/sr/um/load";
	public static final String USER_MAPPING_DELETE = "/sr/um/delete";
	public static final String USER_MAPPING_SEARCH = "/sr/um/search";

	// user mapping Rest Template

		public static final String USER_MAPPING_RT_LIST = "/sr/um/RT/getAll";
		public static final String USER_MAPPING_RT_ADD = "/sr/um/RT/Add";
		public static final String USER_MAPPING_RT_CREATE = "/sr/um/RT/create";
		public static final String USER_MAPPING_RT_UPDATE = "/sr/um/RT/update";
		public static final String USER_MAPPING_RT_LOAD = "/sr/um/RT/load";
		public static final String USER_MAPPING_RT_DELETE = "/sr/um/RT/delete";
		public static final String USER_MAPPING_RT_SEARCH = "/sr/um/RT/search";
	
	public static final String PROFILE_NAME = "/sr/profile/name";
	public static final String REST_TEMPLATE_PROFILE_NAME = "/sr/restTemplate/profile/name";

	/* Profile Edit */
	public static final String PROFILE_EDIT_VIEW = "/sr/profileEdit/view";
	public static final String PROFILE_EDIT_UPDATE = "/sr/profileEdit/update";
	public static final String USER_DEP = "/sr/user/dep";

	// Authentication Add
	public static final String REQUEST_TYPE_ADD = "/sr/RT/Add";
	public static final String REQUEST_SUBTYPE_ADD = "/sr/RST/Add";
	public static final String USER_MAPPING_ADD = "/sr/um/Add";
	public static final String ROOMCONFIG_ADD = "/sr/RC/Add";
	public static final String LOCATION_ADD = "/sr/loc/Add";
	public static final String LOCATION_ADD_RT = "/sr/loc/RT/Add";
	public static final String USER_ROLE_ROLETYPE = "/sr/userRole/RoleType";
	public static final String REST_TEMPLATE_USER_ROLE_ROLETYPE = "/sr/restTemplate/userRole/RoleType";
	// PhoneBook Excel

	public static final String EMERGENCY_CONTACT_GETALL = "/sr/emergency/getAll";
	public static final String EMERGENCY_CONTACT_LOADADD = "/sr/emergency/loadAdd";
	public static final String EMERGENCY_EXCEL_UPLOAD = "/sr/emergency/upload";
	public static final String EMERGENCY_EXCEL_DOWNLOAD = "/sr/emergency/download";
	public static final String EMERGENCY_EXCEL_UPDATE = "/sr/emergency/update";
	public static final String EMERGENCY_CONTACT_DELETE = "/sr/emergency/delete";
	public static final String EMERGENCY_CONTACT_VIEW = "/sr/emergency/view";
	public static final String EMERGENCY_CONTACT_DOWMNLOAD_GETALL = "/sr/emergency/getAllDownload";

	// Holiday
	public static final String GET_HOLIDAY_LIST = "/sr/holiday/getAll";
	public static final String SEARCH_HOLIDAY = "/sr/holiday/getAllSearch";
	public static final String ADD_HOLIDAY = "/sr/holiday/add";
	public static final String DELETE_HOLIDAY = "/sr/holiday/delete";
	public static final String VIEW_HOLIDAY = "/sr/holiday/view";
	public static final String SAVE_HOLIDAY = "/sr/holiday/create";
	public static final String UPDATE_HOLIDAY = "/sr/holiday/update";
	public static final String WIDGET_LIST_HR = "sr/Wid/getAllHR";
	public static final String HOLIDAY_CALENDER = "/sr/holiday/getAllCalenderHoliday";
	public static final String WIDGET_PIC_ATTACH_DOWNLOAD = "/sr/widget/pictureDownload";
	public static final String WIDGET_ATTACHMENT_DOWNLOAD = "/sr/widget/attachmentDownload";
	public static final String WIDGET_PICATTACHMENT_DOWNLOAD = "/sr/widget/zipPictureDownload";
	public static final String VIEW_ALL_RESOLVER_SUMMARY = "/sr/summary/viewAllResolverSummary";

	// Subrequest
	public static final String RESOLVER_SUBREQUEST_CREATE = "/sr/resolver/subrequestcreate";

	// DelegationMaster
	public static final String DELEGATION_MASTER_CREATE = "/sr/delegationMaster/create";
	public static final String DELEGATION_MASTER_GETALL = "/sr/delegationMaster/getAll";
	public static final String DELEGATION_MASTER_DELETE = "/sr/delegationMaster/delete";
	public static final String DELEGATION_MASTER_SEARCH = "/sr/delegationMaster/search";
	public static final String DELEGATION_MASTER_USER_GETALL = "/sr/delegationMaster/userGetAll";
	public static final String DELEGATION_MASTER_USER_SEARCH = "/sr/delegationMaster/userSearch";
	public static final String DELEGATION_AUTH_ADD = "/sr/delegationMaster/authAdd";
	public static final String DELEGATION_MASTER_UPDATE = "/sr/delegationMaster/update";
	public static final String DELEGATION_MASTER_SINGLE_UPDATE = "/sr/delegationMaster/singleUpdate";
	public static final String DELEGATION_MASTER_UPDATE_VIEW = "/sr/delegationMaster/singleView";
	public static final String DELEGATION_MASTER_VIEW = "/sr/delegationMaster/view";
	
	// Excel download
	public static final String EXPORT_SUBLOCATION_LIST = "/sr/excel/sublocationgetAll";
	public static final String EXPORT_DEPARTMENT_LIST = "/sr/excel/departmentgetAll";
	public static final String EXPORT_LOCATION_LIST = "/sr/excel/userlocationAll";
	public static final String EXPORT_USERMAPPING_LIST = "/sr/excel/usermappinggetAll";
	public static final String EXPORT_USERROLE_LIST = "/sr/excel/userRolegetAll";
	public static final String EXPORT_PHONEBOOK_LIST = "/sr/excel/userphonebookAll";
	public static final String EXPORT_HOLIDAYMASTER_LIST = "/sr/excel/holidaymasterAll";
	public static final String EXPORT_EMERGENCYCONTANTS_LIST = "/sr/excel/emergenceContactsAll";
	public static final String EXPORT_REQUEST_LIST = "/sr/excel/requestAll";
	public static final String EXPORT_APPORVAL_LIST = "/sr/excel/apporvalAll";
	public static final String EXPORT_RESLOVER_LIST = "/sr/excel/ResloverAll";
	public static final String EXPORT_REQUESTTYPE_LIST = "/sr/excel/RequestTypeGetAll";
	public static final String EXPORT_REQUESTSUBTYPE_LIST = "/sr/excel/RequestSubTypeGetAll";
	public static final String EXPORT_WIDGET_LIST = "/sr/excel/WidgetGetAll";
	public static final String EXPORT_FLASHNEWS_LIST = "/sr/excel/FlashNewsGet";
	public static final String EXPORT_EXTERNALLINK_LIST = "/sr/excel/ExternalLinkGetAll";
	public static final String EXPORT_REQ_WORKFLOW_LIST = "/sr/excel/reqWorkFlowGetAll";
	public static final String EXPORT_REQ_SCR_CONFIG_LIST = "/sr/excel/reqScreenGetAll";
	
	// forward request
	public static final String CREATE_FORWARD_REQUEST = "/sr/fr/create";
	
	//Entity Master
	public static final String ENTITY_MASTER_CREATE = "/entityMaster/create";
	public static final String ENTITY_MASTER_LIST = "/sr/entityMaster/getAll";
	public static final String ENTITY_MASTER_MODIFY = "/sr/entityMaster/update";
	public static final String ENTITY_MASTER_VIEW =  "/sr/entityMaster/view";
	public static final String ENTITY_MASTER_SEARCH = "/sr/entityMaster/search";
	public static final String ENTITY_MASTER_RENEWAL = "/sr/entityMaster/renewal";
	public static final String ENTITY_MASTER_RENEWAL_REST_TEMPLATE = "/sr/restTemplate/entityMaster/renewal";
	public static final String ENTITY_MASTER_UPDATE = "/sr/entityMaster/update";
	public static final String ENTITY_MASTER_UPDATE_REST_TEMPLATE = "/sr/restTemplate/entityMaster/update";
	public static final String ENTITY_MASTER_ALLLIST = "/sr/entityMaster/getAllEntity";
	
	
	//Entity Master - rta
	public static final String ENTITY_LISTALL = "/sr/entity/getAll";
	public static final String ENTITY_VIEW =  "/sr/entity/view";
	public static final String ENTITY_SEARCH = "/sr/entity/search";
	public static final String ENTITY_ADD =  "/sr/entity/add";
	public static final String ENTITY__REST_TEMPLATE_ADD =  "/sr/entity/restTemplate/add";
	public static final String ENTITY_LISTALLENTITY = "/sr/entity/getAllEntity";
	
	//LogOut
	public static final String LOGOUT = "/sr/logout";

	public static final String REQUEST_SUBTYPE_WORKFLOW_DROPDOWN = "/sr/RSTWF/dropdown";

	//Entity Selection
	public static final String USER_ENTITY_LOAD = "/sr/selection/loadUserEntity";
	public static final String USER_ENTITY_SELECTION = "/sr/selection/selectUserEntity";
	
	public static final String CHANGE_PASSWORD = "/sr/mobile/changePassword";
	public static final String FORWARD_REQUEST = "/sr/mobile/forwardRequest";
	public static final String REDIRECT_REQUEST = "/sr/mobile/redirectRequest";
	public static final String SUBREQUEST_SCREENDETAILS = "/sr/screen/details";
	public static final String SUBREQUEST_MOBILE_SCREENDETAILS = "/sr/mobile/screendetails";	
	
	public static final String TOEMAIL = "/sr/create/email";
		
	public static final String WEEKEND_MASTER_CREATE = "/sr/weekendMaster/create";
	public static final String WEEKEND_MASTER_GETALL = "/sr/weekendMaster/getAll";
	public static final String WEEKEND_MASTER_UPDATE = "/sr/weekendMaster/update";
	public static final String WEEKEND_MASTER_VIEW =  "/sr/weekendMaster/view";
	public static final String WEEKEND_MASTER_SEARCH = "/sr/weekendMaster/search";
	public static final String WEEKEND_MASTER_DELETE = "/sr/weekendMaster/delete";
	public static final String WEEKEND_MASTER_GETADDFIELDS = "/sr/weekendMaster/add";
			
	public static final String WEEKEND_RT_CREATE = "/sr/weekend/create";
	public static final String WEEKEND_RT_GETALL = "/sr/weekend/getAll";
	public static final String WEEKEND_RT_UPDATE = "/sr/weekend/update";
	public static final String WEEKEND_RT_VIEW =  "/sr/weekend/view";
	public static final String WEEKEND_RT_SEARCH = "/sr/weekend/search";
	public static final String WEEKEND_RT_DELETE = "/sr/weekend/delete";
	public static final String WEEKEND_RT_GETADDFIELDS = "/sr/weekend/add";
	
	public static final String DAYS_LIST = "/sr/daysMaster/list";
	public static final String DAYS_RT_LIST= "/sr/days/list";
	
	//entity create
	public static final String ENTITY_PLANNING_LIST= "/entityPlanning/list";
	public static final String ENTITY_PLANNING_LOAD= "/entityPlanning/load";
	
	//entity planning Master
	public static final String ENTITY_PLANNING_MASTER_LIST= "/sr/entityPlanningMaster/list";
	public static final String REST_TEMPLATE_ENTITY_PLANNING_MASTER_LIST= "/sr/restTempalte/entityPlanningMaster/list";
	public static final String ENTITY_PLANNING_MASTER_ADD= "/sr/entityPlanningMaster/add";
	public static final String REST_TEMPLATE_ENTITY_PLANNING_MASTER_ADD= "/sr/restTempalte/entityPlanningMaster/add";
	public static final String ENTITY_PLANNING_MASTER_SAVE= "/sr/entityPlanningMaster/save";
	public static final String REST_TEMPLATE_ENTITY_PLANNING_MASTER_SAVE= "/sr/restTempalte/entityPlanningMaster/save";
	public static final String ENTITY_PLANNING_MASTER_UPDATE= "/sr/entityPlanningMaster/update";
	public static final String REST_TEMPLATE_ENTITY_PLANNING_MASTER_UPDATE= "/sr/restTempalte/entityPlanningMaster/update";
	public static final String ENTITY_PLANNING_MASTER_VIEW= "/sr/entityPlanningMaster/view";
	public static final String REST_TEMPLATE_ENTITY_PLANNING_MASTER_VIEW= "/sr/restTempalte/entityPlanningMaster/view";
	public static final String ENTITY_PLANNING_MASTER_DELETE= "/sr/entityPlanningMaster/delete";
	public static final String REST_TEMPLATE_ENTITY_PLANNING_MASTER_DELETE= "/sr/restTempalte/entityPlanningMaster/delete";
	public static final String ENTITY_PLANNING_MASTER_SEARCH= "/sr/entityPlanningMaster/search";
	public static final String REST_TEMPLATE_ENTITY_PLANNING_MASTER_SEARCH= "/sr/restTempalte/entityPlanningMaster/search";

	public static final String REST_TEMPLATE_ENTITY_PLANNING_DRP= "/sr/restTempalte/entityPlanningMaster/planlist";
	public static final String ENTITY_PLANNING_MASTER_DRP= "/sr/entityPlanningMaster/planlist";
	
	public static final String PASSWORD_EXPIRY= "/sr/usePswdExpirySchedular";
	public static final String ENTITY_EXPIRY= "/sr/usedEntityExpirySchedular";
	public static final String TRN_ENTITY_EXPIRY= "/sr/usedTrnEntityExpirySchedular";
	public static final String ESCALATION_MAIL= "/sr/escalationSchedular";
	
}
