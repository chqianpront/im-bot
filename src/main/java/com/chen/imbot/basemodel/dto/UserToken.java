package com.chen.imbot.basemodel.dto;

import com.chen.imbot.usercenter.model.User;

import com.chen.imbot.usercenter.model.Token;

import lombok.Data;

@Data
public class UserToken {
	private Token token;
	private User user;
}
