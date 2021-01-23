package com.qikserve.supermarket.adapter.wiremock;

import com.qikserve.supermarket.adapter.port.Product;
import com.qikserve.supermarket.domain.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WiremockAdapter implements Product {
    @Override
    public List<ProductDto> getAll() {
        return null;
    }

    @Override
    public ProductDto getOne(String id) {
        return null;
    }
}
