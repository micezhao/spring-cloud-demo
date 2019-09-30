package com.mock.mq;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Producer implements ConfirmCallback,ReturnCallback{
	
	private static final Logger logger = LoggerFactory.getLogger(Producer.class);
	
	private final String QUEUE_NAME = "fund_pay";
	
	@Autowired
	private RabbitTemplate rabbitTemplate ;
	
	@Bean
	public Queue queue() {
//		return new Queue(QUEUE_NAME,false);  // 声明一个非持久化的队列，如果消息中间件重启，那么队列会消失，队列中的消息也会丢失
		return new Queue(QUEUE_NAME);
	}
	
	public Producer(RabbitTemplate rabbitTemplate) {
//		rabbitTemplate.setConfirmCallback(this); //设置消息投递到中间件成功的回调
//		rabbitTemplate.setReturnCallback(this); //设置消息投递到交换机是成功的回调
		rabbitTemplate.setChannelTransacted(true);
	}
	
	@Bean
	public RabbitTransactionManager rabbitTransactionManager(CachingConnectionFactory connectionFactory) {
	    return new RabbitTransactionManager(connectionFactory);
	}

	@Transactional
	public void sender(String text) {
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		messageProperties.setCorrelationIdString(correlationData.getId());
		// 
		Message message = new Message(text.getBytes(), messageProperties);
		rabbitTemplate.convertAndSend(QUEUE_NAME,message,new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				message.getMessageProperties().getHeaders().put("desc", "额外的消息");
				return message;
			}
		},correlationData);
		
		throw new RuntimeException("消息发送方法出现异常");
	}

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		String id = correlationData != null ? correlationData.getId() : "";
	    if (ack) {
	    	logger.info("消息投递成功, id:{}", id);
	    } else {
	    	logger.error("消息未成功投递, id:{}, cause:{}", id, cause);
	    }
	}

	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		logger.info("消息发送失败", message.toString());
	}
	
}
