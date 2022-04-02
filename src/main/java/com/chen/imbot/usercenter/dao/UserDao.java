package com.chen.imbot.usercenter.dao;

import com.chen.imbot.usercenter.model.Token;
import com.chen.imbot.usercenter.model.User;

public interface UserDao {
	int userCount(User user);
	User findUserById(int userId);
	int register(User user);
	User findUserByAccName(String accountName);
	User findUserByPass(User user);
	int addToken(Token token);
	int updateToken(Token token);
	int userTokenCount(Token token);
}
