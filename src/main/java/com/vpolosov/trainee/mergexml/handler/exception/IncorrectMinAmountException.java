package com.vpolosov.trainee.mergexml.handler.exception;

/**
 * Исключение выбрасываемое при некорректной минимальной сумме.
 *
 * @author Andrei Stalybka
 */
public class IncorrectMinAmountException extends RuntimeException {

    /**
     * Конструктор с одним параметром.
     *
     * @param message текст ошибки.
     */
    public IncorrectMinAmountException(String message) {
        super(message);
    }
}
