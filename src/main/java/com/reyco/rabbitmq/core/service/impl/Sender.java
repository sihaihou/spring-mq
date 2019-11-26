package com.reyco.rabbitmq.core.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Sender {
	
	@Autowired
	private RabbitTemplate rabbitAmqpTemplate;
	
	//exchange 交换器名称
	@Value("${mq.config.exchange}")
	private String exchange;
	
	//routingkey 路由键
	@Value("${mq.config.queue.info.routing.key}")
	private String routingkey;
	@PostConstruct
    public void initRabbitTemplate() {
        // 设置生产者消息确认
		rabbitAmqpTemplate.setConfirmCallback(new RabbitConfirmCallback());
		rabbitAmqpTemplate.setReturnCallback(new RabbitReturnCallback());
    }
	/*
	 * 发送消息的方法
	 */
	public void send(Object msg){
		//向消息队列发送消息
		//参数一：交换器名称。
		//参数二：路由键
		//参数三：消息
		this.rabbitAmqpTemplate.convertAndSend(this.exchange, this.routingkey, msg);
	}
	/**
	 * 设置 ReturnCallback 回调
	 * 如果发送到交换器成功, 但是没有匹配的队列, 就会触发这个回调   在 ConfirmCallback 之前执行
	 */
	class RabbitReturnCallback  implements ReturnCallback {
		@Override
		public void returnedMessage(Message message, int replyCode, String replyText, String exchange,
				String routingKey) {
			System.out.println("message:"+message+",replyCode:"+replyCode+",replyText:"+replyText+",exchange:"+exchange+",routingKey:"+routingKey);
		}
	}
	/**
	 * 生产者端将消息发送出去, 消息到达 RabbitMQ 之后, 会返回一个到达确认.
	 * 这个确认实际上就是官方常说的 ConfirmCallback, 我们通过在生产者端使用一个回调类来监听 RabbiMQ 返回的消息确认.
	 * Spring AMQP 中我们通过设置 RabbitTemplate 的 ConfirmCallback 属性来实现消息确认回调, 通过一个实现了 ConfirmCallback 的类来实现回调逻辑.
	 */
	class RabbitConfirmCallback implements ConfirmCallback  {
		@Override
		public void confirm(CorrelationData correlationData, boolean ack, String cause) {
			System.out.println("correlationData:"+correlationData+",ack:"+ack+",cause:"+cause);
		}
	}
}
