package com.sxr.study.es.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author sxr
 * @date 2021/7/18 3:08 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie implements Serializable {
    private String title;
    private String director;
    private Integer year;
    private String[] genres;
}
