package com.chen.imbot.taskflow.model;

import java.util.Date;

import lombok.Data;

@Data
public class Team {
	private int id;
	private String name;
	private int creatorId;
	private Date createdAt;
	private Date updatedAt;
}
