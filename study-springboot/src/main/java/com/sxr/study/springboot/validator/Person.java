package com.sxr.study.springboot.validator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author sxr
 * @date 2021/8/24 4:18 下午
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {

    @NotBlank
    @Pattern(regexp = "^[A-Z]+$")
    private String name;

    @Max(18)
    @Min(10)
    private Integer age;
}
