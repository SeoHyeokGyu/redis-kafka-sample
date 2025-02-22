package com.sbb.rediskafkasample.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbb.rediskafkasample.dto.KafkaMessageDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducerService {
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;

  @Value("${kafka.topic.name}")
  private String topicName;

  // 단순 메시지 전송
  public void sendMessage(String message) {
    kafkaTemplate
        .send(topicName, message)
        .thenAccept(
            result ->
                log.info(
                    "메시지 발행 성공: [{}] partition: {} offset: {}",
                    message,
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset()))
        .exceptionally(
            ex -> {
              log.error("메시지 발행 실패: [{}] error: {}", message, ex.getMessage());
              return null;
            });
  }

  // 객체를 JSON 변환하여 전송
  public void sendMessageDto(KafkaMessageDto messageDto) {
    try {
      String message = objectMapper.writeValueAsString(messageDto);

      kafkaTemplate
          .send(topicName, messageDto.id(), message)
          .thenAccept(
              result -> {
                if (result != null) {
                  log.info(
                      "Message sent to topic: {}, partition: {}, offset: {}",
                      result.getRecordMetadata().topic(),
                      result.getRecordMetadata().partition(),
                      result.getRecordMetadata().offset());
                }
              })
          .exceptionally(
              ex -> {
                log.error("메시지 DTO 발행 실패: [{}] error: {}", message, ex.getMessage());
                return null;
              });
    } catch (JsonProcessingException e) {
      log.error("Error converting message dto to JSON: {}", e.getMessage());
    }
  }

  // 비동기 전송 with Future
  public void sendMessageAsync(String key, String message) {
    kafkaTemplate
        .send(topicName, key, message)
        .toCompletableFuture()
        .whenComplete(
            (result, ex) -> {
              if (ex == null) {
                log.info("Message sent successfully: {}", message);
              } else {
                log.error("Failed to send message: {}", ex.getMessage());
              }
            });
  }

  // 배치 전송
  public void sendBatchMessages(List<KafkaMessageDto> messages) {
    messages.forEach(
        msg -> {
          try {
            String jsonMessage = objectMapper.writeValueAsString(msg);
            kafkaTemplate.send(topicName, msg.id(), jsonMessage);
          } catch (JsonProcessingException e) {
            log.error("Error converting message to JSON: {}", e.getMessage());
          }
        });
  }
}
