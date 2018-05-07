package com.aironman.demo.es.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.aironman.demo.es.model.BitcoinEuroESEntity;

public interface BitCoinESRepository extends ElasticsearchRepository<BitcoinEuroESEntity,String>{

	Page<BitcoinEuroESEntity> findBCById(String id, Pageable pageable);
	
}
