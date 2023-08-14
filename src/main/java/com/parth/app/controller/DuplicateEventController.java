package com.parth.app.controller;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.parth.app.listners.EventConsumer;
import com.parth.app.model.EventDTOs;
import com.parth.app.service.HBaseRedisService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class DuplicateEventController {
	
	@Autowired
	public HBaseRedisService services;

	@Autowired
	private Environment env;


	private final ConcurrentHashMap<String, List<EventDTOs.SourceEvent>> eventsMap;

	public DuplicateEventController() {
		eventsMap = new ConcurrentHashMap<>();
	}

	@GetMapping("/check")
	public void getOrignalUrl() {
		System.out.println("check Url");
	}


	@GetMapping("/get_top_duplicates/{source}/{n}")
	public List<EventDTOs.SourceEvent> getTopNDuplicates(@PathVariable  Integer n) {
		System.out.println("fetching top urls");
		return services.getTop10DuplicatEvents(n, eventsMap);
	}

	@SneakyThrows
	@GetMapping("/startconsumer")
	public void startConsumer() {
		String kafkaUrl = env.getProperty("kafka.server");
		String port = env.getProperty("kafka.port");
		Thread listner1 = new Thread( new EventConsumer(kafkaUrl, port, "scg1", "source-1", eventsMap));
		Thread listner2 = new Thread( new EventConsumer(kafkaUrl, port, "scg2", "source-2", eventsMap));
		Thread listner3 = new Thread( new EventConsumer(kafkaUrl, port, "scg3", "source-3", eventsMap));
		listner1.start();
		listner2.start();
		listner3.start();
		listner1.join();
		listner2.join();
		listner3.join();
	}





}

