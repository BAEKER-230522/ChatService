package com.example.demo.global.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReactiveRedisService {

    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    public Mono<Boolean> setKeyValue(String key, String value) {
        return reactiveRedisTemplate.opsForValue().set(key, value);
    }

    public Mono<String> getValueByKey(String key) {
        return reactiveRedisTemplate.opsForValue().get(key);
    }

    public Mono<Long> incrementValue(String key) {
        return reactiveRedisTemplate.opsForValue().increment(key);
    }

    // 수정 필요할듯
    public Mono<Long> deleteKey(String key) {
        return reactiveRedisTemplate.delete(key);
    }
}
