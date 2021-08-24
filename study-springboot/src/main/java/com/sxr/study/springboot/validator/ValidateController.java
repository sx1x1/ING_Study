package com.sxr.study.springboot.validator;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

/**
 * @author sxr
 * @date 2021/8/24 3:56 下午
 */
@RestController
@RequestMapping("/sxr")
@Validated
public class ValidateController {
    @GetMapping("/a")
    public String testRequestBody(@RequestBody @Valid Person person) {
        System.out.println(person);
        return person.toString();
    }

    @GetMapping("/b/{id}")
    public String testPathVariable(@Valid @Max(value = 5, message = "no") @PathVariable("id") Integer id) {
        System.out.println(id);
        return id.toString();
    }

    @GetMapping("/c")
    public String test(@Valid @Size(max = 3, message = "no") @RequestParam("name") String name) {
        System.out.println(name);
        return name;
    }

}
