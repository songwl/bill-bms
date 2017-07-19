package com.yipeng.bill.bms.service;

/**
 * Created by Administrator on 2017/7/18.
 */
public interface JedisClientService {

    /**
     * 获取string缓存值
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 设置string缓存值
     * @param key
     * @param value
     * @param second
     * @return
     */
    String set(String key, String value);

    /**
     * 设置string缓存值带失效时间
     * @param key
     * @param value
     * @param second
     * @return
     */
    String set(String key, String value, int second);

    /**
     * 获取hash缓存值
     * @param hkey
     * @param key
     * @return
     */
    String hget(String hkey, String key);

    /**
     * 设置hash缓存值
     * @param hkey
     * @param key
     * @param value
     * @return
     */
    long hset(String hkey, String key, String value);

    /**
     * 递增缓存值
     * @param key
     * @return
     */
    long incr(String key);

    /**
     * 设置缓存值有效时间
     * @param key
     * @param second
     * @return
     */
    long expire(String key, int second);

    /**
     * 获取缓存值失效时间
     * @param key
     * @return
     */
    long ttl(String key);

    /**
     * 删除string缓存值
     * @param key
     * @return
     */
    long del(String key);

    /**
     * 批量删除以string为前缀的key
     * @param key
     * @return
     */
    void batchDel(String key);

    /**
     * 删除hash缓存值
     * @param hkey
     * @param key
     * @return
     */
    long hdel(String hkey, String key);

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    boolean exists(String key);

    /**
     * 清空缓存数据
     * @return
     */
    boolean clear();
}
