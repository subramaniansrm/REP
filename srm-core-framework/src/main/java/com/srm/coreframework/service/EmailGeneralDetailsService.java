/**
 * 
 */
package com.srm.coreframework.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.controller.CommonController;
import com.srm.coreframework.dao.EmailGeneralDetailsDao;
import com.srm.coreframework.entity.EmailGeneralDetailsEntity;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.EmailVo;


 
@Service
public class EmailGeneralDetailsService  extends CommonController<EmailVo> {

	private static final Logger logger = LoggerFactory.getLogger(EmailGeneralDetailsService.class);
	
	@Autowired
	EmailGeneralDetailsDao  emailGeneralDetailsDao;

	public  EmailGeneralDetailsEntity getEmailGeneralDetails(EmailVo emailVo, AuthDetailsVo authDetailsVo){
		EmailGeneralDetailsEntity emailGeneralDetailsEntity = new EmailGeneralDetailsEntity();
		try {
			emailGeneralDetailsEntity	= emailGeneralDetailsDao.getEmailGeneralDetails(emailVo, authDetailsVo);
			 
		} catch (Exception exception) {
			 
			throw new CommonException(getMessage("dataFailure", authDetailsVo));
		}

		return emailGeneralDetailsEntity;
	}
	
	public void getEmailGeneralDetails(EmailVo emailVo, EmailGeneralDetailsEntity emailGeneralDetailsEntity) {

		if (null != emailVo.getUserLang() && emailVo.getUserLang().equals(CommonConstant.en)) {
			if (null != emailGeneralDetailsEntity.getTitle()) {
				emailVo.setTitle(emailGeneralDetailsEntity.getTitle());
			}

			if (null != emailGeneralDetailsEntity.getMessageContent()) {
				emailVo.setMessageContent(emailGeneralDetailsEntity.getMessageContent());
			}

		} else {

			if (null != emailGeneralDetailsEntity.getTitleJp()) {
				emailVo.setTitle(emailGeneralDetailsEntity.getTitleJp());
			}

			if (null != emailGeneralDetailsEntity.getMessageContentJp()) {
				emailVo.setMessageContent(emailGeneralDetailsEntity.getMessageContentJp());
			}
		}

	} 

	public EmailGeneralDetailsEntity getEmailGeneralDet(EmailVo emailVo, AuthDetailsVo authDetailsVo) {
		List<Object[]> resultobj = new ArrayList<Object[]>();
		EmailGeneralDetailsEntity emailGeneralDetailsEntity = new EmailGeneralDetailsEntity();
		try {
			resultobj = emailGeneralDetailsDao.getEmailGeneralDet(emailVo, authDetailsVo);

			for (Object user : resultobj) {

				if (null != (String) ((Object[]) user)[0]) {
					emailGeneralDetailsEntity.setTitle((String) ((Object[]) user)[0]);
				}

				if (null != (String) ((Object[]) user)[1]) {
					emailGeneralDetailsEntity.setTitleJp((String) ((Object[]) user)[1]);
				}

				if (null != (String) ((Object[]) user)[2]) {
					emailGeneralDetailsEntity.setMessageContent((String) ((Object[]) user)[2]);
				}

				if (null != (String) ((Object[]) user)[3]) {
					emailGeneralDetailsEntity.setMessageContentJp((String) ((Object[]) user)[3]);
				}

			}

		} catch (Exception exception) {

			throw new CommonException(getMessage("dataFailure", authDetailsVo));
		}

		return emailGeneralDetailsEntity;
	}
	
}
