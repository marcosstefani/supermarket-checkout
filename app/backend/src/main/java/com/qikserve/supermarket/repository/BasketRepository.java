package com.qikserve.supermarket.repository;

import com.qikserve.supermarket.domain.Basket;
import com.qikserve.supermarket.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BasketRepository extends CrudRepository<Basket, Integer> {
    Optional<List<Basket>> findByUserAndClosed(User user, boolean closed);
}
