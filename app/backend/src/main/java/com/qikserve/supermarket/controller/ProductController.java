package com.qikserve.supermarket.controller;

import com.qikserve.supermarket.domain.dto.ProductDto;
import com.qikserve.supermarket.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDto> products() {
        return productService.getAll();
    }

//    @GetMapping(value = "wiremock", produces = MediaType.APPLICATION_JSON_VALUE)
//    public WiremockProduct wiremockProduct(@RequestParam("id") String id) {
//        return wiremockApi.getOne(id);
//    }
}
