package com.chen.imbot.filesys.model;

import java.util.Date;

import lombok.Data;

@Data
public class ImFile {
	private int id;
	private String fileName;
	private long fileSize;
	private String filePath;
	private int deleted;
	private int creatorId;
	private int teamId;
	private int flowId;
	private int taskId;
	private Date createdAt;
	private Date updatedAt;
}
