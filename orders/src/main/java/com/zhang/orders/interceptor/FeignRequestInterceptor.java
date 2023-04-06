package com.zhang.orders.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangjiwei1
 * @description
 * @create 2023-04-06 12:24
 */
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("xxx", "xxx");
        requestTemplate.uri("/9");
        log.info("feign 拦截器");

    }
}
