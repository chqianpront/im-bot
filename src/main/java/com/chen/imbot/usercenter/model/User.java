package com.chen.imbot.usercenter.model;

import java.util.Date;

import lombok.Data;

@Data
public class User {
	private int id;
	private String name;
	private String accountName;
	private String encryptedPassword;
	private Date createdAt;
	private Date updatedAt;
}
