package com.cn.sparkpractice.entity.dataSource;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

@Data
public class DatasourceEntity implements Serializable {
    protected String type;
    protected HashMap<String, String> fieldMappings;
}
