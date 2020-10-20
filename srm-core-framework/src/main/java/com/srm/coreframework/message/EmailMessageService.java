package com.srm.coreframework.message;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:config_mail_message.properties")
public class EmailMessageService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${http.proxySet}")
	private String proxySet;
	@Value("${http.proxyHost}")
	private String proxyHost;
	@Value("${http.proxyPort}")
	private String proxyPort;
	@Value("${spring.mail.from}")
	private String from;

	@Autowired
	private JavaMailSender javaMailSender;

	/**
	 * Normal text message - Example OTP
	 * 
	 * @param toMailId
	 * @param subject
	 * @param body
	 * @throws CoreException
	 */
	public void sendEmailMessage(String toMailId, String subject, String body) {

		Properties systemSettings = System.getProperties();
		systemSettings.put("proxySet", proxySet);
		systemSettings.put("http.proxyHost", proxyHost);
		systemSettings.put("http.proxyPort", proxyPort);
		try {
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setFrom(from);
			simpleMailMessage.setTo(toMailId);
			simpleMailMessage.setSubject(subject);
			simpleMailMessage.setText(body);
			javaMailSender.send(simpleMailMessage);
		} catch (Exception exception) {
			logger.error(exception.getMessage());
		}
	}

	/**
	 * Attach link in email body
	 * 
	 * @param toMailId
	 * @param subject
	 * @param body
	 * @throws MessagingException
	 * @throws CoreException
	 */
	public void sendHtmlMessage(String toMailId, String subject, String body) {

		Properties systemSettings = System.getProperties();
		systemSettings.put("proxySet", proxySet);
		systemSettings.put("http.proxyHost", proxyHost);
		systemSettings.put("http.proxyPort", proxyPort);
		try {

			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			mimeMessage.setSubject(subject);
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setTo(toMailId);
			helper.setText(body, true);

			javaMailSender.send(mimeMessage);
		/*} catch (MessagingException ex) {
			logger.error(ex.getMessage());*/
		} catch (Exception exception) {
			logger.error(exception.getMessage());
		}

	}

	public void sendHtmlMessages(String[] toMailId, String[] cc, String[] bcc, String subject, String body,
			List<?> attachments) {

		Properties systemSettings = System.getProperties();
		systemSettings.put("proxySet", proxySet);
		systemSettings.put("http.proxyHost", proxyHost);
		systemSettings.put("http.proxyPort", proxyPort);
		try {

			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			mimeMessage.setSubject(subject);
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setTo(toMailId);
			helper.setText(body, true);
			if (cc.length > 0) {
				helper.setCc(cc);
			}
			if (bcc.length > 0) {
				helper.setBcc(bcc);
			}

			if (!attachments.isEmpty() && attachments != null) {
				Iterator<?> it = attachments.iterator();

				while (it.hasNext()) {
					FileSystemResource file = new FileSystemResource(new File((String) it.next()));
					helper.addAttachment(file.getFilename(), file);
				}
			}
			javaMailSender.send(mimeMessage);
		/*} catch (MessagingException ex) {
			logger.error(ex.getMessage());*/
		} catch (Exception exception) {
			logger.error(exception.getMessage());
		}
	}
}
