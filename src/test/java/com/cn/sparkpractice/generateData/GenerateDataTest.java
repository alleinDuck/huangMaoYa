package com.cn.sparkpractice.generateData;

public class MakeDataTest {
    //@Test
    //public void readCsvToParquet() {
    //    String path = "/Users/luoyuxiong/decision-optimizer-parent/batch-optimizer-sparkapp/src/test/resources/streamConf/data/pay.csv";
    //    CsvEntity csvEntity = new CsvEntity();
    //    csvEntity.setPath(path);
    //
    //    CsvDatasource csvDatasource = new CsvDatasource(sparkSession, csvEntity);
    //    Dataset<Row> dataset = csvDatasource.read();
    //    dataset = dataset.withColumn("event_code", new Column("name"))
    //            .withColumn("event_date", new Column("event_date"))
    //            .withColumn("terminal_id", functions.lit("023"))
    //            .withColumn("event_dfp", functions.lit("设备指纹"))
    //            .withColumn("customer_type", functions.lit("个人"))
    //            .withColumn("customer_id", functions.lit("KH013"))
    //            .withColumn("customer_name", functions.lit("哈哈哈"))
    //            .withColumn("merchant_id", functions.lit("SH002"))
    //            .withColumn("merchant_name", functions.lit("上海申花银行"));
    //
    //    String[] partitionColumns = {"event_code", "event_date"};
    //    String basePath = "hdfs://nd1:8020/user/luoyx/data";
    //    log.info("写出 hdfs 基础路径：{},  分区字段：{}", basePath, partitionColumns);
    //    ParquetUtil.writeParquet(basePath, dataset, partitionColumns, SaveMode.Append);
    //
    //}
}
