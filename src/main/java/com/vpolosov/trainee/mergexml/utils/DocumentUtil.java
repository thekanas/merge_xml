package com.vpolosov.trainee.mergexml.utils;

import com.vpolosov.trainee.mergexml.aspect.Loggable;
import com.vpolosov.trainee.mergexml.handler.exception.XmlParseException;
import com.vpolosov.trainee.mergexml.handler.exception.XmlTagNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.io.IOException;

import static com.vpolosov.trainee.mergexml.utils.Constant.EMPTY_SIZE;
import static com.vpolosov.trainee.mergexml.utils.Constant.FIRST_ELEMENT;

/**
 * Вспомогательный класс для работы с {@link Document}.
 *
 * @author Maksim Litvinenko
 */
@Component
@RequiredArgsConstructor
public class DocumentUtil {

    /**
     * Билдер для преобразования файла в XML документ.
     */
    private final DocumentBuilder documentBuilder;

    /**
     * Парсит файл в XML документ.
     *
     * @param file XML файл.
     * @return XML документ.
     * @throws XmlParseException ошибка преобразования в XML документ.
     */
    @Loggable
    public Document parse(File file) {
        try {
            return documentBuilder.parse(file);
        } catch (SAXException | IOException e) {
            throw new XmlParseException("File not parsing to xml", e);
        }
    }

    /**
     * Возвращает значение элемента из XML документа.
     *
     * @param xmlFile файл XML
     * @param tagName тег по которому будет происходить поиск.
     * @return значение тега из XML документа.
     * @throws XmlTagNotFoundException когда не удалось найти значение по тегу в XML документе.
     */
    @Loggable
    public String getFirstElementByTagName(File xmlFile, String tagName) {
        var document = parse(xmlFile);
        var nodeList = document.getElementsByTagName(tagName);
        if (nodeList.getLength() == EMPTY_SIZE) {
            throw new XmlTagNotFoundException("Xml tag was not found in document");
        }
        return nodeList.item(FIRST_ELEMENT).getTextContent();
    }

    /**
     * Создаёт новый {@link Document}.
     *
     * @return новый созданный {@link Document}.
     */
    @Loggable
    public Document create() {
        return documentBuilder.newDocument();
    }
}
