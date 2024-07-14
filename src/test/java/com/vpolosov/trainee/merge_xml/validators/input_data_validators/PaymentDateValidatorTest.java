package com.vpolosov.trainee.merge_xml.validators.input_data_validators;

import com.vpolosov.trainee.merge_xml.config.TimeConfig;
import com.vpolosov.trainee.merge_xml.config.XmlConfig;
import com.vpolosov.trainee.merge_xml.handler.exception.IncorrectDateException;
import com.vpolosov.trainee.merge_xml.service.files.FilesParsing;
import com.vpolosov.trainee.merge_xml.utils.DocumentUtil;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Тестирование валидатора для платёжной даты")
class PaymentDateValidatorTest {

    PaymentDateValidator paymentDateValidator;
    TimeConfig timeConfig;
    DocumentUtil documentUtil;
    FilesParsing filesParsing;

    @BeforeEach
    @SneakyThrows
    void setUp() {
        documentUtil = new DocumentUtil(new XmlConfig().documentBuilder());
        timeConfig = new TimeConfig();
        filesParsing = new FilesParsing();
    }

    @Test
    @DisplayName("Проверка файлов при совпадающих датах")
    void verify_whenValidXmlFiles_thenSuccess() {
        var fixedSameDate = LocalDate.parse("22.02.2024", timeConfig.localDateFormat())
            .atStartOfDay()
            .toInstant(ZoneOffset.UTC);
        var clock = Clock.fixed(fixedSameDate, ZoneOffset.UTC);
        paymentDateValidator = Mockito.spy(new PaymentDateValidator(clock, timeConfig.localDateFormat(), documentUtil));
        Path xmlTestFiles = Paths.get("src/test/java/com/vpolosov/trainee/merge_xml/test_fixtures/Ok");
        String path = xmlTestFiles.toFile().getAbsolutePath();
        List<File> xmlFiles = filesParsing.listXmlFiles(path);
        List<File> xsdFiles = filesParsing.listXsdFiles(path);

        paymentDateValidator.verify(xmlFiles, xsdFiles);

        verify(paymentDateValidator, times(1)).verify(xmlFiles, xsdFiles);
    }

    @Test
    @DisplayName("Исключение при разных датах")
    void verify_whenDateIsDifferent_thenThrowsException() {
        var fixedAnotherDate = LocalDate.parse("23.02.2024", timeConfig.localDateFormat())
            .atStartOfDay()
            .toInstant(ZoneOffset.UTC);
        var clock = Clock.fixed(fixedAnotherDate, ZoneOffset.UTC);
        paymentDateValidator = Mockito.spy(new PaymentDateValidator(clock, timeConfig.localDateFormat(), documentUtil));
        Path xmlTestFiles = Paths.get("src/test/java/com/vpolosov/trainee/merge_xml/test_fixtures/Ok");
        String path = xmlTestFiles.toFile().getAbsolutePath();
        List<File> xmlFiles = filesParsing.listXmlFiles(path);
        List<File> xsdFiles = filesParsing.listXsdFiles(path);

        assertThrows(IncorrectDateException.class, () -> paymentDateValidator.verify(xmlFiles, xsdFiles));

        verify(paymentDateValidator, times(1)).verify(xmlFiles, xsdFiles);
    }
}