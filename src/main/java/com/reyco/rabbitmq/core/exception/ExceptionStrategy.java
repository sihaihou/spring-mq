package com.reyco.rabbitmq.core.exception;

import com.reyco.rabbitmq.core.utils.R;

/**
 * 自定义异常处理顶级接口
 * @author reyco
 *
 */
public interface ExceptionStrategy{
	
	/**
	 * 获取异常信息
	 * @param ex  异常对象
	 * @return
	 */
	R getExceptionMsg(Exception ex);
	
}
