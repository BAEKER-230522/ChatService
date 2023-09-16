package com.example.demo.global.util.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisUtil {

    private final ReactiveRedisTemplate reactiveRedisTemplate;

    public <T> Mono<Long> getExpire(T key) {
        return reactiveRedisTemplate.getExpire(key);
    }

    public <T> Mono<Boolean> hasValue(T key) {
        return reactiveRedisTemplate.opsForValue().get(key)
                .map(value -> value != null);
    }

    public <T> Mono<String> getValue(T key) {
        return reactiveRedisTemplate.opsForValue().get(key.toString());
    }

    public <T> Mono<Boolean> setValue(T key, String value, Duration timeout) {
        return reactiveRedisTemplate.opsForValue().set(key.toString(), value, timeout);
    }

    public <T> void delete(T key) {
        reactiveRedisTemplate.delete(key.toString());
    }
}
