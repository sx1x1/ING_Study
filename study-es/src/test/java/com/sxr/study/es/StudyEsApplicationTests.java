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

    // 以下为索引操作测试
    @Test
    void creatIndexTest(){
        indexOperation.creatIndex();
    }

    @Test
    void getIndexTest(){
        indexOperation.getIndex();
    }

    @Test
    void deleteIndexTest(){
        indexOperation.deleteIndex();
    }

    // -------------------------------------
    // 以下为文档操作测试
    @Test
    void indexDocTest(){
        docOperation.indexDoc();
    }

    @Test
    void getDocTest(){
        docOperation.getDoc("12");
    }

    @Test
    void updateDocTest(){
        docOperation.updateDoc();
    }

    @Test
    void deleteDocTest(){
        docOperation.deleteDoc();
    }

    @Test
    void bulkDocTest(){
        docOperation.bulkDoc();
    }

    @Test
    void searchTermTest(){
        docOperation.searchTerm();
    }

    @Test
    void searchMatchTest(){
        docOperation.searchMatch();
    }

}
