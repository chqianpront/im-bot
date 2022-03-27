package com.chen.imbot.usercenter.dao;

import com.chen.imbot.usercenter.model.User;

public interface UserDao {
	int userCount(User user);
	User findUserById(int userId);
	int register(User user);
	User findUserByAccName(String accountName);
	User findUserByPass(User user);
}
