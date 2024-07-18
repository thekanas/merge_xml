package com.vpolosov.trainee.mergexml.validators.impl;

import com.vpolosov.trainee.mergexml.config.XmlConfig;
import com.vpolosov.trainee.mergexml.handler.exception.DifferentPayerException;
import com.vpolosov.trainee.mergexml.utils.FileUtil;
import com.vpolosov.trainee.mergexml.utils.DocumentUtil;
import com.vpolosov.trainee.mergexml.validators.SinglePayerValidator;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;

import static com.vpolosov.trainee.mergexml.utils.XmlTags.PAYER;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Тестирование валидатора для проверки что плательщик один")
class SinglePayerValidatorTest {

    FileUtil fileUtil;
    DocumentUtil documentUtil;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        fileUtil = new FileUtil();
        documentUtil = new DocumentUtil(new XmlConfig().documentBuilder());
    }

    @Test
    @DisplayName("Успешная валидация при одном плательщике")
    void verify_whenSinglePayer_thenSuccess() {
        var singlePayerValidator = spy(new SinglePayerValidator(documentUtil));
        var xmlTestFiles = Paths.get("src/test/resources/test_fixtures/Ok");
        var path = xmlTestFiles.toAbsolutePath().toString();
        var xmlFiles = fileUtil.listXml(path);

        var payer = documentUtil.getFirstElementByTagName(xmlFiles.get(0), PAYER);
        for (var xmlFile : xmlFiles) {
            singlePayerValidator.test(payer, xmlFile);
        }

        verify(singlePayerValidator, times(10)).test(any(String.class), any(File.class));
    }

    @Test
    @DisplayName("Ошибка при разных плательщиках")
    void verify_whenDifferentPayer_thenThrowException() {
        var singlePayerValidator = spy(new SinglePayerValidator(documentUtil));
        var xmlTestFiles = Paths.get("src/test/resources/test_fixtures/sourceXml/DifferentPayer");
        var path = xmlTestFiles.toAbsolutePath().toString();
        var xmlFiles = fileUtil.listXml(path);

        var payer = documentUtil.getFirstElementByTagName(xmlFiles.get(0), PAYER);
        for (int i = 1; i < xmlFiles.size(); i++) {
            int finalI = i;
            assertThrows(DifferentPayerException.class, () -> singlePayerValidator.test(payer, xmlFiles.get(finalI)));
        }

        verify(singlePayerValidator, times(9)).test(any(String.class), any(File.class));
    }
}