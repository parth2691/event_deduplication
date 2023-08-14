package com.parth.app.service;

import com.parth.app.model.EventDTOs;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class HBaseRedisServiceImpl implements HBaseRedisService {

    //TODO  implement all methods with database methods instead of
    @Override
    public boolean insertAndUpdateCount(EventDTOs.SourceEvent event) {
        return false;
    }

    @Override
    public Integer getEventCount(String key, String name) {
        return 1;
    }

    @Override
    public List<EventDTOs.SourceEvent> getTop10DuplicatEvents(Integer n,
                                                                   ConcurrentHashMap<String, List<EventDTOs.SourceEvent>> eventsMap) {
        return eventsMap.values().stream()
                .sorted(Comparator.comparingInt(List::size)).limit(Math.min(n, eventsMap.values().size()))
                .map(sourceEvents -> sourceEvents.get(0))
                .collect(Collectors.toList());
    }


}
