package kr.sujin.cafereview.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RedisRepositoryCustomImpl implements RedisRepository {
    private final RedisTemplate<String, Object> redisTemplate;
 
    @Override
    public void setValues(String key, Object value) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, value);
    }

    @Override
    public Object getValues(String key){
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        if (values.get(key) == null) {
            return new ArrayList<>();
        }
        return (List<Long>) values.get(key);
    }
 
    @Override
    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }
}
