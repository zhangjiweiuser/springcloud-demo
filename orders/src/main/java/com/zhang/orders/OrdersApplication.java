package com.zhang.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableFeignClients
public class OrdersApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(OrdersApplication.class, args);
    }

}
