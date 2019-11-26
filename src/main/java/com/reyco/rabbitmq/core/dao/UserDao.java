package com.reyco.rabbitmq.core.dao;

import com.reyco.rabbitmq.core.domain.UserEntity;

public interface UserDao extends BaseDao<UserEntity>{

	UserEntity getByName(String name);
}
