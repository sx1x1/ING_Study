package com.sxr.study.springboot.validator;

import com.sxr.study.springboot.validator.group.AddGroup;
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

    @NotBlank(groups = AddGroup.class)
    @Pattern(regexp = "^[A-Z]+$",groups = AddGroup.class)
    private String name;

    @Max(value = 18, groups = AddGroup.class)
    @Min(10)
    private Integer age;
}
