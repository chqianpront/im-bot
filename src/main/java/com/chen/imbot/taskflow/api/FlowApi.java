package com.chen.imbot.taskflow.api;

import java.util.List;
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
import com.chen.imbot.utils.DataReturn;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/flow")
public class FlowApi {
	public final static int DEFAULT_PAGE_SIZE = 10;
	@Autowired
	private TaskFlowService taskFlowService;
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public Object createFlow(@RequestParam(required = false) User user, @RequestParam Map param) {
		log.info("user: {}, param: {}", user, param);
		DataReturn ret = new DataReturn();
		if (param.get("teamId") == null) {
			ret.setCode(BaseReturn.NOT_VALID_PARAM);
			return ret;
		}
		int teamId = Integer.parseInt((String) param.get("teamId"));
		
		Flow flow = new Flow();
		flow.setName((String) param.get("name"));
		flow.setTeamId(teamId);
		flow.setCreatorId(user.getId());
		taskFlowService.createFlow(flow);
		ret.setCode(BaseReturn.SUCCESS);
		ret.setData(flow);
		return ret;
	}
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public Object updateFlow(@RequestParam(required = false) User user, @RequestParam Map param) {
		DataReturn ret = new DataReturn();
		return ret;
	}
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public Object list(@RequestParam(required = false) User user, @RequestParam Map param) {
		List<Flow> list = taskFlowService.listFlow(param);
		DataReturn ret = new DataReturn();
		ret.setCode(BaseReturn.SUCCESS);
		ret.setData(list);
		return ret;
	}
}
