package com.chen.imbot.utils;

import lombok.Data;

@Data
public class BaseReturn {
	public final static int NOT_VALID_PARAM = 403;
	public final static int NOT_SIGNIN = 401;
	public final static int SIMPLE_ERROR = -1;
	public final static int SUCCESS = 0;
	
	private int code;
	private String msg;
}
