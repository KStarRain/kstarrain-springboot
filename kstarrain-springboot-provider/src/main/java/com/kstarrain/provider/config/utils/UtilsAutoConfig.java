package com.kstarrain.provider.config.utils;

import com.kstarrain.provider.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author: DongYu
 * @create: 2019-12-25 11:33
 * @description:
 */
@Configuration
public class UtilsAutoConfig {

    @Autowired
    public UtilsAutoConfig(StringRedisTemplate stringRedisTemplate) {
        // Redis工具类
        RedisUtils.setStringRedisTemplate(stringRedisTemplate);
    }
}
