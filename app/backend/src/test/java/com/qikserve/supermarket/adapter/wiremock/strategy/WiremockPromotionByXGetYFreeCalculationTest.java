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
class WiremockPromotionByXGetYFreeCalculationTest {
    @Spy
    private WiremockPromotionByXGetYFreeCalculation calculation;

    private WiremockPromotion promotionMock = new WiremockPromotion(
            "ZRAwbsO2qM",
            WiremockPromotionType.BUY_X_GET_Y_FREE,
            2,
            null,
            1,
            null
    );

    private WiremockProduct productMock = new WiremockProduct(
            "PWWe3w1SDU",
            "Amazing Burger!",
            BigInteger.valueOf(999),
            Arrays.asList(promotionMock)
    );

    @Test
    void ShouldReturnTheDiscountAmountAccordingToTheCalculation() {
        BigDecimal discount = calculation.execute(productMock, promotionMock, 5);
        assertEquals(discount, BigDecimal.valueOf(19.98));
    }

    @Test
    void ShouldReturnTrueIfTheTypeOfPromotionIsCorrect() {
        assertTrue(calculation.canHandle(WiremockPromotionType.BUY_X_GET_Y_FREE));
        assertFalse(calculation.canHandle(WiremockPromotionType.FLAT_PERCENT));
    }
}