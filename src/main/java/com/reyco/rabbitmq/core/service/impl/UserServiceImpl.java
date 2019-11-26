package com.reyco.rabbitmq.core.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.reyco.rabbitmq.core.dao.UserDao;
import com.reyco.rabbitmq.core.domain.UserEntity;
import com.reyco.rabbitmq.core.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public UserEntity get(Integer id) {
		return userDao.get(id);
	}
	/**
	 * 修改
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public UserEntity update(UserEntity userEntity) {
		userDao.update(userEntity);
		return userEntity;
	}
	/**
	 * 删除
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void delete(Integer id) {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(id);
		userEntity.setState(1);
		//userDao.update(userEntity);
	}
	
	@Override
	public List<UserEntity> list(Map<String, Object> map) {
		return userDao.list(map);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public UserEntity save(UserEntity userEntity) {
		 userDao.save(userEntity);
		 return userEntity;
	}
	@Override
	public UserEntity getByName(String name) {
		return userDao.getByName(name);
	}
}
