package com.vpolosov.trainee.mergexml.handler.exception;

/**
 * Исключение выбрасываемое когда количество файлов не соответствует 10.
 *
 * @author ALi Takushinov
 */
public class NotExactlyTenFilesException extends RuntimeException {

    /**
     * Конструктор с одним параметром.
     *
     * @param message текст ошибки.
     */
    public NotExactlyTenFilesException(String message) {
        super(message);
    }
}
