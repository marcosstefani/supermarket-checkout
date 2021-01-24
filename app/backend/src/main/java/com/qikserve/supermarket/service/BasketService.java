package com.qikserve.supermarket.service;

import com.qikserve.supermarket.domain.Basket;
import com.qikserve.supermarket.domain.BasketProduct;
import com.qikserve.supermarket.domain.User;
import com.qikserve.supermarket.domain.dto.CheckoutDto;
import com.qikserve.supermarket.repository.BasketProductRepository;
import com.qikserve.supermarket.repository.BasketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BasketService {
    private final BasketRepository repository;
    private final BasketProductRepository productRepository;
    private final UserService userService;
    private final ProductService productService;

    public BasketService(BasketRepository repository, BasketProductRepository productRepository, UserService userService, ProductService productService) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.userService = userService;
        this.productService = productService;
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

    public CheckoutDto checkout(String username) {
        User user = findUser(username);
        Optional<List<Basket>> optionalBaskets = repository.findByUserAndClosed(user, false);
        return optionalBaskets.map(baskets -> loadCheckout(baskets.get(0))).orElseThrow();
    }

    public void conclude(Integer id) {
        Optional<Basket> basketOptional = repository.findById(id);
        if (basketOptional.isEmpty()) throw new RuntimeException("Basket not found!");

        Basket basket = basketOptional.get();
        basket.setCheckoutAt(LocalDateTime.now());
        basket.setClosed(true);
        repository.save(basket);
    }

    private CheckoutDto loadCheckout(Basket basket) {
        CheckoutDto result = new CheckoutDto(basket.getId());
        Optional<List<BasketProduct>> optionalBasketProducts = productRepository.findByBasket(basket);
        if (optionalBasketProducts.isPresent()) {
            for (BasketProduct basketProduct: optionalBasketProducts.get()) {
                result.addProduct(productService.getOne(basketProduct.getProduct(), basketProduct.getQuantity()));
            }
            return result;
        }
        throw new RuntimeException("Basket not found!");
    }

    private Basket initializeBasket(String username) {
        User user = findUser(username);
        Optional<List<Basket>> optionalBaskets = repository.findByUserAndClosed(user, false);
        return optionalBaskets.map(baskets -> baskets.get(0)).orElse(create(user));
    }

    private User findUser(String username) {
        User user = userService.find(username);
        if (user == null) throw new RuntimeException("User not found");
        return user;
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
