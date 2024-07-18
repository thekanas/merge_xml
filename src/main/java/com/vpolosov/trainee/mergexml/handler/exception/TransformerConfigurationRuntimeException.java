package com.vpolosov.trainee.mergexml.handler.exception;

import javax.xml.transform.TransformerConfigurationException;

/**
 * Исключение выбрасывается при ошибке конфигурации.
 *
 * @author Maksim Litvinenko
 * @see TransformerConfigurationException
 */
public class TransformerConfigurationRuntimeException extends RuntimeException {

    /**
     * Конструктор с одним параметром.
     *
     * @param cause причина ошибки.
     */
    public TransformerConfigurationRuntimeException(Throwable cause) {
        super(cause);
    }
}
