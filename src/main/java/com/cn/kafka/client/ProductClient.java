package com.cn.kafka.client;

import com.cn.kafka.interceptor.DiyProducerInterceptor;
import com.cn.kafka.partition.DiyPartition;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 一个正常的生产逻辑需要具备以下几个步骤：
 * <p>
 *     <li>配置生产者客户端参数及创建相应的生产者实例</li>
 *     <li>构建待发送的消息</li>
 *     <li>发送消息</li>
 *     <li>关闭生产者实例</li>
 * </p>
 *
 */
public class ProductClient {
    public static final String brokerList = "localhost:9092";
    public static final String topic = "topic-demo";

    /**
     * @return {@link Properties}
     */
    public static Properties initConfig(){
        Properties props = new Properties();
        /*
         参数 bootstrap.servers
         生产者客户端连接 Kafka 集群所需的 broker 地址清单，
         具体的内容格式为 host1:port1,host2:port2，
         可以设置一个或多个地址，中间以逗号隔开，此参数的默认值为“”
         broker 即为 Kafka 集群的 ip:port
         */
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);

        /*
        参数 key.serializer 和 value.serializer 这两个参数分别用来指定 key 和 value 序列化操作的序列化器，
        这两个参数无默认值。注意这里必须填写序列化器的全限定名
         */
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());

        /*
        参数 client.id，这个参数用来设定 KafkaProducer 对应的客户端id，默认值为“”。
        如果客户端不设置，则 KafkaProducer 会自动生成一个非空字符串，
        内容形式如“producer-1”、“producer-2”，即字符串“producer-”与数字的拼接。
         */
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "producer.client.id.demo");

        /*
        可重试的异常，如果配置了 retries 参数，那么只要在规定的重试次数内自行恢复了，就不会抛出异常。
        retries 参数的默认值为0
        示例中配置了10次重试。如果重试了10次之后还没有恢复，那么仍会抛出异常，进而发送的外层逻辑就要处理这些异常了
         */
        props.put(ProducerConfig.RETRIES_CONFIG, 10);

        // 自定义分区器
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, DiyPartition.class.getName());

        // 自定义拦截器
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, DiyProducerInterceptor.class.getName());

        return props;
    }

    public static void main(String[] args) {
        Properties props = initConfig();
        /*
        KafkaProducer 是线程安全的，
        可以在多个线程中共享单个 KafkaProducer 实例，
        也可以将 KafkaProducer 实例进行池化来供其他线程调用
         */
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        ProducerRecord<String, String> record =
                new ProducerRecord<>(topic, "Hello, Kafka!");
        try {
            // 同步发送
            Future<RecordMetadata> future = producer.send(record);
            RecordMetadata metadata = future.get();
            System.out.println(metadata.topic() + "-" +
                    metadata.partition() + ":" + metadata.offset());

            // 异步发送
            producer.send(record, new Callback() {
                // 方法 onCompletion 参数互斥
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception != null) {
                        // 对异常做了简单处理，实际可记录为日志
                        exception.printStackTrace();
                    } else {
                        System.out.println(metadata.topic() + "-" +
                                metadata.partition() + ":" + metadata.offset());
                    }
                }
            });

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // 发送多条消息
        int i = 0;
        while (i < 100) {
            ProducerRecord<String, String> recordMore =
                    new ProducerRecord<>(topic, "msg" + i++);
            try {
                producer.send(recordMore).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        producer.close();
    }
}
