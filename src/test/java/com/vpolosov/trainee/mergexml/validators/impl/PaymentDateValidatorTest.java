package com.vpolosov.trainee.mergexml.validators.impl;

import com.vpolosov.trainee.mergexml.config.TimeConfig;
import com.vpolosov.trainee.mergexml.config.XmlConfig;
import com.vpolosov.trainee.mergexml.handler.exception.IncorrectDateException;
import com.vpolosov.trainee.mergexml.utils.FileUtil;
import com.vpolosov.trainee.mergexml.utils.DocumentUtil;
import com.vpolosov.trainee.mergexml.validators.PaymentDateValidator;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Тестирование валидатора для платёжной даты")
class PaymentDateValidatorTest {

    PaymentDateValidator paymentDateValidator;
    TimeConfig timeConfig;
    DocumentUtil documentUtil;
    FileUtil fileUtil;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        documentUtil = new DocumentUtil(new XmlConfig().documentBuilder());
        timeConfig = new TimeConfig();
        fileUtil = new FileUtil();
    }

    @Test
    @DisplayName("Проверка файлов при совпадающих датах")
    void verify_whenValidXmlFiles_thenSuccess() {
        var fixedSameDate = LocalDate.parse("22.02.2024", timeConfig.localDateFormat())
            .atStartOfDay()
            .toInstant(ZoneOffset.UTC);
        var clock = Clock.fixed(fixedSameDate, ZoneOffset.UTC);
        paymentDateValidator = Mockito.spy(new PaymentDateValidator(clock, timeConfig.localDateFormat(), documentUtil));
        Path xmlTestFiles = Paths.get("src/test/resources/test_fixtures/Ok");
        String path = xmlTestFiles.toAbsolutePath().toString();
        List<File> xmlFiles = fileUtil.listXml(path);

        for(var xmlFile : xmlFiles) {
            paymentDateValidator.test(xmlFile);
        }

        verify(paymentDateValidator, times(10)).test(any(File.class));
    }

    @Test
    @DisplayName("Исключение при разных датах")
    void verify_whenDateIsDifferent_thenThrowsException() {
        var fixedAnotherDate = LocalDate.parse("23.02.2024", timeConfig.localDateFormat())
            .atStartOfDay()
            .toInstant(ZoneOffset.UTC);
        var clock = Clock.fixed(fixedAnotherDate, ZoneOffset.UTC);
        paymentDateValidator = Mockito.spy(new PaymentDateValidator(clock, timeConfig.localDateFormat(), documentUtil));
        Path xmlTestFiles = Paths.get("src/test/resources/test_fixtures/Ok");
        String path = xmlTestFiles.toAbsolutePath().toString();
        List<File> xmlFiles = fileUtil.listXml(path);

        for (var xmlFile : xmlFiles) {
            assertThrows(IncorrectDateException.class, () -> paymentDateValidator.test(xmlFile));
        }

        verify(paymentDateValidator, times(10)).test(any(File.class));
    }
}