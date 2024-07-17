package com.vpolosov.trainee.mergexml.handler.exception;

/**
 * Исключение выбрасываемое при дублированных данных.
 *
 * @author Ali Takushinov
 */
public class DuplicationProcessingException extends RuntimeException {

    /**
     * Конструктор с одним параметром.
     *
     * @param message текст ошибки.
     */
    public DuplicationProcessingException(String message) {
        super(message);
    }
}
