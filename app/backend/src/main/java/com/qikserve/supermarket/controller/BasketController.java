package com.qikserve.supermarket.controller;

import com.qikserve.supermarket.domain.dto.ProductDto;
import com.qikserve.supermarket.service.BasketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/basket")
public class BasketController {
    private final BasketService service;

    public BasketController(BasketService service) {
        this.service = service;
    }

    @PostMapping("/products")
    public ResponseEntity<?> send(@RequestParam String user, @RequestBody List<ProductDto> basket) {
        try {
            basket.forEach(productDto -> service.send(user, productDto.getId(), productDto.getQuantity()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/product")
    public ResponseEntity<?> send(@RequestParam String user, @RequestBody ProductDto product) {
        try {
            service.send(user, product.getId(), product.getQuantity());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
