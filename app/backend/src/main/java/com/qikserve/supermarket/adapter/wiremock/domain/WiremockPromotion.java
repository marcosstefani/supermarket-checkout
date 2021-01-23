package com.qikserve.supermarket.adapter.wiremock.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("required_qty")
    private Integer requiredQty;
    private BigInteger price;
    @JsonProperty("free_qty")
    private Integer freeQty;
    private Integer amount;

    public BigDecimal getDecimalPrice() {
        return new BigDecimal(this.getPrice()).divide(BigDecimal.valueOf(100));
    }
}
