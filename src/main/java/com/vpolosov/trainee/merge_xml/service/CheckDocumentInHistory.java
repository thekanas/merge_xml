package com.vpolosov.trainee.merge_xml.service;

import com.vpolosov.trainee.merge_xml.model.History;
import com.vpolosov.trainee.merge_xml.service.specification.HistorySpecifications;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CheckDocumentInHistory {

    private final HistoryService historyService;
    private final Logger loggerForDouble;
    private static final String DOCREF_TAG = "DOCREF";

    public Map<String, String> getLoadDateToBDFromHistory(List<File> xmlFiles) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder();
        Map<String, String> docRefsAndFileNames = new HashMap<>();
        Specification<History> spec = Specification.where(null);
        for (File xmlFile : xmlFiles) {
            Document document = documentBuilder.parse(xmlFile);
            NodeList elementsByTagName = document.getElementsByTagName(DOCREF_TAG);
            String docRef = elementsByTagName.item(0).getTextContent();
            docRefsAndFileNames.put(docRef, xmlFile.getName());
            spec = spec.or(HistorySpecifications.docRefEquals(docRef));
        }
        List<History> histories = historyService.getHistoryListBySpec(spec);
        return histories.stream()
                .peek(history -> loggerForDouble.info("Документ с номером " + history.getDocRef()
                        + " из файла " + docRefsAndFileNames.get(history.getDocRef())
                        + " уже был загружен " + history.getDateTimeUpload()))
                .collect(Collectors.toMap(history -> history.getDateTimeUpload().toString(), history -> docRefsAndFileNames.get(history.getDocRef())));
    }
}
