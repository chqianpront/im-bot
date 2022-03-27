package com.chen.imbot.usercenter.service;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chen.imbot.usercenter.dao.UserDao;
import com.chen.imbot.usercenter.model.Token;
import com.chen.imbot.usercenter.model.User;
import com.chen.imbot.utils.CryptUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public User findUserById(int userId) {
		return userDao.findUserById(userId);
	}
	public Object register(String accountName, String orgPass) {
		User user = new User();
		if (userDao.findUserByAccName(accountName) != null) {
			log.info("account name : {} is already exist", accountName);
			return null;
		}
		user.setAccountName(accountName);
		String encryptedPass = genPassword(orgPass);
		user.setEncryptedPassword(encryptedPass);
		return userDao.register(user);
	}
	public User login(Token token, User user) {
		log.info("token: {}, user: {}", token, user);
		User verifyUser = null;
		if (token != null) {
			verifyUser = verifyToken(token);
			return verifyUser;
		} else if (user != null) {
			verifyUser = verifyPass(user);
		} else {
			log.info("lack of param");
		}
		return verifyUser;
	}
	public Token genToken(User user) {
//		token gen method userId:random:expireTime
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.MONTH, 2);
		Date expireDate = calendar.getTime();
		StringBuilder sb = new StringBuilder();
		sb.append(user.getId());
		sb.append(":");
		Random r = new Random(400);
		sb.append(r.nextInt());
		sb.append(":");
		sb.append(expireDate.getTime());
		sb.append(":");
		String tokenStr = sb.toString();
		
		Token token = new Token();
		token.setUserId(user.getId());
		token.setToken(CryptUtil.encrypted(tokenStr));
		return token;
	}
	public User verifyToken(Token token) {
		if (token == null) return null;
		String tokenStr = CryptUtil.decrypted(token.getToken());
		String[] tArr = tokenStr.split(":");
		Date expireDate = new Date(Integer.valueOf(tArr[2]));
		if (expireDate.before(new Date())) {
			log.info("token :{} is expired", token);
			return null;
		}
		int userId = Integer.valueOf(tArr[0]);
		return findUserById(userId);
	}
	private User verifyPass(User user) {
		Date now = new Date();
		String encStr = genPassword(user.getEncryptedPassword());
		user.setEncryptedPassword(encStr);
		return userDao.findUserByPass(user);
	}
	private String genPassword(String orgPassword) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] d = md.digest(orgPassword.getBytes());
			return new String(d);
		} catch (Exception e) {
			log.error("error: {}", e.getMessage());
			return null;
		}
	}
}
