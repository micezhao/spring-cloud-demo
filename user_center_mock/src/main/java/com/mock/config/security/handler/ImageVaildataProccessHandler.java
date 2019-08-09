package com.mock.config.security.handler;

import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("imageVaildataProccessHandler")
public class ImageVaildataProccessHandler extends VaildataProccess {
	
	private static final Logger  logger = LoggerFactory.getLogger(ImageVaildataProccessHandler.class);

	private static String CODE = "imageCode:123456";
	
	@Override
	protected String generateCode() {
		return CODE;
	}

	@Override
	protected void send(String code) {
		logger.info("以向页面回写验证码："+code);
		
	}

	@Override
	protected boolean checkCode(String code) {
		if(StringUtils.equals(code, CODE)) {
			return true;
		}
		return false;
	}
	

}
