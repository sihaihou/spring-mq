package com.reyco.rabbitmq.core.service;

import com.reyco.rabbitmq.core.domain.UserEntity;

public interface UserService extends BaseService<UserEntity>{
	
	UserEntity getByName(String name);
	
}
