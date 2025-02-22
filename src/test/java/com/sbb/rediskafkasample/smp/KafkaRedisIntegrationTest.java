package com.sbb.rediskafkasample.smp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@EmbeddedKafka
public class KafkaRedisIntegrationTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testKafkaToRedisIntegration() {
        // given
        String topic = "temp-topic";
        String message = "Hello Kafka to Redis!";
        String redisKey = "message-key";

        // when: Kafka에 메시지 보내기
        kafkaTemplate.send(topic, message);

        // KafkaConsumerService가 메시지를 받아 Redis에 저장하도록 실행
        // Consumer가 실행되고 Redis에 값이 저장되었는지 확인

        // then: Redis에서 해당 값 확인
        String redisValue = redisTemplate.opsForValue().get(redisKey);
        assertEquals(message, redisValue);  // 메시지가 Redis에 저장되었는지 확인
    }
}

