package com.aironman.demo.kafka;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import com.aironman.demo.es.model.BitcoinEuroESEntity;
import com.aironman.demo.es.service.BitCoinESService;

/***
 * Naive Kafka consumer implementation. I must interwire here the elastic search
 * connector.
 * 
 * @author aironman
 *
 */
public class MessageListener {
    
    Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    BitCoinESService bcESService;
    // TODO what it is means the number?
    private CountDownLatch latch = new CountDownLatch(3);
    
    private CountDownLatch partitionLatch = new CountDownLatch(2);
    
    private CountDownLatch filterLatch = new CountDownLatch(2);
    
    private CountDownLatch greetingLatch = new CountDownLatch(1);
    
    private CountDownLatch bitCoinLatch = new CountDownLatch(1);
    
    @KafkaListener(topics = "${greeting.topic.name}", group = "foo", containerFactory = "fooKafkaListenerContainerFactory")
    public void listenGroupFoo(String message) {
	logger.info("Received Messasge in group 'foo': " + message);
	latch.countDown();
    }
    
    @KafkaListener(topics = "${greeting.topic.name}", group = "bar", containerFactory = "barKafkaListenerContainerFactory")
    public void listenGroupBar(String message) {
	logger.info("Received Messasge in group 'bar': " + message);
	latch.countDown();
    }
    
    @KafkaListener(topics = "${greeting.topic.name}", containerFactory = "headersKafkaListenerContainerFactory")
    public void listenWithHeaders(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
	logger.info("Received Messasge: " + message + " from partition: " + partition);
	latch.countDown();
    }
    
    @KafkaListener(topicPartitions = @TopicPartition(topic = "${partitioned.topic.name}", partitions = { "0", "3" }))
    public void listenToParition(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
	logger.info("Received Message: " + message + " from partition: " + partition);
	this.partitionLatch.countDown();
    }
    
    @KafkaListener(topics = "${filtered.topic.name}", containerFactory = "filterKafkaListenerContainerFactory")
    public void listenWithFilter(String message) {
	logger.info("Recieved Message in filtered listener: " + message);
	this.filterLatch.countDown();
    }
    
    @KafkaListener(topics = "${greeting.topic.name}", containerFactory = "greetingKafkaListenerContainerFactory")
    public void greetingListener(Greeting greeting) {
	logger.info("Recieved greeting message: " + greeting);
	this.greetingLatch.countDown();
    }
    
    /***
     * This method is invoked when a new message arrives to the topic.
     * 
     * @param bitcoinEuroKafkaEntity
     *            the new message from the topic.
     */
    @KafkaListener(topics = "${message.topic.name}", containerFactory = "bitCoinKafkaListenerContainerFactory")
    public void bitCoinListener(BitcoinEuroKafkaEntity bitcoinEuroKafkaEntity) {
	
	logger.info("kafka message: " + bitcoinEuroKafkaEntity.toString());
	BitcoinEuroESEntity entity = createElasticPojoFromKafkaPojo(bitcoinEuroKafkaEntity);
	BitcoinEuroESEntity saved = bcESService.save(entity);
	logger.info("ES Entity: " + saved != null ? saved.toString() : "ES entity NOT saved!.");
	this.bitCoinLatch.countDown();
    }
    
    public static BitcoinEuroESEntity createElasticPojoFromKafkaPojo(BitcoinEuroKafkaEntity bitcoinEuroKafkaEntity) {
	BitcoinEuroESEntity entity = new BitcoinEuroESEntity();
	entity.set_24hVolumeEur(bitcoinEuroKafkaEntity.get_24hVolumeEur());
	entity.set_24hVolumeUsd(bitcoinEuroKafkaEntity.get_24hVolumeUsd());
	entity.setAvailableSupply(bitcoinEuroKafkaEntity.getAvailableSupply());
	entity.setId(bitcoinEuroKafkaEntity.getId());
	entity.setLastUpdated(bitcoinEuroKafkaEntity.getLastUpdated());
	entity.setMarketCapEur(bitcoinEuroKafkaEntity.getMarketCapEur());
	entity.setMarketCapUsd(bitcoinEuroKafkaEntity.getMarketCapUsd());
	entity.setMaxSupply(bitcoinEuroKafkaEntity.getMaxSupply());
	entity.setName(bitcoinEuroKafkaEntity.getName());
	entity.setPercentChange1h(bitcoinEuroKafkaEntity.getPercentChange1h());
	entity.setPriceEur(bitcoinEuroKafkaEntity.getPriceEur());
	entity.setPriceBtc(bitcoinEuroKafkaEntity.getPriceBtc());
	entity.setPercentChange24h(bitcoinEuroKafkaEntity.getPercentChange24h());
	entity.setPercentChange7d(bitcoinEuroKafkaEntity.getPercentChange7d());
	entity.setRank(bitcoinEuroKafkaEntity.getRank());
	entity.setSymbol(bitcoinEuroKafkaEntity.getSymbol());
	entity.setTotalSupply(bitcoinEuroKafkaEntity.getTotalSupply());
	return entity;
    }
    
    public CountDownLatch getBitCoinLatch() {
	return bitCoinLatch;
    }
    
}
