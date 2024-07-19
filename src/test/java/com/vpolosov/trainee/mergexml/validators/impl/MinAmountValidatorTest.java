package com.vpolosov.trainee.mergexml.validators.impl;

import com.vpolosov.trainee.mergexml.config.XmlConfig;
import com.vpolosov.trainee.mergexml.handler.exception.IncorrectMinAmountException;
import com.vpolosov.trainee.mergexml.handler.exception.IncorrectValueException;
import com.vpolosov.trainee.mergexml.utils.DocumentUtil;
import com.vpolosov.trainee.mergexml.utils.FileUtil;
import com.vpolosov.trainee.mergexml.validators.MinAmountValidator;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Тестирование валидатора для проверки минимальной суммы платежа")
class MinAmountValidatorTest {

    private FileUtil fileUtil;
    private DocumentUtil documentUtil;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        fileUtil = new FileUtil();
        documentUtil = new DocumentUtil(new XmlConfig().documentBuilder());
    }

    @Test
    @DisplayName("Успешная валидация при корректной минимальной сумме платежа")
    void verify_whenMinAmountIsValid_thenSuccess() {
        var minAmountValidator = Mockito.spy(new MinAmountValidator(documentUtil));
        var xmlTestFiles = Paths.get("src/test/resources/test_fixtures/Ok");
        var path = xmlTestFiles.toAbsolutePath().toString();
        var xmlFiles = fileUtil.listXml(path);

        for (var xmlFile : xmlFiles) {
            minAmountValidator.test(xmlFile);
        }

        verify(minAmountValidator, times(10)).test(any(File.class));
    }

    @Test
    @DisplayName("Исключение при некорректной минимальной сумме платежа")
    void verify_whenMinAmountIsNotValid_thenThrowException() {
        var minAmountValidator = spy(new MinAmountValidator(documentUtil));
        var xmlTestFiles = Paths.get("src/test/resources/test_fixtures/sourceXml/MinAmount");
        var path = xmlTestFiles.toAbsolutePath().toString();
        var xmlFiles = fileUtil.listXml(path);

        assertThrows(IncorrectMinAmountException.class, () -> xmlFiles.forEach(minAmountValidator::test));
    }

    @Test
    @DisplayName("Исключение при невалидном значении платежа")
    void verify_whenAmountIsNotValid_thenThrowException() {
        var minAmountValidator = spy(new MinAmountValidator(documentUtil));
        var xmlTestFiles = Paths.get("src/test/resources/test_fixtures/sourceXml/NotCorrectAmount");
        var path = xmlTestFiles.toAbsolutePath().toString();
        var xmlFiles = fileUtil.listXml(path);

        assertThrows(IncorrectValueException.class, () -> xmlFiles.forEach(minAmountValidator::test));
    }
}