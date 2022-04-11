package com.chen.imbot.taskflow.service;

import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chen.imbot.taskflow.dao.TaskFlowDao;
import com.chen.imbot.taskflow.model.Flow;
import com.chen.imbot.taskflow.model.Task;
import com.chen.imbot.taskflow.model.TaskGroup;
import com.chen.imbot.taskflow.model.Team;
import com.chen.imbot.usercenter.model.User;
import com.chen.imbot.usercenter.service.UserService;
import com.chen.imbot.utils.TaskAdapter;
import com.chen.imbot.utils.TaskManager;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskFlowService {
	@Autowired
	private UserService userService;
	@Autowired
	private TaskFlowDao taskFlowDao;
	public Task createTask(Task task) {
		setCreatedUpdatedTime(task);
		taskFlowDao.createTask(task);
		return task;
	}
	public Flow createFlow(Flow flow) {
		setCreatedUpdatedTime(flow);
		taskFlowDao.createFlow(flow);
		return flow;
	}
	public TaskGroup createTaskGroup(TaskGroup taskGroup) {
		setCreatedUpdatedTime(taskGroup);
		taskFlowDao.createTaskGroup(taskGroup);
		return taskGroup;
	}
	public Team createTeam(Team team) {
		setCreatedUpdatedTime(team);
		taskFlowDao.createTeam(team);
		return team;
	}
	private Object setCreatedUpdatedTime(Object obj) {
		Date now = new Date();
		try {
			obj.getClass().getMethod("setCreatedAt", new Class[] {Date.class}).invoke(obj, new Object[] {now});
			obj.getClass().getMethod("setUpdatedAt", new Class[] {Date.class}).invoke(obj, new Object[] {now});
		} catch (Exception e) {
			log.error("error: {}", e.getMessage());
		}
		return obj;
	}
	public List<Flow> listFlow(Map param) {
		return taskFlowDao.listFlow(param);
	}
	private List<User> loginUsers(List<User> users) {
		List<User> lUers = new ArrayList<User>();
		for (User user : users) {
			if (userService.isLogin(user)) {
				lUers.add(user);
			}
		}
		return lUers;
	}
	public boolean pushToUser(Object obj, List<User> users) {
		Gson gson = new Gson();
		String messageStr = gson.toJson(obj);
		List<User> lgUsers = loginUsers(users);
		return TaskManager.offer(new TaskAdapter(new Runnable() {
			
			@Override
			public void run() {
				
			}
		}));
		
	}
}
