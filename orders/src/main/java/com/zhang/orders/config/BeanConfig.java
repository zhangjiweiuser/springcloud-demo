package com.zhang.orders.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhangjiwei1
 * @description
 * @create 2023-04-04 17:05
 */
@Configuration
public class BeanConfig {

//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplate(RestTemplateBuilder builder){
//        RestTemplate restTemplate = builder.build();
//        return restTemplate;
//    }
}
