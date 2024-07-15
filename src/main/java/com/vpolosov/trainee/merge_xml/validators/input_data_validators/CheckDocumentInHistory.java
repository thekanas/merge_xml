package com.vpolosov.trainee.merge_xml.validators.input_data_validators;

import com.vpolosov.trainee.merge_xml.handler.exception.DuplicationProcessingException;
import com.vpolosov.trainee.merge_xml.handler.exception.InvalidCurrencyCodeValueException;
import com.vpolosov.trainee.merge_xml.model.History;
import com.vpolosov.trainee.merge_xml.service.HistoryService;
import com.vpolosov.trainee.merge_xml.service.specification.HistorySpecifications;
import com.vpolosov.trainee.merge_xml.utils.DocumentUtil;
import com.vpolosov.trainee.merge_xml.validators.InputDataValidation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpolosov.trainee.merge_xml.utils.XmlTags.DOCREF;
import static com.vpolosov.trainee.merge_xml.utils.XmlTags.CURRCODE;

@Component
@RequiredArgsConstructor
public class CheckDocumentInHistory implements InputDataValidation {

    public static final String VALID_CURRCODE = "810";

    private final HistoryService historyService;
    private final Logger loggerForDouble;
    private final DocumentUtil documentUtil;

    @SneakyThrows
    public void verify(List<File> xmlFiles, List<File> xsdFiles) {
        Map<String, String> docRefAndFileNameFromHistory = getLoadDateToBDFromHistory(xmlFiles);
        if (!docRefAndFileNameFromHistory.isEmpty()) {
            StringBuilder message = new StringBuilder();
            for (Map.Entry<String, String> entry : docRefAndFileNameFromHistory.entrySet()) {
                message.append("В файле ").append(entry.getValue()).append(" найден платеж, который уже был загружен ранее ")
                        .append(entry.getKey()).append(";");
            }
            throw new DuplicationProcessingException(message.toString());
        }
    }

    private Map<String, String> getLoadDateToBDFromHistory(List<File> xmlFiles) throws IOException, SAXException, ParserConfigurationException {
        Map<String, String> docRefsAndFileNames = new HashMap<>();
        Specification<History> spec = Specification.where(null);
        for (File xmlFile : xmlFiles) {

            String currCode = documentUtil.getFirstElementByTagName(xmlFile, CURRCODE);
            if (!currCode.equals(VALID_CURRCODE)) {
                throw new InvalidCurrencyCodeValueException("Допустимое значение кода валюты " + VALID_CURRCODE);
            }

            String docRef = documentUtil.getFirstElementByTagName(xmlFile, DOCREF);
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
