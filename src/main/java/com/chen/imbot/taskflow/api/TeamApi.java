package com.chen.imbot.taskflow.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chen.imbot.taskflow.model.Team;
import com.chen.imbot.taskflow.service.TaskFlowService;
import com.chen.imbot.usercenter.model.User;
import com.chen.imbot.utils.DataReturn;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/team")
public class TeamApi {
	@Autowired
	private TaskFlowService taskFlowService;
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public Object createTeam(@RequestParam(required = false) User user, @RequestParam Map param) {
		log.info("param: {}", param);
		DataReturn ret = new DataReturn();
		Team team = new Team();
		team.setName((String) param.get("name"));
		team.setCreatorId(user.getId());
		taskFlowService.createTeam(team);
		return ret;
	}
}
