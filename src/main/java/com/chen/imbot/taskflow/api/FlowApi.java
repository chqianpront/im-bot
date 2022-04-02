package com.chen.imbot.taskflow.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chen.imbot.taskflow.model.Flow;
import com.chen.imbot.taskflow.service.TaskFlowService;
import com.chen.imbot.usercenter.model.User;
import com.chen.imbot.utils.BaseReturn;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/flow")
public class FlowApi {
	@Autowired
	private TaskFlowService taskFlowService;
	
	@RequestMapping(value = "createFlow", method = RequestMethod.POST)
	public Object createFlow(@RequestParam(required = false) User user, @RequestParam Map param) {
		log.info("user: {}, param: {}", user, param);
		BaseReturn ret = new BaseReturn();
		if (user == null) {
			log.info("no user info");
			ret.setCode(BaseReturn.NOT_SIGNIN);
			return ret;
		}
		Flow flow = new Flow();
		flow.setName((String) param.get("name"));
		flow.setCreatorId(user.getId());
		taskFlowService.createFlow(flow);
		ret.setCode(BaseReturn.SUCCESS);
		return ret;
	}
}
