package com.vpolosov.trainee.mergexml.handler.exception;

/**
 * Исключение выбрасываемое при некорректной дате.
 *
 * @author Maksim Litvinenko
 */
public class IncorrectDateException extends RuntimeException {

    /**
     * Конструктор с одним параметром.
     *
     * @param message текст ошибки.
     */
    public IncorrectDateException(String message) {
        super(message);
    }
}
