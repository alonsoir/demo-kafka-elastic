package com.aironman.demo.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    
    Logger logger = LoggerFactory.getLogger(getClass());
    
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
    
    public ConsumerFactory<String, String> consumerFactory(String groupId) {
	Map<String, Object> props = new HashMap<>();
	props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
	props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
	props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
	// props.put(JsonDeserializer.TRUSTED_PACKAGES,
	// "{com.aironman.demo.kafka,com.aironman.demoquartz.kafka}");
	return new DefaultKafkaConsumerFactory<>(props);
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> fooKafkaListenerContainerFactory() {
	ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
	factory.setConsumerFactory(consumerFactory("foo"));
	return factory;
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> barKafkaListenerContainerFactory() {
	ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
	factory.setConsumerFactory(consumerFactory("bar"));
	return factory;
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> headersKafkaListenerContainerFactory() {
	ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
	factory.setConsumerFactory(consumerFactory("headers"));
	return factory;
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> partitionsKafkaListenerContainerFactory() {
	ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
	factory.setConsumerFactory(consumerFactory("partitions"));
	return factory;
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> filterKafkaListenerContainerFactory() {
	ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
	factory.setConsumerFactory(consumerFactory("filter"));
	factory.setRecordFilterStrategy(record -> record.value()
	        .contains("World"));
	return factory;
    }
    
    public ConsumerFactory<String, Greeting> greetingConsumerFactory() {
	Map<String, Object> props = new HashMap<>();
	props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
	props.put(ConsumerConfig.GROUP_ID_CONFIG, "greeting");
	return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Greeting.class));
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Greeting> greetingKafkaListenerContainerFactory() {
	ConcurrentKafkaListenerContainerFactory<String, Greeting> factory = new ConcurrentKafkaListenerContainerFactory<>();
	factory.setConsumerFactory(greetingConsumerFactory());
	return factory;
    }
    
    public ConsumerFactory<String, BitcoinEuroKafkaEntity> bitCoinConsumerFactory() {
	Map<String, Object> props = new HashMap<>();
	props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
	props.put(ConsumerConfig.GROUP_ID_CONFIG, "bitcoin");
	props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
	// props.put(JsonDeserializer.TRUSTED_PACKAGES,
	// "{com.aironman.demo.kafka,com.aironman.demoquartz.kafka}");
	JsonDeserializer<BitcoinEuroKafkaEntity> jsonDeserializer = new JsonDeserializer<>(BitcoinEuroKafkaEntity.class);
	jsonDeserializer.addTrustedPackages("com.aironman.demoquartz.kafka");
	return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BitcoinEuroKafkaEntity> bitCoinKafkaListenerContainerFactory() {
	ConcurrentKafkaListenerContainerFactory<String, BitcoinEuroKafkaEntity> factory = new ConcurrentKafkaListenerContainerFactory<>();
	factory.setConsumerFactory(bitCoinConsumerFactory());
	return factory;
    }
    
    @Bean
    public MessageListener messageListener() {
	return new MessageListener();
    }
}
