package com.sxr.study.redis;

import com.sxr.study.redis.api.RedisApi;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class StudyRedisApplicationTests {

    @Resource
    private RedisApi redisApi;

    @Test
    void contextLoads() {
    }

    @Test
    void stringApiTest(){
        redisApi.stringApi();
    }
    @Test
    void listApiTest(){
        redisApi.listApi();
    }
}
