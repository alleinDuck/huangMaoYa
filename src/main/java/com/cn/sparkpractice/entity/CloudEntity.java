package com.cn.sparkpractice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CloudEntity {
    private ContactEntity01 cloud;
    private List<ContactEntity02> extension;
}
