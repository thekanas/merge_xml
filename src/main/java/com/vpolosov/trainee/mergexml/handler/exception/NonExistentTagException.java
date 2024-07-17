package com.vpolosov.trainee.mergexml.handler.exception;

/**
 * Исключение выбрасываемое при отсутствии тега.
 *
 * @author Andrei Stalybka
 */
public class NonExistentTagException extends RuntimeException {

    /**
     * Конструктор с одним параметром.
     *
     * @param message текст ошибки.
     */
    public NonExistentTagException(String message) {
        super(message);
    }
}
