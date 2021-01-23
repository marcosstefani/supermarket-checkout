package com.qikserve.supermarket.adapter.wiremock.strategy;

import com.qikserve.supermarket.adapter.wiremock.domain.WiremockProduct;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotion;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotionType;
import com.qikserve.supermarket.domain.dto.ProductDto;

import java.math.BigDecimal;

public class WiremockPromotionByXGetYFreeCalculation implements WiremockPromotionStrategy {
    @Override
    public boolean canHandle(WiremockPromotionType type) {
        return WiremockPromotionType.BUY_X_GET_Y_FREE == type;
    }

    @Override
    public ProductDto execute(WiremockProduct product, WiremockPromotion promotion, Integer quantity) {
        Integer reminder = quantity % promotion.getRequired_qty();
        Integer quotient = quantity / promotion.getRequired_qty();
        return new ProductDto(
                product.getId(),
                product.getName(),
                quantity,
                product.getDecimalPrice(),
                product.getDecimalPrice().multiply(BigDecimal.valueOf(reminder + quotient))
        );
    }
}
