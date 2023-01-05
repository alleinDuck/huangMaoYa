package com.cn.sparkPractice.dataSource;

import com.cn.sparkPractice.entity.dataSource.DatasourceEntity;
import org.apache.spark.sql.SparkSession;

public abstract class AbstractDatasource<T extends DatasourceEntity>{

    protected SparkSession _spark;

    protected T entity;

    protected AbstractDatasource(SparkSession _spark, T entity) {
        this._spark = _spark;
        this.entity = entity;
    }


}
