package com.kstarrain.provider.springboot;


import com.kstarrain.provider.constants.Constants;
import com.kstarrain.provider.enums.AliveFlagEnum;
import com.kstarrain.provider.persistence.entities.User;
import com.kstarrain.provider.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: DongYu
 * @create: 2020-04-16 14:46
 * @description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUtilsTest {

    private User createUser() {

        User user = new User();
        user.setId(1);
        user.setName("貂蝉");
        user.setAge(20);
        user.setBirthday(new Date());
        user.setDeposit(new BigDecimal("521.34"));
        user.setCreateUser(Constants.SYSTEM);
        user.setCreateDate(new Date());
        user.setUpdateUser(Constants.SYSTEM);
        user.setUpdateDate(new Date());
        user.setAliveFlag(AliveFlagEnum.ENABLED.getCode());

        return user;

    }

    @Test
    public void testString(){

        String key = "String";
        RedisUtils.del(key);
        System.out.println(RedisUtils.exists(key));
        RedisUtils.set(key, "貂蝉");
        System.out.println(RedisUtils.exists(key));
        System.out.println(RedisUtils.get(key));
        System.out.println(RedisUtils.getExpire(key));

        RedisUtils.set(key, "吕布", 1 , TimeUnit.DAYS);
        System.out.println(RedisUtils.get(key));
        System.out.println(RedisUtils.getExpire(key));
        System.out.println(RedisUtils.getExpire(key, TimeUnit.HOURS));


        String key2 = "String-incr";
        RedisUtils.del(key2);
        System.out.println(RedisUtils.incrBy(key2, 8));
        System.out.println(RedisUtils.incrBy(key2, -1));

    }

    @Test
    public void testHash(){

        String key = "Hash";
        RedisUtils.del(key);
        System.out.println(RedisUtils.exists(key));
        System.out.println(RedisUtils.hExists(key, "貂蝉"));
        RedisUtils.hSet(key, "貂蝉","女");
        System.out.println(RedisUtils.hExists(key, "貂蝉"));
        System.out.println(RedisUtils.hGet(key, "貂蝉"));
        System.out.println(RedisUtils.getExpire(key));
        RedisUtils.hSet(key, "貂蝉","女", 1, TimeUnit.DAYS);
        System.out.println(RedisUtils.getExpire(key));
        System.out.println(RedisUtils.getExpire(key, TimeUnit.MINUTES));

        Map<String, String> map = new HashMap<>();
        map.put("吕布", "男");
        RedisUtils.hmSet(key, map);

        System.out.println(RedisUtils.hGetAll(key));

        RedisUtils.hDel(key, "貂蝉");
        System.out.println(RedisUtils.hGetAll(key));
        RedisUtils.hmSet(key, map, 1, TimeUnit.DAYS);

        RedisUtils.hIncrBy(key, "美女", 8);
        RedisUtils.hIncrBy(key, "美女", -3);


    }




}
