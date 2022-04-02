package com.chen.imbot.taskflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chen.imbot.taskflow.dao.TaskFlowDao;
import com.chen.imbot.taskflow.model.Flow;
import com.chen.imbot.taskflow.model.Task;
import com.chen.imbot.taskflow.model.TaskGroup;
import com.chen.imbot.taskflow.model.Team;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskFlowService {
	@Autowired
	private TaskFlowDao taskFlowDao;
	public Task createTask(Task task) {
		taskFlowDao.createTask(task);
		return task;
	}
	public Flow createFlow(Flow flow) {
		taskFlowDao.createFlow(flow);
		return flow;
	}
	public TaskGroup createTaskGroup(TaskGroup taskGroup) {
		taskFlowDao.createTaskGroup(taskGroup);
		return taskGroup;
	}
	public Team createTeam(Team team) {
		taskFlowDao.createTeam(team);
		return team;
	}
}
