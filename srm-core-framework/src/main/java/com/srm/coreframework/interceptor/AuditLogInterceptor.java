package com.srm.coreframework.interceptor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.srm.coreframework.response.CustomUserDetails;
import com.srm.coreframework.security.OAuth2AuthenticationUser;
import com.srm.coreframework.util.BeanUtil;
import com.srm.coreframework.util.DateUtil;
import com.srm.coreframework.util.IAuditLog;
import com.srm.coreframework.vo.AuditLogVO;

public class AuditLogInterceptor extends EmptyInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(AuditLogInterceptor.class);

	private static final String changedTo = " changed to ";
	private static final String addActivityType = "add";
	private static final String updateActivityType = "update";
	private static final Long addActivityId = 1L;
	private static final Long updateActivityId = 2L;
	private static final String deleteActivityType = "delete";
	private static final Long deleteActivityId = 3L;

	/*@Bean
	private EntityManager entityManager() {
		return BeanUtil.getBean(EntityManager.class);
	}*/

	/*@Bean
	private AuditMQSender auditMQSender() {
		return BeanUtil.getBean(AuditMQSender.class);
	}*/

	private Set<Object> inserts = new HashSet<Object>();
	private Set<Object> updates = new HashSet<Object>();
	private Set<Object> deletes = new HashSet<Object>();
	private Map oldies = new HashMap();
	private Map changedValueMap = new HashMap();

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
			throws CallbackException {
		if (entity instanceof IAuditLog)
			inserts.add(entity);
		return false;
	}

	@Override
	public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		if (entity instanceof IAuditLog)
			deletes.add((IAuditLog) entity);
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) throws CallbackException {

		if (entity instanceof IAuditLog) {
			updates.add((IAuditLog) entity);
		}

		if (entity instanceof IAuditLog && previousState != null && previousState.length > 0 && currentState != null
				&& currentState.length > 0) {
			for (int i = 0; i <= currentState.length - 1; i++) {
				if (currentState[i] == null && previousState[i] == null) {

				} else {
					if (currentState[i] instanceof Integer || currentState[i] instanceof Boolean
							|| currentState[i] instanceof LocalDateTime) {
						if (previousState[i] != null) {
							if (currentState[i] != previousState[i]) {
								StringBuffer changed = new StringBuffer();
								changed.append(previousState[i]).append(changedTo).append(currentState[i]);
								changedValueMap.put(propertyNames[i], changed.toString());
							}
						} else {
							StringBuffer changed = new StringBuffer();
							changed.append(previousState[i]).append(changedTo).append(currentState[i]);
							changedValueMap.put(propertyNames[i], changed.toString());
						}
					} else if (currentState[i] instanceof Long) {
						if (previousState[i] != null) {
							if (currentState[i].equals(previousState[i])) {
								StringBuffer changed = new StringBuffer();
								changed.append(previousState[i]).append(changedTo).append(currentState[i]);
								changedValueMap.put(propertyNames[i], changed.toString());
							}
						} else {
							StringBuffer changed = new StringBuffer();
							changed.append(previousState[i]).append(changedTo).append(currentState[i]);
							changedValueMap.put(propertyNames[i], changed.toString());
						}
					} else if (currentState[i] instanceof String) {
						if (previousState[i] != null) {
							if (!currentState[i].equals(previousState[i])) {
								StringBuffer changed = new StringBuffer();
								changed.append(previousState[i]).append(changedTo).append(currentState[i]);
								changedValueMap.put(propertyNames[i], changed.toString());
							}
						} else {
							StringBuffer changed = new StringBuffer();
							changed.append(previousState[i]).append(changedTo).append(currentState[i]);
							changedValueMap.put(propertyNames[i], changed.toString());
						}
					} else if (currentState[i] instanceof Object && previousState[i] instanceof Object) {

						if (!currentState[i].equals(previousState[i])) {
							Map currentMap = new HashMap<>();
							Map previousMap = new HashMap<>();

							String current = currentState[i].toString();
							current = current.replace("{", "").replace("}", "");
							LOG.info(current);
							String[] cureentArray = current.split(",");
							for (String str : cureentArray) {
								String[] nameAndValue = str.trim().split("=");
								if (nameAndValue.length == 1) {
									currentMap.put(nameAndValue[0], "");
								} else {
									currentMap.put(nameAndValue[0], nameAndValue[1]);
								}

							}

							String previous = previousState[i].toString();
							previous = previous.replace("{", "").replace("}", "");
							String[] previousArray = previous.split(",");
							for (String string : previousArray) {
								String[] nameValue = string.trim().split("=");
								if (nameValue.length == 1) {
									previousMap.put(nameValue[0], "");
								} else {
									previousMap.put(nameValue[0], nameValue[1]);
								}
							}
							Set keySet = currentMap.keySet();
							for (Object object : keySet) {
								if (!currentMap.get(object).equals(previousMap.get(object))) {
									StringBuffer changed = new StringBuffer();
									changed.append(previousMap.get(object)).append(changedTo)
											.append(currentMap.get(object));
									changedValueMap.put(object, changed.toString());
								}

							}

						}
					} else {
						StringBuffer changed = new StringBuffer();
						changed.append(previousState[i]).append(changedTo).append(currentState[i]);
						changedValueMap.put(propertyNames[i], changed.toString());
					}
					if (previousState[i] != null) {
						oldies.put(propertyNames[i], previousState[i]);
					}
				}
			}
		}
		return false;
	}

	@Override
	public void postFlush(Iterator iterator) throws CallbackException {
		ObjectMapper mapper = new ObjectMapper();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		String ip = request.getRemoteAddr();
		String screenId = request.getHeader("screen_id");
		String itemCode = request.getHeader("item_code");
		Object[] loggedUser = loggedInUser();
		try {
			for (Iterator it = inserts.iterator(); it.hasNext();) {
				IAuditLog entity = (IAuditLog) it.next();
				AuditLogVO audit = new AuditLogVO();
				audit.setScreenId(Long.valueOf(screenId));
				audit.setScreenName(entity.getClass().getSimpleName());
				audit.setActivityId(addActivityId);
				audit.setActivityType(addActivityType);
				audit.setKeyFieldId(entity.getEntityId());
				audit.setItemCode(itemCode);
				audit.setUserId((Long) loggedUser[0]);
				audit.setUserName((String) loggedUser[1]);
				audit.setIpAddress(ip);
				audit.setEventTime(DateUtil.convertLocalTimeToStr(LocalDateTime.now()));
				audit.setNewValue(mapper.convertValue(entity.toString(), JsonNode.class));
				LOG.info(" =========== Audit Log Value =====================>" + audit);
				//auditMQSender().sendAudit(audit);
			}
			for (Iterator it = updates.iterator(); it.hasNext();) {
				IAuditLog entity = (IAuditLog) it.next();
				AuditLogVO audit = new AuditLogVO();
				audit.setScreenId(Long.valueOf(screenId));
				audit.setScreenName(entity.getClass().getSimpleName());
				audit.setActivityId(updateActivityId);
				audit.setActivityType(updateActivityType);
				audit.setKeyFieldId(entity.getEntityId());
				audit.setItemCode(itemCode);
				audit.setUserId((Long) loggedUser[0]);
				audit.setUserName((String) loggedUser[1]);
				audit.setIpAddress(ip);
				audit.setEventTime(DateUtil.convertLocalTimeToStr(LocalDateTime.now()));
				audit.setOldValue(mapper.convertValue(oldies.toString(), JsonNode.class));
				audit.setNewValue(mapper.convertValue(entity.toString(), JsonNode.class));
				audit.setChangedValue(mapper.convertValue(changedValueMap.toString(), JsonNode.class));
				LOG.info(" =========== Audit Log Value =====================>" + audit);
				//auditMQSender().sendAudit(audit);
			}
			for (Iterator it = deletes.iterator(); it.hasNext();) {
				IAuditLog entity = (IAuditLog) it.next();
				AuditLogVO audit = new AuditLogVO();
				audit.setScreenId(Long.valueOf(screenId));
				audit.setScreenName(entity.getClass().getSimpleName());
				audit.setActivityId(deleteActivityId);
				audit.setActivityType(deleteActivityType);
				audit.setItemCode(itemCode);
				audit.setKeyFieldId(entity.getEntityId());
				audit.setUserId((Long) loggedUser[0]);
				audit.setUserName((String) loggedUser[1]);
				audit.setIpAddress(ip);
				audit.setEventTime(DateUtil.convertLocalTimeToStr(LocalDateTime.now()));
				audit.setOldValue(mapper.convertValue(entity.toString(), JsonNode.class));
				LOG.info(" =========== Audit Log Value =====================>" + audit);
				//auditMQSender().sendAudit(audit);
			}
		} finally {
			inserts.clear();
			updates.clear();
			deletes.clear();
			oldies.clear();
			changedValueMap.clear();
		}
	}

	public Object[] loggedInUser() {
		String loggedInUserName = null;
		int loggedInUserId =0;
		if ((!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken))) {
			OAuth2AuthenticationUser authentication = (OAuth2AuthenticationUser) SecurityContextHolder.getContext()
					.getAuthentication();
			if (authentication != null) {
				CustomUserDetails customUserDetails = authentication.getCustomUserDetails();
				if (customUserDetails != null) {
					loggedInUserName = customUserDetails.getUsername();
					loggedInUserId = customUserDetails.getUserId();
				} else {
					loggedInUserId = 0;
					loggedInUserName = null;
				}
			} else {
				loggedInUserId = 0;
				loggedInUserName = null;
			}
		} else {
			loggedInUserId = 0;
			loggedInUserName = null;
		}
		Object[] obj = new Object[2];
		obj[0] = loggedInUserId;
		obj[1] = loggedInUserName;
		return obj;
	}

}