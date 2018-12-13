package com.mock.remote;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 从fund_center 获取 服务接口的列表
 * @author micezhao 
 *
 */
@FeignClient(name= "fund-center")
public interface FundCenterRemote {
	
    @RequestMapping(value = "/fundCenter/fundPay")
    public String fundPay(@RequestParam(value = "fundAccount") String fundAccout,@RequestParam(value = "fundMoney") String fundMoney);
    
}
