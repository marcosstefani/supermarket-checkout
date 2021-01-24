package com.qikserve.supermarket.adapter.wiremock;

import com.qikserve.supermarket.adapter.wiremock.domain.WiremockProduct;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotion;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotionType;
import com.qikserve.supermarket.adapter.wiremock.strategy.WiremockPromotionByXGetYFreeCalculation;
import com.qikserve.supermarket.adapter.wiremock.strategy.WiremockPromotionFlatPercentCalculation;
import com.qikserve.supermarket.adapter.wiremock.strategy.WiremockPromotionQuantityBasedPriceOverrideCalculation;
import com.qikserve.supermarket.adapter.wiremock.strategy.WiremockPromotionStrategy;
import com.qikserve.supermarket.domain.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class WiremockAdapterTest {;
    @Mock
    private WiremockApi api;

    @Spy
    private WiremockPromotionFlatPercentCalculation flatPercentCalculation;

    @Spy
    private WiremockPromotionQuantityBasedPriceOverrideCalculation quantityBasedPriceOverrideCalculation;

    @Spy
    private WiremockPromotionByXGetYFreeCalculation byXGetYFreeCalculation;

    @Spy
    private ArrayList<WiremockPromotionStrategy> calculations;

    @InjectMocks
    private WiremockAdapter adapter;

    private void configureStrategy() {
        calculations.add(byXGetYFreeCalculation);
        calculations.add(quantityBasedPriceOverrideCalculation);
        calculations.add(flatPercentCalculation);
    }

    private final WiremockPromotion byXGetYFreePromotionMock = new WiremockPromotion(
            "ZRAwbsO2qM",
            WiremockPromotionType.BUY_X_GET_Y_FREE,
            2,
            null,
            1,
            null
    );

    private final WiremockProduct byXGetYFreeProductMock = new WiremockProduct(
            "PWWe3w1SDU",
            "Amazing Burger!",
            BigInteger.valueOf(999),
            Collections.singletonList(byXGetYFreePromotionMock)
    );

    private final WiremockPromotion flatPercentagePromotionMock = new WiremockPromotion(
            "Gm1piPn7Fg",
            WiremockPromotionType.FLAT_PERCENT,
            null,
            null,
            null,
            10
    );

    private final WiremockProduct flatPercentageProductMock = new WiremockProduct(
            "C8GDyLrHJb",
            "Amazing Salad!",
            BigInteger.valueOf(499),
            Collections.singletonList(flatPercentagePromotionMock)
    );

    private final WiremockPromotion qtyBasedPriceOverridePromotionMock = new WiremockPromotion(
            "ibt3EEYczW",
            WiremockPromotionType.QTY_BASED_PRICE_OVERRIDE,
            2,
            BigInteger.valueOf(1799),
            null,
            null
    );

    private final WiremockProduct qtyBasedPriceOverrideProductMock = new WiremockProduct(
            "Dwt5F7KAhi",
            "Amazing Pizza!",
            BigInteger.valueOf(1099),
            Collections.singletonList(qtyBasedPriceOverridePromotionMock)
    );

    private final WiremockProduct withoutPromotionProductMock = new WiremockProduct(
            "4MB7UfpTQs",
            "Boring Fries!",
            BigInteger.valueOf(199),
            Collections.emptyList()
    );

    private final List<WiremockProduct> productsMock = Arrays.asList(
            byXGetYFreeProductMock,
            flatPercentageProductMock,
            qtyBasedPriceOverrideProductMock,
            withoutPromotionProductMock
    );

    @Test
    void shouldReturnAListOfProductsWithoutValueCalculations() {
        doReturn(productsMock).when(api).getAll();

        List<ProductDto> products = adapter.getAll();
        assertEquals(products.size(), 4);
        assertEquals(products.stream().filter(productDto -> productDto.getDiscount() != null).count(), 0);
    }

    @Test
    void shouldReturnTheDiscountAmountAccordingRoleByXGetYFreeCalculation() {
        doReturn(byXGetYFreeProductMock).when(api).getOne(anyString());

        configureStrategy();
        ProductDto product = adapter.getOne("PWWe3w1SDU", 5);
        assertEquals(product.getPrice(), BigDecimal.valueOf(49.95));
        assertEquals(product.getDiscount(), BigDecimal.valueOf(19.98));
        assertEquals(product.getTotal(), BigDecimal.valueOf(29.97));
    }

    @Test
    void shouldReturnTheDiscountAmountAccordingRoleQtyBasedPriceOverrideCalculation() {
        doReturn(qtyBasedPriceOverrideProductMock).when(api).getOne(anyString());

        configureStrategy();
        ProductDto product = adapter.getOne("Dwt5F7KAhi", 5);
        assertEquals(product.getPrice(), BigDecimal.valueOf(54.95));
        assertEquals(product.getDiscount(), BigDecimal.valueOf(7.98));
        assertEquals(product.getTotal(), BigDecimal.valueOf(46.97));
    }

    @Test
    void shouldReturnTheDiscountAmountAccordingRoleFlatPercentageCalculation() {
        doReturn(flatPercentageProductMock).when(api).getOne(anyString());

        configureStrategy();
        ProductDto product = adapter.getOne("C8GDyLrHJb", 5);
        assertEquals(product.getPrice(), BigDecimal.valueOf(24.95));
        assertEquals(product.getDiscount(), BigDecimal.valueOf(2.50).setScale(2, RoundingMode.HALF_EVEN));
        assertEquals(product.getTotal(), BigDecimal.valueOf(22.45));
    }

    @Test
    void shouldReturnWithoutDiscountAmountCalculation() {
        doReturn(withoutPromotionProductMock).when(api).getOne(anyString());

        configureStrategy();
        ProductDto product = adapter.getOne("4MB7UfpTQs", 5);
        assertEquals(product.getPrice(), BigDecimal.valueOf(9.95));
        assertEquals(product.getDiscount(), BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN));
        assertEquals(product.getTotal(), BigDecimal.valueOf(9.95));
    }
}