package com.aironman.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;

import com.aironman.demo.es.model.BitcoinEuroESEntity;
import com.aironman.demo.es.service.BitCoinESService;
import com.aironman.demo.kafka.MessageListener;
import com.aironman.demo.kafka.MessageProducer;
import com.aironman.demo.kafka.service.BitCoinEuroKafkaService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DemoKafkaElasticApplicationTests {
    
    Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    MessageProducer messageProducer;
    
    @Autowired
    MessageListener messageListener;
    
    @Autowired
    BitCoinESService esService;
    
    @Autowired
    BitCoinEuroKafkaService bitCoinEuroKafkaService;
    
    @Test
    public void contextLoads() {
	logger.info("contextLoads...");
    }
    
    @Test
    public void produceKafkaMessage() {
	
	logger.info("INIT produceKafkaMessage...");
	String id = "kafkaId";
	com.aironman.demoquartz.kafka.BitcoinEuroKafkaEntity entity = createKafka_Entity(id);
	ListenableFuture<SendResult<String, com.aironman.demoquartz.kafka.BitcoinEuroKafkaEntity>> listenable = createKafkaEntityAndSendToTopic(
	        entity);
	assertNotNull(listenable);
	try {
	    SendResult<String, com.aironman.demoquartz.kafka.BitcoinEuroKafkaEntity> recovered = listenable.get(10, TimeUnit.SECONDS);
	    assertNotNull(recovered);
	    ProducerRecord<String, com.aironman.demoquartz.kafka.BitcoinEuroKafkaEntity> producerRecord = recovered.getProducerRecord();
	    assertNotNull(producerRecord);
	    com.aironman.demoquartz.kafka.BitcoinEuroKafkaEntity bitCoinRecoveredKafkaEntity = producerRecord.value();
	    assertNotNull(bitCoinRecoveredKafkaEntity);
	    assertEquals("should be equals...", entity, bitCoinRecoveredKafkaEntity);
	} catch (InterruptedException | ExecutionException | TimeoutException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    logger.error("Something went wrong..." + e.getMessage());
	    assertTrue(Boolean.FALSE);
	}
	logger.info("END produceKafkaMessage...");
    }
    
    @Test
    public void testProduceKafkaRecoverFromTopicAndSaveTOElasticSearch() {
	logger.info("INIT testProduceKafkaRecoverFromTopicAndSaveTOElasticSearch...");
	String id = "anotherKafkaId";
	com.aironman.demoquartz.kafka.BitcoinEuroKafkaEntity entity = createKafka_Entity(id);
	logger.info("Sending entity to kafka topic: " + entity.toString());
	ListenableFuture<SendResult<String, com.aironman.demoquartz.kafka.BitcoinEuroKafkaEntity>> listenable = createKafkaEntityAndSendToTopic(
	        entity);
	assertNotNull(listenable);
	logger.info("Sent entity: " + listenable.toString());
	try {
	    SendResult<String, com.aironman.demoquartz.kafka.BitcoinEuroKafkaEntity> recovered = listenable.get(10, TimeUnit.SECONDS);
	    assertNotNull(recovered);
	    ProducerRecord<String, com.aironman.demoquartz.kafka.BitcoinEuroKafkaEntity> producerRecord = recovered.getProducerRecord();
	    assertNotNull(producerRecord);
	    com.aironman.demoquartz.kafka.BitcoinEuroKafkaEntity bitCoinRecoveredKafkaEntity = producerRecord.value();
	    assertNotNull(bitCoinRecoveredKafkaEntity);
	    logger.info("Recovered entity from topic: " + bitCoinRecoveredKafkaEntity.toString());
	    assertEquals("should be equals...", entity, bitCoinRecoveredKafkaEntity);
	    
	    BitcoinEuroESEntity _entity = MessageListener.createElasticPojoFromKafkaPojo(bitCoinRecoveredKafkaEntity);
	    BitcoinEuroESEntity esSaved = esService.save(_entity);
	    assertNotNull(esSaved);
	    logger.info("esSaved: " + esSaved.toString());
	    Optional<BitcoinEuroESEntity> esRecovered = esService.findOne(id);
	    assertNotNull(esRecovered.get());
	    logger.info("esRecovered: " + esRecovered.toString());
	} catch (InterruptedException | ExecutionException | TimeoutException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    logger.error("Something went wrong..." + e.getMessage());
	    assertTrue(Boolean.FALSE);
	}
	logger.info("END testProduceKafkaRecoverFromTopicAndSaveTOElasticSearch...");
    }
    
    private ListenableFuture<SendResult<String, com.aironman.demoquartz.kafka.BitcoinEuroKafkaEntity>> createKafkaEntityAndSendToTopic(
            com.aironman.demoquartz.kafka.BitcoinEuroKafkaEntity entity) {
	return messageProducer.sendMessageToTopic(entity);
    }
    
    @Test
    public void testBitCoinEuroKafkaService() {
	logger.info("INT testBitCoinEuroKafkaService ");
	String id = "testBitCoinEuroKafkaServiceId";
	com.aironman.demoquartz.kafka.BitcoinEuroKafkaEntity entity = createKafka_Entity(id);
	logger.info("Sending entity to kafka topic: " + entity.toString());
	ListenableFuture<SendResult<String, com.aironman.demoquartz.kafka.BitcoinEuroKafkaEntity>> listenable = createKafkaEntityAndSendToTopic(
	        entity);
	assertNotNull(listenable);
	logger.info("Sent entity: " + listenable.toString());
	boolean returned = bitCoinEuroKafkaService.getMessageFromTopic();
	assertTrue(returned);
	logger.info("END testBitCoinEuroKafkaService " + returned);
    }
    
    @Test
    public void createAndSaveToElasticAndSearchById() {
	logger.info("INIT createAndSaveToElasticAndSearchById...");
	String _id = "id";
	BitcoinEuroESEntity entity = createSE_Entity(_id);
	logger.info("entity: ");
	logger.info(entity.toString());
	BitcoinEuroESEntity saved = esService.save(entity);
	logger.info("saved: " + saved.toString());
	logger.info(saved.toString());
	assertNotNull("not null...", saved);
	@SuppressWarnings("deprecation")
	Pageable pageable = new PageRequest(0, 10);
	Page<BitcoinEuroESEntity> page = esService.findById(_id, pageable);
	List<BitcoinEuroESEntity> list = page.getContent();
	assertEquals(true, list.size() > 0);
	BitcoinEuroESEntity _element = list.get(list.size() - 1);
	assertEquals(_element.get_24hVolumeEur(), "_24hVolumeEur");
	logger.info("END createAndSaveToElasticAndSearchById...");
	
    }
    
    @Test
    public void createSaveAndSearchAll_In_Elastic() {
	logger.info("INIT createSaveAndSearchAll_In_Elastic...");
	String _id = "id_1";
	BitcoinEuroESEntity entity = createSE_Entity(_id);
	logger.info("entity: ");
	logger.info(entity.toString());
	BitcoinEuroESEntity saved = esService.save(entity);
	logger.info("saved: ");
	logger.info(saved.toString());
	assertNotNull("not null...", saved);
	Iterable<BitcoinEuroESEntity> iterable = esService.findAll();
	for (BitcoinEuroESEntity _entity : iterable) {
	    logger.info("createSaveAndSearchAll_In_Elastic. Iterating entity: " + _entity.toString());
	}
	logger.info("END createSaveAndSearchAll_In_Elastic...");
	
    }
    
    @Test
    public void createAndSaveToElastic() {
	logger.info("INIT createAndSaveToElastic...");
	String _id = "id_2";
	BitcoinEuroESEntity entity = createSE_Entity(_id);
	logger.info("entity: ");
	logger.info(entity.toString());
	BitcoinEuroESEntity saved = esService.save(entity);
	logger.info(saved.toString());
	assertNotNull("not null...", saved);
	logger.info("END createAndSaveToElastic. saved: " + saved.toString());
    }
    
    private BitcoinEuroESEntity createSE_Entity(String _id) {
	BitcoinEuroESEntity entity = new BitcoinEuroESEntity();
	entity.set_24hVolumeEur("_24hVolumeEur");
	entity.set_24hVolumeUsd("_24hVolumeUsd");
	entity.setAvailableSupply("availableSupply");
	entity.setId(_id);
	entity.setLastUpdated("lastUpdated");
	entity.setMarketCapEur("marketCapEur");
	entity.setMarketCapUsd("marketCapUsd");
	entity.setMaxSupply("maxSupply");
	entity.setName("name");
	entity.setPercentChange1h("percentChange1h");
	entity.setPercentChange24h("percentChange24h");
	entity.setPercentChange7d("percentChange7d");
	entity.setPriceBtc("priceBtc");
	entity.setPriceEur("priceEur");
	entity.setPriceUsd("priceUsd");
	entity.setRank("rank");
	entity.setSymbol("symbol");
	entity.setTotalSupply("totalSupply");
	return entity;
    }
    
    private com.aironman.demoquartz.kafka.BitcoinEuroKafkaEntity createKafka_Entity(String _id) {
	com.aironman.demoquartz.kafka.BitcoinEuroKafkaEntity entity = new com.aironman.demoquartz.kafka.BitcoinEuroKafkaEntity();
	entity.set_24hVolumeEur("_24hVolumeEur");
	entity.set_24hVolumeUsd("_24hVolumeUsd");
	entity.setAvailableSupply("availableSupply");
	entity.setId(_id);
	entity.setLastUpdated("lastUpdated");
	entity.setMarketCapEur("marketCapEur");
	entity.setMarketCapUsd("marketCapUsd");
	entity.setMaxSupply("maxSupply");
	entity.setName("name");
	entity.setPercentChange1h("percentChange1h");
	entity.setPercentChange24h("percentChange24h");
	entity.setPercentChange7d("percentChange7d");
	entity.setPriceBtc("priceBtc");
	entity.setPriceEur("priceEur");
	entity.setPriceUsd("priceUsd");
	entity.setRank("rank");
	entity.setSymbol("symbol");
	entity.setTotalSupply("totalSupply");
	return entity;
    }
    
}
