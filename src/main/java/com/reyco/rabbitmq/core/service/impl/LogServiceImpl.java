package com.reyco.rabbitmq.core.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reyco.rabbitmq.core.dao.LogDao;
import com.reyco.rabbitmq.core.domain.LogEntity;
import com.reyco.rabbitmq.core.service.LogService;

@Service("logService")
public class LogServiceImpl implements LogService{
	@Autowired
	private LogDao logDao;
	
	@Override
	public LogEntity get(Integer id) {
		return null;
	}

	@Override
	public List<LogEntity> list(Map<String, Object> map) {
		return null;
	}

	@Override
	public LogEntity update(LogEntity t) {
		return null;
	}

	@Override
	public void delete(Integer id) {
		
	}

	@Override
	public LogEntity save(LogEntity logEntity) {
		logDao.save(logEntity);
		return logEntity;
	}

}
