package com.cn.sparkPractice.generateData;

import com.cn.sparkPractice.BaseTest;
import com.cn.sparkPractice.dataSource.impl.CsvDataSource;
import com.cn.sparkPractice.entity.dataSource.CsvEntity;
import com.cn.utils.ParquetUtil;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = SpringBootTest.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class GenerateDataTest extends BaseTest {
    @Test
    public void readCsvToParquet() {
        String path = "/Users/luoyuxiong/decision-optimizer-parent" +
                "/batch-optimizer-sparkapp/src/test/resources/streamConf/data/pay.csv";
        CsvEntity csvEntity = new CsvEntity();
        csvEntity.setPath(path);

        CsvDataSource csvDatasource = new CsvDataSource(_spark, csvEntity);
        Dataset<Row> dataset = csvDatasource.read();
        dataset = dataset.withColumn("event_code", new Column("name"))
                .withColumn("event_date", new Column("event_date"));

        String[] partitionColumns = {"EVENT_CODE", "EVENT_DATE"};
        String basePath = "hdfs://nd1:8020/user/luoyx/data";
        log.info("写出 hdfs 基础路径：{},  分区字段：{}", basePath, partitionColumns);
        ParquetUtil.writeParquet(basePath, dataset, partitionColumns, SaveMode.Append);

    }
}
