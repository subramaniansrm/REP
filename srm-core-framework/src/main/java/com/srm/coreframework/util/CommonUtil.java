package com.srm.coreframework.util;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.persistence.Query;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import com.srm.coreframework.auth.AuthUtil;
import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.exception.DAOException;
import com.srm.coreframework.response.CustomUserDetails;
import com.srm.coreframework.security.OAuth2AuthenticationUser;


/**
 * 
 *
 */
public class CommonUtil {

	
	private static final String DUPLICATE_CODE ="DUPLICATE_CODE";
	private static final String DUPLICATE_CODE_MULTIPLE ="DUPLICATE_CODE_MULTIPLE";
	/**
	 * 
	 * 
	 * @param orginalValue user input as String
	 * @param encodedValue logged user's encoded password fetch from database 
	 * @return true match found, return false match not  found  
	 */

	public static Boolean strEncodedValMatcher(String orginalValue, String encodedValue) {
		BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
		Boolean matchcheck = false;
		if (orginalValue != null && encodedValue != null) {
			matchcheck = pwEncoder.matches(orginalValue, encodedValue);
		}
		return matchcheck;
	}

	/**
	 * 
	 * @param newPassword
	 * @return
	 */
	public static String encodeString(String originalValue) {
		BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
		String encodedValue = null;
		if (originalValue != null) {
			encodedValue = pwEncoder.encode(originalValue);
		}
		return encodedValue;
	}
	
	public static String getCreateQuery(String query) throws DAOException {
		String[] queryParam = StringUtils.split(query, "where");
		query = queryParam != null ? queryParam[0].trim() : query;
		if (queryParam[1] != null) {
			String[] qParam = queryParam[1].trim().split("and");

			if (qParam != null) {
				for (int i = 0; i < qParam.length; i++) {
					if (qParam[i].contains("param")) {
						qParam = (String[]) ArrayUtils.remove(qParam, i);
					}
				}
				for (int i = 0; i < qParam.length; i++) {
					if (i == 0) {
						query = query + " where " + qParam[i].trim();
					} else {
						query = query + " and " + qParam[i].trim();
					}
				}
			}
		}
		
		return query;
	}
	
	public static Query getCreateQueryParam(String query, Object param1, Query query1) throws DAOException {
		
		if (param1 instanceof String) {

			String strParam = String.valueOf(param1);

			if (strParam != null) {
				boolean numeric = strParam.matches("\\d+");
				if (numeric) {
					Long longParam = Long.parseLong(strParam);
					if (longParam != null) {
						query1.setParameter("param1", longParam);
					}
				} else {
					query1.setParameter("param1", strParam);
				}
			}
		}
		return query1;
	}
	
	public static Query getCreateQueryMultiParam(String query, Object[] param1, Query query1) throws DAOException {
		
		for (int i = 0; i < param1.length; i++) {

			if (param1[i] instanceof String) {

				String strParam = String.valueOf(param1[i]);

				if (strParam != null) {
					boolean numeric = strParam.matches("\\d+");
					if (numeric) {
						Long longParam = Long.parseLong(strParam);
						if (longParam != null) {
							String q1 = "param" + (i + 1);
							query1.setParameter(q1, longParam);
						}
					} else {
						String qp = "param" + (i + 1);
						query1.setParameter(qp, strParam);
					}
				}
			}
		}
		
		return query1;
	}
	
	public static List<Long> currentRoleList(){
		CustomUserDetails customUserDetails = null;
		 List<Long> authorityList = new ArrayList<>();
		OAuth2AuthenticationUser authentication = (OAuth2AuthenticationUser) SecurityContextHolder.getContext()
				.getAuthentication();
		if (authentication != null) {
			customUserDetails = authentication.getCustomUserDetails();
			authorityList = new ArrayList<Long>();
			StringTokenizer st = new StringTokenizer(customUserDetails.getAuthorities().toString(), ",=[]{}");
			while (st != null && st.hasMoreTokens()) {
				String s2 = st.nextToken();
				if (s2 != null && !CommonConstant.AUTHORITY.equalsIgnoreCase(s2)
						&& !" ".equalsIgnoreCase(s2)) {
					authorityList.add(Long.valueOf(s2.trim()));
				}
			}
	}
		return authorityList;
	}
	
	public static String duplicateCode() {
		return ApiErrorCode.getAPIResponseCode(DUPLICATE_CODE);
	}
	
	public static String duplicateCodeParam() {
		return ApiErrorCode.getAPIResponseCode(DUPLICATE_CODE_MULTIPLE);
	}

	public static String encode(String value) {
		return Base64.getEncoder().encodeToString(value.getBytes());
	}
	
	public static String decode(String value) {
		return new String(Base64.getDecoder().decode(value));
	}
	
	public static String uuidGeneration() {
		
		UUID uuid = UUID.randomUUID();
			
		return uuid.toString();
	}
	
	/* Academic coding start */
	/**
	 * This method is used to set the parameter in the JPA query
	 * @param query - JPA Query object
	 * @param paramMap- input parameter map
	 */
	public static void setQueryParam(javax.persistence.Query query, Map<String, Object> paramMap) {
		for (String key : paramMap.keySet()) {
			query.setParameter(key, paramMap.get(key));
		}
	}
	
	/**
	 * This method is used to get the logged in user id
	 * @return logged in user id
	 */
	public static int loggedInUserId() {
		int loggedInUserId = 0;
		if ((!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken))) {
			OAuth2AuthenticationUser authentication = (OAuth2AuthenticationUser) SecurityContextHolder.getContext()
					.getAuthentication();
			if (authentication != null) {
				CustomUserDetails customUserDetails = authentication.getCustomUserDetails();
				if (customUserDetails != null) {
					loggedInUserId = customUserDetails.getUserId();
				} else {
					loggedInUserId = 0;
				}
			} else {
				loggedInUserId = 0;
			}
		} else {
			loggedInUserId = 0;
		}
		return loggedInUserId;
	}
	/* Academic coding end */
	
	
}
