package com.aironman.demo.es.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aironman.demo.es.model.BitcoinEuroESEntity;

public interface BitCoinESService {

	BitcoinEuroESEntity save(BitcoinEuroESEntity entity);

	BitcoinEuroESEntity findOne(String id);

    Iterable<BitcoinEuroESEntity> findAll();

    Page<BitcoinEuroESEntity> findById(String id, Pageable pageable);

    long count();

    void delete(BitcoinEuroESEntity entity);
}
