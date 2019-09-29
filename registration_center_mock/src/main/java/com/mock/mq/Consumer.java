package com.mock.mq;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fund_pay")
public class Consumer {
	
	private static final Logger logger = LoggerFactory.getLogger(Consumer.class);
	
	@Autowired
	private RabbitTemplate rabbitTemplate ;
	
	@RabbitHandler
	public void messageHandler(Object context) {
		Message message = (Message)context;
		String contex =new String(message.getBody()) ;
		String desc =(String)message.getMessageProperties().getHeaders().get("desc");
		logger.info("message recived... {},消息编号：{}",contex,message.getMessageProperties().getCorrelationIdString());
		if(StringUtils.isNotBlank(desc)) {
			logger.info("desc recived... {}",desc);
		}
	}
}	
