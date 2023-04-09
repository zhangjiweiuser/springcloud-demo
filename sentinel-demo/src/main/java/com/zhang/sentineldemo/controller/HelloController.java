package com.zhang.sentineldemo.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.Rule;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.sun.xml.internal.ws.util.CompletedFuture;
import com.zhang.sentineldemo.service.SlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author zhangjiwei1
 * @description
 * @create 2023-04-06 15:46
 */
@Slf4j
@RestController
public class HelloController {
    private static final String RESOURCE_NAME = "hello";
    private static final String USER_RESOURCE_NAME = "user";
    private static final String DEGRADE_RESOURCE_NAME = "degrade";

    private static final String SLOW_REQUEST_RESOURCE_NAME = "slow";

    private static ExecutorService service = new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));

    @Resource
    SlowService slowService;

    @RequestMapping("/hello")
    public String hello() {
        Entry entry = null;
        try {
            entry = SphU.entry(RESOURCE_NAME);
            String str = "hello world";
            log.info(str);
            return str;
        } catch (BlockException blockException) {
            log.info("被流控了");
            return "被流控了";
        } catch (Exception e) {
            Tracer.traceEntry(e, entry);
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
        return null;
    }

    @PostConstruct
    private static void initFlowRules() {
        List<FlowRule> ruleList = new ArrayList<>();
        // 流控
        FlowRule rule = new FlowRule();
        // 为哪个资源进行流控
        rule.setResource(RESOURCE_NAME);
        // 流控阈值类型，QPS
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 流量受保护的阈值
        rule.setCount(1);

        // 流控
        FlowRule rule2 = new FlowRule();
        // 为哪个资源进行流控
        rule2.setResource(USER_RESOURCE_NAME);
        // 流控阈值类型，QPS
        rule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 流量受保护的阈值
        rule2.setCount(1);
        ruleList.add(rule);
        ruleList.add(rule2);
        FlowRuleManager.loadRules(ruleList);
    }

    /**
     * 若blockHandler和fallback同时指定了，则blockHandler优先级更高
     * 默认blockHandler和fallback和源方法都在同一个类中，方法入参和出参需要一致，若不在同一个类型，则方法需要指定为static
     * exceptionsToIgnore = {ArithmeticException.class} 可以指定某些类型不被处理，需要自己捕获异常去处理
     *
     * @param name
     * @return
     */
    @RequestMapping("/user")
    @SentinelResource(value = USER_RESOURCE_NAME, blockHandler = "blockHandlerForGetUserName", fallback = "fallbackForGetUserName")
    public String getUserName(String name) {
        System.out.println("姓名:" + name);
        double t = 1 / 0;
        return "姓名:" + name;
    }

    class MyTask implements Callable<String> {

        private int i;

        public MyTask(int i) {
            this.i = i;
        }

        @Override
        public String call() throws Exception {
            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(45, 80));
            return String.valueOf(i);
        }
    }

    @RequestMapping("/slow")
    public String slow(String name) {
        List<CompletableFuture<String>> futures = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            int finalI = i;
            if (finalI == 0) {
                CompletableFuture<String> submit = CompletableFuture.supplyAsync(() -> slowService.test1(finalI), service);
                futures.add(submit);
            } else {
                CompletableFuture<String> submit = CompletableFuture.supplyAsync(() -> slowService.test2(finalI), service);
                futures.add(submit);
            }
        }
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        List<String> join = allOf.thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList())).join();
        return join.toString();
    }

    @SentinelResource(value = SLOW_REQUEST_RESOURCE_NAME, blockHandler = "blockHandlerForSlow", fallback = "fallbackForGetUserName")
    public String test1(int finalI) {
        if (finalI == 0) {
            try {
                int nextInt = ThreadLocalRandom.current().nextInt(60, 80);

                System.out.println(finalI + "-----" + nextInt);
                TimeUnit.MILLISECONDS.sleep(nextInt);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return String.valueOf(finalI);
    }

    public String blockHandlerForSlow(int finalI, BlockException e) {
        return "我被流控了:" + finalI;
    }

    @RequestMapping("/slow2")
    public String slow2(String name) {
        return test1(0);
    }

    @RequestMapping("/slow3")
    public String slow3(String name) {
        return slowService.test1(0);
    }

    public String blockHandlerForGetUserName(String name, BlockException e) {
        e.printStackTrace();
        return "被流控:" + name;
    }

    public String fallbackForGetUserName(String name, Throwable e) {
        e.printStackTrace();
        return "异常了:" + name;
    }

    @PostConstruct
    public void initDegrade() {
        List<DegradeRule> degradeRuleList = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource(DEGRADE_RESOURCE_NAME);
        // 设置降级规则，异常数
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        // 异常数
        rule.setCount(2);
        // 熔断持续时长，一旦触发熔断，则在10S内直接调用降级方法，不会去调用源方法，当10s过后，第一次请求如果扔失败，则直接会熔断，不会再经过至少2次请求
        rule.setTimeWindow(10);
        // 触发熔断最小请求数，至少需要2次
        rule.setMinRequestAmount(2);
        // 统计时长，1分钟
        rule.setStatIntervalMs(60 * 1000);
        degradeRuleList.add(rule);


        DegradeRule rule2 = new DegradeRule();
        rule2.setResource(SLOW_REQUEST_RESOURCE_NAME);
        // 设置降级规则，异常数
        rule2.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        // 允许请求的最大延迟50ms
        rule2.setCount(50);
        // 熔断持续时长，一旦触发熔断，则在10S内直接调用降级方法，不会去调用源方法，当10s过后，第一次请求如果扔失败，则直接会熔断，不会再经过至少2次请求
        rule2.setTimeWindow(10);
        // 触发熔断最小请求数，至少需要2次
        rule2.setMinRequestAmount(2);
        // 慢调用阈值
        rule2.setSlowRatioThreshold(0.6);
        // 统计时长，1分钟
        rule2.setStatIntervalMs(10 * 1000);
        degradeRuleList.add(rule2);
        DegradeRuleManager.loadRules(degradeRuleList);
    }

    @RequestMapping("/degrade")
    @SentinelResource(value = DEGRADE_RESOURCE_NAME, entryType = EntryType.IN, blockHandler = "degradeBlockHandler", fallback = "fallbackForGetUserName")
    public String degrade(String name) {
        throw new RuntimeException("异常");
    }

    public String degradeBlockHandler(String name, BlockException e) {
        e.printStackTrace();
        return "我被熔断了";
    }
}
