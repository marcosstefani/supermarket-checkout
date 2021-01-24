package com.qikserve.supermarket.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import kotlin.collections.ArrayDeque;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Getter
@Setter
@JacksonXmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckoutDto {
    private Integer orderNumber;
    private BigDecimal price;
    private BigDecimal discount;
    private BigDecimal total;
    private List<ProductDto> products;

    public CheckoutDto(Integer orderNumber) {
        this.orderNumber = orderNumber;
        this.price = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        this.discount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        this.total = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        this.products = new ArrayDeque<>();
    }

    public void addProduct(ProductDto productDto) {
        this.price = this.getPrice().add(productDto.getPrice()).setScale(2, RoundingMode.HALF_EVEN);
        this.discount = this.getDiscount().add(productDto.getDiscount()).setScale(2, RoundingMode.HALF_EVEN);
        this.total = this.getTotal().add(productDto.getTotal()).setScale(2, RoundingMode.HALF_EVEN);
        this.products.add(productDto);
    }
}
