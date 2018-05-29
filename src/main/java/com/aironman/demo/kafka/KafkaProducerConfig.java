package com.aironman.demo.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaProducerConfig {
    
    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    @Value(value = "${message.topic.name}")
    private String messageTopicName;
    
    @Bean
    public KafkaAdmin admin() {
	Map<String, Object> configs = new HashMap<>();
	configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
	return new KafkaAdmin(configs);
    }
    
    @Bean
    public NewTopic topicMessageName() {
	return new NewTopic(messageTopicName, 10, (short) 2);
    }
    
    @Bean
    public ProducerFactory<String, String> producerFactory() {
	Map<String, Object> configProps = new HashMap<>();
	configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
	configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
	// configProps.put(JsonDeserializer.TRUSTED_PACKAGES,
	// "{com.aironman.demo.kafka,com.aironman.demoquartz.kafka}");
	return new DefaultKafkaProducerFactory<>(configProps);
    }
    
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
	return new KafkaTemplate<>(producerFactory());
    }
    
    @Bean
    public ProducerFactory<String, Greeting> greetingProducerFactory() {
	Map<String, Object> configProps = new HashMap<>();
	configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
	configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
	return new DefaultKafkaProducerFactory<>(configProps);
    }
    
    @Bean
    public KafkaTemplate<String, Greeting> greetingKafkaTemplate() {
	return new KafkaTemplate<>(greetingProducerFactory());
    }
    
    @Bean
    public DefaultKafkaProducerFactory<String, BitcoinEuroKafkaEntity> bitCoinProducerFactory() {
	Map<String, Object> configProps = new HashMap<>();
	configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
	configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
	configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
	// configProps.put(JsonDeserializer.TRUSTED_PACKAGES,
	// "{com.aironman.demo.kafka,com.aironman.demoquartz.kafka}");
	return new DefaultKafkaProducerFactory<String, BitcoinEuroKafkaEntity>(configProps);
    }
    
    @Bean
    public KafkaTemplate<String, BitcoinEuroKafkaEntity> bitCoinkafkaTemplate() {
	return new KafkaTemplate<>(bitCoinProducerFactory());
    }
    
    @Bean
    public MessageProducer messageProducer() {
	return new MessageProducer();
    }
}
