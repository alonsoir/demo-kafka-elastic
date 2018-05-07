package com.aironman.demo.es.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aironman.demo.es.model.BitcoinEuroESEntity;
import com.aironman.demo.es.repository.BitCoinESRepository;

@Service
public class BitCoinESServiceImpl implements BitCoinESService{

	@Autowired
	BitCoinESRepository repository;
	
	@Override
	public BitcoinEuroESEntity save(BitcoinEuroESEntity entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
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
