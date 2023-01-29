package com.cn.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import pl.jalokim.propertiestojson.util.PropertiesToJsonConverter;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Yaml to json util.
 */
public class YamlToJsonForCloudUtil implements Serializable {

    private final Yaml yaml = new Yaml(new SafeConstructor());

    /**
     * Yaml to json.
     *
     * @param key the key of the configuration file in Consul or Nacos
     * @return the json object
     */
    public JSONObject YamlToJson(String key) {
        YamlToJsonForCloudUtil instance = YamlToJsonInner.instance;
        Environment environment = instance.getEnvironment();
        if (environment == null) {
            throw new RuntimeException("Environment 为空！");
        }
        Map<String, Object> propertyMap = getPropertyMap(environment);
        // 将 Map 转为 Json
        String json = new PropertiesToJsonConverter().convertFromValuesAsObjectMap(propertyMap);
        Yaml yaml = instance.getYaml();
        Map<String, Object> load = null;
        Map<String, Object> result = new HashMap<>();
        // 解析 String 中唯一的 YAML 文档并转为 Map
        load = yaml.load(json);
        result.put(key, load.get(key));
        return JSONObject.parseObject(JSONObject.toJSONString(result));
    }

    /**
     * Gets property map.
     *
     * @param environment the environment
     * @return the property map
     */
    public Map<String, Object > getPropertyMap(Environment environment) {
        // 得到当前环境中所有的属性集合
        List<?> propertySourceCollection = ((StandardEnvironment) environment)
                .getPropertySources().stream()
                .map(PropertySource::getSource)
                .collect(Collectors.toList());

        List<LinkedHashMap<String, Object>> list = new ArrayList<>();
        for (Object source : propertySourceCollection) {
            // 判断属性类型是否为 LinkedHashMap
            if (source instanceof LinkedHashMap) {
                list.add((LinkedHashMap<String, Object>) source);
            }
        }

        // 将集合中多个 LinkedHashMap 合并为一个 Map
        return list.stream()
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (value1, value2) -> value1));
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

    /**
     * Gets environment.
     *
     * @return the environment
     */
    public Environment getEnvironment() {
        return SpringContextUtil.getBean(Environment.class);
    }
}
