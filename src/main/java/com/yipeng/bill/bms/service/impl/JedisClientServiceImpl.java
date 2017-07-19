package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.service.JedisClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by Administrator on 2017/7/18.
 */
@Service
public class JedisClientServiceImpl implements JedisClientService {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 获取string缓存值
     * @param key
     * @return
     */
    @Override
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String string = jedis.get(key);
        jedis.close();
        return string;
    }

    /**
     * 设置string缓存值
     * @param key
     * @param value
     * @param second
     * @return
     */
    @Override
    public String set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        String string = jedis.set(key, value);
        jedis.close();
        return string;
    }

    /**
     * 设置string缓存值带失效时间
     * @param key
     * @param value
     * @param second
     * @return
     */
    @Override
    public String set(String key, String value, int seconds) {
        Jedis jedis = jedisPool.getResource();
        String string = jedis.set(key, value);
        jedis.expire(key, seconds);
        jedis.close();
        return string;
    }

    /**
     * 获取hash缓存值
     * @param hkey
     * @param key
     * @return
     */
    @Override
    public String hget(String hkey, String key) {
        Jedis jedis = jedisPool.getResource();
        String string = jedis.hget(hkey, key);
        jedis.close();
        return string;
    }

    /**
     * 设置hash缓存值
     * @param hkey
     * @param key
     * @param value
     * @return
     */
    @Override
    public long hset(String hkey, String key, String value) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hset(hkey, key, value);
        jedis.close();
        return result;
    }

    /**
     * 递增缓存值
     * @param key
     * @return
     */
    @Override
    public long incr(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.incr(key);
        jedis.close();
        return result;
    }

    /**
     * 设置缓存值有效时间
     * @param key
     * @param second
     * @return
     */
    @Override
    public long expire(String key, int second) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.expire(key, second);
        jedis.close();
        return result;
    }

    /**
     * 获取缓存值失效时间
     * @param key
     * @return
     */
    @Override
    public long ttl(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.ttl(key);
        jedis.close();
        return result;
    }

    /**
     * 删除string缓存值
     * @param key
     * @return
     */
    @Override
    public long del(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.del(key);
        jedis.close();
        return result;
    }

    /**
     * 批量删除以string为前缀的key
     * @param key
     * @return
     */
    @Override
    public void batchDel(String key) {
        Jedis jedis = jedisPool.getResource();
        Set<String> set = jedis.keys(key+"*");
        Iterator<String> it = set.iterator();
        while(it.hasNext()){
            String keyStr = it.next();
            jedis.del(keyStr);
        }
        jedis.close();
    }

    /**
     * 删除hash缓存值
     * @param hkey
     * @param key
     * @return
     */
    @Override
    public long hdel(String hkey, String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hdel(hkey, key);
        jedis.close();
        return result;
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    @Override
    public boolean exists(String key) {
        Jedis jedis = jedisPool.getResource();
        boolean exists = jedis.exists(key);
        jedis.close();
        return exists;
    }

    /**
     * 清空缓存数据
     * @return
     */
    @Override
    public boolean clear() {
        Jedis jedis = jedisPool.getResource();
        jedis.flushAll();
        jedis.close();
        return true;
    }
}
