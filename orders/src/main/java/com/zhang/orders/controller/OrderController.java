package com.zhang.orders.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author zhangjiwei1
 * @description
 * @create 2023-04-04 15:07
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    RestTemplate restTemplate;

    @RequestMapping("/add")
    public String add() {

        String forObject = restTemplate.getForObject("http://stock-service/stock/deduct", String.class);
        System.out.println("新增订单：" + forObject);

        return "新增库存";
    }
}
