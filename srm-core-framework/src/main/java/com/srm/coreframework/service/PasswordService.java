package com.srm.coreframework.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.transaction.Transactional;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.srm.coreframework.config.CommonException;
import com.srm.coreframework.config.MailMessages;
import com.srm.coreframework.controller.CommonController;
import com.srm.coreframework.dao.PasswordDAO;
import com.srm.coreframework.entity.EmailGeneralDetailsEntity;
import com.srm.coreframework.entity.UserEntity;
import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;
import com.srm.coreframework.vo.EmailVo;
import com.srm.coreframework.vo.LoginForm;
import com.srm.coreframework.vo.UserMasterVO;

@Component
public class PasswordService extends CommonController<LoginForm>{

	org.slf4j.Logger logger = LoggerFactory.getLogger(PasswordService.class);

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private MailMessages mailMessages;

	@Autowired
	PasswordDAO passwordDAO;
	
	@Autowired
	EmailGeneralDetailsService emailGeneralDetailsService;
		
	@Transactional
	public UserMasterVO getLogin(String userName) throws IllegalAccessException, InvocationTargetException {
		return passwordDAO.getLogin(userName);
	}

	/**
	 * This method is to used to check old password.
	 * 
	 * @param changePasswordRequest
	 * @return LoginForm
	 */
	public void getChangePassword(LoginForm changePasswordRequest,AuthDetailsVo authDetailsVo) throws CommonException  {
		String newPassword = null;
		String newPasswordValidate = null;
		try {
		// Empty check of old password , new password , confirm password
		if ((null != changePasswordRequest.getOldPassword() && !changePasswordRequest.getOldPassword().equals(""))) {
			if ((null != changePasswordRequest.getNewPassword()
					&& !changePasswordRequest.getNewPassword().equals(""))) {
				if ((null != changePasswordRequest.getConfirmPassword()
						&& !changePasswordRequest.getConfirmPassword().equals(""))) {

					// Check the new password and confirm password same or not
					if (changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {

						Object[] userEntity = null;

						// Get the user details of login user id to verify the
						// old
						// password match check
							boolean status = false;

							userEntity = passwordDAO.getOldPassword(changePasswordRequest);

							if (userEntity != null) {

								BCryptPasswordEncoder Oencoder = new BCryptPasswordEncoder();

							if (Oencoder.matches(changePasswordRequest.getOldPassword(),(String) userEntity[0])) {
								
									// New password encryption
									BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
									newPassword = passwordEncoder.encode(changePasswordRequest.getNewPassword());
									newPasswordValidate = changePasswordRequest.getNewPassword();

									changePasswordRequest.setNewPassword(newPassword);

									// Password Validation
									passwordLicenseValidation(newPasswordValidate, authDetailsVo);

									// Update the password in Data base

								status = passwordDAO.changePassword(changePasswordRequest);
								 
								if (status == false) {
									throw new CommonException(getMessage("dbFailure",authDetailsVo));
								}

							} else {
								throw new CommonException(getMessage("oldPasswordMismatch",authDetailsVo));
							}

						} else {
							throw new CommonException(getMessage("dbFailure",authDetailsVo));
						}
					} else {
						throw new CommonException(getMessage("passwordNotSame",authDetailsVo));
					}
				}

				else {
					throw new CommonException(getMessage("confirmPassworManditory",authDetailsVo));
				}
			} else {
				throw new CommonException(getMessage("newPassworManditory",authDetailsVo));
			}
		} else {
			throw new CommonException(getMessage("oldPassworManditory",authDetailsVo));
		}

		createChangePasswordMail(changePasswordRequest, newPasswordValidate,authDetailsVo);
				
		} catch (NoResultException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage(e.getMessage(),authDetailsVo));
		} catch (NonUniqueResultException e) {
			logger.error(e.getMessage());
			throw new CommonException(getMessage("noUniqueFound",authDetailsVo));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new CommonException(getMessage(e.getMessage(),authDetailsVo));
		}
	}

