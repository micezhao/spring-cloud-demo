package com.mock.remote;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 网关调用远程用户中心，验证用户身份
 * @author micezhao
 *
 */

@FeignClient(name= "user-center")
public interface UserCenterRemote{
	
	@RequestMapping("user/validateToken")
	public String validateToken(@RequestParam("token") String token);
	
}
