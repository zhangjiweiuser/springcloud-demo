package com.zhang.orders.controller;

import com.zhang.orders.service.OrderService;
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
}
