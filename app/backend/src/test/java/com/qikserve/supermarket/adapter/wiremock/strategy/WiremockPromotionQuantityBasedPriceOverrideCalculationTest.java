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
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class WiremockPromotionQuantityBasedPriceOverrideCalculationTest {
    @Spy
    private WiremockPromotionQuantityBasedPriceOverrideCalculation calculation;

    private WiremockPromotion promotionMock = new WiremockPromotion(
            "ibt3EEYczW",
            WiremockPromotionType.QTY_BASED_PRICE_OVERRIDE,
            2,
            BigInteger.valueOf(1799),
            null,
            null
    );

    private WiremockProduct productMock = new WiremockProduct(
            "Dwt5F7KAhi",
            "Amazing Pizza!",
            BigInteger.valueOf(1099),
            Arrays.asList(promotionMock)
    );

    @Test
    void shouldReturnTheDiscountAmountAccordingToTheCalculation() {
        BigDecimal discount = calculation.execute(productMock, promotionMock, 5);
        assertEquals(discount, BigDecimal.valueOf(7.98));
    }

    @Test
    void shouldReturnTrueIfTheTypeOfPromotionIsCorrect() {
        assertTrue(calculation.canHandle(WiremockPromotionType.QTY_BASED_PRICE_OVERRIDE));
        assertFalse(calculation.canHandle(WiremockPromotionType.FLAT_PERCENT));
    }
}