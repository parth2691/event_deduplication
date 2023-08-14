package com.parth.app.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

public interface EventDTOs {

    @Jacksonized
    @Builder
    @Value
    @AllArgsConstructor
    class SourceEvent {
        String source;
        String eventName;
        JsonNode eventData;//Keeping event data as JsonNode
    }


    @Jacksonized
    @Builder
    @Value
    class EventLookUpRequest {
        String source;
        String eventName;
    }


    @Jacksonized
    @Builder
    @Value
    class SourceEventModel {
        String key; //source+eventName
        JsonNode data;
        Long count;
    }



}
