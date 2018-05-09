package com.aironman.demo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
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

	@Autowired
	BitCoinESService service;
	
	@Test
	public void contextLoads() {
		BitcoinEuroESEntity entity = new BitcoinEuroESEntity();
		entity .set_24hVolumeEur("_24hVolumeEur");
		entity.set_24hVolumeUsd("_24hVolumeUsd");
		entity .setAvailableSupply("availableSupply");
		entity .setId("id");
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
		BitcoinEuroESEntity saved = service.save(entity );
		assertNotNull("not null...",saved );
		
		Iterable<BitcoinEuroESEntity>  iterable = service.findAll();
		for (BitcoinEuroESEntity _entity:iterable)
		{
			System.out.println("entity: " + _entity.toString());
		}
		
		String _id = "id";
		Pageable pageable = new PageRequest(0,10);
		Page<BitcoinEuroESEntity> page = service.findById(_id, pageable);
		List<BitcoinEuroESEntity> list =page.getContent();
		assertEquals(true, list.size()>0);
		BitcoinEuroESEntity _element = list.get(list.size() - 1);
		assertEquals(_element .get_24hVolumeEur(), "_24hVolumeEur");
	}

}
