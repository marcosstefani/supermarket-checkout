package com.qikserve.supermarket.service;

import com.qikserve.supermarket.domain.Basket;
import com.qikserve.supermarket.domain.BasketProduct;
import com.qikserve.supermarket.domain.User;
import com.qikserve.supermarket.domain.dto.CheckoutDto;
import com.qikserve.supermarket.domain.dto.ProductDto;
import com.qikserve.supermarket.repository.BasketProductRepository;
import com.qikserve.supermarket.repository.BasketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {
    @Mock
    private BasketRepository basketRepository;

    @Mock
    private BasketProductRepository productRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private BasketService service;

    private User userMock = new User(
            "Jan",
            LocalDateTime.now()
    );

    private Basket basketMock = new Basket(
            userMock,
            false
    );

    private BasketProduct basketProductMock = new BasketProduct(
            basketMock,
            "4MB7UfpTQs",
            1
    );

    private ProductDto productDtoMock = new ProductDto(
            "Dwt5F7KAhi",
            "Amazing Pizza!",
            5,
            BigDecimal.valueOf(54.95),
            BigDecimal.valueOf(7.98),
            BigDecimal.valueOf(46.97)
    );

    @Test
    void whenSendingAProductShouldUpdateTheBasketWhenItAlreadyExists() {
        BasketProduct updated = basketProductMock;
        updated.setQuantity(5);

        doReturn(userMock).when(userService).find(anyString());
        doReturn(Collections.singletonList(basketMock)).when(basketRepository).findByUserAndClosed(userMock, false);
        doReturn(Optional.of(basketProductMock)).when(productRepository).findByBasketAndProduct(any(Basket.class), anyString());
        doReturn(productDtoMock).when(productService).getOne(anyString(), anyInt());
        doReturn(updated).when(productRepository).save(any(BasketProduct.class));

        ProductDto product = service.send("Jan", "4MB7UfpTQs", 5);
        assertEquals(product.getId(), "Dwt5F7KAhi");
        assertEquals(product.getQuantity(), 5);
    }

    @Test
    void whenSendingAProductShouldCreateTheBasketWhenItNotExists() {
        BasketProduct updated = basketProductMock;
        updated.setQuantity(5);

        doReturn(userMock).when(userService).find(anyString());
        doReturn(new ArrayList<>()).when(basketRepository).findByUserAndClosed(userMock, false);
        doReturn(basketMock).when(basketRepository).save(any(Basket.class));
        doReturn(Optional.empty()).when(productRepository).findByBasketAndProduct(any(Basket.class), anyString());
        doReturn(productDtoMock).when(productService).getOne(anyString(), anyInt());
        doReturn(updated).when(productRepository).save(any(BasketProduct.class));

        ProductDto product = service.send("Jan", "Dwt5F7KAhi", 5);
        assertEquals(product.getId(), "Dwt5F7KAhi");
        assertEquals(product.getQuantity(), 5);
    }

    @Test
    void whenConsultingTheCheckoutTheTransformedObjectMustBeReturned() {
        doReturn(userMock).when(userService).find(anyString());
        doReturn(Collections.singletonList(basketMock)).when(basketRepository).findByUserAndClosed(userMock, false);
        doReturn(Collections.singletonList(basketProductMock)).when(productRepository).findByBasket(any(Basket.class));
        doReturn(productDtoMock).when(productService).getOne(anyString(), anyInt());

        CheckoutDto checkout = service.checkout("Jan");
        assertEquals(checkout.getPrice(), BigDecimal.valueOf(54.95));
        assertEquals(checkout.getDiscount(), BigDecimal.valueOf(7.98));
        assertEquals(checkout.getTotal(), BigDecimal.valueOf(46.97));

        doReturn(new ArrayList<>()).when(productRepository).findByBasket(any(Basket.class));
        assertThrows(RuntimeException.class, () -> {
            service.checkout("Jan");
        });

        doReturn(new ArrayList<>()).when(basketRepository).findByUserAndClosed(userMock, false);
        assertThrows(RuntimeException.class, () -> {
            service.checkout("Jan");
        });
    }

    @Test
    void whenCompletingTheCheckoutShouldCloseTheBasket() {
        Basket concluded = basketMock;
        concluded.setClosed(true);
        concluded.setCheckoutAt(LocalDateTime.now());

        doReturn(Optional.of(basketMock)).when(basketRepository).findById(anyInt());
        doReturn(concluded).when(basketRepository).save(any(Basket.class));

        Basket basket = service.conclude(1);
        assertTrue(basket.isClosed());
        assertNotNull(basket.getCheckoutAt());

        doReturn(null).when(basketRepository).findById(anyInt());
        assertThrows(RuntimeException.class, () -> {
            service.conclude(1);
        });
    }
}