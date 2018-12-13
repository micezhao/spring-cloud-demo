package com.mock.remote;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.mock.remote.Hystrix.RemoteHystrix;

/**
 * 从fund_center 获取 服务接口的列表
 * @author micezhao 
 * 如果远程服务响应时间超过1秒，熔断器开始工作
 */
@FeignClient(name= "fund-center",fallback = RemoteHystrix.class)
public interface MockProcessCenterRemote {
	
	// 模拟 远程调用成功 院方接口
	@RequestMapping("/mockProcess/doSuccess")
	public String doSuccess(@RequestParam("hyHospitalId") String hyHospitalId,@RequestParam("scheduleId") String scheduleId,@RequestParam("idCardNo") String idCardNo,@RequestParam("patientName") String patientName );
	
	// 模拟 远程调用失败 院方接口
	@RequestMapping("/mockProcess/doFailed")
	public String doFailed(@RequestParam("hyHospitalId") String hyHospitalId,@RequestParam("scheduleId") String scheduleId,@RequestParam("idCardNo") String idCardNo,@RequestParam("patientName") String patientName );
}
