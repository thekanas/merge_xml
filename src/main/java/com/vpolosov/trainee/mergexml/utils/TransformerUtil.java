package com.vpolosov.trainee.mergexml.utils;

import com.vpolosov.trainee.mergexml.aspect.Loggable;
import com.vpolosov.trainee.mergexml.handler.exception.TransformerConfigurationRuntimeException;
import com.vpolosov.trainee.mergexml.handler.exception.TransformerRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

/**
 * Вспомогательный класс для работы с {@link Transformer}.
 *
 * @author Maksim Ltivinenko
 */
@Component
@RequiredArgsConstructor
public class TransformerUtil {

    /**
     * Фабрика для создания новых экземпляров {@link Transformer}.
     */
    private final TransformerFactory transformerFactory;

    /**
     * Преобразует источник XML в результат.
     *
     * @param xmlSource    входные данные XML для преобразования.
     * @param outputTarget результат преобразования xmlSourc.
     * @throws TransformerConfigurationRuntimeException когда невозможно создать экземпляр Transformer.
     * @throws TransformerRuntimeException              если в ходе преобразования произошла неисправимая ошибка.
     */
    @Loggable
    public void transform(Source xmlSource, Result outputTarget) {
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            throw new TransformerConfigurationRuntimeException(e);
        }
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        try {
            transformer.transform(xmlSource, outputTarget);
        } catch (TransformerException e) {
            throw new TransformerRuntimeException(e);
        }
    }
}
