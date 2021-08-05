package com.sxr.study.redis.api;

import com.sxr.study.redis.util.RedisUtil;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ListPosition;

import java.util.HashMap;
import java.util.Map;

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
        System.out.println("mset===" + jedis.mset("key1", "value1", "key2", "value2"));
        System.out.println("mget===" + jedis.mget("key1", "key2"));
        System.out.println("setnx===" + jedis.setnx("key1", "v1"));
        System.out.println("setex===" + jedis.setex("k1", 10L, "v1"));
        System.out.println("setrange===" + jedis.setrange("name", 5, "xi"));
        jedis.flushDB();
    }

    public void listApi() {
        Jedis jedis = RedisUtil.getJedis();
        System.out.println("left--" + jedis.lpush("left", "1", "2", "3"));
        System.out.println("left--" + jedis.lrange("left", 0, -1));
        System.out.println("right--" + jedis.rpush("right", "1", "2", "3"));
        System.out.println("right--" + jedis.lrange("right", 0, -1));
        System.out.println("lpop--" + jedis.lpop("left"));
        System.out.println("llen--" + jedis.llen("left"));
        System.out.println("rpop--" + jedis.rpop("right"));
        System.out.println("lrem--" + jedis.lrem("right", 1, "2"));
        System.out.println("lindex--" + jedis.lindex("right", 1));
        System.out.println("lset--" + jedis.lset("right", 1, "22"));
        System.out.println("ltrim--" + jedis.ltrim("right", 0, 1));
        System.out.println("linsert--" + jedis.linsert("right", ListPosition.BEFORE, "22", "11"));
        System.out.println("rpoplpush--" + jedis.rpoplpush("right", "left"));
        jedis.flushDB();
    }

    public void hashApi() {
        Jedis jedis = RedisUtil.getJedis();
        Map<String, String> map = new HashMap<>();
        map.put("name", "sxr");
        map.put("age", "18");
        System.out.println("hset--" + jedis.hset("user", map));
        System.out.println("hget--" + jedis.hget("user", "name"));
        System.out.println("hgetAll" + jedis.hgetAll("user"));
        System.out.println("hexists" + jedis.hexists("user", "age"));
        System.out.println("hsetnx" + jedis.hsetnx("user", "address", "abc"));
    }


}
