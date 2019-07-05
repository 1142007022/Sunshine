package com.jiangdong.sunshine.cache;

import com.jiangdong.sunshine.entity.Entity;

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
    private static Long NO_TIME = 0L;

    /**
     * 键值对集合
     */
    private static final Map<String, Entity> map = new ConcurrentHashMap<>();
    /**
     * 定时器线程池，用于清除过期缓存
     */
    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public static void put(String key, Object data) {
        CacheManager.put(key, data, NO_TIME);
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
            map.put(key, new Entity(data, future));
        } else {
            map.put(key, new Entity(data, null));
        }
    }

    public static <T> T get(String key) {
        Entity entity = map.get(key);
        return entity == null ? null : (T) entity.value;
    }

    public static <T> T remove(String key) {
        //清除原缓存数据
        Entity entity = map.remove(key);
        if (entity == null) {
            return null;
        }
        //清除原键值对定时器
        if (entity.future != null) {
            entity.future.cancel(true);
        }
        return (T) entity.value;
    }

    public static Integer size() {
        return map.size();
    }

}
