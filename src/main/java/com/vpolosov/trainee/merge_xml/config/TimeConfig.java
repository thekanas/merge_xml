package com.vpolosov.trainee.merge_xml.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.format.DateTimeFormatter;

/**
 * Конфигурация времени.
 *
 * @author Maksim Litvinenko
 */
@Configuration
public class TimeConfig {

    /**
     * Создаёт бин для формата даты.
     *
     * @return возвращает парсер для даты.
     */
    @Bean
    public DateTimeFormatter localDateFormat() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }

    /**
     * Создаёт бин определяющий Часы, обеспечивающие доступ к текущему моменту,
     * дате и времени с использованием часового пояса.
     *
     * @return системные часы во временной зоне {@code UTC}.
     */
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
