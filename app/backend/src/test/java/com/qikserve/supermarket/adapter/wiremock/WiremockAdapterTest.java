package com.qikserve.supermarket.adapter.wiremock;

import com.qikserve.supermarket.adapter.wiremock.domain.WiremockProduct;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotion;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotionType;
import com.qikserve.supermarket.adapter.wiremock.strategy.WiremockPromotionByXGetYFreeCalculation;
import com.qikserve.supermarket.adapter.wiremock.strategy.WiremockPromotionFlatPercentCalculation;
import com.qikserve.supermarket.adapter.wiremock.strategy.WiremockPromotionQuantityBasedPriceOverrideCalculation;
import com.qikserve.supermarket.domain.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class WiremockAdapterTest {;
    @Mock
    private WiremockApi api;

    @Mock
    private WiremockPromotionFlatPercentCalculation flatPercentCalculation;

    @Mock
    private WiremockPromotionQuantityBasedPriceOverrideCalculation quantityBasedPriceOverrideCalculation;

    @Mock
    private WiremockPromotionByXGetYFreeCalculation byXGetYFreeCalculation;

    @Spy
    @InjectMocks
    private WiremockAdapter adapter;

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
}