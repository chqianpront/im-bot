package com.chen.imbot.taskflow.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chen.imbot.taskflow.model.Task;
import com.chen.imbot.taskflow.service.TaskFlowService;
import com.chen.imbot.usercenter.model.User;
import com.chen.imbot.utils.BaseReturn;
import com.chen.imbot.utils.DataReturn;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/task")
public class TaskApi {
	@Autowired
	private TaskFlowService taskFlowService;
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public Object createTask(@RequestParam(required = false) User user, @RequestParam Map param) {
		DataReturn ret = new DataReturn();
		log.info("param: {}", param);
		if (param.get("flowId") == null) {
			ret.setCode(BaseReturn.NOT_VALID_PARAM);
			return ret;
		}
		int flowId = Integer.parseInt((String) param.get("flowId"));
		Task task = new Task();
		task.setName((String) param.get("name"));
		task.setFlowId(flowId);
		task.setCreatorId(user.getId());
		taskFlowService.createTask(task);
		ret.setCode(BaseReturn.SUCCESS);
		ret.setData(task);
		
		return ret;
	}
}
