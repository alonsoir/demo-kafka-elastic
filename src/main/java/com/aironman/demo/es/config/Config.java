package com.aironman.demo.es.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.NodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.aironman.demo.es.repository")
@ComponentScan(basePackages = { "com.aironman.demo.es.service" })
public class Config {
    
    // @Value("${elasticsearch.home:/usr/local/Cellar/elasticsearch/2.3.2}")
    @Value("${elasticsearch.home:/usr/local/Cellar/elasticsearch/6.2.4}")
    private String elasticsearchHome;
    
    private static Logger logger = LoggerFactory.getLogger(Config.class);
    
    @Bean
    public Client client() {
	try {
	    logger.info("Creating client with ES. elasticsearchHome is " + elasticsearchHome);
	    final Path tmpDir = Files.createTempDirectory(Paths.get(System.getProperty("java.io.tmpdir")), "elasticsearch_data");
	    logger.debug("tmpDir is " + tmpDir.toAbsolutePath().toString());
	    
	    // @formatter:off
	    
	    final Settings.Builder elasticsearchSettings = Settings.settingsBuilder().put("http.enabled", "false")
	            .put("path.data", tmpDir.toAbsolutePath().toString())
	            .put("path.home", elasticsearchHome);
	    
	    logger.info("http.enabled: " + elasticsearchSettings.get("http.enabled"));
	    logger.info("path.data: " + elasticsearchSettings.get("path.data"));
	    logger.info("path.home: " + elasticsearchSettings.get("path.home"));
	    
	    // How can i connect to another server running in a docker container?
	    return new NodeBuilder()
	            .local(true)
	            .settings(elasticsearchSettings)
	            .node()
	            .client();
	    
	    // @formatter:on
	} catch (final IOException ioex) {
	    logger.error("Cannot create temp dir", ioex);
	    throw new RuntimeException();
	}
    }
    
    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
	return new ElasticsearchTemplate(client());
    }
}
