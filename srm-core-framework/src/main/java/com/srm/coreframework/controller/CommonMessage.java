package com.srm.coreframework.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.srm.coreframework.util.CommonConstant;
import com.srm.coreframework.vo.AuthDetailsVo;


public class CommonMessage {

	@Autowired
	private MessageSource messageSource;
	
	public String getMessage(String code,AuthDetailsVo authDetailsVo) {
		return getMessage(code, new Object[] {},authDetailsVo);
	}

	public String getMessage(String code, Object args[],AuthDetailsVo authDetailsVo) {
		String langCode = authDetailsVo.getLangCode();
		Locale locale = null;
		if (CommonConstant.LANG_CODE_JP.equalsIgnoreCase(langCode)) {
			locale = Locale.JAPANESE;
		} else if (CommonConstant.LANG_CODE_EN.equalsIgnoreCase(langCode)) {
			locale = Locale.ENGLISH;
		} else {
			locale = Locale.ENGLISH;
		}
		return messageSource.getMessage(code, args, locale);
	}

	public String getMessageBylangCode(String code, String langCode) {
		return getMessageBylangCode(code, langCode, new Object[] {});
	}

	public String getMessageBylangCode(String code, String langCode, Object args[]) {
		Locale locale = null;
		if (CommonConstant.LANG_CODE_JP.equalsIgnoreCase(langCode)) {
			locale = Locale.JAPANESE;
		} else if (CommonConstant.LANG_CODE_EN.equalsIgnoreCase(langCode)) {
			locale = Locale.ENGLISH;
		} else {
			locale = Locale.ENGLISH;
		}
		return messageSource.getMessage(code, args, locale);
	}
}
