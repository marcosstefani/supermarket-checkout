package com.qikserve.supermarket.service;

import com.qikserve.supermarket.repository.BasketRepository;
import org.springframework.stereotype.Service;

@Service
public class BasketService {
    private final BasketRepository repository;

    public BasketService(BasketRepository repository) {
        this.repository = repository;
    }

    public void add(String product) {

    }
}
