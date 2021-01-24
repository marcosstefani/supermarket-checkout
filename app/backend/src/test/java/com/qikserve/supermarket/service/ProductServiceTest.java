package com.qikserve.supermarket.service;

import com.qikserve.supermarket.adapter.port.ProductAdapter;
import com.qikserve.supermarket.domain.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductAdapter productAdapter;

    @InjectMocks
    private ProductService service;

    private ProductDto byXGetYFreeProductMock = new ProductDto(
            "ZRAwbsO2qM",
            "Amazing Burger!",
            null,
            BigDecimal.valueOf(9.99),
            null,
            null
    );

    private ProductDto flatPercentageProductMock = new ProductDto(
            "PWWe3w1SDU",
            "Amazing Salad!",
            null,
            BigDecimal.valueOf(4.99),
            null,
            null
    );

    private ProductDto qtyBasedPriceOverrideProductMock = new ProductDto(
            "Gm1piPn7Fg",
            "Amazing Pizza!",
            null,
            BigDecimal.valueOf(10.99),
            null,
            null
    );

    private ProductDto withoutPromotionProductMock = new ProductDto(
            "4MB7UfpTQs",
            "Boring Fries!",
            null,
            BigDecimal.valueOf(1.99),
            null,
            null
    );

    private List<ProductDto> productDtoList = new ArrayList<>();

    private void loadListOfProducts() {
        productDtoList.add(byXGetYFreeProductMock);
        productDtoList.add(flatPercentageProductMock);
        productDtoList.add(qtyBasedPriceOverrideProductMock);
        productDtoList.add(withoutPromotionProductMock);
    }

    @Test
    void shouldReturnTheListOfPricelessProductsWhenOrderingTheCompleteList() {
        loadListOfProducts();
        doReturn(productDtoList).when(productAdapter).getAll();

        List<ProductDto> productDtoList = service.getAll();
        assertEquals(productDtoList.size(), 4);
    }

    @Test
    void shouldReturnAProductWithDetailedValues() {
        ProductDto details = this.byXGetYFreeProductMock;
        details.setQuantity(5);
        details.setPrice(BigDecimal.valueOf(54.95));
        details.setDiscount(BigDecimal.valueOf(7.98));
        details.setTotal(BigDecimal.valueOf(46.97));

        doReturn(details).when(productAdapter).getOne(anyString(), anyInt());
        ProductDto productDto = service.getOne("ZRAwbsO2qM", 5);
        assertEquals(productDto.getId(), "ZRAwbsO2qM");
        assertEquals(productDto.getName(), "Amazing Burger!");
        assertEquals(productDto.getQuantity(), 5);
        assertEquals(productDto.getPrice(), BigDecimal.valueOf(54.95));
        assertEquals(productDto.getDiscount(), BigDecimal.valueOf(7.98));
        assertEquals(productDto.getTotal(), BigDecimal.valueOf(46.97));
    }
}