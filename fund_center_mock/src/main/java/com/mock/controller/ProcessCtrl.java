package com.mock.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.mock.mq.Producer;

@RestController
@RequestMapping("mockProcess")
public class ProcessCtrl {
	
	private final Logger logger = LoggerFactory.getLogger(ProcessCtrl.class);
	
	@Autowired
	private Producer producer;
	
	@RequestMapping("/doSuccess")
	public String doSuccess(@RequestParam("hyHospitalId") String hyHospitalId,@RequestParam("scheduleId") String scheduleId,@RequestParam("idCardNo") String idCardNo,@RequestParam("patientName") String patientName ){
		JSONObject json = new JSONObject();
		json.put("rtnCode", "0");
		json.put("rtnMsg", "排班Id:"+scheduleId+" |用户姓名："+patientName+" |用户证件号："+idCardNo+" |锁号成功");
		logger.info("--->>> doSuccess {},{}","[响应来自 fund_center_节点_1]排班Id:"+scheduleId+" |用户姓名："+patientName+" |用户证件号："+idCardNo+" |锁号成功");
		return json.toJSONString();
	}
	
	@RequestMapping("/doFailed")
	public String doFailed(@RequestParam("hyHospitalId") String hyHospitalId,@RequestParam("scheduleId") String scheduleId,@RequestParam("idCardNo") String idCardNo,@RequestParam("patientName") String patientName ){
		JSONObject json = new JSONObject();
	//	try{
			//线程等待时间超过5秒，熔断器介入
//			Thread.sleep(10000L);
			//线程等待时间未超过5秒，熔断器不介入，等待系统返处理结果
			//Thread.sleep(3000L);
	//	}catch(InterruptedException e){
			
	//	}
		json.put("rtnCode", "9");
		json.put("rtnMsg", "对不起，院方接口受理失败");
		logger.info("--->>> doFailed {},{}"," [响应来自 fund_center_节点_1] 对不起，院方接口受理失败");
		return json.toJSONString();
	}
	
	@RequestMapping("/send")
	public String sendMsg() {
		producer.sender("fund_center message send successed");
		return "200";
	}
}
