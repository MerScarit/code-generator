package com.scarit.web.manager;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


/**
 * 多级缓存
 *
 * @author ADI
 */
@Component
public class CacheManager {

    //    @Resource
//    StringRedisTemplate stringRedisTemplate;
//
//    Cache<String, String> localCache = Caffeine.newBuilder()
//            .expireAfterWrite(1, TimeUnit.MINUTES)
//            .maximumSize(10_000)
//            .build();
    // 缓存,将 value 直接存储为JSON对象
    @Resource
    RedisTemplate<String, Object> redisTemplate;

    Cache<String, Object> localCache = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .maximumSize(10_000)
            .build();


    /**
     * 写入缓存
     */
    public void put(String key, Object value) {
        localCache.put(key, value);
//        stringRedisTemplate.opsForValue().set(key, value,100, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(key, value,100, TimeUnit.MINUTES);
    }

    /**
     * 读取缓存
     */
//    public String get(String key) {
    public Object get(String key) {
        // 查找一个缓存元素， 没有查找到的时候返回null
        Object value = localCache.getIfPresent(key);
        if (value != null) {
            return value;
        }

        // 本地缓存没有命中，尝试从 redis 获取
        value = redisTemplate.opsForValue().get(key);
//        value = stringRedisTemplate.opsForValue().get(key);
        if (value != null) {
            // 将 Redis 缓存存到本地缓存
            localCache.put(key, value);

        }
        return value;
    }

    /**
     *  清理缓存
     */
    public void delete(String key) {

        // 移除一个缓存元素
        localCache.invalidate(key);
        redisTemplate.delete(key);
//        stringRedisTemplate.delete(key);
    }

}
