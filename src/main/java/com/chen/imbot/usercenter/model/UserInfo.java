package com.chen.imbot.usercenter.model;

import java.util.Date;

import lombok.Data;

@Data
public class UserInfo {
	private int id;
	private int userId;
	private String email;
	private String name;
	private int gender;
	private Date birthday;
	private String place;
	private Date createdAt;
	private Date updatedAt;
}
