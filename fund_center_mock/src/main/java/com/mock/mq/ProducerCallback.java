package com.mock.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;

//@Component 
public class ProducerCallback implements ConfirmCallback {
	
	private static final Logger logger = LoggerFactory.getLogger(ProducerCallback.class);
	
	public ProducerCallback(RabbitTemplate rabbitTemplate) {
		rabbitTemplate.setConfirmCallback(this);
	}

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		String id = correlationData != null ? correlationData.getId() : "";
	    if (ack) {
	    	logger.info("消息确认成功, id:{}", id);
	    } else {
	    	logger.error("消息未成功投递, id:{}, cause:{}", id, cause);
	    }
	}

}
