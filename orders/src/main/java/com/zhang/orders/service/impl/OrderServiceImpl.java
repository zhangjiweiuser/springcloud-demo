package com.zhang.orders.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.zhang.orders.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @SentinelResource(value = "/getUser",blockHandler = "getUserBlockHandler")
    public String getUser() {
        return "查询用户";
    }

    public String getUserBlockHandler(BlockException e){
        return "流控";
    }
}
