package com.aironman.demo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

public class MessageProducer {

	Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    KafkaTemplate<String, BitcoinEuroKafkaEntity> producer;
    
    @Value(value = "${message.topic.name}")
    private String topicName;

    @Value(value = "${partitioned.topic.name}")
    private String partionedTopicName;

    @Value(value = "${filtered.topic.name}")
    private String filteredTopicName;

    public ListenableFuture<SendResult<String, BitcoinEuroKafkaEntity>> sendMessageToTopic (BitcoinEuroKafkaEntity entity) {
    	ListenableFuture<SendResult<String, BitcoinEuroKafkaEntity>>  listenable =null;
    	try {
    		listenable = producer.send(topicName,entity);
    		logger.info("listenable: " + listenable.toString());
    	}catch (Exception e) {
    		logger.error("Something went wrong when sending entity to topic: " + e.getMessage());
    	}catch (Throwable th) {
    		logger.error("Something went wrong when sending entity to topic: " + th.getMessage());
    	}
    	return listenable ;
    }
    
    public void sendMessageToPartitionedTopicName(BitcoinEuroKafkaEntity entity) {
    	producer.send(partionedTopicName,entity);
    }
    
    public void sendMessageToFilteredTopicName(BitcoinEuroKafkaEntity entity) {
    	producer.send(filteredTopicName,entity);
    }
}
