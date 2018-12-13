package com.mock.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RefreshScope
@RestController
@RequestMapping("fundCenter")
public class FundCtrl {
   
	private final Logger logger = LoggerFactory.getLogger(FundCtrl.class);

	@Value("${fund.init.info}")
	private String appName;
	
    @RequestMapping(value = "/fundPay")
    public String fundPay(@RequestParam(value = "fundAccount") String fundAccout,@RequestParam(value = "fundMoney") String fundMoney){
    	JSONObject json = new JSONObject();
    	if("100210174030".equals(fundAccout)){
    		json.put("rtnCode", "0");
    		json.put("rtnMsg", "[响应来自 fund_center_节点_1] 支付成功");
    	}else{
    		json.put("rtnCode", "9");
    		json.put("rtnMsg", "[响应来自 fund_center_节点_1] 支付失败,账户余额不足");
    	}
		return json.toJSONString();
    }
    
    @RequestMapping(value = "/getAppInfo")
    public String getAppName(){
		return appName;
    }
    
    
}