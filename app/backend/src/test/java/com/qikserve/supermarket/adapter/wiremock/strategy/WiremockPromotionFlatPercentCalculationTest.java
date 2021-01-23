package com.qikserve.supermarket.adapter.wiremock.strategy;

import com.qikserve.supermarket.adapter.wiremock.domain.WiremockProduct;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotion;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        BigDecimal discount = calculation.execute(productMock, promotionMock, 5);
        assertEquals(discount, BigDecimal.valueOf(2.50).setScale(2, RoundingMode.HALF_EVEN));
    }

    @Test
    void ShouldReturnTrueIfTheTypeOfPromotionIsCorrect() {
        assertTrue(calculation.canHandle(WiremockPromotionType.FLAT_PERCENT));
        assertFalse(calculation.canHandle(WiremockPromotionType.QTY_BASED_PRICE_OVERRIDE));
    }
}