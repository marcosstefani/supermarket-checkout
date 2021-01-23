package com.qikserve.supermarket.adapter.wiremock.strategy;

import com.qikserve.supermarket.adapter.wiremock.domain.WiremockProduct;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotion;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotionType;
import com.qikserve.supermarket.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class WiremockPromotionByXGetYFreeCalculation implements WiremockPromotionStrategy {
    @Override
    public boolean canHandle(WiremockPromotionType type) {
        return WiremockPromotionType.BUY_X_GET_Y_FREE == type;
    }

    @Override
    public ProductDto execute(WiremockProduct product, WiremockPromotion promotion, Integer quantity) {
        Integer reminder = quantity % promotion.getRequiredQty();
        Integer quotient = quantity / promotion.getRequiredQty();
        BigDecimal price = product.getDecimalPrice().multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal total = product.getDecimalPrice().multiply(BigDecimal.valueOf(reminder + quotient)).setScale(2, RoundingMode.HALF_EVEN);

        return new ProductDto(
                product.getId(),
                product.getName(),
                quantity,
                price,
                price.subtract(total),
                total
        );
    }
}
