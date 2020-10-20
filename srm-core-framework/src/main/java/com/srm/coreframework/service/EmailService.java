package com.srm.coreframework.service;

import java.io.IOException;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.config.MailMessages;
import com.srm.coreframework.controller.CommonController;
import com.srm.coreframework.vo.EmailVo;
import com.srm.coreframework.vo.LoginForm;

@Component
public class EmailService extends CommonController<LoginForm>{

	org.slf4j.Logger logger = LoggerFactory.getLogger(EmailService.class);

	@Autowired
	private AmqpTemplate rabbitTemplate;		
	
	@Value("${spring.mail.routingkey}")
	private String routingkey;

	@Value("${spring.mail.audit.exchange}")
	private String auditExchange;
	
	@Value("${spring.mail.audit.queue}")
	private String queue;	
	 
	/**
	 * Method is used to send a mail of forget password
	 * 
	 * @param generatedPassword
	 * @param emailId
	 * @return
	 * @throws CommonException
	 * @throws Exception
	 */
	public boolean sendEmail(EmailVo emailVo) throws CommonException, Exception {			 	
		 								
		ObjectMapper objectMapper = new ObjectMapper();
		String json;
		try {
			json = objectMapper.writeValueAsString(emailVo);

			rabbitTemplate.convertAndSend(auditExchange, routingkey, json);

			return true;
		} catch (JsonGenerationException | JsonMappingException e) {			 
			e.printStackTrace();
		} catch (IOException e) {			 
			e.printStackTrace();
		}

		return true;	 		
	}
}
