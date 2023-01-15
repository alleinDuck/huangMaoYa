package com.cn.sparkPractice.ymlToJson;

import com.alibaba.fastjson.JSONObject;
import com.cn.model.Person;
import com.cn.sparkPractice.entity.CloudEntity;
import com.cn.utils.YamlToJsonForCloudUtil;
import com.cn.utils.YamlToJsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import pl.jalokim.propertiestojson.util.PropertiesToJsonConverter;
import pl.jalokim.propertiestojson.util.PropertiesToJsonConverterBuilder;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest(classes = SpringBootTest.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class YamlToJsonTest {

    @Autowired
    private Person person;

    @Autowired
    private Environment environment;

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
        YamlToJsonUtil yamlToJsonUtil = YamlToJsonUtil.getInstance();
        JSONObject jsonObject = yamlToJsonUtil.YamlToJson(key);
        System.out.println(jsonObject);
    }

     //从配置中心取值，有pojo
    @Test
    public void YAMLToJSON() {
        Yaml yaml = new Yaml(new Constructor(Person.class));
        Person person = yaml.loadAs(JSONObject.toJSONString(this.person), Person.class);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(person));
        System.out.println(jsonObject.toString());
    }


    // 从配置中心取值，没有pojo
    @Test
    public void YamlToJsonEnvironment() {
        List<?> propertySourceCollection = ((StandardEnvironment) environment)
                .getPropertySources().stream()
                .map(PropertySource::getSource)
                .collect(Collectors.toList());

        List<LinkedHashMap<String, Object>> list = new ArrayList<>();
        for (Object source : propertySourceCollection) {
            if (source instanceof LinkedHashMap) {
                list.add((LinkedHashMap<String, Object>) source);
            }
        }

        Map<String, Object > propertyMap = list.stream()
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (value1, value2) -> value1));

        YamlToJsonForCloudUtil yamlToJsonUtil = YamlToJsonForCloudUtil.getInstance();
        PropertiesToJsonConverter converter = PropertiesToJsonConverterBuilder.builder().build();
        String json = converter.convertFromValuesAsObjectMap(propertyMap);
        JSONObject dom = yamlToJsonUtil.YamlToJson("dom", json);
        System.out.println(JSONObject.toJSONString(dom));
    }
}
