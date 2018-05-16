package com.aironman.demo;

import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@PropertySource(value = { "application.properties" })
@EnableEurekaClient
@RestController
public class DemoKafkaElasticApplication {
    
    Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    Environment env;
    
    @Autowired
    ElasticsearchOperations operations;
    
    public static void main(String[] args) {
	SpringApplication.run(DemoKafkaElasticApplication.class, args);
    }
    
    @PostConstruct
    public void creatingDocuments() {
	logger.info("INIT @PostConstruct...");
	// this must be in the property file
	// bitcoin by default
	String indexName = env.getProperty("elasticsearch.indexName");
	logger.info("indexName  is " + indexName);
	try {
	    boolean indexExist = operations.indexExists(indexName);
	    if (!indexExist) {
		boolean creatingIndex = operations.createIndex(indexName);
		logger.info("index " + indexName + " created? " + creatingIndex);
	    } else
		logger.info("index  " + indexName + " exists? " + indexExist);
	    Client client = operations.getClient();
	    Settings settings = client.settings();
	    Map<String, String> mapSettings = settings.getAsMap();
	    Set<String> keySet = mapSettings.keySet();
	    for (String key : keySet)
		logger.info("key: " + key +
		        " value: " + mapSettings.get(key));
	    
	} catch (Exception e) {
	    logger.error("ups!" + e.getMessage());
	    System.exit(-1);
	}
	logger.info("END @PostConstruct...");
	
    }
    
    @RequestMapping("/greeting")
    public String greeting() {
	return "Hello from EurekaClient! This is DemoKafkaElasticApplication...";
    }
}
