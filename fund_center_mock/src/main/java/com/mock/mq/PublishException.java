package com.mock.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublishException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6164439779406729808L;
	private static final Logger logger = LoggerFactory.getLogger(PublishException.class);
	
	public PublishException(String message) {
       logger.error(message);
    }
}
