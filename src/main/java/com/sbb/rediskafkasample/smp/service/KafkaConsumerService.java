package com.sbb.rediskafkasample.smp.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private final RedisTemplate<String, String> redisTemplate;

    public KafkaConsumerService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @KafkaListener(topics = "temp-topic", groupId = "temp-group")
    public void consume(String message) {
        // 메시지를 Redis에 저장
        redisTemplate.opsForValue().set("message-key", message);
        System.out.println("Received message: " + message);
    }
}
