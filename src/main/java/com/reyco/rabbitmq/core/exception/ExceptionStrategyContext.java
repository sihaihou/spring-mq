package com.reyco.rabbitmq.core.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reyco.rabbitmq.core.utils.R;

/**
 * 自定义异常处理策略
 * @author reyco
 *
 */
@Component("exceptionStrategyContext")
public class ExceptionStrategyContext {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private Map<String,ExceptionStrategy> exceptionStrategyMap = new HashMap<String,ExceptionStrategy>();
	
	public R getExceptionMsg(String type,Exception e) {
		ExceptionStrategy exceptionStrategy = exceptionStrategyMap.get(type);
		return exceptionStrategy.getExceptionMsg(e);
	}
	
}
