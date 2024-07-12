package com.vpolosov.trainee.merge_xml.handler.exception;

/**
 * Исключение выбрасывается, когда не получилось преобразовать в XML документ.
 *
 * @author Maksim Litvinenko
 */
public class XmlParseException extends RuntimeException {

    /**
     * Конструктор с одним параметром.
     *
     * @param message текст ошибки.
     * @param cause причина ошибки.
     */
    public XmlParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
