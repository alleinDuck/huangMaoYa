package com.cn.utils;

import com.alibaba.fastjson.JSONObject;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Yaml to json util.
 */
public class YamlToJsonUtil implements Serializable {
    private final static String BOOTSTRAP = "bootstrap-test.yml";

    private final Yaml yaml = new Yaml();

    /**
     * Yaml to json.
     *
     * @param key the key
     * @return the json object
     */
    public JSONObject YamlToJson(String key) {
        YamlToJsonUtil instance = YamlToJsonInner.instance;
        Yaml yaml = instance.getYaml();
        InputStream resourceAsStream = null;
        try {
            resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(BOOTSTRAP);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> load = null;
        Map<String, Object> result = new HashMap<>();
        load = yaml.load(resourceAsStream);
        result.put(key, load.get(key));
        return JSONObject.parseObject(JSONObject.toJSONString(result));
    }

    private YamlToJsonUtil() {

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static YamlToJsonUtil getInstance() {
        return YamlToJsonInner.instance;
    }

    private static class YamlToJsonInner {
        private static final YamlToJsonUtil instance = new YamlToJsonUtil();
    }

    /**
     * Gets yaml.
     *
     * @return the yaml
     */
    public Yaml getYaml() {
        return yaml;
    }
}
