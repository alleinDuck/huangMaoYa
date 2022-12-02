package com.cn.sparkpractice.ymlToJson;

import com.alibaba.fastjson.JSONObject;
import com.cn.sparkpractice.entity.CloudEntity;
import com.cn.utils.YamlToJsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = SpringBootTest.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class YamlToJsonTest {

    @Test
    public void YAMLToJSON01() {
        Yaml yaml = new Yaml();
        InputStream resourceAsStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("bootstrap-test.yml");
        // 解析唯一的 yaml 文档转为 Java 对象
        Map<String, Object> load = yaml.load(resourceAsStream);
        Map<String, Object> result = new HashMap<>();
        result.put("spring.cloud.nacos", load.get("spring.cloud.nacos"));
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(result));
        System.out.println(jsonObject.toString());
    }

    @Test
    public void YAMLToJSON02() {
        Yaml yaml = new Yaml(new Constructor(CloudEntity.class));
        InputStream resourceAsStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("yamlToJson.yml");
        // 解析唯一的 yaml 文档转为 Java 对象
        CloudEntity cloud = yaml.load(resourceAsStream);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(cloud));
        System.out.println(jsonObject.toString());
    }

    @Test
    public void YAMLToJSON03() {
        String key = "spring.cloud.nacos";
        YamlToJsonUtil yamlToJsonUtil = new YamlToJsonUtil();
        JSONObject jsonObject = yamlToJsonUtil.YamlToJson(key);
        System.out.println(jsonObject);
    }
}
