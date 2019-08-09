package com.mock.config.security.handler;

import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("smsVaildataProccessHandler")
public class SmsVaildataProccessHandler extends VaildataProccess  {
	
	private static final Logger logger = LoggerFactory.getLogger(SmsVaildataProccessHandler.class);
	
	private static String CODE = "smsCode:000000";
	
	@Override
	protected String generateCode() {
		return CODE;
	}

	@Override
	protected void send(String code) {
		logger.info("已向用户手机发送验证码："+code);
		
	}

	@Override
	protected boolean checkCode(String code) {
		if(StringUtils.equals(code, CODE)) {
			return true;
		}
		return false;
	}
	
	
}
