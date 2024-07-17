package com.vpolosov.trainee.mergexml.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

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

    /**
     * Фабрика для создания объектов типа {@link Transformer}.
     *
     * @return новую фабрику с параметрами по умолчанию.
     */
    @Bean
    public TransformerFactory transformerFactory() {
        return TransformerFactory.newDefaultInstance();
    }
}
