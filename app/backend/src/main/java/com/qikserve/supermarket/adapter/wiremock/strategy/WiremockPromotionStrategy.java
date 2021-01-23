package com.qikserve.supermarket.adapter.wiremock.strategy;

import com.qikserve.supermarket.adapter.wiremock.domain.WiremockProduct;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotion;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotionType;
import com.qikserve.supermarket.domain.dto.ProductDto;

public interface WiremockPromotionStrategy {
    boolean canHandle(WiremockPromotionType type);
    ProductDto execute(WiremockProduct product, WiremockPromotion promotion, Integer quantity);
}
