package com.vpolosov.trainee.merge_xml.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

/**
 * Конфигурация времени.
 *
 * @author Maksim Litvinenko
 */
@Configuration
public class TimeConfig {

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
