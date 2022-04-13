package com.chen.imbot.aop;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.chen.imbot.usercenter.model.User;
import com.chen.imbot.usercenter.service.UserService;
import com.chen.imbot.utils.BaseReturn;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class ApiAop {

	public final static String HEADER_TASKFLOW_TOKEN_STR = "CHEN_TASKFLOW_TOKEN";
	@Autowired
	private UserService userService;
	@Pointcut("execution(public * com.chen.imbot..*.api..*Api..*(..))")
	public void NeedUserInfo() {}
	@Pointcut("!execution(public * com.chen.imbot.usercenter.api..*Api..*(..))")
	public void notNeedUserInfo() {}
	@Pointcut("NeedUserInfo() && notNeedUserInfo()")
	public void cutPoint() {}
	@Before("cutPoint()")
	public void cutLog() {
		log.info("<========== class: {}, before ==============>", ApiAop.class);
	}
	@Around("cutPoint()")
	public Object parseToken(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("taskflow aop around");
		String taskflowToken = "";
//		将token解析为user 信息
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		log.info("request url: {}", request.getRequestURL().toString());
//		get token from cookie
		String tokenStr = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName() == HEADER_TASKFLOW_TOKEN_STR) {
					tokenStr = cookie.getValue();
					break;
				}
			}
		}
//		get token from header
		if (!StringUtils.hasText(tokenStr)) {
			tokenStr = request.getHeader(HEADER_TASKFLOW_TOKEN_STR);
		}
//		get token from param
		if (!StringUtils.hasText(tokenStr)) {
			tokenStr = taskflowToken;
		}
		
		Object[] args = joinPoint.getArgs();
		User user = userService.verifyToken(tokenStr);
		if (args[0] == null) {
			args[0] = user;
		} else {
			log.error("not space set user");
		}
		if (user == null) {
			BaseReturn ret = new BaseReturn();
			log.info("no user info");
			ret.setCode(BaseReturn.NOT_SIGNIN);
			return ret;
		}
		return joinPoint.proceed(args);
	}
}
