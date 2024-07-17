package com.vpolosov.trainee.mergexml.handler.exception;

/**
 * Исключение выбрасываемое при невалидном номере валюты.
 *
 * @author Daria Koval
 */
public class InvalidCurrencyCodeValueException extends RuntimeException {

    /**
     * Конструктор с одним параметром.
     *
     * @param message текст ошибки.
     */
    public InvalidCurrencyCodeValueException(String message) {
        super(message);
    }
}
