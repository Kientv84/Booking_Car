package com.bookingcar.kientv84.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

  @Bean
  public StringRedisTemplate stringRedisTemplate(LettuceConnectionFactory factory) {
    return this.cofigureStringRedisTemplate(factory);
  }

  private StringRedisTemplate cofigureStringRedisTemplate(LettuceConnectionFactory factory) {
    final StringRedisTemplate redisTemplate = new StringRedisTemplate();
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashKeySerializer(new GenericToStringSerializer<>(String.class));
    redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
    redisTemplate.setConnectionFactory(factory);
    return redisTemplate;
  }
}
