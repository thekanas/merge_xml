package com.vpolosov.trainee.mergexml.validators;

import com.vpolosov.trainee.mergexml.aspect.Loggable;
import com.vpolosov.trainee.mergexml.handler.exception.InvalidSchemaException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * Объединяет все валидаторы XML документа в один класс.
 *
 * @author Maksim Litvinenko
 */
@Component
@RequiredArgsConstructor
public class Validators {

    /**
     * Проверка файла на размер.
     */
    @Getter
    @Accessors(fluent = true)
    private final CheckFileSize checkFileSize;

    /**
     * Список валидаторов с одним параметром для проверки XML файла.
     */
    private final List<Predicate<File>> singleParamValidators;

    /**
     * Валидатор для проверки одного плательщика.
     */
    private final BiPredicate<String, File> paymentValidator;

    /**
     * Валидатор для проверки XML файла по XSD схеме.
     */
    private final BiPredicate<File, Validator> xmlValidator;

    /**
     * Прогоняет XML документ по всем валидаторам.
     *
     * @param xmlFile   документ для объединения платежа.
     * @param validator проверяет схему документа платежа.
     * @param payer     плательщик.
     * @return true если все проверки прошли успешно иначе выбрасывает соответствующее исключение.
     */
    @Loggable
    public boolean validate(File xmlFile, Validator validator, String payer) {
        return xmlValidator.test(xmlFile, validator)
            && singleParamValidators.stream().allMatch(predicate -> predicate.test(xmlFile))
            && paymentValidator.test(payer, xmlFile);
    }

    /**
     * Создать {@link Validator} по схеме XSD файла.
     *
     * @param xsdFile схема XSD файла.
     * @return валидатор для проверки XML файлов.
     * @throws InvalidSchemaException когда не удалось создать схему по XSD файлу.
     */
    @Loggable
    public Validator createValidator(File xsdFile) {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaFile = new StreamSource(xsdFile);
        Schema schema;
        try {
            schema = factory.newSchema(schemaFile);
        } catch (SAXException e) {
            throw new InvalidSchemaException(e);
        }
        return schema.newValidator();
    }
}
