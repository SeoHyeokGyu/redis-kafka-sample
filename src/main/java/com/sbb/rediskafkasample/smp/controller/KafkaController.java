package com.sbb.rediskafkasample.smp.controller;

import com.sbb.rediskafkasample.smp.service.KafkaProducerService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaProducerService kafkaProducerService;
    private final RedisTemplate<String, String> redisTemplate;

    public KafkaController(KafkaProducerService kafkaProducerService, RedisTemplate<String, String> redisTemplate) {
        this.kafkaProducerService = kafkaProducerService;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/send")
    public String sendMessageToKafka(String message) {
        kafkaProducerService.sendMessage("test-topic", message);
        return "Message sent to Kafka!";
    }

    @GetMapping("/get")
    public String getMessageFromRedis() {
        return redisTemplate.opsForValue().get("message-key");
    }
}
