package com.chen.imbot.taskflow.model;

import java.util.Date;

import lombok.Data;

@Data
public class TaskGroup {
	private int id;
	private int name;
	private int creatorId;
	private Date createdAt;
	private Date updatedAt;
}
