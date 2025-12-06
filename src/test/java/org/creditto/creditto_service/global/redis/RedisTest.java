package org.creditto.creditto_service.global.redis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void testRedisConnection() {
        String key = "test-key";
        Object value = "test-value";

        redisTemplate.opsForValue().set(key, value);
        Object expectedValue = redisTemplate.opsForValue().get(key);
        Assertions.assertEquals(value, expectedValue);
    }
}
