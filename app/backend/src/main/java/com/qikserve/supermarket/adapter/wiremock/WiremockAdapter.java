package com.qikserve.supermarket.adapter.wiremock;

import com.qikserve.supermarket.adapter.port.ProductAdapter;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockProduct;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotion;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockPromotionType;
import com.qikserve.supermarket.adapter.wiremock.strategy.WiremockPromotionStrategy;
import com.qikserve.supermarket.domain.dto.ProductDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WiremockAdapter implements ProductAdapter {

    private final WiremockApi api;
    private final List<WiremockPromotionStrategy> calculations;

    public WiremockAdapter(WiremockApi api, List<WiremockPromotionStrategy> calculations) {
        this.api = api;
        this.calculations = calculations;
    }

    @Override
    public List<ProductDto> getAll() {
        return api.getAll().stream()
                .map(this::initialize)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getOne(String id, Integer quantity) {
        WiremockProduct wiremockProduct = api.getOne(id);
        ProductDto productDto = initialize(wiremockProduct);

        BigDecimal discount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        if (!wiremockProduct.getPromotions().isEmpty()) {
            discount = calculateDiscount(quantity, wiremockProduct);
        }
        return resume(productDto, discount, quantity);
    }

    private BigDecimal calculateDiscount(Integer quantity, WiremockProduct wiremockProduct) {
        BigDecimal discount = BigDecimal.ZERO;

        // this loop guarantees the order in which discounts are applied
        for (WiremockPromotionType type: WiremockPromotionType.values()) {
            Optional<WiremockPromotion> promotionOpt = wiremockProduct.getPromotions().stream().filter(promotion -> promotion.getType() == type).findFirst();
            if (promotionOpt.isPresent()) {
                discount = discount.add(calculateDiscount(wiremockProduct, promotionOpt.get(), quantity));
            }
        }
        return discount;
    }

    private BigDecimal calculateDiscount(WiremockProduct product, WiremockPromotion promotion, Integer quantity) {
        return calculations.stream()
                .filter(strategy -> strategy.canHandle(promotion.getType()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Strategy of calculation not implemented!"))
                .execute(product, promotion, quantity);
    }

    private ProductDto resume(ProductDto productDto, BigDecimal discount, Integer quantity) {
        BigDecimal price = productDto.getPrice().multiply(BigDecimal.valueOf(quantity));
        productDto.setQuantity(quantity);
        productDto.setPrice(price.setScale(2, RoundingMode.HALF_EVEN));
        productDto.setDiscount(discount.setScale(2, RoundingMode.HALF_EVEN));
        productDto.setTotal(price.subtract(discount).setScale(2, RoundingMode.HALF_EVEN));
        return productDto;
    }

    @NotNull
    private ProductDto initialize(WiremockProduct wp) {
        return new ProductDto(
                wp.getId(),
                wp.getName(),
                null,
                wp.getDecimalPrice(),
                null,
                null);
    }
}
