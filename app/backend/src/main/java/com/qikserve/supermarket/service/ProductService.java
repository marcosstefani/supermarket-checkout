package com.qikserve.supermarket.service;

import com.qikserve.supermarket.adapter.port.ProductAdapter;
import com.qikserve.supermarket.domain.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductAdapter productAdapter;

    public ProductService(ProductAdapter productAdapter) {
        this.productAdapter = productAdapter;
    }

    public List<ProductDto> getAll() {
        return productAdapter.getAll();
    }
}
