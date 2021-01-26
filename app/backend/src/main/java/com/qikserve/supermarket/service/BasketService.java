package com.qikserve.supermarket.service;

import com.qikserve.supermarket.domain.Basket;
import com.qikserve.supermarket.domain.BasketProduct;
import com.qikserve.supermarket.domain.User;
import com.qikserve.supermarket.domain.dto.CheckoutDto;
import com.qikserve.supermarket.domain.dto.ProductDto;
import com.qikserve.supermarket.exception.BasketNotFoundException;
import com.qikserve.supermarket.exception.UserNotFoundException;
import com.qikserve.supermarket.repository.BasketProductRepository;
import com.qikserve.supermarket.repository.BasketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        if (baskets.isEmpty()) throw new BasketNotFoundException("Basket not found to user ".concat(username));
        return loadCheckout(baskets.get(0));
    }

    public Basket conclude(Integer id) {
        Optional<Basket> basketOptional = repository.findById(id);
        if (basketOptional.isEmpty()) throw new BasketNotFoundException("Basket not found with id ".concat(id.toString()));

        Basket basket = basketOptional.get();
        basket.setCheckoutAt(LocalDateTime.now());
        basket.setClosed(true);
        return repository.save(basket);
    }

    public List<CheckoutDto> history(String username) {
        List<CheckoutDto> result = new ArrayList<>();
        User user = findUser(username);
        final List<Basket> baskets = repository.findByUserAndClosed(user, true);
        for (Basket basket : baskets) {
            CheckoutDto dto = new CheckoutDto(basket.getId());
            dto.setCheckoutAt(basket.getCheckoutAt());
            final List<BasketProduct> products = productRepository.findByBasket(basket);
            for (BasketProduct product : products) {
                if (product.getQuantity() > 0) {
                    dto.addProduct(new ProductDto(
                        product.getProduct(),
                        product.getName(),
                        product.getQuantity(),
                        product.getPrice(),
                        product.getDiscount(),
                        product.getTotal()));
                }
            }
            result.add(dto);
        }
        return result;
    }

    private CheckoutDto loadCheckout(Basket basket) {
        CheckoutDto result = new CheckoutDto(basket.getId());
        List<BasketProduct> basketProducts = productRepository.findByBasket(basket);
        if (basketProducts.isEmpty()) {
            throw new BasketNotFoundException("Basket not found with id ".concat(basket.getId().toString()));
        }
        for (BasketProduct basketProduct: basketProducts) {
            final ProductDto productDto = productService.getOne(basketProduct.getProduct(), basketProduct.getQuantity());
            saveBasketProduct(basketProduct, productDto);
            result.addProduct(productDto);
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
        if (user == null) throw new UserNotFoundException("User not found with username " + username);
        return user;
    }

    private Basket create(User user) {
        Basket basket = new Basket(user, false);
        return repository.save(basket);
    }

    private ProductDto update(Integer quantity, BasketProduct product) {
        product.setQuantity(quantity);
        final ProductDto productDto = productService.getOne(product.getProduct(), product.getQuantity());
        saveBasketProduct(product, productDto);
        return productDto;
    }

    private ProductDto create(Basket basket, String productId, Integer quantity) {
        BasketProduct product = new BasketProduct(basket, productId, quantity);
        final ProductDto productDto = productService.getOne(product.getProduct(), product.getQuantity());
        saveBasketProduct(product, productDto);
        return productDto;
    }

    private void saveBasketProduct(BasketProduct product, ProductDto productDto) {
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDiscount(productDto.getDiscount());
        product.setTotal(productDto.getTotal());
        productRepository.save(product);
    }
}
