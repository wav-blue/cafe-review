package kr.sujin.cafereview.repository;

public interface RedisRepository {
    void setValues(String key, String value);

    String getValues(String key);
 
    void deleteValue(String key);
}
