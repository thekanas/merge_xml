package com.vpolosov.trainee.merge_xml.handler.exception;

/**
 * Исключение выбрасываемое при ненайденном теге в XML документе.
 *
 * @author Maksim Litvinenko
 */
public class XmlTagNotFoundException extends RuntimeException {

    /**
     * Конструктор с одним параметром.
     *
     * @param message текст ошибки.
     */
    public XmlTagNotFoundException(String message) {
        super(message);
    }
}
