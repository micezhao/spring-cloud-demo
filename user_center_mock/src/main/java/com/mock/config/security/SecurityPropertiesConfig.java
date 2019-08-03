package com.mock.config.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.mock.properties.SecurityProperties;

/**
 * 启用熟悉加载的配置类对象
 * @author zhaochen
 *
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityPropertiesConfig {

}
