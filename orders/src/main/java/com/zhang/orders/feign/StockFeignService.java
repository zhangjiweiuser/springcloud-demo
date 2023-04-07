package com.zhang.orders.feign;

import com.zhang.orders.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * name：指定调用rest接口所对应的服务名
 * path：指定调用rest接口所在的stockController指定的@RequestMapping
 * fallback: 是指异常了，服务降级的方法
 * 同时要配置feign.sentinel.enable=true
 */
@FeignClient(name = "stock-service", path = "/stock",configuration = {FeignConfig.class},fallback = StockFeignServiceFallback.class)
public interface StockFeignService {

    @RequestMapping("/deduct")
    public String deduct();

    @RequestMapping("/deduct2")
    public String deduct2();
}
