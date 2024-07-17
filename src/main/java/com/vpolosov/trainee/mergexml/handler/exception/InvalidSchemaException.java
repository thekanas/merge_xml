package com.vpolosov.trainee.mergexml.handler.exception;

import javax.xml.transform.Source;
import javax.xml.validation.SchemaFactory;

/**
 * Исключение выбрасываемое при невалидном XSD файле для создания схемы.
 *
 * @author Maksim Litvinenko
 * @see SchemaFactory#newSchema(Source)
 */
public class InvalidSchemaException extends RuntimeException {

    /**
     * Конструктор с одним параметром.
     *
     * @param cause причина ошибки.
     */
    public InvalidSchemaException(Throwable cause) {
        super(cause);
    }
}
