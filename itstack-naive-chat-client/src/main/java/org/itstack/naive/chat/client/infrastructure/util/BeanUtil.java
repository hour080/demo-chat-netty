package org.itstack.naive.chat.client.infrastructure.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/16 22:19
 */
public class BeanUtil {
    private static Map<String, Object> cacheMap = new ConcurrentHashMap<>();

    //synchronized防止多个线程同时添加addBean
    public synchronized static void addBean(String name, Object obj){
        cacheMap.put(name, obj);
    }

    public static <T> T getBean(String name, Class<T> tClass){
        return (T) cacheMap.get(name);
    }
}
