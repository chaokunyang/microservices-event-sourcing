package com.timeyang.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author chaokunyang
 */
@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public JedisConnectionFactory redisConnectionFactory(
            @Value("${spring.redis.host}") String redisHost,
            @Value("${spring.redis.port}") Integer redisPort) {
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
        redisConnectionFactory.setHostName(redisHost);
        redisConnectionFactory.setPort(redisPort);

        return redisConnectionFactory;
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager((redisTemplate));
        cacheManager.setDefaultExpiration(10000); // 10000s后过期
        return cacheManager;
    }
}