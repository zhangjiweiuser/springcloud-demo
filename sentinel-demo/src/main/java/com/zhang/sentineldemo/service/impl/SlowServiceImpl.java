package com.zhang.sentineldemo.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.zhang.sentineldemo.service.SlowService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class SlowServiceImpl implements SlowService {
    private static final String SLOW_REQUEST_RESOURCE_NAME = "slow";

    @SentinelResource(value = SLOW_REQUEST_RESOURCE_NAME, blockHandler = "blockHandlerForSlow", fallback = "fallbackForSlow")
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

    public String test2(int finalI) {
        return String.valueOf(finalI);
    }
    public String blockHandlerForSlow(int finalI, BlockException e) {
        return "我被流控了:" + finalI;
    }

    public String fallbackForSlow(int finalI, Throwable e) {
        e.printStackTrace();
        return "异常了:" + finalI;
    }
}
