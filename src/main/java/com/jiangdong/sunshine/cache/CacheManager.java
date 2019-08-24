package com.jiangdong.sunshine.cache;

import com.jiangdong.sunshine.entity.CacheEntity;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @program: Sunshine
 * @description: 缓存工具类
 * @author: JD
 * @create: 2019-07-05 11:32
 **/
public class CacheManager {

    /**
     * 设置过期时间若为0 则不过期
     */
    private static Long NO_OVER_DATE = 0L;

    /**
     * 键值对集合
     */
    private static final Map<String, CacheEntity> map = new ConcurrentHashMap<>();
    /**
     * 定时器线程池，用于清除过期缓存
     */
    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public static void put(String key, Object data) {
        CacheManager.put(key, data, NO_OVER_DATE);
    }

    public static void put(String key, Object data, long time) {
        CacheManager.remove(key);
        if (time > 0) {
            Future future = executor.schedule(new Runnable() {
                @Override
                public void run() {
                    map.remove(key);
                }
            }, time, TimeUnit.MILLISECONDS);
            map.put(key, new CacheEntity(data, future));
        } else {
            map.put(key, new CacheEntity(data, null));
        }
    }

    public static <T> T get(String key) {
        CacheEntity cacheEntity = map.get(key);
        return cacheEntity == null ? null : (T) cacheEntity.value;
    }

    public static <T> T remove(String key) {
        //清除原缓存数据
        CacheEntity cacheEntity = map.remove(key);
        if (cacheEntity == null) {
            return null;
        }
        //清除原键值对定时器
        if (cacheEntity.future != null) {
            cacheEntity.future.cancel(true);
        }
        return (T) cacheEntity.value;
    }

    public static Integer size() {
        return map.size();
    }

}
