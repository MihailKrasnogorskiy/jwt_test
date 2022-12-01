package ru.mikser256.jwt_test.exceptions;

/**
 * исключение выбрасываемое при невозможности найти какой-то объект
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
