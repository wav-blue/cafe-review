package kr.sujin.cafereview.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RedisRepositoryCustomImpl implements RedisRepository {
    private final RedisTemplate<String, String> redisTemplate;
 
    @Override
    public void setValues(String key, String value) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, value);
    }

    @Override
    public String getValues(String key){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        if (values.get(key) == null) {
            return "";
        }
        return values.get(key);
    }
 
    @Override
    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }
}
