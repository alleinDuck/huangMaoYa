package com.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(basePackages = {"com.cn.*"},
        includeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE)})
public class SparkPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SparkPracticeApplication.class, args);
    }

}
