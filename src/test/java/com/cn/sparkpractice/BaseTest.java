package com.cn.sparkpractice;

import org.apache.spark.sql.SparkSession;

import java.io.Serializable;

public class BaseTest implements Serializable {
    protected SparkSession _spark = null;

    public static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(BaseTest.class);

}
