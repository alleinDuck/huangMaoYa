package com.cn.utils;

import com.alibaba.fastjson.JSONObject;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Yaml to json util.
 */
public class YamlToJsonForCloudUtil implements Serializable {

    private final Yaml yaml = new Yaml(new SafeConstructor());

    /**
     * Yaml to json.
     *
     * @param key the key
     * @return the json object
     */
    public JSONObject YamlToJson(String key, String json) {
        YamlToJsonForCloudUtil instance = YamlToJsonInner.instance;
        Yaml yaml = instance.getYaml();
        try {
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> load = null;
        Map<String, Object> result = new HashMap<>();
        load = yaml.load(json);
        result.put(key, load.get(key));
        return JSONObject.parseObject(JSONObject.toJSONString(result));
    }

    private YamlToJsonForCloudUtil() {

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static YamlToJsonForCloudUtil getInstance() {
        return YamlToJsonInner.instance;
    }

    private static class YamlToJsonInner {
        private static final YamlToJsonForCloudUtil instance = new YamlToJsonForCloudUtil();
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
