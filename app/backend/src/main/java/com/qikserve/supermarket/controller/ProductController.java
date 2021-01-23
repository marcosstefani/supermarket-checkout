package com.qikserve.supermarket.controller;

import com.qikserve.supermarket.adapter.wiremock.WiremockApi;
import com.qikserve.supermarket.adapter.wiremock.domain.WiremockProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private WiremockApi wiremockApi;

    public List<WiremockProduct> wiremockProducts() {
        return wiremockApi.getAll();
    }
}
