package com.chen.imbot.taskflow.model;

import java.util.Date;

import lombok.Data;

@Data
public class Task {
	private int id;
	private String name;
	private int status;
	private int taskGroupId;
	private int flowId;
	private int creatorId;
	private int assignUserId;
	private int taskOrder;
	private String taskTitle;
	private String taskDesc;
	private Date taskStartTime;
	private Date taskEndTime;
	private Date createdAt;
	private Date updatedAt;
}
