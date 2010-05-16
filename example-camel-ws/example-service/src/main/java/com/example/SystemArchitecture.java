package com.example;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Captures common pointcut definitions
 */
@Aspect
public class SystemArchitecture {
	
	/**
	 * A join point is a component if it is annotated with @Component
	 */
	@Pointcut("execution(public * @org.springframework.stereotype.Component *.*(..))")
	public void component(){}
	
	/**
	 * A join point is a repository if it is annotated with @Repository
	 */
	@Pointcut("execution(public * @org.springframework.stereotype.Repository *.*(..))")
	public void repository(){}

	/**
	 * A join point is a controller if it is annotated with @Controller
	 */
	@Pointcut("execution(public * @org.springframework.stereotype.Controller *.*(..))")
	public void controller(){}

	/**
	 * A join point is a service if it is annotated with @Service
	 */
	@Pointcut("execution(public * @org.springframework.stereotype.Service *.*(..))")
	public void service(){}

	/**
	 * A join point is a web service if it is annotated with @WebService
	 */
	@Pointcut("execution(public * @javax.jws.WebService *.*(..))")
	public void webService(){}

}
