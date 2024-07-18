package com.vpolosov.trainee.mergexml.validators;

import com.vpolosov.trainee.mergexml.aspect.Loggable;
import com.vpolosov.trainee.mergexml.handler.exception.IncorrectXmlFileException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.function.BiPredicate;

/**
 * Валидатор XML документа по XSD схеме.
 *
 * @author Ali Takushinov
 * @author Andrei Stalybka
 * @author Maksim Litvinenko
 */
@Component
@RequiredArgsConstructor
public class XmlValidator implements BiPredicate<File, Validator> {

    /**
     * Логирование для пользователя.
     */
    private final Logger loggerForUser;

    /**
     * {@inheritDoc}
     *
     * @throws IncorrectXmlFileException если файл не прошёл проверку XSD схемы.
     */
    @Loggable
    @Override
    public boolean test(File xmlFile, Validator validator) {
        try {
            validator.validate(new StreamSource(xmlFile));
        } catch (SAXException | IOException e) {
            loggerForUser.error("Файл {} не прошел проверку.", xmlFile.getName());
            throw new IncorrectXmlFileException("Invalid XML file with name: " + xmlFile.getName());
        }
        return true;
    }
}