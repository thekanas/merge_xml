package com.vpolosov.trainee.merge_xml.validators.input_data_validators;

import com.vpolosov.trainee.merge_xml.config.XmlConfig;
import com.vpolosov.trainee.merge_xml.handler.exception.DifferentPayerException;
import com.vpolosov.trainee.merge_xml.service.files.FilesParsing;
import com.vpolosov.trainee.merge_xml.utils.DocumentUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Тестирование валидатора для проверки что плательщик один")
class SinglePayerValidatorTest {

    FilesParsing filesParsing;
    DocumentUtil documentUtil;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        filesParsing = new FilesParsing();
        documentUtil = new DocumentUtil(new XmlConfig().documentBuilder());
    }

    @Test
    @DisplayName("Успешная валидация при одном плательщике")
    void verify_whenSinglePayer_thenSuccess() {
        var singlePayerValidator = spy(new SinglePayerValidator(documentUtil));
        var xmlTestFiles = Paths.get("src/test/java/com/vpolosov/trainee/merge_xml/test_fixtures/Ok");
        var path = xmlTestFiles.toFile().getAbsolutePath();
        var xmlFiles = filesParsing.listXmlFiles(path);
        var xsdFiles = filesParsing.listXsdFiles(path);

        singlePayerValidator.verify(xmlFiles, xsdFiles);

        verify(singlePayerValidator, times(1)).verify(xmlFiles, xsdFiles);
    }

    @Test
    @DisplayName("Ошибка при разных плательщиках")
    void verify_whenDifferentPayer_thenThrowException() {
        var singlePayerValidator = spy(new SinglePayerValidator(documentUtil));
        var xmlTestFiles = Paths.get("src/test/java/com/vpolosov/trainee/merge_xml/test_fixtures/sourceXml/DifferentPayer");
        var path = xmlTestFiles.toFile().getAbsolutePath();
        var xmlFiles = filesParsing.listXmlFiles(path);
        var xsdFiles = filesParsing.listXsdFiles(path);

        assertThrows(DifferentPayerException.class, () -> singlePayerValidator.verify(xmlFiles, xsdFiles));

        verify(singlePayerValidator, times(1)).verify(xmlFiles, xsdFiles);
    }
}