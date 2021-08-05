package com.sxr.study.redis.util;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * @author sxr
 * @date 2021/8/5 3:48 下午
 */
@Component
public class RedisUtil {
    private static Jedis jedis;

    public static Jedis getJedis(){
        jedis = new Jedis("localhost",6379);
        return jedis;
    }
}