	public void checkUserExist(String loginId) throws CommonException, Exception {

		int count = passwordDAO.forgotPassword(loginId);

		if(count == 0){
			throw new CommonException("userNotExist");
		}

	}

	
	public void createChangePasswordMail(LoginForm changePasswordRequest, String newPassword ,AuthDetailsVo authDetailsVo)
			throws CommonException, Exception {

		// New password mail send to user
		EmailVo emailVo = new EmailVo();
		emailVo.setSystemConfigurationVo(getSystemConfigurationDetails(authDetailsVo));

		UserMasterVO userMasterVO1 = getEmailAddress(changePasswordRequest.getUserId(), authDetailsVo);
		emailVo.setToUserAddress(userMasterVO1.getEmailId());

		emailVo.setSystemConfigurationVo(getSystemConfigurationDetails(authDetailsVo)); 
		
		
		AuthDetailsVo authDetVo = new AuthDetailsVo();
		authDetVo.setEntityId(authDetailsVo.getEntityId());
		emailVo.setSystemConfigurationVo(getSystemConfigurationDetails(authDetVo));
		emailVo.setTrEmailFlag(CommonConstant.CONSTANT_ONE);

		if (null != authDetailsVo.getLangCode() && authDetailsVo.getLangCode().equals(CommonConstant.en)) {
			authDetVo.setLangCode(CommonConstant.en);
		} else {
			authDetVo.setLangCode(CommonConstant.jp);
		}
		emailVo.setUserLang(authDetailsVo.getLangCode());

		// get title and message from db
		emailVo.setGroupId(CommonConstant.CPD);
		EmailGeneralDetailsEntity emailGeneralDetailsEntity = emailGeneralDetailsService.getEmailGeneralDetails(emailVo,
				authDetailsVo);
		emailGeneralDetailsService.getEmailGeneralDetails(emailVo, emailGeneralDetailsEntity);
		 
		emailVo.setMessageContent(getMessage("dear", authDetVo)+ " " +authDetailsVo.getFirstName() +","
			              + "\n" + emailVo.getMessageContent() + " : "  + newPassword 			 
			              +" \n \n \n" + getMessage("bestRegards",authDetailsVo)  
			              +" \n" + getMessage("supportTeam",authDetailsVo));
		 
		if (null != emailVo.getSystemConfigurationVo()&& emailVo.getSystemConfigurationVo().size() > 0) {
		
			emailService.sendEmail(emailVo);
		}
	}
		
