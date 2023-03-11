package com.cn.kafka.serializer;

import com.cn.model.Company;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 如何使用自定义的序列化器 CompanySerializer ？
 * 只需将 KafkaProducer 的 value.serializer 参数设置为 CompanySerializer 类的全限定名即可
 *
 * <p>
 *     properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
 *         CompanySerializer.class.getName());
 * </p>
 *
 */
public class CompanySerializer implements Serializer<Company> {
    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, Company data) {
        if (data == null) {
            return null;
        }
        byte[] name, address;
        if (data.getName() != null) {
            name = data.getName().getBytes(StandardCharsets.UTF_8);
        } else {
            name = new byte[0];
        }
        if (data.getAddress() != null) {
            address = data.getAddress().getBytes(StandardCharsets.UTF_8);
        } else {
            address = new byte[0];
        }
        ByteBuffer buffer = ByteBuffer.
                allocate(4 + 4 + name.length + address.length);
        buffer.putInt(name.length);
        buffer.put(name);
        buffer.putInt(address.length);
        buffer.put(address);
        return buffer.array();
    }


    @Override
    public void close() {
        Serializer.super.close();
    }
}
