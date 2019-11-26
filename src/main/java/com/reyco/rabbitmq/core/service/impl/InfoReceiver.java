package com.reyco.rabbitmq.core.service.impl;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.reyco.rabbitmq.core.domain.LogEntity;
import com.reyco.rabbitmq.core.domain.LoginLog;
import com.reyco.rabbitmq.core.service.LogService;
/**
 * 消息接收者
 * @author reyco
 * @RabbitListener bindings:绑定队列
 * @QueueBinding  value:绑定队列的名称
 *                exchange:配置交换器
 * 
 * @Queue value:配置队列名称
 *        autoDelete:是否是一个可删除的临时队列:当所有消费者断开连接是否自动删除队列，true：删除 false:不删除
 *        
 * @Exchange value:为交换器起个名称
 *           type:指定具体的交换器类型
 *           autoDelete:当所有绑定队列都不在使用时，是否自动删除交换器。true:删除  false:不删除
 */
@Component
@RabbitListener(
			bindings=@QueueBinding(
					value=@Queue(value="${mq.config.queue.info}",autoDelete="false"), 
					exchange=@Exchange(value="${mq.config.exchange}",type=ExchangeTypes.DIRECT,autoDelete="false"),
					key="${mq.config.queue.info.routing.key}"
			)
		)
public class InfoReceiver {
	@Autowired
	private LogService logService;
	
	/**
	 * 接收消息的方法。采用消息队列监听机制
	 * @param msg
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@RabbitHandler
	public void process(LogEntity logEntity) throws Exception{
		System.out.println("使用。。。。。。。");
		logEntity = logService.save(logEntity);
		//throw new RuntimeException();
		
	}

}