	/**
	 * This method is to used for forgot password.
	 * 
	 * @param forgotPasswordRequest
	 * @return LoginForm
	 * @throws Exception
	 */
	public void forgotPassword(LoginForm forgotPasswordRequest) throws CommonException {

		Object[] userEntity = null;

		boolean status = false;

		String genPassword = null;

		// To get the user details
		try {

			userEntity = passwordDAO.forgotPassword(forgotPasswordRequest);
		
			if (userEntity != null) {

				String hashedPassword = null;
				// New password generation
				genPassword = passwordGenerator();

				// New password mail send to user
				EmailVo emailVo = new EmailVo();
				emailVo.setPassword(genPassword);
				emailVo.setToUserAddress((String) userEntity[1]);

				//get Entty id of username
				
				UserEntity user = getUser(forgotPasswordRequest.getUserLoginId());
								
				AuthDetailsVo authDetVo = new AuthDetailsVo();
				authDetVo.setEntityId(user.getEntityLicenseEntity().getId());
				emailVo.setSystemConfigurationVo(getSystemConfigurationDetails(authDetVo));
				emailVo.setTrEmailFlag(CommonConstant.CONSTANT_ONE);

				if (null != user.getLangCode() && user.getLangCode().equals(CommonConstant.en)) {
					authDetVo.setLangCode(CommonConstant.en);
				} else {
					authDetVo.setLangCode(CommonConstant.jp);
				}
				emailVo.setUserLang(user.getLangCode());

				// get title and message from db
				emailVo.setGroupId(CommonConstant.FPD);
				EmailGeneralDetailsEntity emailGeneralDetailsEntity = emailGeneralDetailsService.getEmailGeneralDetails(emailVo,
						authDetVo);
				emailGeneralDetailsService.getEmailGeneralDetails(emailVo, emailGeneralDetailsEntity);
				 
				emailVo.setMessageContent(getMessage("dear", authDetVo)+ " "+user.getFirstName() + ","
					              + "\n" + emailVo.getMessageContent() + " : "
					              + genPassword + "\n" + "\n" 
					              +" \n \n \n" + getMessage("bestRegards",authDetVo)  
					              +" \n" + getMessage("supportTeam",authDetVo));
				
				if (null != emailVo.getSystemConfigurationVo() && emailVo.getSystemConfigurationVo().size() > 0) {
					emailService.sendEmail(emailVo);
				}

				// Encrypt the new password
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				hashedPassword = passwordEncoder.encode(genPassword);
				// encryptedPassword = CodeSecurity.encrypt(genPassword);

				forgotPasswordRequest.setNewPassword(hashedPassword);
				forgotPasswordRequest.setUserId((int) userEntity[0]);

				// Update the password in Data base
				status = passwordDAO.changePassword(forgotPasswordRequest);

				if (status == false) {
					throw new CommonException("dataFailure");
				}
			}
		} catch (NoResultException e) {
			logger.error(e.getMessage());
			throw new CommonException("userNotExist");
		} catch (NonUniqueResultException e) {
			logger.error(e.getMessage());
			throw new CommonException("noUniqueFound");
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException("enterValidDetails");
		}
	}

	/**
	 * This method is to used for password generation.
	 * 
	 * @return String
	 */
	public String passwordGenerator() throws CommonException {
		char[] password = null;
		try {
			String values = CommonConstant.CAPITAL_CHARS + CommonConstant.SMALL_CHARS + CommonConstant.NUMBERS
					+ CommonConstant.SYMBOLS;
			Random rndm_method = new Random();
			password = new char[CommonConstant.NEW_PASSWORD_LENGTH];

			for (int i = 0; i < CommonConstant.NEW_PASSWORD_LENGTH; i++) {
				password[i] = values.charAt(rndm_method.nextInt(values.length()));
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException("passwordGenerationFailed");
		}
		return password.toString();
	}

	/**
	 * Method is used to send a mail of forget password
	 * 
	 * @param generatedPassword
	 * @param emailId
	 * @return
	 * @throws CommonException
	 * @throws Exception
	 */
/*	public LoginForm forgotpasswordMail(String generatedPassword, String emailId) throws CommonException, Exception {

		LoginForm loginForm = new LoginForm();
		Properties props = new Properties();
		props.put("mail.smtp.host", mailMessages.getSmtpHost());
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");

		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		props.put("mail.smtp.ssl.socketFactory", sf);
		Authenticator auth = new SMTPAuthenticator();
		// Session mailSession = Session.getInstance(props, auth);
		Session session = Session.getInstance(props, auth);

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(mailMessages.getFromMailAddress()));
		if (null != emailId) {
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailId));
		}
		InternetAddress emailAddr = new InternetAddress(emailId);
		try {
			emailAddr.validate();
		} catch (AddressException ex) {
			throw new CommonException("Invalid email Address");
			// ex.printStackTrace();
		}
		message.setSubject("Changed Password");
		message.setText(" Password generated is " + generatedPassword);
		message.saveChanges();

		Transport.send(message);

		return loginForm;
	}

	private class SMTPAuthenticator extends Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(mailMessages.getSmtpUserName(), mailMessages.getSmtpPassword());
		}
	}
*/
}
