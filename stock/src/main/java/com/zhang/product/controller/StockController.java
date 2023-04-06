package com.zhang.product.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangjiwei1
 * @description
 * @create 2023-04-04 17:04
 */
@RestController
@RequestMapping("/stock")
public class StockController {

    @RequestMapping("/deduct")
    public String deduct(){
        System.out.println("库存扣减");
        return "库存扣减";
    }
}
