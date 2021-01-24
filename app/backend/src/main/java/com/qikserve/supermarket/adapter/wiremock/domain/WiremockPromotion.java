package com.qikserve.supermarket.adapter.wiremock.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WiremockPromotion {
    private String id;
    private WiremockPromotionType type;
    private Integer required_qty;
    private BigInteger price;
    private Integer free_qty;
    private Integer amount;

    public BigDecimal getDecimalPrice() {
        return new BigDecimal(this.getPrice()).divide(BigDecimal.valueOf(100));
    }
}
