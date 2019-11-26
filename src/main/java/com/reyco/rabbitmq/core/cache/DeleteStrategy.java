package com.reyco.rabbitmq.core.cache;
/**
 * 缓存删除策略顶级接口
 * @author reyco
 *
 */
public interface DeleteStrategy {
	
	void deleteCache();
	
}
