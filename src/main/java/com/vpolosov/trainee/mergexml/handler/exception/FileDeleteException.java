package com.vpolosov.trainee.mergexml.handler.exception;

/**
 * Исключение выбрасываемое при невозможности удалить файл.
 *
 * @author Maksim Litvinenko
 */
public class FileDeleteException extends RuntimeException {

    /**
     * Конструктор с одним параметром.
     *
     * @param message текст ошибки.
     */
    public FileDeleteException(String message) {
        super(message);
    }
}
