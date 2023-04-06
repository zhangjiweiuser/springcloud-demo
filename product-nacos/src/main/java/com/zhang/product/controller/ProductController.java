package com.zhang.product.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangjiwei1
 * @description
 * @create 2023-04-04 17:04
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @RequestMapping("/{id}")
    public String get(@PathVariable("id") Integer id) throws InterruptedException {
        System.out.println("查询商品:" + id);
        return "查询商品:" + id;
    }
}
