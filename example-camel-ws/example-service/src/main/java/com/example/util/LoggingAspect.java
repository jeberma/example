package com.example.util;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggingAspect {

	private static final Log log = LogFactory.getLog("example-service");

	@Pointcut(
			"com.example.SystemArchitecture.component() || " +
			"com.example.SystemArchitecture.repository() || " +
			"com.example.SystemArchitecture.controller() || " +
			"com.example.SystemArchitecture.service() || " + 
			"com.example.SystemArchitecture.webService()"
	)
	protected void allLayers() {}

	@AfterThrowing(pointcut="allLayers()", throwing="t")
	public void logError(JoinPoint jp, Throwable t) {
		if(!log.isErrorEnabled()) {
			return;
		}
		String msg = new StringBuilder()
							.append(formatSignature(jp))
							.append(": ")
							.toString();
		log.error(msg, t);
	}

	@Before("allLayers()")
	public void logEntering(JoinPoint jp) {
		if(!log.isDebugEnabled()) {
			return;
		}
		String msg = new StringBuilder()
							.append("Entering ")
							.append(formatSignature(jp))
							.append(": ")
							.append(Arrays.toString(jp.getArgs()))
							.toString();
		log.debug(msg);
	}

	@AfterReturning(pointcut="allLayers()", returning="result")
	public void logExiting(JoinPoint jp, Object result) {
		if(!log.isDebugEnabled()) {
			return;
		}
		String msg = new StringBuilder()
							.append("Exiting ")
							.append(formatSignature(jp))
							.append(": ")
							.append(result)
							.toString();
		log.debug(msg);
	}

	private String formatSignature(JoinPoint jp) {
		if(jp.getTarget() == null || jp.getTarget().getClass() == null) {
			return jp.getSignature() == null ? "" : jp.getSignature().toLongString();
		}
		return new StringBuilder()
					.append(jp.getSignature().toShortString())
					.toString();
	}
}
