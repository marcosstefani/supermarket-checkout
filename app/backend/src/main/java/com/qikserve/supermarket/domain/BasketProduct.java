package com.qikserve.supermarket.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
@Getter
@Setter
public class BasketProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "basket_id", referencedColumnName = "id")
    private Basket basket;
    private String product;
    private Integer quantity;

    public BasketProduct(Integer id, Basket basket, String product, Integer quantity) {
        this.id = id;
        this.basket = basket;
        this.product = product;
        this.quantity = quantity;
    }
}
