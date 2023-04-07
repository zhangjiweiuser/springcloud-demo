package com.zhang.gateway2.gateway;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author zhangjiwei1
 * @description
 * @create 2023-04-07 16:18
 */
@Component
public class CheckAuthRoutePredicateFactory extends AbstractRoutePredicateFactory<CheckAuthRoutePredicateFactory.Config> {
    public static final String HEADER_KEY = "name";


    public CheckAuthRoutePredicateFactory() {
        super(CheckAuthRoutePredicateFactory.Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(HEADER_KEY);
    }

    @Override
    public Predicate<ServerWebExchange> apply(CheckAuthRoutePredicateFactory.Config config) {

        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange exchange) {

                return "zhang".equals(config.getName());
            }

            @Override
            public String toString() {
                return String.format("name: %s ", config.name);
            }
        };
    }

    @Validated
    public static class Config {

        @NotEmpty
        private String name;



        public String getName() {
            return name;
        }

        public CheckAuthRoutePredicateFactory.Config setName(String name) {
            this.name = name;
            return this;
        }
    }
}

