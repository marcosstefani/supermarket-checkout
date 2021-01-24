package com.qikserve.supermarket.repository;

import com.qikserve.supermarket.domain.Basket;
import com.qikserve.supermarket.domain.BasketProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BasketProductRepository extends CrudRepository<BasketProduct, Integer> {
    Optional<BasketProduct> findByBasketAndProduct(Basket basket, String product);
    List<BasketProduct> findByBasket(Basket basket);
}
