package com.sbb.rediskafkasample.controller;

import com.sbb.rediskafkasample.dto.KafkaMessageDto;
import com.sbb.rediskafkasample.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/kafka")
@RestController
public class KafkaController {
  private final KafkaProducerService kafkaProducerService;

  @GetMapping("/init")
  public String init() {
    log.debug("init");
    return "init";
  }

  @PostMapping("/send")
  public ResponseEntity<String> sendMessage(@RequestBody KafkaMessageDto message) {
    kafkaProducerService.sendMessageDto(message);
    return ResponseEntity.ok("Message sent successfully");
  }
}
