package com.chen.imbot.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("state")
public class StateController {
	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String test() {
		return "test";
	}
}
