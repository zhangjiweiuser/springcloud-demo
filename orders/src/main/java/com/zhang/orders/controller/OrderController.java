package com.zhang.orders.controller;

import com.zhang.orders.feign.ProductFeginService;
import com.zhang.orders.feign.StockFeignService;
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
//    @Resource
//    RestTemplate restTemplate;

    @Resource
    StockFeignService stockFeignService;
    @Resource
    ProductFeginService productFeginService;

    @RequestMapping("/add")
    public String add() {

        String forObject = stockFeignService.deduct();
        System.out.println("新增订单：" + forObject);
        String s = productFeginService.get(1);
        System.out.println("商品查询结果:" + s);
        return "新增库存";
    }


}
