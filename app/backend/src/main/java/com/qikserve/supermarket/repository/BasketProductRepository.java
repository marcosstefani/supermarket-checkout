package com.qikserve.supermarket.repository;

import com.qikserve.supermarket.domain.BasketProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketProductRepository extends CrudRepository<BasketProduct, Integer> {}
