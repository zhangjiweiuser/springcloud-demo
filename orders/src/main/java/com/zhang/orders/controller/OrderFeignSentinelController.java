package com.zhang.orders.controller;

import com.zhang.orders.feign.ProductFeginService;
import com.zhang.orders.feign.StockFeignService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhangjiwei1
 * @description
 * @create 2023-04-04 15:07
 */
@RestController
@RequestMapping("/order")
public class OrderFeignSentinelController {
//    @Resource
//    RestTemplate restTemplate;

    @Resource
    StockFeignService stockFeignService;

    @RequestMapping("/feignSentinel")
    public String feignSentinel() {

        String s = stockFeignService.deduct2();
        System.out.println("新增订单：" + s);

        return "新增库存";
    }
}
