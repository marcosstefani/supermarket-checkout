package com.qikserve.supermarket.adapter.wiremock.strategy;

import com.qikserve.supermarket.adapter.wiremock.domain.WiremockProduct;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotion;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotionType;
import com.qikserve.supermarket.domain.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class WiremockPromotionFlatPercentCalculationTest {
    @Spy
    private WiremockPromotionFlatPercentCalculation calculation;

    private WiremockPromotion promotionMock = new WiremockPromotion(
            "Gm1piPn7Fg",
            WiremockPromotionType.FLAT_PERCENT,
            null,
            null,
            null,
            10
    );

    private WiremockProduct productMock = new WiremockProduct(
            "C8GDyLrHJb",
            "Amazing Salad!",
            BigInteger.valueOf(499),
            Arrays.asList(promotionMock)
    );

    @Test
    void ShouldReturnTheDiscountAmountAccordingToTheCalculation() {
        ProductDto dto = calculation.execute(productMock, promotionMock, 5);
        assertEquals(dto.getTotal(), BigDecimal.valueOf(22.45));
        assertEquals(dto.getDiscount(), BigDecimal.valueOf(2.50).setScale(2, RoundingMode.HALF_EVEN));
        assertEquals(dto.getPrice(), BigDecimal.valueOf(24.95));
    }

    @Test
    void ShouldReturnTrueIfTheTypeOfPromotionIsCorrect() {
        assertTrue(calculation.canHandle(WiremockPromotionType.FLAT_PERCENT));
        assertFalse(calculation.canHandle(WiremockPromotionType.QTY_BASED_PRICE_OVERRIDE));
    }
}