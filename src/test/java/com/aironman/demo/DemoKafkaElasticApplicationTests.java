package com.aironman.demo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.aironman.demo.es.model.BitcoinEuroESEntity;
import com.aironman.demo.es.service.BitCoinESService;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoKafkaElasticApplicationTests {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	BitCoinESService service;
	
	@Test
	public void contextLoads() {
		logger .info("contextLoads...");
	}
	
	@Test
	public void createAndSaveToElasticAndSearchById() {
		logger .info("INIT createAndSaveToElasticAndSearchById...");
		String _id = "id";
		BitcoinEuroESEntity entity = createSE_Entity(_id);
		logger .info("entity: ");
		logger.info(entity .toString());
		BitcoinEuroESEntity saved = service.save(entity );
		logger .info("saved: " + saved .toString());
		logger.info(saved .toString());
		assertNotNull("not null...",saved );
		Pageable pageable = new PageRequest(0,10);
		Page<BitcoinEuroESEntity> page = service.findById(_id, pageable);
		List<BitcoinEuroESEntity> list =page.getContent();
		assertEquals(true, list.size()>0);
		BitcoinEuroESEntity _element = list.get(list.size() - 1);
		assertEquals(_element .get_24hVolumeEur(), "_24hVolumeEur");
		logger .info("END createAndSaveToElasticAndSearchById...");

	}

	@Test
	public void createSaveAndSearchAll_In_Elastic() {
		logger .info("INIT createSaveAndSearchAll_In_Elastic...");
		String _id = "id_1";
		BitcoinEuroESEntity entity = createSE_Entity(_id);		
		logger .info("entity: ");
		logger.info(entity .toString());
		BitcoinEuroESEntity saved = service.save(entity );
		logger .info("saved: ");
		logger.info(saved .toString());
		assertNotNull("not null...",saved );
		Iterable<BitcoinEuroESEntity>  iterable = service.findAll();
		for (BitcoinEuroESEntity _entity:iterable)
		{
			logger .info("createSaveAndSearchAll_In_Elastic. Iterating entity: " + _entity.toString());
		}
		logger .info("END createSaveAndSearchAll_In_Elastic...");

	}

	@Test
	public void createAndSaveToElastic() {
		logger .info("INIT createAndSaveToElastic...");
		String _id = "id_2";
		BitcoinEuroESEntity entity = createSE_Entity(_id);
		logger .info("entity: ");
		logger.info(entity .toString());
		BitcoinEuroESEntity saved = service.save(entity );
		logger.info(saved .toString());
		assertNotNull("not null...",saved );
		logger .info("END createAndSaveToElastic. saved: " + saved .toString());
	}

	private BitcoinEuroESEntity createSE_Entity(String _id) {
		BitcoinEuroESEntity entity = new BitcoinEuroESEntity();
		entity .set_24hVolumeEur("_24hVolumeEur");
		entity.set_24hVolumeUsd("_24hVolumeUsd");
		entity .setAvailableSupply("availableSupply");
		entity .setId(_id);
		entity .setLastUpdated("lastUpdated");
		entity .setMarketCapEur("marketCapEur");
		entity .setMarketCapUsd("marketCapUsd");
		entity .setMaxSupply("maxSupply");
		entity .setName("name");
		entity .setPercentChange1h("percentChange1h");
		entity .setPercentChange24h("percentChange24h");
		entity .setPercentChange7d("percentChange7d");
		entity .setPriceBtc("priceBtc");
		entity .setPriceEur("priceEur");
		entity .setPriceUsd("priceUsd");
		entity .setRank("rank");
		entity .setSymbol("symbol");
		entity .setTotalSupply("totalSupply");
		return entity;
	}

}
