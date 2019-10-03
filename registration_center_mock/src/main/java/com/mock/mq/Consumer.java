package com.mock.mq;

import java.io.IOException;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
@RabbitListener(queues = "fund_pay")
public class Consumer {
	
	private static final Logger logger = LoggerFactory.getLogger(Consumer.class);
	
	
//	@RabbitHandler
//	public void messageHandler(Object context) {
//		Message message = (Message)context;
//		String contex =new String(message.getBody()) ;
//		String desc =(String)message.getMessageProperties().getHeaders().get("desc");
//		logger.info("message recived... {},消息编号：{}",contex,message.getMessageProperties().getCorrelationIdString());
//		if(StringUtils.isNotBlank(desc)) {
//			logger.info("desc recived... {}",desc);
//		}
//	}
	
	@RabbitHandler
	public void receiveMessage(Object obj, Channel channel) {  
		Message message = (Message)obj;
        String receiveMessage = message.getBody().toString();
		try {  
            // 手动签收  
        	logger.info("接收到消息:[{}]", receiveMessage);  
//            if (new Random().nextInt(10) < 5) {  
//            	logger.warn("拒绝一条信息:[{}]，此消息将会由死信交换器进行路由.", receiveMessage);  
//                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
//            } else {  
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);  
//            }  
        } catch (Exception e) {  
        	logger.info("接收到消息之后的处理发生异常.", e);  
            try {  
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);  
            } catch (IOException e1) {  
            	logger.error("签收异常.", e1);  
            }  
        }  
    } 
}	
