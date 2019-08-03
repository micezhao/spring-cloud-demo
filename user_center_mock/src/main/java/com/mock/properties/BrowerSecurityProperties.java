package com.mock.properties;

import com.mock.config.security.type.LoginTypeEnum;

public class BrowerSecurityProperties {
	
	//设置默认的登录页面，如果配置文件中配置了个性化登录页，那就启用配置文件中的值
	private String loginPage = "default-login.html";
	
	//登录类型
	private LoginTypeEnum loginType = LoginTypeEnum.JSON;
	
	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public LoginTypeEnum getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginTypeEnum loginType) {
		this.loginType = loginType;
	}

	
	
	
	
}
