package com.qikserve.supermarket.adapter.wiremock;

import com.qikserve.supermarket.adapter.port.ProductAdapter;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockProduct;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotion;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotionType;
import com.qikserve.supermarket.adapter.wiremock.strategy.WiremockPromotionStrategy;
import com.qikserve.supermarket.domain.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class WiremockAdapter implements ProductAdapter {
    private WiremockApi api;
    private List<WiremockPromotionStrategy> calculations;

    @Override
    public List<ProductDto> getAll() {
        return api.getAll().stream()
                .map(this::initialProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getOne(String id, Integer quantity) {
        WiremockProduct wiremockProduct = api.getOne(id);
        ProductDto productDto = initialProductDto(wiremockProduct);

        BigDecimal discount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        if (!wiremockProduct.getPromotions().isEmpty()) {
            discount = calculateDiscount(quantity, wiremockProduct);
        }
        return calculate(productDto, discount, quantity);
    }

    private BigDecimal calculateDiscount(Integer quantity, WiremockProduct wiremockProduct) {
        BigDecimal discount = BigDecimal.ZERO;
        for (WiremockPromotionType type: WiremockPromotionType.values()) {
            Optional<WiremockPromotion> promotionOpt = wiremockProduct.getPromotions().stream().filter(promotion -> promotion.getType() == type).findFirst();
            if (promotionOpt.isPresent()) {
                Optional<WiremockPromotionStrategy> strategyOpt = calculations.stream().filter(calc -> calc.canHandle(promotionOpt.get().getType())).findFirst();
                if (strategyOpt.isPresent()) {
                    discount = discount.add(strategyOpt.get().execute(wiremockProduct, promotionOpt.get(), quantity));
                }
            }
        }
        return discount;
    }

    private ProductDto calculate(ProductDto productDto, BigDecimal discount, Integer quantity) {
        BigDecimal price = productDto.getPrice().multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_EVEN);
        productDto.setPrice(price);
        productDto.setDiscount(discount);
        productDto.setTotal(price.subtract(discount));
        return productDto;
    }

    @NotNull
    private ProductDto initialProductDto(WiremockProduct wp) {
        return new ProductDto(
                wp.getId(),
                wp.getName(),
                null,
                wp.getDecimalPrice(),
                null,
                null);
    }
}
