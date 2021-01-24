package com.qikserve.supermarket.adapter.wiremock.strategy;

import com.qikserve.supermarket.adapter.wiremock.domain.WiremockProduct;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotion;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotionType;

import java.math.BigDecimal;

public interface WiremockPromotionStrategy {
    boolean canHandle(WiremockPromotionType type);
    BigDecimal execute(WiremockProduct product, WiremockPromotion promotion, Integer quantity);
}
