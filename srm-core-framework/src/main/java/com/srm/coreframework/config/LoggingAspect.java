package com.srm.coreframework.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

@Aspect
@EnableAspectJAutoProxy
@Component
public class LoggingAspect {

	final Logger slf4jLogger=LoggerFactory.getLogger(LoggingAspect.class);
		
	/**
	 * Declaring named pointcut
	 */
	@Pointcut("execution(* com.srm.*.*.*.*(..))")
	public void logForAllControllerMethodsBefore() {		
	}	

	@Before("logForAllControllerMethodsBefore()") // applying pointcut on before advice
	public void logBefore(JoinPoint joinPoint) throws JsonProcessingException// it is advice (before advice)
	{
		slf4jLogger.info("Entering in Class Name: " + joinPoint.getSignature().getDeclaringTypeName());
		slf4jLogger.info("Entering in Method :  " + joinPoint.getSignature().getName());
	}	
}
