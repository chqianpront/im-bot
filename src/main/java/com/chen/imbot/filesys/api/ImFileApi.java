package com.chen.imbot.filesys.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chen.imbot.filesys.model.ImFile;
import com.chen.imbot.filesys.service.ImFileService;
import com.chen.imbot.usercenter.model.User;
import com.chen.imbot.utils.BaseReturn;
import com.chen.imbot.utils.DataReturn;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/file")
public class ImFileApi {
	@Autowired
	private ImFileService imFileService;
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public Object create(@RequestParam(required = false) User user, @RequestParam("file") MultipartFile file, @RequestParam(required = false) Integer teamId, @RequestParam(required = false) Integer flowId, @RequestParam(required = false) Integer taskId) {
		log.info("file info : {}", file);
		DataReturn ret = new DataReturn();
		if (file.isEmpty()) {
			ret.setCode(BaseReturn.SIMPLE_ERROR);
			ret.setMsg("no file contain");
			return ret;
		}
		ImFile imFile = new ImFile();
		if (flowId != null) imFile.setFlowId(flowId);
		if (teamId != null) imFile.setTeamId(teamId);
		if (taskId != null) imFile.setTaskId(taskId);
		imFile.setCreatorId(user.getId());
		ImFile saveRet = imFileService.transferTo(user, file, imFile);
		if (saveRet == null) {
			ret.setCode(BaseReturn.SIMPLE_ERROR);
			return ret;
		}
		ret.setCode(BaseReturn.SUCCESS);
		ret.setData(saveRet);
		return ret;
	}
}
