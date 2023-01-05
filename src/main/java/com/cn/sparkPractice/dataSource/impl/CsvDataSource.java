package com.cn.sparkPractice.dataSource.impl;

import com.cn.sparkPractice.dataSource.AbstractDatasource;
import com.cn.sparkPractice.entity.dataSource.CsvEntity;
import lombok.extern.log4j.Log4j2;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.nio.charset.StandardCharsets;

@Log4j2
public class CsvDataSource extends AbstractDatasource<CsvEntity> {
    String path;
    String encoding;


    public Dataset<Row> read() {
        log.info("读取 csv 基础路径：{},  路径集合：{}", path, path);

        try {
            return _spark.read()
                    .option("inferschema", "true")
                    .option("header", "true")
                    .option("encoding", encoding)
                    .csv(path);
        }catch (Exception e){
            String message = String.format("读取 CSV 文件异常，basePath：%s, 路径集合: %s, 错误原因： %s", path, path, e.getMessage());
            throw new RuntimeException(message, e);
        }
    }

    public CsvDataSource(SparkSession _spark,  CsvEntity entity) {
        super(_spark, entity);
        path = this.entity.getPath();
        encoding = this.entity.getCode();

        if (encoding == null) {
            encoding = StandardCharsets.UTF_8.name();
        }
    }
}
