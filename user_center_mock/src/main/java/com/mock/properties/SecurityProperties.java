package com.mock.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 该类用于加载配置文件中前缀 = "user.security.*" 的配置项 
 * @author zhaochen
 *
 */
@ConfigurationProperties(prefix = "user.security")
public class SecurityProperties {
	
	// 当配置项 = user.security.browser.* 时 将会被BrowerSecurityProperties 类包装
	private BrowerSecurityProperties browser = new BrowerSecurityProperties();

	public BrowerSecurityProperties getBrowser() {
		return browser;
	}

	public void setBrowser(BrowerSecurityProperties browser) {
		this.browser = browser;
	}
	
}
