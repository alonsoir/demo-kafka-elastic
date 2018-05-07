package com.aironman.demo.kafka.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aironman.demo.kafka.MessageListener;

@Service
public class BitCoinEuroKafkaServiceImpl implements BitCoinEuroKafkaService {

	@Autowired
	private MessageListener listener;

	@Override
	public void getMessageFromTopic() {
		try {
			// I am not sure if this method is ever invoked... This is NOT cool...
			System.out.println("BitCoinEuroServiceImpl.getMessageFromTopic...");
			listener.getBitCoinLatch().await(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Ups! " + e.getLocalizedMessage());
		}
	}

}
