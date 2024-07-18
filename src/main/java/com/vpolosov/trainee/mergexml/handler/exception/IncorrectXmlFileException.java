package com.vpolosov.trainee.mergexml.handler.exception;

/**
 * Исключение выбрасываемое при некорректном XML файле.
 *
 * @author Ali Takushinov
 */
public class IncorrectXmlFileException extends RuntimeException {

    /**
     * Конструктор с одним параметром.
     *
     * @param message текст ошибки.
     */
    public IncorrectXmlFileException(String message) {
        super(message);
    }
}
