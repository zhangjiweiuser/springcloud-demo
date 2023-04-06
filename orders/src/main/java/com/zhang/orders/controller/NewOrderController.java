package com.zhang.orders.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class NewOrderController {

    @RequestMapping("/newOrder")
//    @SentinelResource(value = "newOrder",blockHandler = "newOrderBlockHandler")
    public String newOrder(){
        return "newOrder";
    }
    public String newOrderBlockHandler(BlockException e){
        return "流控";
    }

    @RequestMapping("/newOrderThread")
    @SentinelResource(value = "newOrderThread",blockHandler = "newOrderBlockHandler")
    public String newOrderThread() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return "newOrder";
    }
    @RequestMapping("/add")
    public String add() {
        return "add";
    }

    @RequestMapping("/get")
    public String get() {
        return "get";
    }


}
