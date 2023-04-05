package com.zhang.orders.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CustomRule extends AbstractLoadBalancerRule {
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    public Server choose(Object key) {
        ILoadBalancer loadBalancer = this.getLoadBalancer();
        // 获取当前请求的服务的实例
        List<Server> reachableServers = loadBalancer.getReachableServers();
        int i = ThreadLocalRandom.current().nextInt(0, reachableServers.size());
        return reachableServers.get(i);
    }
}
