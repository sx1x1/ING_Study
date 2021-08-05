package com.sxr.study.redis.api;

import com.sxr.study.redis.util.RedisUtil;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * @author sxr
 * @date 2021/8/5 3:28 下午
 */
@Service
public class RedisApi {

    public void stringApi() {
        Jedis jedis = RedisUtil.getJedis();
        System.out.println("set===" + jedis.set("name", "sunxiran"));
        System.out.println("get===" + jedis.get("name"));
        System.out.println("getrange===" + jedis.getrange("name", 0, 3));
        System.out.println("getSet===" + jedis.getSet("name", "sxr"));
    }
}
