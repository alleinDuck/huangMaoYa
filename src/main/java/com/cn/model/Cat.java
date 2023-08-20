package com.cn.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class Cat {

    @Value("miaomiao")
    private String name;

    @Autowired
    private Person master;

}

