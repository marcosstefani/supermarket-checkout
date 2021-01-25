package com.qikserve.supermarket.service;

import com.qikserve.supermarket.domain.Basket;
import com.qikserve.supermarket.domain.BasketProduct;
import com.qikserve.supermarket.domain.User;
import com.qikserve.supermarket.domain.dto.CheckoutDto;
import com.qikserve.supermarket.domain.dto.ProductDto;
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

    public ProductDto send(String username, String productId, Integer quantity) {
        Basket basket = initializeBasket(username);
        Optional<BasketProduct> optionalBasketProduct = productRepository.findByBasketAndProduct(basket, productId);
        return optionalBasketProduct.map(basketProduct -> update(quantity, basketProduct)).orElseGet(() -> create(basket, productId, quantity));
    }

    public CheckoutDto checkout(String username) {
        User user = findUser(username);
        List<Basket> baskets = repository.findByUserAndClosed(user, false);
        if (baskets.isEmpty()) throw new RuntimeException("Basket not found!");
        return loadCheckout(baskets.get(0));
    }

    public Basket conclude(Integer id) {
        Optional<Basket> basketOptional = repository.findById(id);
        if (basketOptional.isEmpty()) throw new RuntimeException("Basket not found!");

        Basket basket = basketOptional.get();
        basket.setCheckoutAt(LocalDateTime.now());
        basket.setClosed(true);
        return repository.save(basket);
    }

    private CheckoutDto loadCheckout(Basket basket) {
        CheckoutDto result = new CheckoutDto(basket.getId());
        List<BasketProduct> basketProducts = productRepository.findByBasket(basket);
        if (basketProducts.isEmpty()) {
            throw new RuntimeException("Basket not found!");
        }
        for (BasketProduct basketProduct: basketProducts) {
            result.addProduct(productService.getOne(basketProduct.getProduct(), basketProduct.getQuantity()));
        }
        return result;
    }

    private Basket initializeBasket(String username) {
        User user = findUser(username);
        List<Basket> baskets = repository.findByUserAndClosed(user, false);
        if (baskets.isEmpty()) return create(user);
        return baskets.get(0);
    }

    private User findUser(String username) {
        User user = userService.find(username);
        if (user == null) throw new RuntimeException("User not found!");
        return user;
    }

    private Basket create(User user) {
        Basket basket = new Basket(user, false);
        return repository.save(basket);
    }

    private ProductDto update(Integer quantity, BasketProduct product) {
        product.setQuantity(quantity);
        productRepository.save(product);
        return productService.getOne(product.getProduct(), product.getQuantity());
    }

    private ProductDto create(Basket basket, String productId, Integer quantity) {
        BasketProduct product = new BasketProduct(basket, productId, quantity);
        productRepository.save(product);
        return productService.getOne(product.getProduct(), product.getQuantity());
    }
}
