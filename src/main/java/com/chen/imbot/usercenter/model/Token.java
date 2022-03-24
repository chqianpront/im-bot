package com.chen.imbot.usercenter.model;

import java.util.Date;

import lombok.Data;

@Data
public class Token {
	private int id;
	private int userId;
	private String token;
	private String refreshToken;
	private Date createdAt;
	private Date updatedAt;
}
