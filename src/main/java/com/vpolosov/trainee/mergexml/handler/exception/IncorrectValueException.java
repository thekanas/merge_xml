package com.vpolosov.trainee.mergexml.handler.exception;

/**
 * Исключение выбрасываемое при невалидном значении тега.
 *
 * @author Andrei Stalybka
 */
public class IncorrectValueException extends RuntimeException {

    /**
     * Конструктор с одним параметром.
     *
     * @param message текст ошибки.
     */
    public IncorrectValueException(String message) {
        super(message);
    }
}
