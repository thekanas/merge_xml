package com.vpolosov.trainee.merge_xml.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Конфигурация для XML.
 *
 * @author Maksim Litvinenko
 */
@Configuration
public class XmlConfig {

    /**
     * Создаёт бин для работы с XML файлом.
     *
     * @return билдер {@link DocumentBuilder}.
     * @throws ParserConfigurationException если невозможно создать DocumentBuilder,
     *                                      удовлетворяющий запрошенной конфигурации.
     */
    @Bean
    public DocumentBuilder documentBuilder() throws ParserConfigurationException {
        return DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder();
    }
}
