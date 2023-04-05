package com.zhang.orders.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * name：指定调用rest接口所对应的服务名
 * path：指定调用rest接口所在的stockController指定的@RequestMapping
 */
@FeignClient(name = "stock-service", path = "/stock")
public interface StockFeignService {

    @RequestMapping("/deduct")
    public String deduct();
}
