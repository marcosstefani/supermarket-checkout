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
public class WiremockPromotionQuantityBasedPriceOverrideCalculation implements WiremockPromotionStrategy {
    @Override
    public boolean canHandle(WiremockPromotionType type) {
        return WiremockPromotionType.QTY_BASED_PRICE_OVERRIDE == type;
    }

    @Override
    public BigDecimal execute(WiremockProduct product, WiremockPromotion promotion, Integer quantity) {
        log.info("Applying promotion ".concat(promotion.getType().name()));
        Integer reminder = quantity % promotion.getRequired_qty();
        Integer quotient = quantity / promotion.getRequired_qty();
        BigDecimal promotionValue = BigDecimal.valueOf(quotient).multiply(promotion.getDecimalPrice()).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal normalValue = BigDecimal.valueOf(reminder).multiply(product.getDecimalPrice()).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal price = product.getDecimalPrice().multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal total = promotionValue.add(normalValue).setScale(2, RoundingMode.HALF_EVEN);

        return price.subtract(total);
    }
}
