package com.sbb.rediskafkasample.config;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaConsumerFactoryCustomizer;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Slf4j
@Configuration
public class KafkaProducerConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  // 자바 소스에서 설정하는 방법(application.yml에서도 설정 가능)
  public ProducerFactory<String, String> producerFactory() {
    Map<String, Object> config = new HashMap<>();

    // 필수 설정
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

    // 추가 설정 (선택사항)
    config.put(ProducerConfig.ACKS_CONFIG, "all");
    config.put(ProducerConfig.RETRIES_CONFIG, 3);

    return new DefaultKafkaProducerFactory<>(config);
  }

  //  @Bean
  public KafkaTemplate<String, String> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }

  @Bean
  public DefaultKafkaProducerFactoryCustomizer kafkaProducerFactoryCustomizer() {
    return kafkaProducerFactory -> {
      Map<String, Object> configurationProperties =
          kafkaProducerFactory.getConfigurationProperties();
      log.debug(
          "KafkaProducerFactoryCustomizer configurationProperties: {}", configurationProperties);
    };
  }

  @Bean
  public DefaultKafkaConsumerFactoryCustomizer kafkaConsumerFactoryCustomizer() {
    return kafkaConsumerFactory -> {
      Map<String, Object> configurationProperties =
          kafkaConsumerFactory.getConfigurationProperties();
      log.debug(
          "kafkaConsumerFactoryCustomizer configurationProperties: {}", configurationProperties);
    };
  }
}
