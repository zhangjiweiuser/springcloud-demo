package com.zhang.orders.feign;

import org.springframework.stereotype.Component;

/**
 * @author zhangjiwei1
 * @description
 * @create 2023-04-07 12:26
 */
@Component
public class StockFeignServiceFallback implements StockFeignService{
    public String deduct() {
        return null;
    }

    public String deduct2() {
        return "降级了";
    }
}
