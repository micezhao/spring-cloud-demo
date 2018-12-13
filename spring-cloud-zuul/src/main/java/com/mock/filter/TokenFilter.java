package com.mock.filter;
import com.alibaba.fastjson.JSONObject;
import com.mock.remote.UserCenterRemote;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class TokenFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(TokenFilter.class);
    
    @Autowired
    UserCenterRemote userCenterRemote;
    
    @Override
    public String filterType() {
        return "pre"; // 可以在请求被路由之前调用
    }

    @Override
    public int filterOrder() {
        return 0; // filter执行顺序，通过数字指定 ,优先级为0，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        return true;// 是否执行该过滤器，此处为true，说明需要过滤
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        logger.info("--->>> TokenFilter {},{}", request.getMethod(), request.getRequestURL().toString());

        String token = request.getParameter("token");// 获取请求的参数
        
        //调用远程用户中心方法，验证用户身份是否合法
        String result=userCenterRemote.validateToken(token);	
        JSONObject rtnJson=JSONObject.parseObject(result);
        if (StringUtils.equals("0", (String)rtnJson.get("rtnCode"))) {
            ctx.setSendZuulResponse(true); //对请求进行路由
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);
            return null;
        } else {
            ctx.setSendZuulResponse(false); //不对其进行路由
            ctx.setResponseStatusCode(400);
            JSONObject json = new JSONObject();
            json.put("rtnCode", "9");
			json.put("rtnMsg","token validate failed");
			ctx.setResponseBody(json.toJSONString());
            ctx.set("isSuccess", false);
            return null;
        }
    }

}