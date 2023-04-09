package com.zhang.sentineldemo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class CompleteFutureTest {
    private static ExecutorService service = new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));


    public static void main(String[] args) {
        List<CompletableFuture<String>> futures = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            int finalI = i;
            if (finalI == 0) {
                CompletableFuture<String> submit = CompletableFuture.supplyAsync(() -> test1(finalI), service);
                futures.add(submit);
            } else {
                CompletableFuture<String> submit = CompletableFuture.supplyAsync(() -> test2(finalI), service);
                futures.add(submit);
            }
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        String collect = futures.stream().map(t -> {
            try {
                return t.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.joining(","));
        System.out.println(collect);
        service.shutdown();
//        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
//        List<String> join = allOf.thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList())).join();
//        System.out.println(join.toString());
    }

    private static String test1(int i) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "aa";
    }
    private static String test2(int i){
        return "bb";
    }
}
