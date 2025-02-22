package com.sbb.rediskafkasample.dto;

import java.time.LocalDateTime;

public record KafkaMessageDto(String id, String content, String timestamp) {}
