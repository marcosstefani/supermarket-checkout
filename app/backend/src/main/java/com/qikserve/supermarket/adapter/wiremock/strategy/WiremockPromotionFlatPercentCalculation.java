package com.qikserve.supermarket.adapter.wiremock.strategy;

import com.qikserve.supermarket.adapter.wiremock.domain.WiremockProduct;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotion;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotionType;
import com.qikserve.supermarket.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class WiremockPromotionFlatPercentCalculation implements WiremockPromotionStrategy {
    @Override
    public boolean canHandle(WiremockPromotionType type) {
        return WiremockPromotionType.FLAT_PERCENT == type;
    }

    @Override
    public ProductDto execute(WiremockProduct product, WiremockPromotion promotion, Integer quantity) {
        BigDecimal price = product.getDecimalPrice().multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal discount = price.multiply(BigDecimal.valueOf(promotion.getAmount())).divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_EVEN);

        return new ProductDto(
                product.getId(),
                product.getName(),
                quantity,
                price,
                discount,
                price.subtract(discount)
        );
    }
}
