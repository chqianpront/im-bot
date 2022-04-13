package com.chen.imbot.basemodel.dto;

import lombok.Data;

@Data
public class ModelForSocket {
	public static int PUBLISH = 0;
	public static int SUBSCRIBE = 1;
	public static int UNSUBSCRIBE = 2;
	private int actionType;
	private Class klassName;
	private Object data;
}
