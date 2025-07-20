package com.example.ecom.exception;

public class ProductOutOfStockException extends RuntimeException{
    public ProductOutOfStockException(String message){
        super(message);
    }

}
