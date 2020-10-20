package com.srm.coreframework.config;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.srm.coreframework.auth.AuthUserInfo;


public class TokenManager extends TimerTask {

	public static Map<String, AuthUserInfo> tokenMap = new LinkedHashMap<String, AuthUserInfo>();
	public static Map<String, String> threadTokenMap = new LinkedHashMap<String, String>();
	
	public static AuthUserInfo getAuthUserInfo() {
		/*String token = threadTokenMap.get("auth");
		if (token != null) {
			return tokenMap.get(token);
			//return tokenMap.get(token);
		}*/
		return  tokenMap.get("auth");
	}
	
	
	
	
	public static boolean isTokenAvailable(String token) {
		return token!=null && tokenMap.containsKey(token);
	}
	
	public static void setToken(String token, AuthUserInfo authUserInfo) {
		tokenMap.put(token, authUserInfo);
	}
	
	public static void updateTokenTime(String token) {
	
	}
	
	static {
		Timer time = new Timer();
		TokenManager tm = new TokenManager();
		// every 1 min
		time.schedule(tm, 0, 1*60000);
	}
	
	@Override
	public void run() {
		Set<String> keySet = tokenMap.keySet();
		Date currentDate = new Date();
		for (String token : keySet) {
			
	}
	}
}
