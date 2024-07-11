package com.vpolosov.trainee.merge_xml.validators.input_data_validators;

import com.vpolosov.trainee.merge_xml.aspect.Loggable;
import com.vpolosov.trainee.merge_xml.handler.exception.IncorrectXmlFileException;
import com.vpolosov.trainee.merge_xml.validators.InputDataValidation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
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

@Component
@RequiredArgsConstructor
public class XmlValidator implements InputDataValidation {

    private final Logger loggerForUser;

    @Loggable
    private Validator initValidator(File xsdFile) throws SAXException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI );
        Source schemaFile = new StreamSource(xsdFile);
        Schema schema = factory.newSchema(schemaFile);

        return schema.newValidator();
    }

    @Loggable
    @SneakyThrows
    public void verify(List<File> xmlFiles, List<File> xsdFiles) {
        Validator validator = initValidator(xsdFiles.get(0));

        for (File xml : xmlFiles) {
            try {
                validator.validate(new StreamSource(xml));
            } catch (SAXException e) {
                loggerForUser.error("Файл {} не прошел проверку.", xml.getName());
                throw new IncorrectXmlFileException("Invalid XML file with name: " + xml.getName());
            }
        }
    }
}