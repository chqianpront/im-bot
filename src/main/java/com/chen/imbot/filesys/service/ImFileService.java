package com.chen.imbot.filesys.service;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chen.imbot.filesys.dao.ImFileDao;
import com.chen.imbot.filesys.model.ImFile;
import com.chen.imbot.usercenter.model.User;
import com.chen.imbot.utils.ImUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImFileService {
	@Value("${taskflow.filesys.rootpath}")
	private String rootPath;
	@Autowired
	private ImFileDao imFileDao;
	public ImFile transferTo(User user, MultipartFile file, ImFile imFile) {
		Calendar c = Calendar.getInstance();
		StringBuilder sb = new StringBuilder();
		sb.append(rootPath);
		sb.append("/");
		sb.append(c.get(Calendar.MONTH) + 1);
		sb.append("/");
		sb.append(c.get(Calendar.DAY_OF_MONTH));
		sb.append("/");
		File savePath = new File(sb.toString());
		if (!savePath.exists()) savePath.mkdirs();
		sb.append(file.getOriginalFilename());
		File toSaveFile = new File(sb.toString());
		while (toSaveFile.exists()) {
			sb.append("1");
			toSaveFile = new File(sb.toString());
		}
		log.info("to save file path: {}", toSaveFile.getPath());
		try {
			InputStream is = file.getInputStream();
			Files.copy(is, toSaveFile.toPath());
			imFile.setFileName(file.getOriginalFilename());
			imFile.setFilePath(sb.toString());
			imFile.setFileSize(file.getSize());
			ImUtil.setCreatedUpdatedTime(imFile);
			imFileDao.createFile(imFile);
			return imFile;
		} catch (Exception e) {
			log.error("error: {}", e.getMessage());
			return null;
		}
	}
}
