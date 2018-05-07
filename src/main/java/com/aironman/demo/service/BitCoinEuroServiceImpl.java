package com.aironman.demo.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aironman.demo.kafka.MessageListener;

@Service
public class BitCoinEuroServiceImpl implements BitCoinEuroService {

	@Autowired
	private MessageListener listener;

	@Override
	public void getMessageFromTopic() {
		try {
			listener.getBitCoinLatch().await(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Ups! " + e.getLocalizedMessage());
		}
	}

}
