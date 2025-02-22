package com.sbb.rediskafkasample.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaConsumerService {
  private final RedisService redisService;

  @KafkaListener(topics = "test-topic", groupId = "test-consumer-group")
  public void receiveMessage(String message) {
    log.info("Received message: {}", message);

    log.info("saving data");
    redisService.saveData("key_" + UUID.randomUUID(), message);
  }
}
