package com.bookingcar.kientv84.services.impls;

import com.bookingcar.kientv84.services.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisServiceImpl implements RedisService {

  private final StringRedisTemplate redisTemplate;
  private final ObjectMapper objectMapper;

  private final String EROR_CONVERTING_MSG = "Error while converting JSON";

  @Override
  public <T> T getValue(String key, Class<T> valueType) {
    T t = null;
    try {
      var dataJson = redisTemplate.opsForValue().get(key);
      if (StringUtils.isNotEmpty(dataJson)) {
        t = objectMapper.readValue(dataJson, valueType);
      }
    } catch (JsonProcessingException e) {
      log.error(EROR_CONVERTING_MSG, e);
    }
    return t;
  }

  @Override
  public void deleteByKey(@NotNull String key) {
    redisTemplate.delete(key);
  }

  @Override
  public <T> void setValue(String key, T data) {
    try {
      String dataJson = objectMapper.writeValueAsString(data);

      redisTemplate.opsForValue().set(key, dataJson);
    } catch (JsonProcessingException e) {
      log.error(EROR_CONVERTING_MSG, e);
    }
  }

  @Override
  public <T> void setValue(String key, T data, int expireDuration) {
    try {
      String dataJson = objectMapper.writeValueAsString(data);

      redisTemplate.opsForValue().set(key, dataJson, expireDuration, TimeUnit.SECONDS);
    } catch (JsonProcessingException e) {
      log.error(EROR_CONVERTING_MSG, e);
    }
  }
}
