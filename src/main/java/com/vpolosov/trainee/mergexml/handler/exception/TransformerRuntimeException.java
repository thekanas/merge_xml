package com.vpolosov.trainee.mergexml.handler.exception;

import javax.xml.transform.TransformerException;

/**
 * Выбрасывает исключение в процессе преобразования.
 *
 * @author Maksim Litvinenko
 * @see TransformerException
 */
public class TransformerRuntimeException extends RuntimeException {

    /**
     * Конструктор с одним параметром.
     *
     * @param cause причина ошибки.
     */
    public TransformerRuntimeException(Throwable cause) {
        super(cause);
    }
}
