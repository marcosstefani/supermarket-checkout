package com.qikserve.supermarket.exception;

public class BasketNotFoundException extends RuntimeException {
  public BasketNotFoundException(String message) {
    super(message);
  }
}
