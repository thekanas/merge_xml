package com.vpolosov.trainee.mergexml.handler.exception;

/**
 * Исключение выбрасываемое при ненайденном файле.
 *
 * @author Maksim Litvinenko
 */
public class FileNotFoundException extends RuntimeException {

    /**
     * Конструктор с одним параметром.
     *
     * @param message текст ошибки.
     */
    public FileNotFoundException(String message) {
        super(message);
    }
}
