package com.vpolosov.trainee.mergexml.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация логирования.
 * <p>
 * Установить другие уровни логирования можно в файле {@code logback-spring.xml}.
 *
 * @author Ali Takushinov
 */
@Configuration
public class LoggerConfiguration {

    /**
     * Создаёт бин логирования для пользователя.
     *
     * @return логирование для пользователя.
     */
    @Bean
    public Logger loggerForUser() {
        return LoggerFactory.getLogger("loggerForUser");
    }

    /**
     * Создаёт бин для логирования дубликатов.
     *
     * @return логирование для дубликатов.
     */
    @Bean
    public Logger loggerForDouble() {
        return LoggerFactory.getLogger("loggerForDouble");
    }
}