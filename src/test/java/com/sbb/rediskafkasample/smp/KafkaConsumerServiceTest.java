package com.sbb.rediskafkasample.smp;

import com.sbb.rediskafkasample.smp.service.KafkaConsumerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.core.ConsumerFactory;
import static org.mockito.Mockito.*;

@EnableKafka
@EmbeddedKafka
public class KafkaConsumerServiceTest {

    @Mock
    private RedisTemplate<String, String> redisTemplate;  // RedisTemplate을 모의 객체로 사용

    @InjectMocks
    private KafkaConsumerService kafkaConsumerService;  // KafkaConsumerService에 모의 RedisTemplate 주입

    @BeforeEach
    public void setUp() {
        // Mockito 초기화
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConsumeMessage() {
        // given
        String message = "Hello Redis";
        String redisKey = "message-key";

        // when
        kafkaConsumerService.consume(message);

        // then
        // Redis에 메시지가 저장되었는지 확인
        verify(redisTemplate, times(1)).opsForValue().set(redisKey, message);
    }
}

