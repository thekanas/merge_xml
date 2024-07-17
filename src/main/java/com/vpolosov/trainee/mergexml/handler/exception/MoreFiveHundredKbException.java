package com.vpolosov.trainee.mergexml.handler.exception;

/**
 * Исключение выбрасываемое при превышении размера файла в 500 кб.
 *
 * @author Ali Takushinov
 */
public class MoreFiveHundredKbException extends RuntimeException {

    /**
     * Конструктор с одним параметром.
     *
     * @param message текст ошибки.
     */
    public MoreFiveHundredKbException(String message) {
        super(message);
    }

}
