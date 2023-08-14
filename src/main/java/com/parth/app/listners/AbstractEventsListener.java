package com.parth.app.listners;


import com.parth.app.model.EventDTOs;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractEventsListener  {
    public String kafkaURL;
    public String port;
    public Properties props;
    public String groupId;
    public KafkaConsumer<String, String> consumer;
    public String topic;

    public AbstractEventsListener(String kafkaURL, String port, String groupId, String topic) {
        this.kafkaURL = kafkaURL;
        this.port = port;
        this.groupId = groupId;
        this.props = new Properties();
        this.props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaURL + ":" + port);
        this.props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        this.props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        this.props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        this.props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        this.props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(this.props);
        this.topic = topic;
    }


    public abstract void consumeMessages(ConcurrentHashMap<String, List<EventDTOs.SourceEvent>> map);

}
