package com.chen.imbot.usercenter.service;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.util.ByteUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.chen.imbot.basemodel.dto.UserToken;
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
	public UserToken login(Token token, User user) {
		log.info("token: {}, user: {}", token, user);
		Token verifyToken = new Token();
		User verifyUser = null;
		UserToken userToken = null;
		if (token != null) {
			verifyUser = verifyToken(token.getToken());
		} else if (user != null) {
			verifyUser = verifyPass(user);
		} else {
			log.info("lack of param");
		}
		if (verifyUser != null) {
			userToken = new UserToken();
			Token loginToken = genToken(verifyUser);
			createOrUpdateToken(loginToken);
			userToken.setToken(loginToken);
			userToken.setUser(verifyUser);
		}
		return userToken;
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
	public User verifyToken(String token) {
		if (token == null) return null;
		String tokenStr = CryptUtil.decrypted(token);
		if (tokenStr == null) return null;
		String[] tArr = tokenStr.split(":");
		Date expireDate = new Date(Long.parseLong(tArr[2]));
		if (expireDate.before(new Date())) {
			log.info("token :{} is expired", token);
			return null;
		}
		int userId = Integer.valueOf(tArr[0]);
		return findUserById(userId);
	}
	public boolean isLogin(User user) {
		String token = userDao.userToken(user.getId());
		User u = verifyToken(token);
		return u.getId() == user.getId();
	}
	private User verifyPass(User user) {
		Date now = new Date();
		String encStr = genPassword(user.getEncryptedPassword());
		user.setEncryptedPassword(encStr);
		return userDao.findUserByPass(user);
	}
	private String genPassword(String orgPassword) {
		try {
			return DigestUtils.md5DigestAsHex(orgPassword.getBytes("utf-8"));
		} catch (Exception e) {
			log.error("error: {}", e.getMessage());
			return null;
		}
	}
	private void createOrUpdateToken(Token token) {
		int tokenExitst = userDao.userTokenCount(token);
		if (tokenExitst > 0) {
			userDao.updateToken(token);
		} else {
			userDao.addToken(token);
		}
	}
}
