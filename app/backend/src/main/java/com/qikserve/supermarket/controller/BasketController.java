package com.qikserve.supermarket.controller;

import com.qikserve.supermarket.domain.dto.CheckoutDto;
import com.qikserve.supermarket.domain.dto.ProductDto;
import com.qikserve.supermarket.service.BasketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/basket")
public class BasketController {
    private final BasketService service;

    public BasketController(BasketService service) {
        this.service = service;
    }

    @PostMapping("/product")
    public ResponseEntity<?> send(@RequestParam String user, @RequestBody ProductDto product) {
        try {
            service.send(user, product.getId(), product.getQuantity());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestParam String user) {
        try {
            CheckoutDto checkout = service.checkout(user);
            return new ResponseEntity<>(checkout, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestParam Integer id) {
        try {
            service.conclude(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
