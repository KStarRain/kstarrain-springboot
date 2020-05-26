package com.kstarrain.provider.utils;

import org.springframework.data.redis.core.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author: DongYu
 * @create: 2020-05-25 16:56
 * @description:
 */
public class RedisUtils {

    private static StringRedisTemplate stringRedisTemplate;
    private static ValueOperations<String, String> stringOperations;
    private static HashOperations<String, String, String> hashOperations;
    private static ListOperations<String, String> listOperations;
    private static SetOperations<String, String> setOperations;
    private static ZSetOperations<String, String> zSetOperations;

    public static void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        RedisUtils.stringRedisTemplate = stringRedisTemplate;
        RedisUtils.stringOperations = stringRedisTemplate.opsForValue();
        RedisUtils.hashOperations = stringRedisTemplate.opsForHash();
        RedisUtils.listOperations = stringRedisTemplate.opsForList();
        RedisUtils.setOperations = stringRedisTemplate.opsForSet();
        RedisUtils.zSetOperations = stringRedisTemplate.opsForZSet();
    }

    /** ============================= Common ============================ */
    /**
     * 指定key失效时间
     * @param key 键
     * @param time 时间
     * @param timeUnit 时间单位
     */
    public static void expire(String key, long time, TimeUnit timeUnit) {
        stringRedisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 根据key获取过期时间
     * @param key 键
     * @return 时间(单位:秒) 返回0代表为永久有效
     */
    public static long getExpire(String key) {
        return stringRedisTemplate.getExpire(key);
    }

    /**
     * 根据key获取过期时间
     * @param key 键
     * @param timeUnit 时间单位
     * @return 时间 返回0代表为永久有效
     */
    public static long getExpire(String key, TimeUnit timeUnit ) {
        return stringRedisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public static boolean exists(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 删除key
     * @param key 可以传一个值 或多个
     */
    public static void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                stringRedisTemplate.delete(key[0]);
            } else {
                stringRedisTemplate.delete(Arrays.asList(key));
            }
        }
    }
    /** ============================ String ============================= */
    /**
     * [String]根据key获取value
     * @param key 键
     * @return 值
     */
    public static String get(String key) {
        return stringOperations.get(key);
    }
    /**
     * [String]设置key和value
     * @param key 键
     * @param value 值
     */
    public static void set(String key, String value) {
        stringOperations.set(key, value);
    }
    /**
     * [String]设置key和value并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public static void set(String key, String value, long time, TimeUnit timeUnit) {
        stringOperations.set(key, value, time, timeUnit);
    }
    /**
     * [String]递增/减
     * @param key 键
     * @param increment 要增加/减少的数  （减为负数）
     * @return
     */
    public static long incrBy(String key, long increment) {
        return stringOperations.increment(key, increment);
    }


    /** ================================ Hash ================================= */
    /**
     * [Hash]根据key获取hash
     * @param key 键
     * @return 对应多个键值
     */
    public static Map<String, String> hGetAll(String key) {
        return hashOperations.entries(key);
    }

    /**
     * [Hash]设置key的hash
     * @param key 键
     * @param hash 对应多个键值
     */
    public static void hmSet(String key, Map<String, String> hash) {
        hashOperations.putAll(key, hash);
    }

    /**
     * [Hash]设置key的hash并设置时间
     * @param key 键
     * @param hash 对应多个键值
     * @param time 时间
     * @param timeUnit 时间单位
     */
    public static void hmSet(String key, Map<String, String> hash, long time, TimeUnit timeUnit) {
        hashOperations.putAll(key, hash);
        expire(key, time, timeUnit);
    }

    /**
     * [Hash]根据key以及hash中的field项获取value
     * @param key 键 不能为null
     * @param field 项 不能为null
     * @return 值
     */
    public static String hGet(String key, String field) {
        return hashOperations.get(key, field);
    }

    /**
     * [Hash]向key的hash表中添加field-value（如果hash表不存在,就会创建）
     * @param key 键
     * @param field 项
     * @param value 值
     */
    public static void hSet(String key, String field, String value) {
        hashOperations.put(key, field, value);
    }

    /**
     * [Hash]向key的hash表中添加field-value并设置时间（如果hash表不存在,就会创建）
     * @param key 键
     * @param field 项
     * @param value 值
     * @param time 时间 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @param timeUnit 时间单位
     */
    public static void hSet(String key, String field, String value, long time, TimeUnit timeUnit) {
        hashOperations.put(key, field, value);
        expire(key, time, timeUnit);
    }

    /**
     * [Hash]删除指定key的hash表中field的值
     * @param key 键 不能为null
     * @param field 项 可以是多个 不能为null
     */
    public static void hDel(String key, String... field) {
        hashOperations.delete(key, field);
    }

    /**
     * [Hash]判断指定key的hash表中是否有field的值
     * @param key 键 不能为null
     * @param field 项 不能为null
     * @return true 存在 false不存在
     */
    public static boolean hExists(String key, String field) {
        return hashOperations.hasKey(key, field);
    }

    /**
     * [Hash]key中hash表的field值递增 （如果不存在,就会创建一个 并把新增后的值返回）
     * @param key 键
     * @param field 项
     * @param increment 要增加/减少的数  （减为负数）
     * @return
     */
    public static long hIncrBy(String key, String field, long increment) {
        return hashOperations.increment(key, field, increment);
    }


    /** =============================== List ================================= */
    /**
     * [List]根据key以及index区间获取list （start=0，end=-1 代表所有值）
     * @param key 键
     * @param start 开始
     * @param end 结束
     * @return
     */
    public static List<String> lRange(String key, long start, long end) {
        return listOperations.range(key, start, end);
    }

    /**
     * [List]根据key获取list的长度
     * @param key 键
     * @return
     */
    public static long lLen(String key) {
        return listOperations.size(key);
    }

    /**
     * [List]根据key以及index获取list中的值
     * @param key 键
     * @param index 索引 (index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推)
     * @return
     */
    public static String lIndex(String key, long index) {
        return listOperations.index(key, index);
    }

    /**
     * [List]设置key的list，将值插入到list尾部
     * @param key 键
     * @param value 值
     * @return
     */
    public static void rPush(String key, String value) {
        listOperations.rightPush(key, value);
    }

    /**
     * [List]设置key的list，将值插入到list尾部并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @param timeUnit 时间单位
     */
    public static void rPush(String key, String value, long time, TimeUnit timeUnit) {
        listOperations.rightPush(key, value);
        expire(key, time, timeUnit);
    }

    /**
     * [List]设置key和list
     * @param key 键
     * @param value 值
     * @return
     */
    public static void rPushAll(String key, List<String> value) {
        listOperations.rightPushAll(key, value);
    }

    /**
     * [List]设置key和list并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间
     * @param timeUnit  时间单位
     * @return
     */
    public static void rPushAll(String key, List<String> value, long time, TimeUnit timeUnit) {
        listOperations.rightPushAll(key, value);
        expire(key, time, timeUnit);
    }

    /**
     * [List]设置key中list的index对应的值
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return
     */
    public static void lSet(String key, long index, String value) {
        listOperations.set(key, index, value);
    }
    /**
     * 移除N个值为value
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public static void lRem(String key, long count, String value) {
       listOperations.remove(key, count, value);
    }

    // ============================set=============================
    /**
     * 根据key获取Set中的所有值
     * @param key 键
     * @return
     */
    public static Set<String> smembers(String key) {
        return setOperations.members(key);
    }

    /**
     * 根据value从一个set中查询,是否存在
     * @param key 键
     * @param value 值
     * @return true 存在 false不存在
     */
    public static boolean sismember(String key, String value) {
        return setOperations.isMember(key, value);
    }

    /**
     * 将数据放入set缓存
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static void sadd(String key, String... values) {
        setOperations.add(key, values);
    }
    /**
     * 将set数据放入缓存
     * @param key 键
     * @param time 时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static void sadd(String key, long time, TimeUnit timeUnit, String... values) {
        setOperations.add(key, values);
        expire(key, time, timeUnit);
    }
    /**
     * 获取set缓存的长度
     * @param key 键
     * @return
     */
    public static long scard(String key) {
        return setOperations.size(key);
    }
    /**
     * 移除值为value的
     * @param key 键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public static void srem(String key, Object... values) {
        setOperations.remove(key, values);
    }


}
