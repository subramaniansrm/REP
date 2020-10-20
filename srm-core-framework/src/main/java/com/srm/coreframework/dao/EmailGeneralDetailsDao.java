package com.srm.coreframework.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.srm.coreframework.config.UserMessages;
import com.srm.coreframework.entity.EmailGeneralDetailsEntity;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.EmailVo;

@Component
public class EmailGeneralDetailsDao extends CommonDAO{
	
	@Autowired
	UserMessages userMessages;
			
	@SuppressWarnings("unchecked")
	public EmailGeneralDetailsEntity getEmailGeneralDetails(EmailVo emailVo, AuthDetailsVo authDetailsVo) {
		EmailGeneralDetailsEntity emailGeneralDetailsEntity = new EmailGeneralDetailsEntity();
		
		try{
		String query = "FROM EmailGeneralDetailsEntity c WHERE c.groupId = '" + emailVo.getGroupId() + "' " ;				 

		List<EmailGeneralDetailsEntity> list = (List<EmailGeneralDetailsEntity>) getEntityManager().createQuery(query)
				.getResultList();

		if (list.size() > 0) {
			emailGeneralDetailsEntity = list.get(0);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return emailGeneralDetailsEntity;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getEmailGeneralDet(EmailVo emailVo, AuthDetailsVo authDetailsVo) {
		 
		List<Object[]> resultobject = new ArrayList<Object[]>();
		try{
		String query = " SELECT title, title_jp, message_content, message_content_jp from "+getCommonDatabaseSchema()+".email_general_details WHERE group_id = '" + emailVo.getGroupId() + "' " ;				 

		  resultobject = (List<Object[]>) getEntityManager().createNativeQuery(query).getResultList();

	 
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultobject;
	}
	
	
}