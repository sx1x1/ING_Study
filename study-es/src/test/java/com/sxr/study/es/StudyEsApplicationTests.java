package com.sxr.study.es;

import com.sxr.study.es.api.DocOperation;
import com.sxr.study.es.api.IndexOperation;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class StudyEsApplicationTests {

    @Resource
    private DocOperation docOperation;

    @Resource
    private IndexOperation indexOperation;

    @Test
    void contextLoads() {
    }

    @Test
    void queryTest(){
        docOperation.query();
    }

    @Test
    void creatIndexTest(){
        indexOperation.creatIndex();
    }

    @Test
    void deleteIndexTest(){
        indexOperation.deleteIndex();
    }

}
