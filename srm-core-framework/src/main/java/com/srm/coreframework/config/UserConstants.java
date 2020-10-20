package com.srm.coreframework.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/properties/constants.properties")
public class UserConstants {		
	
	@Value("#{'${staticScreenIds}'.split(',')}") 
	private List<Integer> staticScreenIds;
		 
        
	/**
	 * @return the staticScreenIds
	 */
	public List<Integer> getStaticScreenIds() {
		return staticScreenIds;
	}

	/**
	 * @param staticScreenIds the staticScreenIds to set
	 */
	public void setStaticScreenIds(List<Integer> staticScreenIds) {
		this.staticScreenIds = staticScreenIds;
	}

}
