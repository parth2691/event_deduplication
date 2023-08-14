package com.parth.app.listners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parth.app.model.EventDTOs;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class EventConsumer extends AbstractEventsListener implements Runnable {

    private ConcurrentHashMap<String, List<EventDTOs.SourceEvent>> map;
    private final ObjectMapper objectMapper;

    public EventConsumer(String kafkaURL, String port, String groupId, String topic,
                         ConcurrentHashMap<String, List<EventDTOs.SourceEvent>> map) {
        super(kafkaURL, port, groupId, topic);
        this.map = map;
        this.objectMapper = new ObjectMapper();
    }

    @SneakyThrows
    @Override
    public void consumeMessages(ConcurrentHashMap<String, List<EventDTOs.SourceEvent>> map) {
        consumer.subscribe(Collections.singletonList(this.topic));
        System.out.println("Inside run method");
        consumer.assign(Collections.singleton(new TopicPartition(this.topic,0)));
        consumer.seekToEnd(Collections.singleton(new TopicPartition(this.topic,0)));
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1));
        EventDTOs.SourceEvent sourceEvent;
        for (ConsumerRecord<String, String> record : records) {
            sourceEvent = objectMapper.readValue(record.value(), EventDTOs.SourceEvent.class);
            String eventKey = sourceEvent.getSource()+"_"+sourceEvent.getEventName();
            List<EventDTOs.SourceEvent> sourceEvents = map.getOrDefault(eventKey, new ArrayList<>());
            sourceEvents.add(sourceEvent);
            map.put(eventKey, sourceEvents);
        }
    }

    @Override
    public void run() {
        consumeMessages(map);
    }
}
