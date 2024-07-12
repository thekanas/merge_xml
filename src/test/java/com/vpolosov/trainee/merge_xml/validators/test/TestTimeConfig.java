package com.vpolosov.trainee.merge_xml.validators.test;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneOffset;

/**
 * Тестовая конфигурация времени.
 *
 * @author Maksim Litvinenko
 */
@TestConfiguration
public class TestTimeConfig {

    /**
     * Создаёт бин определяющий Часы, обеспечивающие доступ к текущему моменту,
     * дате и времени с использованием часового пояса.
     *
     * @return фиксированное время.
     */
    @Bean
    public Clock clock() {
        var instant = LocalDate.parse("22.02.2024")
            .atStartOfDay()
            .toInstant(ZoneOffset.UTC);
        return Clock.fixed(instant, ZoneOffset.UTC);
    }
}
