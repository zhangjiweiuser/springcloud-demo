package com.zhang.orders.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.zhang.orders.service.OrderService;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @SentinelResource(value = "/getUser",blockHandler = "getUserBlockHandler")
    public String getUser() {
        return "查询用户";
    }

    @Trace
    public String skywalkingTest(String name) {
        return name;
    }

    /**
     * key:可以随意指定，value=returnedObj是固定的，代表返回值信息
     * value=arg[0]，代表参数信息
     * @param id
     * @return
     */
    @Trace
    @Tags({@Tag(key = "getById",value = "returnedObj"),
            @Tag(key = "paramaa",value = "arg[0]")})
    public String getById(Integer id) {
        return "id:"+id;
    }

    public String getUserBlockHandler(BlockException e){
        return "流控";
    }
}
