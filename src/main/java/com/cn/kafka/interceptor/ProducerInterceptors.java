package com.cn.kafka.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * 如何使用自定义的拦截器 DiyProducerInterceptor ？
 * 只需将 KafkaProducer 的 value.serializer 参数设置为 DiyProducerInterceptor 类的全限定名即可
 *
 * <p>
 *     properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,
 *         DiyProducerInterceptor.class.getName());
 * </p>
 *
 *
 * 两个拦截器，形成了 拦截链
 * <p>
 *     properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,
 *         ProducerInterceptorPrefix.class.getName() + ","
 *                 + ProducerInterceptorPrefixPlus.class.getName());
 * </p>
 *
 */
public class ProducerInterceptors implements ProducerInterceptor<String, String> {
    private volatile long sendSuccess = 0;
    private volatile long sendFailure = 0;

    /*
    KafkaProducer 在将消息序列化和计算分区之前,
    会调用生产者拦截器的 onSend() 方法来对消息进行相应的定制化操作
     */
    // 为每条消息添加一个前缀“prefix1-”
    @Override
    public ProducerRecord<String, String> onSend(
            ProducerRecord<String, String> record) {
        String modifiedValue = "prefix1-" + record.value();
        return new ProducerRecord<>(record.topic(),
                record.partition(), record.timestamp(),
                record.key(), modifiedValue, record.headers());
    }

    /*
    KafkaProducer 会在消息被应答（Acknowledgement）之前或消息发送失败时,
    调用生产者拦截器的 onAcknowledgement() 方法，优先于用户设定的 Callback 之前执行
     */
    // 计算发送消息的成功率
    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        if (exception == null) {
            sendSuccess++;
        } else {
            sendFailure ++;
        }
    }

    @Override
    public void close() {
        double successRatio = (double)sendSuccess / (sendFailure + sendSuccess);
        System.out.println("[INFO] 发送成功率="
                + String.format("%f", successRatio * 100) + "%");
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
