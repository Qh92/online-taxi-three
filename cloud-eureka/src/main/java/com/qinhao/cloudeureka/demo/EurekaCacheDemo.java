package com.qinhao.cloudeureka.demo;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 模拟Eureka readWriteCacheMap工作原理
 *
 * @author Qh
 * @version 1.0
 * @date 2021-12-04 10:30
 */
@RestController
public class EurekaCacheDemo {
    LoadingCache<String,String> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    return "load : " + new Random().nextInt(100);
                }
            });

    @PostMapping("/test-set/{id}")
    public String testSet(@PathVariable("id") String id) {
        cache.put(id, "set : " + new Random().nextInt(100));
        return "success";
    }

    @GetMapping("/test-get/{id}")
    public String testGet(@PathVariable("id") String id) {
        try {
            return cache.get(id);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
