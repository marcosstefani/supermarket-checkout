package com.qikserve.supermarket.service;

import com.qikserve.supermarket.domain.Basket;
import com.qikserve.supermarket.domain.BasketProduct;
import com.qikserve.supermarket.domain.User;
import com.qikserve.supermarket.repository.BasketProductRepository;
import com.qikserve.supermarket.repository.BasketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasketService {
    private final BasketRepository repository;
    private final BasketProductRepository productRepository;
    private final UserService userService;

    public BasketService(BasketRepository repository, BasketProductRepository productRepository, UserService userService) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    public void send(String username, String productId, Integer quantity) {
        Basket basket = initializeBasket(username);
        Optional<BasketProduct> optionalBasketProduct = productRepository.findByBasketAndProduct(basket, productId);
        if (optionalBasketProduct.isPresent()) {
            BasketProduct product = optionalBasketProduct.get();
            product.setQuantity(quantity);
            productRepository.save(product);
        } else {
            create(basket, productId, quantity);
        }

    }

    private Basket initializeBasket(String username) {
        Optional<List<Basket>> optionalBaskets = repository.findByUsernameAndClosed(username, false);
        User user = userService.find(username);
        if (user == null) throw new RuntimeException("User not found");
        return optionalBaskets.map(baskets -> baskets.get(0)).orElse(create(user));
    }

    private Basket create(User user) {
        Basket basket = new Basket(user, false);
        return repository.save(basket);
    }

    private BasketProduct create(Basket basket, String productId, Integer quantity) {
        BasketProduct product = new BasketProduct(basket, productId, quantity);
        return productRepository.save(product);
    }
}
