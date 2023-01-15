package com.cn.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "test")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private Info info;

    private List<Pet> pet;

    @Data
    public static class Info {
        private String name;
        private Integer age;
    }

    @Data
    public static class Pet {
        private String name;
        private Integer age;
    }
}
