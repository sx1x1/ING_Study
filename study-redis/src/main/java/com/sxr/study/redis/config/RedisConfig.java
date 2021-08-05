package com.sxr.study.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

/**
 * @author sxr
 * @date 2021/8/5 3:48 下午
 */
@Configuration
public class RedisConfig {
    @Bean
    public Jedis getJedis(){
        Jedis jedis = new Jedis("localhost",6379);
        return jedis;
    }
}
