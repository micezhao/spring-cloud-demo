package com.mock.config.security.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public  class VaildataProccessHolder  {
	
	@Autowired
	private Map<String,VaildataProccessHandler> vaildataHandlerMap;
	
	public VaildataProccessHandler findVaildataHandler(String type) {
		VaildataProccessHandler handler = vaildataHandlerMap.get(type+"VaildataProccessHandler");
		if(handler == null) {
			throw new RuntimeException("验证器不存在");
		}
		return handler;
	}
	
}
