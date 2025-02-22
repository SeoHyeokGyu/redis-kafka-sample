package com.sbb.rediskafkasample.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisService {
  private final RedisTemplate<String, Object> redisTemplate;

  // 데이터 저장
  public void saveData(String key, String value) {
    redisTemplate.opsForValue().set(key, value);
  }

  // 데이터 조회
  public String getData(String key) {
    return (String) redisTemplate.opsForValue().get(key);
  }

  // 데이터 삭제
  public void deleteData(String key) {
    redisTemplate.delete(key);
  }
}
