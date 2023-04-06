package com.zhang.orders.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @author zhangjiwei1
 * @description
 * @create 2023-04-06 11:05
 */
//@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level feginLoggerLevel() {
        return Logger.Level.FULL;
    }
}
