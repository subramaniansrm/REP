package com.srm.coreframework.i18n;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

@Service
public class CoreMessageSource {

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:locale/messages", "classpath:/framework_messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

	public String getMessage(String id) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource().getMessage(id, null, locale);
	}

	public String getMessage(String id, Object[] args) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource().getMessage(id, args, locale);
	}

}
