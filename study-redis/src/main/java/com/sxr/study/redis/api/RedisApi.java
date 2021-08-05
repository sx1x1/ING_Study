package com.sxr.study.redis.api;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

/**
 * @author sxr
 * @date 2021/8/5 3:28 下午
 */
@Service
public class RedisApi {
    @Resource
    private Jedis jedis;

    public void stringApi() {
        System.out.println("set" + jedis.set("name", "sunxiran"));
        System.out.println("get" + jedis.get("name"));
        System.out.println("getrange" + jedis.getrange("name", 0, 3));
        System.out.println("getSet" + jedis.getSet("name", "sxr"));
    }
}
