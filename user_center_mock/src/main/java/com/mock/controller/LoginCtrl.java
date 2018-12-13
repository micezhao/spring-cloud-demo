package com.mock.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("user")
public class LoginCtrl {
    private final Logger logger = LoggerFactory.getLogger(LoginCtrl.class);
    
    @Value("${usercenter.init.info}")
	private String appName;
    
    
    @RequestMapping("/validateToken")
    public String validateToken(@RequestParam("token") String token) {
        JSONObject json = new JSONObject();
        String tokenStore = "validateToken";
    	logger.info("--->>> validateToken {}","用户身份请求认证");
        if(StringUtils.isNotBlank(token)){
        	if(StringUtils.equals(tokenStore, token)){
        		json.put("rtnCode", "0");
                json.put("rtnMsg", "身份认证成功");
                logger.info("--->>> validateToken {}","用户身份认证成功");
                return json.toString();
        	}
        }
        json.put("rtnCode", "9");
        json.put("rtnMsg", "用户身份认证失败");
        logger.info("--->>> validateToken {}","用户身份认证失败");
        return json.toString();
    }
    
    @RequestMapping("/registerUser")
    public String registerUser(@RequestParam("userName") String name,@RequestParam("password") String password){
    	JSONObject rtnJson = new JSONObject();
    	JSONObject json = new JSONObject();
    	// 注册用户的逻辑处理
    	json.put("userName", name);
    	json.put("password", password);
    	json.put("token", "Bear xxx validateToken");
    	
    	rtnJson.put("rtnCode", "0");
    	rtnJson.put("rtnMsg", "身份认证成功");
    	rtnJson.put("rtnData", json);
		return rtnJson.toJSONString();
    }
    
    @RequestMapping("/loginUser")
    public String loginUser(@RequestParam("userName") String name,@RequestParam("password") String password){
    	JSONObject rtnJson = new JSONObject();
    	if(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(password)){
    		rtnJson.put("rtnCode", "0");
        	rtnJson.put("rtnMsg", "用户注册成功");
    		return rtnJson.toJSONString();
    	}
    	rtnJson.put("rtnCode", "9");
    	rtnJson.put("rtnMsg", "用户注册失败");
		return rtnJson.toJSONString();
    }
    
    
    @RequestMapping(value = "/getAppInfo")
    public String getAppName(){
		return appName;
    }
    
}