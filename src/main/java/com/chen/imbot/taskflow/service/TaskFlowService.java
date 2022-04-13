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

import com.chen.imbot.socket.SocketServer;
import com.chen.imbot.taskflow.dao.TaskFlowDao;
import com.chen.imbot.taskflow.model.Flow;
import com.chen.imbot.taskflow.model.Task;
import com.chen.imbot.taskflow.model.TaskGroup;
import com.chen.imbot.taskflow.model.Team;
import com.chen.imbot.usercenter.dao.UserDao;
import com.chen.imbot.usercenter.model.User;
import com.chen.imbot.usercenter.service.UserService;
import com.chen.imbot.utils.ImUtil;
import com.chen.imbot.utils.TaskAdapter;
import com.chen.imbot.utils.TaskManager;
import com.google.gson.Gson;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskFlowService {
	@Autowired
	private UserService userService;
	@Autowired
	private TaskFlowDao taskFlowDao;
	@Autowired
	private UserDao userDao;

	public Task createTask(Task task) {
		ImUtil.setCreatedUpdatedTime(task);
		taskFlowDao.createTask(task);
		List<Integer> flowUserIds = userDao.flowUserIds(task.getFlowId());
		List<User> flowUsers = flowUserIds.stream().map(id -> userDao.findUserById(id)).toList();
		pushToUsers(task, flowUsers);
		return task;
	}

	public Flow createFlow(Flow flow) {
		ImUtil.setCreatedUpdatedTime(flow);
		taskFlowDao.createFlow(flow);
		return flow;
	}

	public TaskGroup createTaskGroup(TaskGroup taskGroup) {
		ImUtil.setCreatedUpdatedTime(taskGroup);
		taskFlowDao.createTaskGroup(taskGroup);
		return taskGroup;
	}

	public Team createTeam(Team team) {
		ImUtil.setCreatedUpdatedTime(team);
		taskFlowDao.createTeam(team);
		return team;
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

	public boolean pushToUsers(Object obj, List<User> users) {
		Gson gson = new Gson();
		String messageStr = gson.toJson(obj);
		boolean finalRet = true;
		List<Integer> failUserIds = new ArrayList<>();
		for (User u : users) {
			boolean sendRet = pushToUser(messageStr, u);
			if (!sendRet) {
				failUserIds.add(u.getId());
				log.info("send push message data to: {} is false", u);
			}
		}
		if (failUserIds.size() > 0) log.error("fail send users: {}", String.join(",", failUserIds.stream().map(String::valueOf).toList()));
		return finalRet;
	}
	public boolean pushToUser(String message, User user) {
		log.info("message: {} send to user id : {}", message, user.getId());
		return TaskManager.offer(new TaskAdapter(new Runnable() {
			
			@Override
			public void run() {
				Channel channel = userService.getChannel(user);
				if (channel == null) return;
				SocketServer.getInstance().sendData(channel, message);
			}
		}));
	}
}
