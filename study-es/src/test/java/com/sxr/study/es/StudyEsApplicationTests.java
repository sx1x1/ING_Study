package com.sxr.study.es;

import com.sxr.study.es.api.Operation;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class StudyEsApplicationTests {

    @Resource
    private Operation operation;

    @Test
    void contextLoads() {
    }

    @Test
    void queryTest(){
        operation.query();
    }

}
