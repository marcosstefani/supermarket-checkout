package com.qikserve.supermarket.adapter.wiremock.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "product")
public class WiremockProduct {
    private String id;
    private String name;
    private BigInteger price;
    @JacksonXmlElementWrapper(localName = "promotions")
    private List<WiremockPromotion> promotions;

    public BigDecimal getDecimalPrice() {
        return new BigDecimal(this.getPrice()).divide(BigDecimal.valueOf(100));
    }
}
