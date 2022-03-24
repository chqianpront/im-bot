package com.chen.imbot.taskflow.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/flow")
public class Flow {
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list() {
		return "list";
	}
}
