package com.Ecommerce.Exceptions;
public class InvalidRequestException extends RuntimeException {
public InvalidRequestException(String message) {
super(message);
}
}