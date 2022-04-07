package com.chen.imbot.taskflow.dao;

import java.util.List;
import java.util.Map;

import com.chen.imbot.taskflow.model.Flow;
import com.chen.imbot.taskflow.model.Task;
import com.chen.imbot.taskflow.model.TaskGroup;
import com.chen.imbot.taskflow.model.Team;

public interface TaskFlowDao {
	int createTask(Task task);
	int createFlow(Flow flow);
	int createTaskGroup(TaskGroup taskGroup);
	int createTeam(Team team);
	int updateTask(Task task);
	int updateFlow(Flow flow);
	int updateTaskGroup(TaskGroup taskGroup);
	int updateTeam(Team team);
	List<Flow> listFlow(Map param);
}
