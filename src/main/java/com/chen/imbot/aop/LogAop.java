package com.chen.imbot.aop;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class LogAop {
	@Pointcut("execution(public * com.chen.imbot..*.api..*..*(..))")
	public void pcut() {}
	@Before("pcut()")
	public void showPara() {

		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		String reqUrl = request.getRequestURL().toString();
		log.info("<==== req url : {}  =====>", reqUrl);
		Map reqParam = request.getParameterMap();
		StringBuilder sb = new StringBuilder();
		Set<Map.Entry> es = reqParam.entrySet();
		for (Map.Entry en : es) {
			sb.append(en.getKey());
			sb.append(": ");
			sb.append(en.getValue());
			sb.append(";");
		}
		log.info("<===== request parameter {} =====>", sb.toString());
	}
}
