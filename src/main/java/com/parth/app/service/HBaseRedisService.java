package com.parth.app.service;



import com.parth.app.model.EventDTOs;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public interface HBaseRedisService {

    boolean insertAndUpdateCount(EventDTOs.SourceEvent event);

    Integer getEventCount(String key, String name);

    List<EventDTOs.SourceEvent> getTop10DuplicatEvents(Integer n,
                                                            ConcurrentHashMap<String, List<EventDTOs.SourceEvent>> eventsMap);

}
