package com.aironman.demo.es.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aironman.demo.es.model.BitcoinEuroESEntity;
import com.aironman.demo.es.repository.BitCoinESRepository;

@Service
public class BitCoinESServiceImpl implements BitCoinESService {
    
    Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    BitCoinESRepository repository;
    
    @Override
    public BitcoinEuroESEntity save(BitcoinEuroESEntity entity) {
	
	BitcoinEuroESEntity saved = null;
	try {
	    saved = repository.save(entity);
	} catch (Exception e) {
	    logger.error("Ups! " + e.getMessage());
	}
	return saved;
    }
    
    @Override
    public BitcoinEuroESEntity findOne(String id) {
	// TODO Auto-generated method stub
	return repository.findOne(id);
    }
    
    @Override
    public Iterable<BitcoinEuroESEntity> findAll() {
	// TODO Auto-generated method stub
	return repository.findAll();
    }
    
    @Override
    public Page<BitcoinEuroESEntity> findById(String id, Pageable pageable) {
	// TODO Auto-generated method stub
	return repository.findBCById(id, pageable);
    }
    
    @Override
    public long count() {
	// TODO Auto-generated method stub
	return repository.count();
    }
    
    @Override
    public void delete(BitcoinEuroESEntity entity) {
	// TODO Auto-generated method stub
	repository.delete(entity);
    }
    
}
