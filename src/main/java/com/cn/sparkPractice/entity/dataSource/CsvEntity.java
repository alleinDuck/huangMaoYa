package com.cn.sparkPractice.entity.dataSource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CsvEntity extends DatasourceEntity{
    public String path;
    public String code;
}
