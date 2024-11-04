package kr.sujin.cafereview.repository;

public interface RedisRepository {
    void setValues(String key, Object value);

    Object getValues(String key);
 
    void deleteValue(String key);
}
