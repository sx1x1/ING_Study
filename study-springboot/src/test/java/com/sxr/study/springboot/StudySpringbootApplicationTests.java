package com.sxr.study.springboot;

import com.alibaba.fastjson.JSON;
import com.sxr.study.springboot.validator.Person;
import com.sxr.study.springboot.validator.ValidateController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
class StudySpringbootApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testValidateController() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new ValidateController()).build();

        Person person = Person.builder().name("SXr").age(1).build();
        mockMvc.perform(get("/sxr/a")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(person)));

        mockMvc.perform(get("/sxr/b/10"));

        mockMvc.perform(get("/sxr/c")
                .param("name","sunxiran"));

    }

}
