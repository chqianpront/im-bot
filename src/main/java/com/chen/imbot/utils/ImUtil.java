package com.chen.imbot.utils;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImUtil {

	public static Object setCreatedUpdatedTime(Object obj) {
		Date now = new Date();
		try {
			obj.getClass().getMethod("setCreatedAt", new Class[] { Date.class }).invoke(obj, new Object[] { now });
			obj.getClass().getMethod("setUpdatedAt", new Class[] { Date.class }).invoke(obj, new Object[] { now });
		} catch (Exception e) {
			log.error("error: {}", e.getMessage());
		}
		return obj;
	}
}
