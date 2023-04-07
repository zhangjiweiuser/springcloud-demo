package com.zhang.orders.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.zhang.orders.service.OrderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    OrderService orderService;

    @RequestMapping("/getUser1")
    public String getUser1() {
        return orderService.getUser();
    }

    @RequestMapping("/getUser2")
    public String getUser2() {
        return orderService.getUser();
    }

    /**
     * 热点参数流量防控
     * @return
     */
    @RequestMapping("/get/{id}")
    @SentinelResource(value = "getById",blockHandler = "hotParamBlockHandler")
    public String getById(@PathVariable("id") Integer id) {
        return "getById";
    }

    public String hotParamBlockHandler(@PathVariable("id") Integer id, BlockException e){
        return "热点异常处理";
    }
}
