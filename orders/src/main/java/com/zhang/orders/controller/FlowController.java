package com.zhang.orders.controller;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangjiwei1
 * @description
 * @create 2023-04-07 10:01
 */
@RestController
@RequestMapping("/flow")
public class FlowController {

    @RequestMapping("/flow")
    public String flow() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        return "flow";
    }

    @RequestMapping("/err")
    public String err() {
       int t = 1/0;
        return "err";
    }

    @RequestMapping("/gateway")
    public String gateway() throws InterruptedException {
        return "gateway";
    }

    @RequestMapping("/header")
    public String header(@RequestHeader("X-Request-red") String color) {
        return color;
    }
}
