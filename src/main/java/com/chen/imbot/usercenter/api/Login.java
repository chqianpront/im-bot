package com.chen.imbot.usercenter.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chen.imbot.basemodel.dto.UserToken;
import com.chen.imbot.usercenter.model.User;
import com.chen.imbot.usercenter.service.UserService;
import com.chen.imbot.utils.BaseReturn;
import com.chen.imbot.utils.UserReturn;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/uc")
public class Login {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public BaseReturn login(@RequestParam Map<String, String> req) {
		String accountName = req.get("accountName");
		String orgPassword = req.get("password");
		User user = new User();
		user.setAccountName(accountName);
		user.setEncryptedPassword(orgPassword);
		UserToken verify = userService.login(null, user);
		
		UserReturn ret = new UserReturn();
		if (verify != null) {
			ret.setCode(0);
			ret.setUser(verify.getUser());
			ret.setToken(verify.getToken());
		} else {
			ret.setCode(-1);
			ret.setMsg("user not found");
		}
		return ret;
	}
	@RequestMapping(value = "registry", method = RequestMethod.POST)
	public BaseReturn registery(@RequestParam String accountName, @RequestParam String password) {
		Object regRet = userService.register(accountName, password);
		UserReturn ret = new UserReturn();
		if (regRet != null && regRet.getClass().equals(User.class)) {
			ret.setCode(0);
			ret.setUser((User) regRet);
		} else {
			log.info("acc create fail");
			ret.setCode(-1);
			ret.setMsg("create fail");
		}
		return ret;
	}
}
