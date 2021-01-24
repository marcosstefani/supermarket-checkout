package com.qikserve.supermarket.repository;

import com.qikserve.supermarket.domain.Basket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketRepository extends CrudRepository<Basket, Integer> {
    List<Basket> findByUsernameAndClosed(String username, boolean closed);
}
