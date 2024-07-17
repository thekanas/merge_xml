package com.vpolosov.trainee.mergexml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Точка запуска приложения.
 *
 * @author Ali Takushinov
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class MergeXmlApplication {

    /**
     * Запускает приложение.
     *
     * @param args аргументы командной строки.
     */
    public static void main(String[] args) {
        SpringApplication.run(MergeXmlApplication.class, args);
    }

}
