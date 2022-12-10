package com.cn.utils;

import org.apache.spark.sql.*;

import java.util.List;


/**
 * The type Parquet util.
 *
 */
public class ParquetUtil {

    /**
     * Instantiates a new Parquet util.
     */
    private ParquetUtil(){}

    /**
     * Write parquet.
     *
     * @param <T>             the type parameter
     * @param parquetFilePath the parquet file path
     * @param list            the list
     * @param tClass          the t class
     * @param partitionColumn the partition column
     */
    public static <T> void writeParquet(String parquetFilePath, List<T> list, Class<T> tClass, String[] partitionColumn) {
        SparkSession sparkSession = getSparkSession();
        // Create an instance of a Bean class

        // Encoders are created for Java beans
        Dataset<T> dataset = sparkSession.createDataset(list, Encoders.bean(tClass));
        writeParquet(parquetFilePath, dataset, partitionColumn);
    }

    /**
     * Write parquet.
     *
     * @param <T>             the type parameter
     * @param dataset         the dataset
     * @param partitionColumn the partition column
     */
    public static <T> void writeParquet(String parquetFilePath, Dataset<T> dataset, String[] partitionColumn) {
        writeParquet(parquetFilePath, dataset, partitionColumn, SaveMode.Overwrite);
    }

    /**
     * Write parquet.
     *
     * @param <T>             the type parameter
     * @param parquetFilePath the parquet file path
     * @param partitionColumn the partition column
     * @param saveMode        the save mode
     */
    public static <T> void writeParquet(String parquetFilePath, Dataset<T> dataset, String[] partitionColumn, SaveMode saveMode) {
        // Create an instance of a Bean class

        // Encoders are created for Java beans
        DataFrameWriter<T> mode = dataset.write().mode(saveMode);
        if (partitionColumn != null) {
            mode.partitionBy(partitionColumn);
        }
        mode.parquet(parquetFilePath);
    }

    /**
     * Write parquet from csv.
     *
     * @param basePath        the base path
     * @param parquetFilePath the parquet file path
     * @param csvPath         the csv path
     */
    public static void writeParquetFromCsv(String basePath, String parquetFilePath, String csvPath) {

        SparkSession sparkSession = getSparkSession();
        // Create an instance of a Bean class

        // Encoders are created for Java beans
        Dataset<Row> dataset = sparkSession.read().option("inferschema", "true").option("header", true).csv(csvPath);
        dataset.write().mode(SaveMode.Append).option("basePath", basePath).partitionBy("event_code","biz_date")
                .parquet(basePath);
    }

    /**
     * Write parquet from csv.
     *
     * @param basePath        the base path
     * @param csvPath         the csv path
     * @param partitionColumn the partition column
     */
    public static void writeParquetFromCsv(String basePath, String csvPath, String[] partitionColumn) {
        writeParquetFromCsv(basePath, csvPath, partitionColumn, SaveMode.Overwrite);
    }

    /**
     * Write parquet from csv.
     *
     * @param basePath        the base path
     * @param csvPath         the csv path
     * @param partitionColumn the partition column
     * @param saveMode        the save mode
     */
    public static void writeParquetFromCsv(String basePath, String csvPath, String[] partitionColumn, SaveMode saveMode) {

        SparkSession sparkSession = getSparkSession();
        // Create an instance of a Bean class

        // Encoders are created for Java beans
        Dataset<Row> dataset = sparkSession.read().option("inferschema", "true").option("header", true).csv(csvPath);

        // Encoders are created for Java beans
        DataFrameWriter<Row> mode = dataset.write().mode(saveMode).option("basePath", basePath);
        if (partitionColumn != null && partitionColumn.length != 0) {
            mode.partitionBy(partitionColumn);
        }
        mode.parquet(basePath);
    }

    public static  Dataset<Row> readParquet(String parquetFilePath) {
        return readParquet(parquetFilePath, parquetFilePath);
    }

    /**
     * Gets spark session.
     *
     * @return the spark session
     */
    private static SparkSession getSparkSession() {
        return SparkSession.builder().getOrCreate();
    }


    /**
     * Read parquet dataset.
     *
     * @param basePath        the base path
     * @param parquetFilePath the parquet file path
     * @return the dataset
     */
    public static  Dataset<Row> readParquet(String basePath, String... parquetFilePath) {
        SparkSession sparkSession = getSparkSession();
//         忽略不合法的 Parquet 文件
        sparkSession.sql("set spark.sql.files.ignoreCorruptFiles=true");


        try {
            return sparkSession.read()
    //                合并 schema
                    .option("mergeSchema", "true")
                    .option("basePath", basePath)
                    .parquet(parquetFilePath);
        } catch (Exception e){
            String message = String.format("读取 Parquet 文件异常，basePath：%s, parquetFilePath: %s, 错误原因： %s", basePath, parquetFilePath, e.getMessage());
            throw new RuntimeException(message, e);
        }
    }

    /**
     * Read parquet schema string.
     *
     * @param parquetFilePath the parquet file path
     * @return the string
     */
    public static  String readParquetSchema(String parquetFilePath) {
        SparkSession sparkSession = getSparkSession();
        return sparkSession.read().parquet(parquetFilePath).schema().prettyJson();
    }

}
