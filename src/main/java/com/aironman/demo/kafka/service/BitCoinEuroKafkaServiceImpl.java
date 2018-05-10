package com.aironman.demo.kafka.service;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aironman.demo.kafka.MessageListener;

@Service
public class BitCoinEuroKafkaServiceImpl implements BitCoinEuroKafkaService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MessageListener listener;

	@Override
	public void getMessageFromTopic() {
		try {
			// I am not sure if this method is ever invoked... This is NOT cool...
			logger.info("BitCoinEuroServiceImpl.getMessageFromTopic...");
			boolean countDownLatch = listener.getBitCoinLatch().await(10, TimeUnit.SECONDS);
			logger.info("getMessageFromTopic returned countDownLatch: " + countDownLatch );
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Ups! " + e.getLocalizedMessage());
		}
	}

}
