package com.qikserve.supermarket.repository;

import com.qikserve.supermarket.domain.Basket;
import com.qikserve.supermarket.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.OrderBy;
import java.util.List;

@Repository
public interface BasketRepository extends CrudRepository<Basket, Integer> {
    @OrderBy("checkoutAt DESC, id DESC")
    List<Basket> findByUserAndClosed(User user, boolean closed);
}
