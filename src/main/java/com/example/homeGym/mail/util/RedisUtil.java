package com.example.homeGym.mail.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class RedisUtil {
    //인증은 String형식으로 진행되기에 의존성 주입
    private final StringRedisTemplate template;

    //key로 value를 가져오는 메소드
    public String getData(String key){
        ValueOperations<String, String> valueOperations = template.opsForValue();
        return valueOperations.get(key);
    }

    //해당 key에 해당 value가 존재하는지 확인
    public boolean existData(String key){
        return Boolean.TRUE.equals(template.hasKey(key));
    }

    //key, value, duration 설정
    public void setDataExpire(String key, String value, Long duration){
        ValueOperations<String, String> valueOperations = template.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public void deleteData(String key){
        template.delete(key);
    }

}
