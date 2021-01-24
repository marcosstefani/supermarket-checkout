package com.qikserve.supermarket.adapter.wiremock.strategy;

import com.qikserve.supermarket.adapter.wiremock.domain.WiremockProduct;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotion;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
public class WiremockPromotionFlatPercentCalculation implements WiremockPromotionStrategy {
    @Override
    public boolean canHandle(WiremockPromotionType type) {
        return WiremockPromotionType.FLAT_PERCENT == type;
    }

    @Override
    public BigDecimal execute(WiremockProduct product, WiremockPromotion promotion, Integer quantity) {
        log.info("Applying promotion ".concat(promotion.getType().name()));
        BigDecimal price = product.getDecimalPrice().multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_EVEN);

        return price.multiply(BigDecimal.valueOf(promotion.getAmount())).divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_EVEN);
    }
}
