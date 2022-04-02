package com.chen.imbot.taskflow.model;

import java.util.Date;

import lombok.Data;

@Data
public class Flow {
	private int id;
	private String name;
	private int teamId;
	private int creatorId;
	private int memberCount;
	private Date createdAt;
	private Date updatedAt;
}
