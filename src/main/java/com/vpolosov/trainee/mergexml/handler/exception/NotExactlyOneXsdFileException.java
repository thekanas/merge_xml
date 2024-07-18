package com.vpolosov.trainee.mergexml.handler.exception;

/**
 * Исключение выбрасываемо когда XSD файл не один.
 *
 * @author Ali Takushinov
 */
public class NotExactlyOneXsdFileException extends RuntimeException {

    /**
     * Конструктор с одним параметром.
     *
     * @param message текст ошибки.
     */
    public NotExactlyOneXsdFileException(String message) {
        super(message);
    }
}
