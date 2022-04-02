package com.chen.imbot.utils;

import com.chen.imbot.usercenter.model.Token;
import com.chen.imbot.usercenter.model.User;

import lombok.Data;

@Data
public class UserReturn extends BaseReturn {
	private User user;
	private Token token;
}
