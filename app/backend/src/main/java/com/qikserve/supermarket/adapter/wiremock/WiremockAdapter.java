package com.qikserve.supermarket.adapter.wiremock;

import com.qikserve.supermarket.adapter.port.ProductAdapter;
import com.qikserve.supermarket.domain.dto.ProductDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WiremockAdapter implements ProductAdapter {
    private WiremockApi api;

    @Override
    public List<ProductDto> getAll() {
        return api.getAll().stream()
                .map(wp -> new ProductDto(
                        wp.getId(),
                        wp.getName(),
                        null,
                        wp.getDecimalPrice(),
                        null,
                        null))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getOne(String id) {
        return null;
    }
}
