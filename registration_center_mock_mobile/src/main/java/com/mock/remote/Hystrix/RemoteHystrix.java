package com.mock.remote.Hystrix;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.mock.remote.MockProcessCenterRemote;

/**
 * 熔断器 ： 用于保护远程服务调用方不被服务提供方的异常所影响（配置文件可声明熔断器的超时检查时间，请求超时则熔断器介入处理）
 * 熔断器：支持方法级别的处理
 * @author micehzhao
 *
 */
@Component
public class RemoteHystrix implements MockProcessCenterRemote{
	
	@Override
	public String doSuccess(String hyHospitalId, String scheduleId,
			String idCardNo, String patientName) {
		JSONObject json = new JSONObject();
		json.put("rtnCode", "-1");
		json.put("rtnMsg", "真糟糕呢~~~~远程服务发生异常，方法调用进行熔断处理");
		return json.toJSONString();
	}

	@Override
	public String doFailed(String hyHospitalId, String scheduleId,
			String idCardNo, String patientName) {
		JSONObject json = new JSONObject();
		json.put("rtnCode", "-1");
		json.put("rtnMsg", "真糟糕呢~~~~远程服务发生异常，方法调用进行熔断处理");
		return json.toJSONString();
	}
	
	
}
