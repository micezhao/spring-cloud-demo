package com.mock.controller;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.mock.remote.FundCenterRemote;
import com.mock.remote.MockProcessCenterRemote;

/**
 * 挂号中心功能接口
 * @author micezhao
 *
 */
@RestController
@RequestMapping("registration")
public class RegistrationCtrl {

	private final Logger logger = LoggerFactory.getLogger(RegistrationCtrl.class);
	
    @Autowired
    FundCenterRemote fundRemote;
	
    @Autowired
    MockProcessCenterRemote mockRemote;
    
    @Value("${registration.init.info}")
	private String appName;
    
    // 模拟锁号操作 scheduleId = 999999999 成功，其他失败
    @RequestMapping("/doOrder")
    public String doOrder(@RequestParam("scheduleId") String scheduleId) {
    	String result = "";
    	if("999999999".equals(scheduleId)){
    		result = mockRemote.doSuccess("协和医院", scheduleId, "4120147541127421", "模拟用户");
    	}else{	//模拟院方请求超时，熔断器生效
    		result = mockRemote.doFailed("协和医院", scheduleId, "4120147541127421", "模拟用户");
    	}
    	return result+"from mobile";
    }
    
    // 模拟资金核心的处理  tradoNo = 10001 成功，其他失败
    @RequestMapping("/confirmOrder")
    public String confirmOrder(@RequestParam("tradeNo") String tradeNo) {
    	JSONObject json = new JSONObject();
    	String result = "";
    	if(StringUtils.equals("10001", tradeNo)){	// 模拟调用成功
    		result=fundRemote.fundPay("100210174030", "45.6");
    	}else{										
    		result=fundRemote.fundPay("100210174999", "45.6");
    	}
    	JSONObject rtnJson=JSONObject.parseObject(result);
    	if("0".equals(rtnJson.get("rtnCode"))){
    		json.put("rtnCode", "0");
    		json.put("rtnMsg", "挂号成功，祝您身体健康！");
    	}else{
    		json.put("rtnCode", "9");
    		json.put("rtnMsg", "挂号失败，支付异常！系统将自动退款，挂号费将原路返回，请留意您的账户变化");
    	}
        return json.toJSONString()+"from mobile";
    }
    
    @RequestMapping(value = "/getAppInfo")
    public String getAppName(){
		return appName;
    }
    
}