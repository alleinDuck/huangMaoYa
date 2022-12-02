package com.cn.utils;

import com.alibaba.fastjson.JSONObject;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class YamlToolsUtil {
    private final static String BOOTSTRAP = "bootstrap-test.yml";

    private final ReentrantLock lock = new ReentrantLock();

    private final Yaml yaml = new Yaml();

    public JSONObject YamlToJson(String key) {
        InputStream resourceAsStream = null;
        try {
            resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(BOOTSTRAP);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        lock.lock();
        Map<String, Object> load = null;
        Map<String, Object> result = new HashMap<>();
        try {
            load = yaml.load(resourceAsStream);
            result.put(key, load.get(key));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        return JSONObject.parseObject(JSONObject.toJSONString(result));
    }
}
