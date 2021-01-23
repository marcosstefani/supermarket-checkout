package com.qikserve.supermarket.adapter.port;

import com.qikserve.supermarket.domain.dto.ProductDto;

import java.util.List;

public interface ProductAdapter {
    List<ProductDto> getAll();
    ProductDto getOne(String id, Integer quantity);
}
